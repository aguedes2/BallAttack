package com.guedesinfo.tutorial.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.guedesinfo.tutorial.GamePanel;
import com.guedesinfo.tutorial.engine.Constants;

public class Player implements GameObject{
    //FIELDS
    private int color;
    private int life;
    private long firingTimer, firingDelay;
    private boolean firing;

    private int x, y, dx, dy, r, points = 0;

    private int powerLevel = 0;
    private int power = 0;
    private int[] requiredPower = {1, 2, 3, 4, 5, 6, 7, 8};

    ////////////////////////////////////////
    //CONSTRUCTOR
    public Player(){
        x = Constants.SCREEN_WIDTH / 2 - r/2;
        y = Constants.SCREEN_HEIGHT / 4 - r/2;
        r = 50;

        dx = 0;
        dy = 0;

        color = Color.rgb(255, 255, 255);

        firingTimer = System.nanoTime();
        firingDelay = 200;
    }

    //METHODS
    public void setFiring(boolean b){firing = b;}
    public void setX(Point point){x = point.x;}
    public void setY(Point point){y = point.y;}

    public double getX(){return x;}
    public double getY(){return y;}
    public double getR(){return r;}


    private void limits(Point point){
        x = point.x;
        y = point.y;

        if(x - r < 0) x = r;
        if(y - r < 0) y = r;
        if(x + r > Constants.SCREEN_WIDTH) x = Constants.SCREEN_WIDTH - r;
        if(y + r > Constants.SCREEN_HEIGHT) y = Constants.SCREEN_HEIGHT - r;
    }

    private void collidingWithEnemy(){
        for(int i = 0; i < EnemiesManager.enemies.size(); i++){
            Enemy e = EnemiesManager.enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();
            double er = e.getR();

            double distX = ex - x;
            double distY = ey - y;
            double distR = er + r;
            double dist = Math.sqrt(distX * distX + distY * distY);
            if(dist < distR){
//                System.out.println("Colidding with enemy");
            }
        }
    }

    private void firing(){
        if(firing){
            long elapsed = (System.nanoTime() - firingTimer) / 1000000;
            if(elapsed > firingDelay){
                firingTimer = System.nanoTime();
                GamePanel.bullets.add(new Bullet(-90, x , y, 5, 1));
            }
        }
    }

    public void update(Point point){
        limits(point);
        collidingWithEnemy();
        firing();
    }

    @Override
    public void update() {}

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(200, 200, 200));
        canvas.drawCircle((float)x, (float)y, (float)(r), paint);
        paint.setColor(color);
        canvas.drawCircle((float)x, (float)y, (float)(r - r/4), paint);

        //print string
        String pts = "Points: " + points;
        canvas.drawText(pts, Constants.SCREEN_WIDTH/2,50, paint);
    }
}
