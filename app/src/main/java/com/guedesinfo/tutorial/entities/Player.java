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
    private long firingTimer, firingDelay, specialTimer, timerRecovering;
    private boolean firing, special, recovering, show;

    private int x, y, dx, dy, r, points = 0, frames = 0;

    private int powerLevel = 0;
    private int power = 0;
    private int[] requiredPower = {1, 2, 3, 4, 5, 6, 7, 8};

    ////////////////////////////////////////
    //CONSTRUCTOR
    public Player(){
        x = Constants.SCREEN_WIDTH / 2 - r/2;
        y = Constants.SCREEN_HEIGHT / 4 - r/2;
        r = 50;
        life = 3;

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
    public int getPoints(){return points;}
    public int getLife(){return life;}

    private void gainLife(){
        life++;
    }

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
                recovering = true;
                timerRecovering = System.nanoTime();
            }
        }
    }

    private boolean show(){
        frames++;
        if(frames % 3 == 0){
            frames = 0;
            show = !show;
        }
        return show;
    }

    private void recovering(){
        if(recovering){
            long elapsed = (System.nanoTime() - timerRecovering) / 1000000;
            if(elapsed > 1500) {
                recovering = false;
                timerRecovering = 0;
            }
        }
    }

    private void collectPowerUp(){
        for( int i = 0; i < GamePanel.powerUp.size(); i++){
            PowerUp pu = GamePanel.powerUp.get(i);
            if(pu instanceof PowerUp){
                double px = pu.getX();
                double py = pu.getY();
                double pr = pu.getR();

                double distX = px - x;
                double distY = py - y;
                double distR = pr + r;
                double dist = Math.sqrt(distX * distX + distY * distY);
                if(dist < distR){
                    int type = pu.getType();
                    System.out.println("Collecting power up >> " + type);
                    if(type == 1){gainLife(); points += 30;}
                    if(type == 2){increasePower(1); points += 20;}
                    if(type == 3){increasePower(2); points += 30;}
                    GamePanel.powerUp.remove(pu);
                    if(power > 4){
                        special = true;
                        specialTimer = System.nanoTime();
                    }
                    i--;
                    break;
                }
            }
        }
    }

    private void increasePower(int p){
        int maxPower = requiredPower.length;
        if(power + p <= maxPower){
            power += p;
            if(power > requiredPower[powerLevel]){
                power -= requiredPower[powerLevel];
                powerLevel++;
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
        collectPowerUp();
        firing();
        recovering();
    }

    @Override
    public void update() {}

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        if(!recovering){
            paint.setColor(Color.rgb(200, 200, 200));
            canvas.drawCircle((float)x, (float)y, (float)(r), paint);
            paint.setColor(color);
            canvas.drawCircle((float)x, (float)y, (float)(r - r/4), paint);
        }else{
            if(show()){
                paint.setColor(Color.rgb(180, 0, 0));
                canvas.drawCircle((float)x, (float)y, (float)(r), paint);
                paint.setColor(Color.rgb(230, 0, 0));
                canvas.drawCircle((float)x, (float)y, (float)(r - r/4), paint);
            }
        }
    }
}
