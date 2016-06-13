package com.example.dennis.test;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player {
    private Bitmap animation;
    private double xPos;
    private double yPos;
    private Rect sRectangle;
    private int fps;
    private int numFrames;
    private int currentFrame;
    private int Vheight;
    private int Vwidth;
    private long frameTimer;
    int spriteHeight;
    int spriteWidth;
    Paint paint;

    //touch
    private float mDownX;
    private float mDownY;
    private final float SCROLL_THRESHOLD = 10;
    private boolean isOnClick;

    public Player() {
        sRectangle = new Rect(0, 0, 0, 0);
        frameTimer = 0;
        currentFrame = 0;
        setXPos(1000);
        setYPos(500);
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
        this.numFrames = frameCount;
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

    public void draw(Canvas canvas, double angle, double backgroundXpos, double backgroundYpos, int CanvasWidth, int CanvasHeight, boolean ismoving) {
        Rect dest = new Rect((int)getXPos(), (int)getYPos(), (int)getXPos() + (int)spriteWidth,(int)getYPos() + spriteHeight);
       canvas.drawBitmap(animation, sRectangle, dest, null);

        if(ismoving) {
            setXPos(getXPos() + Math.cos(angle) * 5 * -1);
            setYPos(getYPos() + Math.sin(angle) * 5 * -1);

            //TODO change width and height to background canvas
            if (getXPos() < backgroundXpos) {
                setXPos(backgroundXpos);
            }
            if (getXPos() + spriteWidth > CanvasWidth) {
                setXPos(CanvasWidth - spriteWidth);
            }
            if (getYPos() < backgroundYpos) {
                setYPos(backgroundYpos);
            }
            if (getYPos() + spriteHeight > backgroundYpos + CanvasHeight) {
                setYPos(backgroundYpos + CanvasHeight - spriteHeight);
            }
        }
    }
}