package com.sds.study.graphicapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 자기만의 고유한 변수들을 갖는 객체....
 * x,y,width,height,색상,velx,vely속도..
 */

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int velX;
    protected int velY;
    protected int color;
    protected RectF rectF;
    protected Paint paint;
    Bitmap bitmap;

    public GameObject(int x, int y, int width, int height, int color,Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        rectF = new RectF(x,y,x+width,y+height);
        paint = new Paint();
        paint.setColor(color);
        this.bitmap = bitmap;

    }

    /*이 클래스를 상속받는 객체가 어떤 행동을 하지 알수 없으므로 추상메서드로만 정의해 놓자*/
    public abstract void tick(); /*물리적 변화*/
    public abstract void render(Canvas canvas);/*화면에 어떻게 그려질지 결정*/

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
