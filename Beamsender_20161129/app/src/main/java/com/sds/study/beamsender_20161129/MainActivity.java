package com.sds.study.beamsender_20161129;

/*nfc는 반드시 태그형태로만 존재하는것은 아니기 때문에 스간 */

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.nio.charset.Charset;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {
    NfcAdapter nfcAdapter;//nfc칩을 표현한 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNfcSupport();

        //어댑터와 리스너와 연결
        nfcAdapter.setNdefPushMessageCallback(this,this);
    }
    /*--------------------------------------------------------------------------------------
    NFC 지원여부 체크
    -----------------------------------------------------------------------------------------
    * */
    public void checkNfcSupport(){
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter==null){
            showAlert("안내","nfc를 지원하지 않습니다.");
            finish();
            return;
        }
    }

    /*개발자는 받는쪽 디바이스에 어떤 메세지를 보낼지 아래의 메서드를 재정의 하면 된다*/
    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        NdefMessage message = new NdefMessage(new NdefRecord[]{
                createTextRecord("아두이노",true)
        });
        return message;
    }

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

    public void showAlert(String title, String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title).setMessage(msg).show();

    }
}
