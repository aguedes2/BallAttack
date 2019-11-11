package com.guedesinfo.tutorial.engine;

import android.graphics.*;
import android.graphics.fonts.Font;
import androidx.annotation.RequiresApi;
import com.guedesinfo.tutorial.GamePanel;

public class UI {

    int color2 = 100;
    boolean colorUp;

    @RequiresApi(api = 29)
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        float textSize;
        int width = Constants.SCREEN_WIDTH;
        int height = Constants.SCREEN_HEIGHT;
        paint.setColor(Color.rgb(255, 255, 255));
        //intro wave

        if(GamePanel.states == States.PLAYING){
            //score
            textSize = 50f;
            String pts = "Points: " + GamePanel.player.getPoints();
            //String bounds
            paint.setTextSize(textSize);
            canvas.drawText(pts,width/3 + 70,70, paint);


            //wave number
            String wave = "Wave - " + GamePanel.waveNumber;
            canvas.drawText(wave,width - width/4,70, paint);
            //indication wave
            wave = "-  W A V E  " + GamePanel.waveNumber + "  -";
            Rect bounds = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
            int alpha =(int)(255 * Math.sin(3.14 * GamePanel.waveStartTimerDiff / GamePanel.waveDelay));
            if(alpha > 255) alpha = 255;

            paint.setColor(Color.argb(alpha, 255, 255, 255));
            paint.setTextSize(60f);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(wave, width/2, height/2, paint);

            //life player
            int lives = GamePanel.player.getLife();
            int x = 50;
            int y = 70;
            if(lives < 5){
                for(int i = 0; i < lives; i++){
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.rgb(255, 255, 255));
                    canvas.drawCircle(x + (i * 70), y, 30, paint);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(10);
                    paint.setColor(Color.rgb(192,192,192));
                    canvas.drawCircle(x + (i * 70), y, 30, paint);
                }
            }else{
                String lfs = lives + " X ";
                canvas.drawText(lfs, 30, y, paint);
                paint.setColor(Color.rgb(200, 200, 200));
                canvas.drawCircle(90 + x, y - 20, 30, paint);
                paint.setColor(Color.rgb(255, 255, 255));
                canvas.drawCircle(90 + x, y - 20, 30 - 10, paint);
            }

            //power up
            int power = GamePanel.player.getPower();
            int requiredPower = GamePanel.player.getRequirePower();

            int left = 30, right = 70, top = 130, bottom = top + 40;
            int widthP = 40;
            paint.setColor(Color.rgb(255, 255, 0));
            paint.setStyle(Paint.Style.FILL);
            if(power != 0){
                canvas.drawRect(new Rect(left, top, left + power * widthP, top + widthP), paint);
            }

            paint.setColor(Color.rgb(255, 165, 0));
            paint.setStyle(Paint.Style.STROKE);

            for(int i = 1; i <= requiredPower; i++)
                canvas.drawRect(new Rect(left, top, left + i * widthP, top + widthP), paint);
        }

        if(GamePanel.states == States.GAME_OVER){
            //game over
            String msg = "> G A M E  O V E R <";

            if(!colorUp){
                color2+=10;
                if(color2 > 255) {
                    color2 = 255;
                    colorUp = true;
                }
            }else{
                color2-=10;
                if(color2 <= 100) {
                    color2 = 100;
                    colorUp = false;
                }
            }

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.argb(255, color2, 0, 0));
            paint.setTextSize(80f);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(msg, width/2, height/2, paint);
        }
    }
}
