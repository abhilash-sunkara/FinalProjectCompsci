package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Characters.EnemyPlaneSprite;
import java.util.ArrayList;

public class EnemyManager{
    private ArrayList<EnemyPlaneSprite> enemies; 
    SpriteBatch batch;
    float time;

    public EnemyManager(SpriteBatch input){
        batch = input;
    }

    public void create(){
        enemies = new ArrayList<EnemyPlaneSprite>();
        time = 0;
    }

    public void update(){
        time += Gdx.graphics.getDeltaTime();
        if(time % 2000 == 0){
            System.out.println("fuck java");
            EnemyPlaneSprite e = new EnemyPlaneSprite("ship_0001.png", batch);
            enemies.add(e);
        }
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).update();
        }
    }
}