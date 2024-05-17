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


/**
 * Power up spawner and updater class
 */
public class PowerUpManager {

    /**
     * SpriteBatch object to update and render powerups
     */
    private final SpriteBatch batch;
    /**
     * World object to track bodies
     */
    private final World world;


    /**
     * Tracks all spawned powerups
     */
    private final Array<PowerUp> spawnedPowerUps = new Array<>();

    /**
     * Queue that displays future powerups
     */
    private final Queue<Spawnables> powerUps = new Queue<>();

    /**
     * Texture for {@link WingMan}
     */
    private final Texture WingManTexture = new Texture(Gdx.files.internal("ship_0020.png"));

    /**
     * Texture for {@link MachineGun}
     */
    private final Texture MachineGunTexture = new Texture(Gdx.files.internal("MachineGun.png"));

    /**
     * Texture for {@link ExtraSpeed}
     */
    private final Texture ExtraSpeedTexture = new Texture(Gdx.files.internal("ExtraSpeed.png"));

    /**
     * Texture for {@link CarpetBomb}
     */
    private final Texture CarpetBombTexture = new Texture(Gdx.files.internal("CarpetBomb.png"));

    /**
     * Texture for {@link ExtraLife}
     */
    private final Texture ExtraLifeTexture = new Texture(Gdx.files.internal("ExtraLife.png"));

    /**
     * Boolean that checks if powerUp display needs to be updated
     */
    private boolean needToUpdateDisplay = true;
    /**
     * powerUp display that stores the sprites that are shown
     */
    private final Sprite[] powerUpDisplay = {new Sprite(WingManTexture), new Sprite(WingManTexture), new Sprite(WingManTexture), new Sprite(WingManTexture)};

    /**
     * Spawn delay between powerups
     */
    private final float spawnDelay = 5f;
    /**
     * Timer that checks if power-up should be spawned
     */
    private float spawnTime;

    /**
     * Boolean that checks if an extra life should be spawned based on player lives
     */
    private boolean shouldSpawnExtraLife = false;

    //Sprite test = new Sprite(new Texture(Gdx.files.internal("ship_0020.png")));

    /**
     * Constructor for PowerUpManager
     * @param sb SpriteBatch object for rendering sprites
     * @param w World object to track bodies
     */
    public PowerUpManager(SpriteBatch sb, World w){
        batch = sb;
        world = w;

        initializePowerUps();
    }

    /**
     * Initializes future powerups
     */
    public void initializePowerUps(){
        for(int i = 0; i < 4; i++){
            addRandom();
            powerUpDisplay[i].setPosition(580, ((4-i) * 40)-40);
            powerUpDisplay[i].setTexture(WingManTexture);
        }
        //System.out.println(powerUps.size);

        powerUpDisplay[0].setScale(2, 2);
    }

    /**
     * Checks if an extra life should be spawned
     */
    public void checkShouldSpawnExtraLife(){
        shouldSpawnExtraLife = BluePlaneSprite.lives < 4;
    }

    /**
     * Update-every-frame method that controls springing and displaying powerups
     */
    public void update(){
        checkShouldSpawnExtraLife();
        spawn();
        displayFuturePowerUps();
        //System.out.println(shouldSpawnExtraLife);
    }

    /**
     * Spawns a powerup based on first value in queue
     */
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

    /**
     * Adds a random powerup to future powerups queue
     */
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

    /**
     * displays future powerups
     */
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

    /**
     * Restarts all powerups
     */
    public void restart(){
        powerUps.clear();
        spawnedPowerUps.clear();
        initializePowerUps();
    }

    /**
     * Shows all types of powerups that can be spawned
     */
    public enum Spawnables{
        WINGMAN, MACHINEGUN, EXTRASPEED, CARPETBOMB, EXTRALIFE
    }


}
