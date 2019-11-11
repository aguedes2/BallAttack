package com.guedesinfo.ballAttack;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.RequiresApi;
import com.guedesinfo.ballAttack.engine.Constants;
import com.guedesinfo.ballAttack.engine.MainThread;
import com.guedesinfo.ballAttack.engine.States;
import com.guedesinfo.ballAttack.engine.UI;
import com.guedesinfo.ballAttack.entities.*;

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
    private UI ui;
    //Wave Fields
    public static long waveStartTimer;
    public static long waveStartTimerDiff;
    public static boolean waveStart;
    public static int waveNumber = 0;
    public static int waveDelay = 3000;

    public static States states = States.PLAYING;

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
        ui = new UI();

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

    private void restartGame(){
        System.out.println("Restarting Games");
        player.isDead(false);
        player.setFiring(false);
        player.resetPoints();
        player.setLife(3);
        player.setPower(0);
        player.setPowerLevel(0);
        player.restartPosition();
        EnemiesManager.enemies.clear();
        bullets.clear();
        powerUp.clear();
        waveStartController();
        states = States.PLAYING;
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
                if(states == States.PLAYING){
                    playerPoint.set((int)event.getX(), (int)event.getY());
                    player.setFiring(true);
                }

                break;
            case MotionEvent.ACTION_UP:
                if(states == States.PLAYING) player.setFiring(false);
                if(states == States.GAME_OVER) restartGame();
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
            boolean remove = powerUp.get(i).update();
            if(remove){
                powerUp.remove(i);
                i--;
            }
        }
    }

    @TargetApi(29)
    @RequiresApi(api = 29)
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(waveNumber < 5) canvas.drawColor(Color.rgb(0, 100, 255));
        else if(waveNumber < 10) canvas.drawColor(Color.rgb(0, 10, 25));
        else if(waveNumber < 15) canvas.drawColor(Color.rgb(125, 25, 55));
        else if(waveNumber < 20) canvas.drawColor(Color.rgb(80, 120, 50));
        else if(waveNumber < 25) canvas.drawColor(Color.rgb(100, 100, 100));

        if(states == States.PLAYING){
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

        ui.draw(canvas);
    }
}
