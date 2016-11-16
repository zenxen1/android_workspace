package com.sds.study.graphicapp;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * 오브젝트를 화면에 등장시키거나, 없애기도 하는 오브젝트 관리자..
 */

public class ObjectManager  {
    ArrayList<GameObject> list = new ArrayList<GameObject>();

    public void addObject(GameObject obj){
        list.add(obj);
    }
    public void removeObject(GameObject obj){
        list.remove(obj);
    }

    /*등록된 모든 오브젝트의 tick,render 여기서 호출해주자*/
    public void tick(){
        for(int i=0;i<list.size();i++){
            list.get(i).tick();
        }
    }
    public void render(Canvas canvas){
        for(int i=0;i<list.size();i++){
            list.get(i).render(canvas);
        }

    }
}
