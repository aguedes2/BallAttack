package com.guedesinfo.tutorial.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class PowerUp {

    private double x, y, r;
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
    public boolean update(){
        if(type == 1) y += 5;
        if(type == 1) y += 7;
        if(type == 1) y += 9;

        return y < 0 + r;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();

        paint.setColor(color1);
        canvas.drawCircle((float)x, (float)y, (float)r, paint);
        paint.setColor(color2);
        canvas.drawCircle((float)x, (float)y, (float)(r - r/4), paint);
    }
}
