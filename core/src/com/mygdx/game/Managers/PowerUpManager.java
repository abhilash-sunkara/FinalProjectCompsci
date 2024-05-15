package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Characters.BluePlaneSprite;
import com.mygdx.game.PowerUps.*;
import com.badlogic.gdx.utils.Queue;



public class PowerUpManager {

    private final SpriteBatch batch;
    private final World world;


    private final Array<PowerUp> spawnedPowerUps = new Array<>();

    private final Queue<Spawnables> powerUps = new Queue<>();


    private final Texture WingManTexture = new Texture(Gdx.files.internal("ship_0020.png"));
    private final Texture MachineGunTexture = new Texture(Gdx.files.internal("MachineGun.png"));
    private final Texture ExtraSpeedTexture = new Texture(Gdx.files.internal("ExtraSpeed.png"));
    private final Texture CarpetBombTexture = new Texture(Gdx.files.internal("CarpetBomb.png"));
    private final Texture ExtraLifeTexture = new Texture(Gdx.files.internal("ExtraLife.png"));
    private boolean needToUpdateDisplay = true;
    private final Sprite[] powerUpDisplay = {new Sprite(WingManTexture), new Sprite(WingManTexture), new Sprite(WingManTexture), new Sprite(WingManTexture)};

    private final float spawnDelay = 5f;
    private float spawnTime;

    private boolean shouldSpawnExtraLife = false;

    //Sprite test = new Sprite(new Texture(Gdx.files.internal("ship_0020.png")));

    public PowerUpManager(SpriteBatch sb, World w){
        batch = sb;
        world = w;

        initializePowerUps();
    }

    public void initializePowerUps(){
        for(int i = 0; i < 4; i++){
            addRandom();
            powerUpDisplay[i].setPosition(580, ((4-i) * 40)-40);
            powerUpDisplay[i].setTexture(WingManTexture);
        }
        //System.out.println(powerUps.size);

        powerUpDisplay[0].setScale(2, 2);
    }

    public void checkShouldSpawnExtraLife(){
        shouldSpawnExtraLife = BluePlaneSprite.lives < 4;
    }

    public void update(){
        checkShouldSpawnExtraLife();
        spawn();
        displayFuturePowerUps();
        //System.out.println(shouldSpawnExtraLife);
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
                    //System.out.println("Spawned Wingman");
                    break;
                case MACHINEGUN:
                    spawnedPowerUps.add(new MachineGun(batch, world, "MachineGun.png"));
                    //System.out.println("Spawned MachineGun");
                    break;
                case EXTRASPEED:
                    spawnedPowerUps.add(new ExtraSpeed(batch, world, "ExtraSpeed.png"));
                    //System.out.println("Spawned ExtraSpeed");
                    break;
                case CARPETBOMB:
                    spawnedPowerUps.add(new CarpetBomb(batch, world, "CarpetBomb.png"));
                    //System.out.println("Spawned CarpetBomb");
                    break;
                case EXTRALIFE:
                    spawnedPowerUps.add(new ExtraLife(batch, world, "ExtraLife.png"));
                    //System.out.println("Spawned ExtraLife");
                    break;
                default:
                    //System.out.println("Power-Up not recognized");
                    break;
            }
            spawnTime -= spawnDelay;
            addRandom();
            needToUpdateDisplay = true;
        }

        for(int i = 0; i < spawnedPowerUps.size; i++){
            spawnedPowerUps.get(i).update();
        }
    }

    public void addRandom() {
        if (!shouldSpawnExtraLife) {
            //System.out.println("ran normal");
            int randomPowerUp = (int) (Math.random() * 100);
            if (randomPowerUp < 20) {
                powerUps.addLast(Spawnables.WINGMAN);
            } else if (randomPowerUp < 30) {
                powerUps.addLast(Spawnables.CARPETBOMB);
            } else if (randomPowerUp < 60) {
                powerUps.addLast(Spawnables.EXTRASPEED);
            } else if (randomPowerUp < 100) {
                powerUps.addLast(Spawnables.MACHINEGUN);
            }
        } else {
            //System.out.println("ran lives");
            int randomPowerUp = (int) (Math.random() * 100);
            //System.out.println(randomPowerUp);
            if (randomPowerUp < 15) {
                powerUps.addLast(Spawnables.WINGMAN);
            } else if (randomPowerUp < 25) {
                powerUps.addLast(Spawnables.CARPETBOMB);
            } else if (randomPowerUp < 40) {
                powerUps.addLast(Spawnables.EXTRASPEED);
            } else if (randomPowerUp < 60) {
                powerUps.addLast(Spawnables.MACHINEGUN);
            } else if (randomPowerUp < 100){
                powerUps.addLast(Spawnables.EXTRALIFE);
            }
        }
    }

    public void displayFuturePowerUps(){
        if(needToUpdateDisplay) {
            for (int i = 0; i < 4; i++) {
                Spawnables refEnum = powerUps.get(i);
                if (refEnum == Spawnables.WINGMAN) {
                    powerUpDisplay[i].setTexture(WingManTexture);
                } else if (refEnum == Spawnables.CARPETBOMB) {
                    powerUpDisplay[i].setTexture(CarpetBombTexture);
                } else if (refEnum == Spawnables.EXTRASPEED) {
                    powerUpDisplay[i].setTexture(ExtraSpeedTexture);
                } else if (refEnum == Spawnables.MACHINEGUN) {
                    powerUpDisplay[i].setTexture(MachineGunTexture);
                } else if (refEnum == Spawnables.EXTRALIFE){
                    powerUpDisplay[i].setTexture(ExtraLifeTexture);
                }
            }
            needToUpdateDisplay = false;
        }

        for(Sprite s : powerUpDisplay){
            s.draw(batch);
        }

    }

    public enum Spawnables{
        WINGMAN, MACHINEGUN, EXTRASPEED, CARPETBOMB, EXTRALIFE
    }


}
