package com.example.dennis.test;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class Thread extends java.lang.Thread {
    private SurfaceHolder surfaceHolder;
    private ISurface panel;
    private boolean run = false;

    //touch
    private float mDownX;
    private float mDownY;
    private final float SCROLL_THRESHOLD = 10;
    private boolean isOnClick;
    private double angle;
    private com.example.dennis.test.Control_Player Control_Player;

    public Thread(SurfaceHolder surfaceHolder, ISurface panel) {
        this.surfaceHolder = surfaceHolder;
        this.panel = panel;
        Control_Player = new Control_Player();

        panel.onInitalize();
    }

    public void setRunning(boolean value) {
        run = value;
    }

    private long timer;

    @Override
    public void run() {

        Canvas c;
        while (run) {
            c = null;
            timer = System.currentTimeMillis();
            panel.onUpdate(timer);

            try {
                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    panel.onDraw(c);
                }
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                //if (mDownX > player.getXPos() && mDownX < player.getXPos() + player.spriteWidth && mDownY > player.getYPos() && mDownY < player.getYPos() + player.spriteHeight) {
                    //isOnClick = true;

                    //angle = angle((float) player.getXPos(), (float) player.getYPos(), 700, 700);

                    //player.setXPos(player.getXPos() + Math.cos(angle) * 5);
                    //player.setYPos(player.getYPos() + Math.sin(angle) * 5);
                //}
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (isOnClick) {
                    //TODO onClick code
                    if(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isOnClick && (Math.abs(mDownX - ev.getX()) > SCROLL_THRESHOLD || Math.abs(mDownY - ev.getY()) > SCROLL_THRESHOLD)) {
                    isOnClick = false;
                }
                if (mDownX > Control_Player.getXPos() && mDownX < Control_Player.getXPos() + Control_Player.spriteWidth && mDownY > Control_Player.getYPos() && mDownY < Control_Player.getYPos() + Control_Player.spriteHeight) {
                    Control_Player.setXPos(mDownX - (Control_Player.spriteWidth/2));
                    Control_Player.setYPos(mDownY - (Control_Player.spriteHeight/2));
                }

                break;
            default:
                break;
        }
        return true;
    }



}