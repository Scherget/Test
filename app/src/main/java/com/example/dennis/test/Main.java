package com.example.dennis.test;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.graphics.Paint;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;


public class Main extends Activity {
    Player player = new Player();
    Control_Player controlPlayer = new Control_Player();
    Control_Shoot controlShoot = new Control_Shoot();
    List<Shoot> shoot_list=new ArrayList<Shoot>();
    Background background = new Background();

    Paint paint = new Paint();
    private double angle;
    private float mDownX;
    private float mDownY;
    private double timer;
    public Rect sRectangle;
    private Bitmap animation;
    private final float SCROLL_THRESHOLD = 10;
    private boolean PlayerIsMoving;
    private boolean ShootIsMoving;
    private boolean temp = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new AndroidTutorialPanel(this));
    }


    class AndroidTutorialPanel extends DrawablePanel {

        public AndroidTutorialPanel(Context context) {
            super(context);
        }

        @Override
        public void onInitalize() {
            // convert pixel
            int XY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());

            paint.setColor(Color.BLACK);
            Main.this.player.Initialize(BitmapFactory.decodeResource(getResources(), R.drawable.ship), XY, XY, 14, 4);
            Main.this.controlPlayer.Initialize(BitmapFactory.decodeResource(getResources(), R.drawable.pearlring), 192, 192, 14, 4);
            Main.this.controlShoot.Initialize(BitmapFactory.decodeResource(getResources(), R.drawable.pearlring), 192, 192, 14, 4);
            PlayerIsMoving = false;
            ShootIsMoving = false;
        }


        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (temp){
                Main.this.background.Initialize(BitmapFactory.decodeResource(getResources(), R.drawable.hintergrund),getHeight(), getWidth(), 14, 4);
                temp = false;
            }

            canvas.drawColor(Color.BLACK);

            Check_Shoot(canvas);

            angle = (angle((float) controlPlayer.getXPos() + controlPlayer.spriteWidth / 2, (float) controlPlayer.getYPos() + controlPlayer.spriteHeight / 2, 1425, 875));
            int convertHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 282, getResources().getDisplayMetrics());
            int convertWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getResources().getDisplayMetrics());

            Main.this.background.draw(canvas, angle, getWidth(), getHeight(), PlayerIsMoving);
            Main.this.player.draw(canvas, angle, background.getXPos(), background.getYPos(), getWidth(), getHeight(), PlayerIsMoving);
            Main.this.controlPlayer.draw(canvas);
            Main.this.controlShoot.draw(canvas);

            Creat_Shoot();

            int x = getWidth();
            int y = getHeight();

        }

        @Override
        public void onUpdate(long gameTime) {
            Main.this.player.Update(gameTime);
        }

        public boolean onTouchEvent(MotionEvent event) {

            int pointerCount = event.getPointerCount();

            for (int i = 0; i < pointerCount; i++) {
                int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mDownX = event.getX(i);
                        mDownY = event.getY(i);

                        if (mDownX > player.getXPos() && mDownX < player.getXPos() + player.spriteWidth && mDownY > player.getYPos() && mDownY < player.getYPos() + player.spriteHeight) {
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (PlayerIsMoving) {
                            PlayerIsMoving = false;
                            controlPlayer.setXPos(1400 - controlPlayer.spriteWidth / 2);
                            controlPlayer.setYPos(850 - controlPlayer.spriteHeight / 2);
                        }
                        if (ShootIsMoving) {
                            ShootIsMoving = false;
                            controlShoot.setXPos(225 - controlShoot.spriteWidth / 2);
                            controlShoot.setYPos(850 - controlShoot.spriteHeight / 2);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mDownX = event.getX(i);
                        mDownY = event.getY(i);
                        //Control_Player
                        if (mDownX > controlPlayer.getXPos() && mDownX < controlPlayer.getXPos() + controlPlayer.spriteWidth && mDownY > controlPlayer.getYPos() && mDownY < controlPlayer.getYPos() + controlPlayer.spriteHeight) {
                            controlPlayer.setXPos(mDownX - (controlPlayer.spriteWidth / 2));
                            controlPlayer.setYPos(mDownY - (controlPlayer.spriteHeight / 2));
                            PlayerIsMoving = true;
                        }
                        //Control_Shoot
                        if (mDownX > controlShoot.getXPos() && mDownX < controlShoot.getXPos() + controlShoot.spriteWidth && mDownY > controlShoot.getYPos() && mDownY < controlShoot.getYPos() + controlShoot.spriteHeight) {
                            controlShoot.setXPos(mDownX - (controlShoot.spriteWidth / 2));
                            controlShoot.setYPos(mDownY - (controlShoot.spriteHeight / 2));
                            ShootIsMoving = true;
                        }
                        //Background

                        break;
                }
            }
            return true;
        }

        public double angle(float x1, float y1, float x2, float y2)
        {
            return Math.atan2(y2 - y1, x2 - x1);
        }

        public void Creat_Shoot(){
            if (ShootIsMoving){
                if (System.currentTimeMillis() > timer + 250) {
                    timer = System.currentTimeMillis();
                    angle = (angle((float) controlShoot.getXPos() + controlShoot.spriteWidth / 2, (float) controlShoot.getYPos() + controlShoot.spriteHeight / 2, 200, 875));
                    shoot_list.add(new Shoot(angle));
                    shoot_list.get(shoot_list.size() - 1).Initialize(BitmapFactory.decodeResource(getResources(), R.drawable.shoot), 192, 192, 14, 4, player.getXPos() + 8, player.getYPos() + 8);
                }
            }
        }
        public void Check_Shoot(Canvas canvas){
            //TODO change to moving background
            if (shoot_list != null) {
                //Draw every visible shoot and deleting the rest
                for (int i = 0; i < shoot_list.size(); i++) {
                    shoot_list.get(i).draw(canvas);
                    if (shoot_list.get(i).getXPos() < 0 || shoot_list.get(i).getXPos() > canvas.getWidth() || shoot_list.get(i).getYPos() < 0 || shoot_list.get(i).getYPos() > canvas.getHeight()){
                        shoot_list.remove(i);
                    }
                }
            }
        }
        public float convertPixelsToDp(float px, Context context) {
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
            return dp;
        }
    }
}