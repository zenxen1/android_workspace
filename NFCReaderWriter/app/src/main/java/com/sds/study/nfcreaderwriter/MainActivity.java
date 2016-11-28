package com.sds.study.nfcreaderwriter;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    Switch my_swich;
    TextView txt_mode;
    EditText edit_writer;
    TextView txt_read;
    String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getName();
        setContentView(R.layout.activity_main);

        Log.d(TAG,"태그 발견시 액티비티는 "+this);//tag 발견시 액티비티가 새로운 주소로 계속뜬다

        my_swich = (Switch)findViewById(R.id.my_swich);
        txt_mode=(TextView)findViewById(R.id.txt_mode);
        edit_writer=(EditText)findViewById(R.id.edit_writer);
        txt_read = (TextView)findViewById(R.id.txt_read);

        my_swich.setOnCheckedChangeListener(this);

        /*Ndef 형식의 태그가 발견되면, 시스템이 필터에 의해 우리에게 그 정보를 전달해 준다*/
        Intent intent = getIntent();
        getInfo(intent);
    }

    /*----------------------------------------------------------------------------------
NFC 태그에 데이터 읽기!!
* ----------------------------------------------------------------------------------*/
    public void getInfo(Intent intent){
        String action = intent.getAction();

        //지금 전달된 인텐트가 표준 nfc인 경우..
        if(action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)){
            Parcelable[] parcelables=intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            //인텐트에 넣기 위해 parcelable화된 NdefMessage를 다시 복원하자
            NdefMessage[] messages = new NdefMessage[parcelables.length];
            for(int i=0;i<messages.length;i++){
                messages[i]=(NdefMessage)parcelables[i];
                //하나의 메세지는 복수개의 레코드를 보유할 수 있다..
                NdefRecord[] records = messages[i].getRecords();
                for(int a=0;a<records.length;a++){
                    String data = new String((records[a].getPayload()));
                    txt_read.append(data+"\n");
                }
            }
        }
    }

    @Override
    /*액티비티는 변함이 없고, 인텐트만 갱신되었을때....manifast에서 android:launchMode="singleTask" 추가됨에따라 새로운
    * 액티비티가 생기지 않으므로...이걸 사용한다...*/
    protected void onNewIntent(Intent intent) {
        Log.d(TAG,"onNewIntent 호출");
        if(txt_mode.getText().toString().equals("읽기모드")) {
            getInfo(intent);
        }else {
            String msg = edit_writer.getText().toString();
            Tag tag =intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{
                    createTextRecord(msg,true)
            });
            writerTag(tag,ndefMessage);
            Toast.makeText(this, "작성완료", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            txt_mode.setText("쓰기모드");
        }else{
            txt_mode.setText("읽기모드");
        }

        Toast.makeText(this, "스위치 바꿨어?", Toast.LENGTH_SHORT).show();
    }


    /*----------------------------------------------------------------------------------
    NFC 태그에 데이터 쓰기!!
    * ----------------------------------------------------------------------------------*/
    public NdefRecord createTextRecord(String payload, boolean encodeInUtf8) {
        byte[] langBytes = Locale.getDefault().getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = payload.getBytes(utfEncoding);
        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT, new byte[0], data);
        return record;
    }

    public void writerTag(Tag tag, NdefMessage message){
        //작성한 내용이 있을 경우, 태그를 대기 하도록 한다...
        if(tag!=null){ //대상 태그가 있다면...

            try {
                Ndef ndef = Ndef.get(tag);
                if(ndef ==null){
                    //기존 데이터 포맷하고 넣자!!!
                    NdefFormatable form = NdefFormatable.get(tag);
                    if(tag!=null){
                        form.connect();
                        form.format(message);//메세지포멧....
                        form.close();
                    }
                }else{
                    ndef.connect();
                    ndef.writeNdefMessage(message);//메세지 입력
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FormatException e) {
                e.printStackTrace();
            }

        }

    }
    public void btnClick(View view){
    }
}
