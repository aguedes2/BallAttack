package com.guedesinfo.ballAttack.entities;

import android.graphics.Canvas;
import androidx.annotation.RequiresApi;
import com.guedesinfo.ballAttack.GamePanel;

import java.util.ArrayList;
import java.util.Random;

public class EnemiesManager implements GameObject{

    public static ArrayList<Enemy> enemies;
    private Enemy enemy;
    private int color1;

    //CONSTRUCT
    public EnemiesManager(){
        enemies = new ArrayList <Enemy>();
        enemy = new Enemy(1, 1);
    }

    //////////////////////////////////////////////////////////////////////////
    //METHODS
    public static void createNewEnemies(){
        enemies.clear();
        newEnemies(GamePanel.waveNumber);
    }

    private static void newEnemies(int number){
        if(number == 0) number = 1;
        switch (number){
            case 1:qtdEnemies(number, 1, 1);break;
            case 2:qtdEnemies(number, 1, 2);break;
            case 3:qtdEnemies(number, 1, 3);break;
            case 4:qtdEnemies(number, 1, 4);break;
            case 5:qtdEnemies(number, 2, 1);break;
            case 6:qtdEnemies(number, 2, 2);break;
            case 7:qtdEnemies(number, 2, 3);break;
            case 8:qtdEnemies(number, 2, 4);break;
            case 9:qtdEnemies(number, 3, 1);break;
            case 10:qtdEnemies(number, 3, 2);break;
            case 11:qtdEnemies(number, 3, 3);break;
            case 12:qtdEnemies(number, 3, 4);break;
            case 13:qtdEnemies(number, 4, 1);break;
            case 14:qtdEnemies(number, 4, 2);break;
            case 15:qtdEnemies(number, 4, 3);break;
            case 16:qtdEnemies(number, 4, 4);break;
            default:
                int choice = new Random().nextInt(5);
                if(choice > 4) choice = 4;
                if(choice == 0) choice = 1;
                int type = new Random().nextInt(4);
                if(type < 1) type = 1;
                if(type > 3) type = 3;
                qtdEnemies(number, type, choice);
                break;
        }
    }

    private static void qtdEnemies(int number, int type, int rank){
        if(number == 0) number = 1;
        if(number < 5){create(number * 2, type, rank);}
        if(number < 10){create(number + 2, type, rank);}
        if(number < 15){create(number, type, rank);}
        else{
            Enemy e1, e2;
            int newRank = new Random().nextInt(5);
            if(newRank == 0) newRank = 1;
            if(newRank > 4) newRank = 4;

            int newType = new Random().nextInt(5);
            if(newType == 0) newType = 1;
            if(newType > 4) newType = 4;

            int qtd = number /3;

            for(int i = 0; i < qtd; i++){
                e1 = new Enemy(type, rank);
                e2 = new Enemy(newType, newRank);
                enemies.add(e1);
                enemies.add(e2);
            }
        }
    }

    private static void create(int number, int type, int rank){
        for(int i = 0; i < number; i++){
            Enemy e = new Enemy(type, rank);
            enemies.add(e);
        }
    }

    private void updateEnemies(){
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).update();
        }
    }

    @Override
    public void update() {
        if(enemies.size() != 0) updateEnemies();
    }

    @RequiresApi(api = 29)
    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).draw(canvas);
        }
    }
}
