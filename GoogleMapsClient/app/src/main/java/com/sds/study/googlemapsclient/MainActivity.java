package com.sds.study.googlemapsclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    Toolbar toolbar;
    SupportMapFragment mapFragment;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        //프레그먼트와 콜백 인터페이스 연결
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_navi,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //웹서버로부터 갱신된 데이터 가져오기
    public void getData(){
        MapAsync mapAsync = new MapAsync(googleMap);
        mapAsync.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //액션 버튼을 누르면....
        if(item.getItemId()==R.id.m_refresh){
            getData();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //맵이 준비되면...
        this.googleMap=googleMap;

        LatLng point = new LatLng(37.234234,127.39234);

        googleMap.addMarker(new MarkerOptions()
            .title("내마커")
            .position(point)
        );

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,16));
    }
}
