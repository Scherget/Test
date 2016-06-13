package com.example.dennis.test;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Background {
    private Bitmap animation;
    private double xPos;
    private double yPos;
    private Rect sRectangle;
    private int fps;
    private double angle;
    private long frameTimer;
    int spriteHeight;
    int spriteWidth;
    Paint paint;


    public Background() {
        sRectangle = new Rect(0, 0, 0, 0);
        frameTimer = 0;
        setXPos(0);
        setYPos(0);
        this.paint = new Paint();
    }

    public void Initialize(Bitmap bitmap, int height, int width, int fps, int frameCount) {
        this.animation = bitmap;
        this.spriteHeight = height;
        this.spriteWidth = width;
        this.sRectangle.top = 0;
        this.sRectangle.bottom = height;
        this.sRectangle.left = 0;
        this.sRectangle.right = width;
        this.fps = 1000 / fps;
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

    public void draw(Canvas canvas, double angle, int width, int height, boolean ismoving) {

        if(ismoving) {
            //setXPos(getXPos() + Math.cos(angle) * 5);
            setYPos(getYPos() + Math.sin(angle) * 5);
        }

        Rect dest = new Rect((int)getXPos(), (int)getYPos(), (int)getXPos() + width,(int)getYPos() + height);
        canvas.drawBitmap(animation, sRectangle, dest, null);


    }
}