package com.guedesinfo.ballAttack.engine;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.os.Build;
import android.view.SurfaceHolder;
import androidx.annotation.RequiresApi;
import com.guedesinfo.ballAttack.GamePanel;

public class MainThread extends Thread{
    public static final int MAX_FPS = 30;
    private double averageFPS;
    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @TargetApi(29)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void run(){
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        int totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        GamePanel.waveStartController();

        while (running){
            startTime = System.nanoTime();
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }catch(Exception e){
                e.printStackTrace();
            } finally {
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){e.printStackTrace();}
                }
            }
            timeMillis = (System.nanoTime() - startTime)/ 1000000;
            waitTime = targetTime - timeMillis;
            try{
                if(waitTime > 0 ){
                    sleep(waitTime);
                }
            }catch(Exception e){e.printStackTrace();}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == MAX_FPS){
                averageFPS = 1000 / ((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
//                System.out.println("averageFPS >> " + averageFPS);
            }
        }
    }

    public void setRunning(boolean b) {
        running = b;
    }
}
