package com.guedesinfo.tutorial.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.guedesinfo.tutorial.GamePanel;
import com.guedesinfo.tutorial.engine.Constants;

public class Bullet {

    //FIELDS
    private int r, type;
    private double x, y, dx, dy, rad, speed;
    private int color1;

    //CONSTRUCTOR
    public Bullet(double angle, int x, int y){
        this.x = x;
        this.y = y;
        r = 8;
        speed = 50;

        rad = Math.toRadians(angle);
        dx = Math.cos(rad) * speed;
        dy = Math.sin(rad) * speed;

        color1 = Color.rgb(255, 255, 0);
    }

    public Bullet(double angle, int x, int y, int r, int type){
        this.x = x;
        this.y = y;
        this.r = r;
        speed = 50;
        this.type = type;

        rad = Math.toRadians(angle);
        dx = Math.cos(rad) * speed;
        dy = Math.sin(rad) * speed;

        color1 = Color.rgb(255, 255, 0);
    }

    //FUNCTIONS
    public double getX(){return x;}
    public double getY(){return y;}
    public double getR(){return r;}
    public int getType(){return type;}

    public void setType(int t){type = t;}

    public boolean update(){
        x += dx;
        y += dy;

        return x < -r || x > Constants.SCREEN_WIDTH + r || y < -r || y > Constants.SCREEN_HEIGHT + r;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void draw(Canvas canvas){
        Paint p = new Paint();
        p.setColor(color1);
        canvas.drawCircle((float)x, (float) y, r, p);
    }
}
