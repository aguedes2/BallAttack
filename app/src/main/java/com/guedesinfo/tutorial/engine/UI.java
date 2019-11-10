package com.guedesinfo.tutorial.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.guedesinfo.tutorial.GamePanel;

public class UI {

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        float textSize;
        int width = Constants.SCREEN_WIDTH;
        int height = Constants.SCREEN_HEIGHT;
        paint.setColor(Color.rgb(255, 255, 255));
        //intro wave

        //score
        textSize = 50f;
        String pts = "Points: " + GamePanel.player.getPoints();
        //String bounds
        paint.setTextSize(textSize);
        canvas.drawText(pts,width - width/4,70, paint);

        //life player
        int lives = GamePanel.player.getLife();
        int x = 50;
        int y = 70;
        if(lives < 5){
            for(int i = 0; i < lives; i++){
                paint.setColor(Color.rgb(200, 200, 200));
                canvas.drawCircle(x + (i * 70), y, 30, paint);
                paint.setColor(Color.rgb(255, 255, 255));
                canvas.drawCircle(x + (i * 70), y, 30 - 10, paint);
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

        //game over
    }
}
