package com.guedesinfo.tutorial.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.guedesinfo.tutorial.GamePanel;

import java.util.Random;

public class Particles {
    double x, y, dx = 0, dy = 0, r;
    private int curLife = 0;
    private int color1, color2;

    public Particles(double x, double y, double r){
        this.x = x;
        this.y = y;
        this.r = r;
        dx = new Random().nextGaussian();
        dy = new Random().nextGaussian();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    public void setR(double r){this.r = r;}
    public void setColor(int c){color1 = c;}
    public void setColor2(int c){color1 = c;}
    ///////////////////////////////////////////////////////////////////////////////////////////

    public void update(){
        int speed = 2;
        x += dx * speed;
        y += dy * speed;

        curLife++;
        if(curLife > 30){
            GamePanel.particles.remove(this);
        }
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();

        paint.setColor(color1);
        canvas.drawCircle((float)x, (float)y, (float)r, paint);
        paint.setColor(color2);
        canvas.drawCircle((float)x, (float)y, (float)(r - r/4), paint);
    }
}
