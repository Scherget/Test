package com.example.dennis.test;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Control_Player {
    private Bitmap animation;
    private double xPos;
    private double yPos;
    private Rect sRectangle;
    private int fps;
    private long frameTimer;
    int spriteHeight;
    int spriteWidth;
    Paint paint;

    //touch
    private float mDownX;
    private float mDownY;
    private final float SCROLL_THRESHOLD = 10;
    private boolean isOnClick;

    public Control_Player() {
        sRectangle = new Rect(0, 0, 0, 0);
        frameTimer = 0;
        this.paint = new Paint();
    }

    public void Initialize(Bitmap bitmap, int height, int width, int fps, int frameCount) {
        this.animation = bitmap;
        this.spriteHeight = height;
        this.spriteWidth = width;
        this.sRectangle.top = 0;
        this.sRectangle.bottom = spriteHeight;
        this.sRectangle.left = 0;
        this.sRectangle.right = spriteWidth;
        this.fps = 1000 / fps;
        setXPos(1400-width/2);
        setYPos(850 - height / 2);
        paint.setColor(Color.RED);
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void setXPos(double value) {
        xPos = value;
    }

    public void setYPos(double value) {
        yPos = value;
    }

    public void Update(long gameTime) {
        if( gameTime > frameTimer + fps) {
            frameTimer = gameTime;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(1400, 850, 50, paint);
        Rect dest = new Rect((int)getXPos(), (int)getYPos(), (int)getXPos() + (int)spriteWidth,(int)getYPos() + spriteHeight);
        canvas.drawBitmap(animation, sRectangle, dest, null);
    }
}