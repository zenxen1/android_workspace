package com.sds.study.musicapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by student on 2016-11-11.
 */

public class MusicAdapter extends BaseAdapter{
    Context context;
    String TAG;
    ArrayList<Music> list = new ArrayList<Music>();
    public MusicAdapter(Context context) {
        this.context=context;
        TAG = this.getClass().getName();
        //제이슨 가져오기!!!
        AssetManager assetManager = context.getAssets();
        BufferedReader buffr=null;
        try {
            InputStream is= assetManager.open("music.data");
            buffr = new BufferedReader(new InputStreamReader(is));
            String data = null;
            StringBuffer sb = new StringBuffer();
            while(true){
                data = buffr.readLine();
                if(data==null)break;
                sb.append(data);
                Log.d(TAG,data);
            }
            //제이슨완성시점

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("musicList");
            for(int i =0;i<jsonArray.length();i++){
                JSONObject obj = (JSONObject) jsonArray.get(i);
                Music music = new Music();
                music.setTitle(obj.getString("title"));
                music.setArtist(obj.getString("artist"));
                music.setFilename(obj.getString("file"));
                list.add(music);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if(buffr!=null){
                try {
                    buffr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //각 index에 보여질 뷰 (버튼, 각종 뷰..but 복합위젯이 대부분....
    public View getView(int i, View view, ViewGroup viewGroup) {

       /* Music music = new Music();
        music.setTitle("nobody");
        music.setArtist("wondergirl");
        music.setFilename("R.raw.b");*/
        Music music = list.get(i);
        MusicItem item = new MusicItem(context,music);

        return item;
    }
}
