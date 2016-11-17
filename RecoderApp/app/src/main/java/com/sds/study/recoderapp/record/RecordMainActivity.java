package com.sds.study.recoderapp.record;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sds.study.recoderapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by student on 2016-11-17.
 */

public class RecordMainActivity extends AppCompatActivity {
    MediaRecorder recorder;
    String TAG;
    static final int REQUEST_RECORD_PERMISSION = 1;
    Boolean flag = false;
    ImageView mic;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getName();
        setContentView(R.layout.record_main);
        mic = (ImageView)findViewById(R.id.mic);

    }

    public void init(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

    }

    /*저장파일 구하기*/
    public String getSaveFile(){
        File dir = new File(Environment.getExternalStorageDirectory(),"iot_record");
        /*현재시간구하기*/
        //long currentTime = System.currentTimeMillis();
        Date d = new Date();
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HHmmss").format(d);
        Log.d(TAG,"현재시간"+currentTime);

        File saveFile = new File(dir,currentTime+".mp4");
        return saveFile.getAbsolutePath();

    }

    public void showList(){
        Intent intent = new Intent(this,FileListActivity.class);
        this.startActivity(intent);
    }

    public void startRecord(){
        if(flag) {

            try {
                init();
                recorder.setOutputFile(getSaveFile());
                recorder.prepare();
                recorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mic.setImageResource(R.drawable.stop);
        }else{
            recorder.stop();
            recorder.reset();
            mic.setImageResource(R.drawable.play);

            showList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG,"requestcode:"+requestCode);
        Log.d(TAG,"grantResults0"+grantResults[0]);
        Log.d(TAG,"grantResults1"+grantResults[1]);

        switch (requestCode){
            case REQUEST_RECORD_PERMISSION :
                if(permissions.length>0 && grantResults[0]==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this,"앱사용을 위해서는 미디어 접근 권한 줘라",Toast.LENGTH_SHORT).show();
                }else if(permissions.length>0 && grantResults[1]==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this,"앱사용을 위해서는 오디를 줘라",Toast.LENGTH_SHORT).show();
                }

        }
    }

    /*각종 권한을 체크하자*/
    public void btnClick(View view){
        flag = !flag;
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recordPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);

        if(writePermission== PackageManager.PERMISSION_DENIED || recordPermission==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO
            },REQUEST_RECORD_PERMISSION);
        }else{
            startRecord();
        }

    }
}
