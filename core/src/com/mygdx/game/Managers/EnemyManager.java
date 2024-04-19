package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Characters.EnemyPlaneSprite;
import java.util.ArrayList;

public class EnemyManager{
    private ArrayList<EnemyPlaneSprite> enemies; 
    SpriteBatch batch;
    float time;
    private World world;
    private float[][] spawnPositions = {{100, 300, 500}, {200, 400}, {300}};
    private int spawnWave = 0;

    public EnemyManager(SpriteBatch input, World world){
        batch = input;
        enemies = new ArrayList<>();
        time = 0;
        this.world = world;
    }

    public void update(){
        time += Gdx.graphics.getDeltaTime();
        if(time > 2){
            spawnWave = (int) (Math.random() * 3);
            for(int i = 0; i < spawnPositions[spawnWave].length; i++){
                enemies.add(new EnemyPlaneSprite("ship_0001.png", batch, world).setStartPos(spawnPositions[spawnWave][i], 440));
            }
            time -= 2;
        }
        for(int i = 0; i < enemies.size(); i++){
            if(!enemies.get(i).isActive){
                enemies.remove(i);
                i--;
            } else {
                enemies.get(i).update();
            }
        }
    }
}