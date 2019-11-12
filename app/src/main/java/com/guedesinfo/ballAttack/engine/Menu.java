package com.guedesinfo.ballAttack.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.Random;

public class Menu {
    private String start = "S T A R T   G A M E";
    private boolean colorUp = true, show, alphaUp;
    private int color = 10, frames;
    private float x = 0, y = 0, r = 8, dx, dy, speed, alpha = 10;

    public Menu(){
        r = 8;
        x = (float)(Math.random() * Constants.SCREEN_WIDTH/2 * Constants.SCREEN_WIDTH/4);
        y = r;
        speed = 15;

        double angle = Math.random() * 140 + 20;
        double rad = Math.toRadians(angle);

        dx = (float) (Math.cos(rad) * speed);
        dy = (float) (Math.sin(rad) * speed);
    }

    private void variateColor(){
        if(colorUp){
            if(color < 256) color += 10;
            else colorUp = false;
        }else{
            if(color > 10) color -= 10;
            else colorUp = true;
        }
    }

    private void variateRadius(){
        if(r < 50){
            r += 5;
        }else if(r > 8){
            r -= 5;
        }
    }

    private void limits(){
        if(x < r | x > Constants.SCREEN_WIDTH - r) dx = -dx;
        if(y < r | y > Constants.SCREEN_HEIGHT - r) dy = -dy;
    }

    private void variatePosition(){
        variateColor();
        variateRadius();
        limits();

        x += dx;
        y += dy;
    }

    public void update(){
        variatePosition();

        frames++;
        if(frames % 30 == 0){
            frames = 0;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void draw(Canvas canvas){
        Paint p = new Paint();
        p.setTextSize(70f);
        p.setTextAlign(Paint.Align.CENTER);

        int alpha =(int)(255 * Math.sin(3.14 * frames  / 25));
        if(alpha > 255) alpha = 255;

        p.setColor(Color.argb(alpha, 250,250, 25));
        canvas.drawText(start, Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2, p);

        // circles to update
        Paint paint = new Paint();
        paint.setColor(Color.rgb(new Random().nextInt(255) * 100, new Random().nextInt(255)*100, new Random().nextInt(255)*100));
        canvas.drawCircle(x, y, r, paint);
    }


}
