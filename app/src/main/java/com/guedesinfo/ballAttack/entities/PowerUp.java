package com.guedesinfo.ballAttack.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.guedesinfo.ballAttack.engine.Constants;

public class PowerUp {

    private double x, y, r, speed;
    private int type;
    private int color1, color2;

    /**
     * Power up type
     * Type 1 => Life
     * Type 2 => power 1
     * Type 3 => power 2
     */

    public PowerUp(int type, double x, double y){
        this.x = x;
        this.y = y;
        this.type = type;

        if(type == 1){
            color1 = Color.rgb(255, 0, 255);
            color2 = Color.rgb(139,0,139);
            r = 20;
        }
        if(type == 2){
            color1 = Color.rgb(0,255,0);
            color2 = Color.rgb(0,100,0);
            r = 30;
        }
        if(type == 3){
            color1 = Color.rgb(255,215,0);
            color2 = Color.rgb(255,140,0);
            r = 40;
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    public double getX(){return x;}
    public double getY(){return y;}
    public double getR(){return r;}
    public int getType(){return type;}

    public boolean update(){
        if(type == 1) speed = 10;
        if(type == 2) speed = 15;
        if(type == 3) speed = 20;

        y += speed;

        return y > Constants.SCREEN_HEIGHT - r;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();

        paint.setColor(color1);
        canvas.drawCircle((float)x, (float)y, (float)r, paint);
        paint.setColor(color2);
        canvas.drawCircle((float)x, (float)y, (float)(r - r/4), paint);
    }
}
