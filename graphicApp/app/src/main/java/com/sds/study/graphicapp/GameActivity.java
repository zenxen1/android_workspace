package com.sds.study.graphicapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by student on 2016-11-16.
 */

public class GameActivity extends Activity{
    String TAG;
    GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getName();
        gameView = new GameView(this);
        setContentView(gameView);



    }
}
