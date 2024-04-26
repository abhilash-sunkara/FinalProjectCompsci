package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.PowerUps.WingMan;

public class PowerUpManager {

    private SpriteBatch batch;
    private World world;

    private Array<WingMan> wingManPowerUp = new Array<>();

    private float spawnDelay = 5f;
    private float spawnTime;

    public PowerUpManager(SpriteBatch sb, World w){
        batch = sb;
        world = w;
    }

    public void update(){
        spawnTime += Gdx.graphics.getDeltaTime();


        if(spawnTime > spawnDelay){
            spawnWingMan();
            spawnTime -= spawnTime;
        }

        for(int i = 0; i < wingManPowerUp.size; i++){
            wingManPowerUp.get(i).update();
        }
    }

    public void spawnWingMan(){
        //System.out.println("ran powerup spawn");
        wingManPowerUp.add(new WingMan(batch, world));
    }


}
