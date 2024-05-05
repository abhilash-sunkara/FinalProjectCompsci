package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.PowerUps.*;
import com.badlogic.gdx.utils.Queue;



public class PowerUpManager {

    private SpriteBatch batch;
    private World world;

    private Array<PowerUp> spawnedPowerUps = new Array<>();

    private Queue<Spawnables> powerUps = new Queue<>();


    private float spawnDelay = 5f;
    private float spawnTime;

    public PowerUpManager(SpriteBatch sb, World w){
        batch = sb;
        world = w;
        initializePowerUps();
    }

    public void initializePowerUps(){
        for(int i = 0; i < 4; i++){
            addRandom();
        }
        System.out.println(powerUps.size);
    }

    public void update(){
        spawn();
    }

    public void spawn(){
        Spawnables spawnPowerUpSpawn;

        spawnTime += Gdx.graphics.getDeltaTime();

        if(spawnTime > spawnDelay){
            System.out.println(powerUps.size);
            spawnPowerUpSpawn = powerUps.removeFirst();
            switch (spawnPowerUpSpawn){
                case WINGMAN:
                    spawnedPowerUps.add(new WingMan(batch, world, "ship_0020.png"));
                    System.out.println("spawn");
                    break;
                case MACHINEGUN:
                    spawnedPowerUps.add(new MachineGun(batch, world, "MachineGun.png"));
                    System.out.println("spawn");
                    break;
                case EXTRASPEED:
                    spawnedPowerUps.add(new ExtraSpeed(batch, world, "ExtraSpeed.png"));
                    System.out.println("spawn");
                    break;
                case CARPETBOMB:
                    spawnedPowerUps.add(new CarpetBomb(batch, world, "CarpetBomb.png"));
                    System.out.println("spawn");
                    break;
                default:
                    System.out.println("Powerup not recognized");
                    break;
            }
            spawnTime -= spawnDelay;
        }

        for(int i = 0; i < spawnedPowerUps.size; i++){
            spawnedPowerUps.get(i).update();
        }
    }

    public void addRandom(){
        int randomPowerUp = (int) (Math.random() * 4);
        if (randomPowerUp == 0){
            powerUps.addLast(Spawnables.WINGMAN);
        } else if (randomPowerUp == 1){
            powerUps.addLast(Spawnables.CARPETBOMB);
        } else if (randomPowerUp == 2){
            powerUps.addLast(Spawnables.EXTRASPEED);
        } else if (randomPowerUp == 3){
            powerUps.addLast(Spawnables.MACHINEGUN);
        }
    }

    public enum Spawnables{
        WINGMAN, MACHINEGUN, EXTRASPEED, CARPETBOMB
    }


}
