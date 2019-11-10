package com.guedesinfo.tutorial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.guedesinfo.tutorial.engine.Constants;
import com.guedesinfo.tutorial.engine.MainThread;
import com.guedesinfo.tutorial.entities.*;

import java.util.ArrayList;

/**
 * created by Nando Guedes
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    //FIELDS
    private MainThread thread;
    private boolean running;
    //ENTITIES
    public static ArrayList <Bullet> bullets;
    public static ArrayList <Particles> particles;
    public static ArrayList <PowerUp> powerUp;
    public static Player player;
    public static Point playerPoint;
    public static EnemiesManager em;
    //Wave Fields
    private static long waveStartTimer;
    private static long waveStartTimerDiff;
    public static boolean waveStart;
    public static int waveNumber = 0;
    private int waveDelay = 3000;

    //CONSTRUCT
    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

//        player = new Player(new Rect(100, 100, 200, 200), Color.rgb(255, 255, 255));
        player = new Player();
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT - Constants.SCREEN_HEIGHT / 4);

        em = new EnemiesManager();
        bullets = new ArrayList<Bullet>();
        particles = new ArrayList<Particles>();
        powerUp = new ArrayList <PowerUp>();

        setFocusable(true);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    //METHODS
    public static void waveStartController(){
        waveStartTimer = 0;
        waveStartTimerDiff = 0;
        waveStart = true;
        waveNumber = 0;
    }

    private void waveController(){
        if(waveStartTimer == 0 && EnemiesManager.enemies.size() == 0){
            waveNumber++;
            waveStart = false;
            waveStartTimer = System.nanoTime();
        }else{
            waveStartTimerDiff = (System.nanoTime() - waveStartTimer) / 1000000;
            if(waveStartTimerDiff > waveDelay){
                waveStart = true;
                waveStartTimer = 0;
                waveStartTimerDiff = 0;
            }
        }

        if(waveStart && EnemiesManager.enemies.size() == 0) EnemiesManager.createNewEnemies();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int)event.getX(), (int)event.getY());
                player.setFiring(true);
                break;
            case MotionEvent.ACTION_UP:
                player.setFiring(false);
                break;
        }
        return true;
//        return super.onTouchEvent(event);
    }

    public void update(){
        waveController();
        player.setX(playerPoint);
        player.setY(playerPoint);

        player.update(playerPoint);
        em.update();

        for(int i = 0; i < bullets.size(); i++){
            boolean remove = bullets.get(i).update();
            if(remove){
                bullets.remove(i);
                i--;
            }
        }

        for(int i = 0; i < particles.size(); i++){
            particles.get(i).update();
        }

        for(int i = 0; i < powerUp.size(); i++){
            powerUp.get(i).update();
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        canvas.drawColor(Color.BLUE);

        em.draw(canvas);
        player.draw(canvas);

        for (Bullet bullet : bullets) {
            bullet.draw(canvas);
        }

        for(Particles p : particles){
            p.draw(canvas);
        }

        for(PowerUp pu : powerUp){
            pu.draw(canvas);
        }
    }
}
