package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Characters.EnemyPlaneSprite;
import com.mygdx.game.GameLevel.Plane;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EnemyManager{
    private final ArrayList<EnemyPlaneSprite> enemies;
    SpriteBatch batch;
    float time;
    private final World world;
    private final float[][] spawnPositions = {{100, 300, 500}, {200, 400}, {300}};
    private int spawnWave = 0;

    private final ArrayList<Body> bodyRemover;

    public static int enemiesEscaped = 0;

    public EnemyManager(SpriteBatch input, World world, ArrayList<Body> ar){
        batch = input;
        enemies = new ArrayList<>();
        time = 0;
        this.world = world;

        bodyRemover = ar;
    }

    public void update(){
        time += Gdx.graphics.getDeltaTime();
        if(time > 2){
            spawnWave = (int) (Math.random() * 3);
            for(int i = 0; i < spawnPositions[spawnWave].length; i++){
               int rand = (int)(Math.random()*6) + 1;
               boolean canMove = rand > 2 ? false : true;
                enemies.add(new EnemyPlaneSprite("ship_0001.png", batch, world, bodyRemover, canMove).setStartPos(spawnPositions[spawnWave][i], 440));
            }
            time -= 2;
        }
        for(int i = 0; i < enemies.size(); i++){
            if(!enemies.get(i).isActive){
 //               Plane.explode = true;
 //               Plane.explosionLocationx = enemies.get(i).getPosition().x;
 //               Plane.explosionLocationy = enemies.get(i).getPosition().y;
                bodyRemover.add(enemies.get(i).body);
                enemies.remove(i);

                i--;
            }  else {
                enemies.get(i).update();
                if(enemies.get(i).isOutOfBounds()){
                    bodyRemover.add(enemies.get(i).body);
                    enemies.remove(i);
                    i--;
                    enemiesEscaped++;
                    System.out.println("Total enemies escaped : " + enemiesEscaped);
                }
            }
        }
    }

    public void restart(){
        clearAllEnemies();
        EnemyManager.enemiesEscaped = 0;
    }


    public void clearAllEnemies(){
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).destroy();
            //bodyRemover.add(enemies.get(i).body);
        }
        enemies.clear();
    }
}