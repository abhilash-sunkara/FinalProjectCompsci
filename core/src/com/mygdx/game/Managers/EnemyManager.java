package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Characters.EnemyPlaneSprite;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EnemyManager{
    private ArrayList<EnemyPlaneSprite> enemies; 
    SpriteBatch batch;
    float time;
    private World world;
    private float[][] spawnPositions = {{100, 300, 500}, {200, 400}, {300}};
    private int spawnWave = 0;

    private ArrayList<Body> bodyRemover;

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
                enemies.add(new EnemyPlaneSprite("ship_0001.png", batch, world, bodyRemover).setStartPos(spawnPositions[spawnWave][i], 440));
            }
            time -= 2;
        }
        for(int i = 0; i < enemies.size(); i++){
            if(!enemies.get(i).isActive){
                bodyRemover.add(enemies.get(i).body);
                enemies.remove(i);
                i--;
            } else {
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
}