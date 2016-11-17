package com.sds.study.recoderapp.record;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sds.study.recoderapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 2016-11-17.
 */

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener{
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList <String> list;
    String TAG;
    String filename;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       TAG = this.getClass().getName();
        View view = inflater.inflate(R.layout.fragment_list,null);
        listView = (ListView) view.findViewById(R.id.listView);

        list = (ArrayList) getFiles();

        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }
    /*외부 저장소인 iot_record 디렉토리의 모든 파일을 가져오자*/
    public List getFiles(){
        File dir = new File(Environment.getExternalStorageDirectory(),"iot_record");
        File[] file = dir.listFiles();
        ArrayList list = new ArrayList();
        for(int i=0;i<file.length;i++){
            list.add(file[i].getName());
        }

        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
        TextView txt = (TextView) view;
        filename = txt.getText().toString();
        Toast.makeText(getContext(),"파일명은"+filename,Toast.LENGTH_SHORT).show();

        /*뷰페이저중 index1에 해당하는 detailfragment 보여주기*/
        FileListActivity fileListActivity= (FileListActivity)getContext();
        Log.d(TAG,"넌 누구냐??"+fileListActivity);
        fileListActivity.viewPager.setCurrentItem(1);
    }
}
