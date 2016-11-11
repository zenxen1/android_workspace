package com.sds.study.adapterviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * 복합위젯을 ListView로 보여주자!!
 * 오전에 자체 제작한 List와 안드로이드 api 에서 지원하는 AdapterView 결정적 차이점???
 * 메모리 효율성이 극대화됨....
 */

public class HeroListActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Listview는 껍데기에 불가하므로 적절한 adapter를 이용해보자
        //ArrayAdapter는 단순한 TextView 하나로 구현시만 사용 가능하므로,
        //개발자가 정의한 복잡 위젯은 사용이 불가능하다..따라서 Adapter를 직접 재정의해서 구현해야 한다.
        //Swing에서 TavleModel을 상속받아 재정의하는것과 완전동일...

        setContentView(R.layout.activity_list);
        /*ListView listView = (ListView) findViewById(R.id.listView);*/
        /*GridView listView = (GridView) findViewById(R.id.listView);*/
        Spinner listView = (Spinner) findViewById(R.id.listView);
        MyAdapter myAdapter = new MyAdapter(this);

        listView.setAdapter(myAdapter);
    }
}
