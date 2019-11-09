package com.guedesinfo.tutorial.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.guedesinfo.tutorial.GamePanel;
import com.guedesinfo.tutorial.engine.Constants;

public class Enemy implements GameObject{

    //FIELDS
    private int color1, color2;
    private int type, rank, health;
    private double x, y, r, dx, dy, rad, speed;
    private double xMin = r, yMin = r, xMax = Constants.SCREEN_WIDTH - r, yMax = Constants.SCREEN_HEIGHT - r;
    private long hitTimer;
    private boolean ready, hit, dead;

    //CONSTRUCTOR
    public Enemy(int type, int rank){
        x = dx;
        y = dy;
        this.type = type;
        this.rank = rank;
        if(type == 1){
            //default enemies variable: radius and speed
            health = 3;
            if(rank == 1){
                color1 = Color.rgb(0, 0,139); //blue
                color2 = Color.rgb(0, 0, 200); //dark bluewidth = 100;
                r = 80;
                speed = 20;
            }
            if(rank == 2){
                color1 = Color.rgb(255, 255, 0); //yellow
                color2 = Color.rgb(85, 107, 47);//dark olive yellow
                r = 70;
                speed = 30;
            }
            if(rank == 3){
                color1 = Color.rgb(255, 192, 203); //pink
                color2 = Color.rgb(255, 20, 147); //deep pink
                r = 60;
                speed = 40;
            }
            if(rank == 4){
                color1 = Color.rgb(255, 0, 0); //red
                color2 = Color.rgb(139, 0, 0);//dark red
                r = 50;
                speed = 50;
            }
        }
        else if(type == 2){
            //speed fixed, variable:health and radius
            speed = 20;
            if(rank == 1){
                color1 = Color.rgb(0,128,0); //green
                color2 = Color.rgb(0,100,0); //dark green
                r = 60;
                health = 3;
            }
            if(rank == 2){
                color1 = Color.rgb(224,255,255); //light cyan
                color2 = Color.rgb(244,164,96);//sandy brown
                r = 70;
                health = 4;
            }
            if(rank == 3){
                color1 = Color.rgb(245,222,179);//weat
                color2 = Color.rgb(245,222,179);//weat
                r = 80;
                health = 5;
            }
            if(rank == 4){
                color1 = Color.rgb(192,192,192);//silver
                color2 = Color.rgb(128,128,128);//gray
                r = 90;
                health = 6;
            }
        }
        else if(type == 3){
            //health and speed variables
            r = 80;
            if(rank == 1){
                color1 = Color.rgb(75,0,130);//indigo
                color2 = Color.rgb(37,0, 75); //purple
                health = 6;
                speed = 40;
            }
            if(rank == 2){
                color1 = Color.rgb(255, 113, 56); //gold
                color2 = Color.rgb(122, 91, 10); //dark gold rod
                health = 5;
                speed = 50;
            }
            if(rank == 3){
                color1 = Color.rgb(255, 60, 10); //hot pink
                color2 = Color.rgb(170, 40, 9); //medium violet red
                health = 4;
                speed = 40;
            }
            if(rank == 4){
                color1 = Color.rgb(139,0,0);//dark red
                color2 = Color.rgb(75,0,0);//brown
                health = 3;
                speed = 70;
            }
        }
        else if(type == 4){
            //speed variable
            health = 3;
            r = 7;
            if(rank == 1){
                color1 = Color.rgb(218,165,32); //goldenrod
                color2 = Color.rgb(184,134,11);// dark goldenrod
                speed = 50;
            }
            if(rank == 2){
                color1 = Color.rgb(250,250,210); //LightGoldenrodYellow
                color2 = Color.rgb(255,218,185); //PeachPuf
                speed = 60;
            }
            if(rank == 3){
                color1 = Color.rgb(160,82,45); //sienna
                color2 = Color.rgb(139,69,19); //saddleBrown
                speed = 70;
            }
            if(rank == 4){
                color1 = Color.rgb(244,164,96); //SandyBrown
                color1 = Color.rgb(210,105,30); //chocolate
                speed = 80;
            }
        }

        x = 5 * Math.random() * Constants.SCREEN_WIDTH / 4 ;
        y = r;

        double angle = Math.random() * 140 + 20;
        rad = Math.toRadians(angle);

        dx = Math.cos(rad) * speed;
        dy = Math.sin(rad) * speed;

        ready = false;
    }

    //////////////////////////////////////////////////////////////////////////////////
    //METHODS
    public double getX(){return x;}
    public double getY(){return y;}
    public double getR(){return r;}
    public double getType(){return type;}

    public void hit(int type){
        this.hitTimer = System.nanoTime();
        this.hit = true;
        int bType = type;
        switch(type){
            case 1: health--; break;
            case 2: health -= 2; break;
            case 3: health -= 3; break;
            case 4: health -= 4; break;
        }

        if(health <= 0)dead = true;
    }

    private void isCollidingWithBullet(){
        for(int i = 0; i < GamePanel.bullets.size(); i++){
            Bullet b = GamePanel.bullets.get(i);
            if(b != null){
                double bx = b.getX();
                double by = b.getY();
                double br = b.getR();

                double dx = x - bx;
                double dy = y - by;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if(dist < br - r){
                    hit(b.getType());
                    GamePanel.bullets.remove(i);
                    i--;
                }
            }
        }
    }

    private void toMove(){
        x += dx;
        y += dy;

        if(!ready){
            if(x > r && y > r && x < Constants.SCREEN_WIDTH - r && y < Constants.SCREEN_HEIGHT - r) ready = true;
        }
        if(x < r | x > Constants.SCREEN_WIDTH - r) dx = -dx;
        if(y < r | y > Constants.SCREEN_HEIGHT - r) dy = -dy;
    }

    @Override
    public void update() {
        toMove();
        isCollidingWithBullet();
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        if(hit){
            paint.setColor(Color.WHITE);
            canvas.drawCircle((float)x, (float)y, (float)r, paint);
        }
        else{
            paint.setColor(color1);
            canvas.drawCircle((float)x, (float)y, (float)r, paint);
            paint.setColor(color2);
            canvas.drawCircle((float)x, (float)y, (float)(r - r/4), paint);
        }
    }
}
