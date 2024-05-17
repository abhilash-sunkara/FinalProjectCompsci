package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.GameLevel.Plane;
import com.mygdx.game.Managers.EnemyManager;
import com.mygdx.game.Projectiles.Bullet;


import java.util.ArrayList;

/**
 * Main player class
 * Controls movement, weapons, resetting and powerup effects
 */
public class BluePlaneSprite {

    /**
     * Sprite object that handles rendering and textures
     */
    public Sprite sprite;
    /**
     * SpriteBatch renderer used for rendering in main scene
     */
    private final SpriteBatch renderer;

    /**
     * Holds all active player bullets and iterates through them to update them
     */
    ArrayList<Bullet> bulletManager = new ArrayList<>();
    /**
     * Burst fire control boolean for first bullet
     */
    private boolean shotFirst;
    /**
     * Burst fire control boolean for second bullet
     */
    private boolean shotSecond;
    /**
     * Burst fire control boolean for third bullet
     */
    private boolean shotThird;
    /**
     * Burst fire control boolean for fourth bullet
     */
    private boolean shotFourth;
    /**
     * Burst fire control boolean for fifth bullet
     */
    private boolean shotFifth;
    /**
     * Burst fire timer to decide when player can shoot bullets
     */
    private float burstTimer;
    /**
     * Time between individual burst bullets
     */
    private float burstDelay = 0.075f;

    /**
     * Enum to track firemode of player
     * @see FIREMODE
     */
    private FIREMODE firemode = FIREMODE.BURST;
    /**
     * Time limit for machine gun power up
     */
    private final float machineGunTimeLimit = 5f;
    /**
     * Timer to track machine gun power up time
     */
    private float machineGunTimeSeconds = 0f;

    /**
     * Timer to track when player can shoot
     */
    float timeSeconds = 0f;
    /**
     * Minimum time between burst fires
     */
    float weaponFireDelay = 1f;

    /**
     * Timer to track when machine gun firemode fires
     */
    float machineGunFireTimer = 0f;
    /**
     * Time between individual machine gun bullets
     */
    float machineGunFireDelay = 0.2f;

    /**
     * Boolean to check when extra speed powerup is active
     */
    boolean extraSpeedActive = false;
    /**
     * Time limit for extra speed powerup
     */
    float extraSpeedTimeLimit = 3f;
    /**
     * Timer to track how long extra speed is active
     */
    float extraSpeedTimer = 0f;
    /**
     * Tracks if player can shoot bullets
     */
    private boolean canShoot = true;
    /**
     * Body definition for colliders
     */
    private final BodyDef bf = new BodyDef();
    /**
     * Body object used for collision detection and movement
     */
    private final Body planeBody;
    /**
     * Max velocity of plane
     */
    private float MAX_VEL = 2000f;
    /**
     * Force and acceleration added to the player during movement
     */
    private float addedForce = 400;
    /**
     * World object to track all active bodies in the game
     */
    private final World world;
    /**
     * Ambient background music
     */
    private final Music browningMusic;
    /**
     * Music played during weapon fire
     */
    private final Music burstMusic;

    /**
     * Tracks player lives
     */
    public static int lives = 4;
    /**
     * Tracks if player should reset position
     */
    private boolean shouldReset = false;

    /**
     * External ArrayList to track inactive bullets and remove them
     */
    private final ArrayList<Body> bodyRemover;

    /**
     * Checks if wingman powerup is active
     */
    public boolean isWingManActive = false;
    /**
     * Wingman powerup object
     */
    WingManSprite wingman;

    /**
     * Tracks all active enemy planes
     */
    EnemyManager enemyManager;

    /**
     * Time limit for invincibility when game is started
     */
    public float invincibilityTime = 1f;
    /**
     * Checks time until invincibility is over
     */
    public float invincibilityTimer = 0f;

    /**
     * Max y position
     */
    private final float boundTopY = 440;
    /**
     * Min y position
     */
    private final float boundBottomY = 40;
    /**
     * Max x position
     */
    private final float boundRightX = 600;
    /**
     * Min x position
     */
    private final float boundLeftX = 36;

    /**
     * Constructor for player object
     * @param imgFile String filepath to image used for rendering
     * @param batch SpriteBatch used for rendering in main scene
     * @param world World object used to track active bodies and collisisons
     * @param ar Body remover arraylist
     * @param em Enemy manager object
     */
    public BluePlaneSprite(String imgFile, SpriteBatch batch, World world, ArrayList<Body> ar, EnemyManager em){
        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        renderer = batch;
        bf.type = BodyType.DynamicBody;
        this.world = world;
        planeBody = world.createBody(bf);
        planeBody.setUserData(sprite);
        planeBody.setLinearDamping(2.0f);
        planeBody.setUserData(this);

        planeBody.setTransform(300, 80, 0);

        CircleShape cs = new CircleShape();
        cs.setRadius(6f);
        FixtureDef fd = new FixtureDef();
        fd.filter.categoryBits = 0x0003;
        fd.filter.maskBits = 0x0002;
        fd.filter.groupIndex = -1;
        fd.shape = cs;
        Fixture fixture = planeBody.createFixture(fd);
        fixture.setUserData(this);
        System.out.println(fixture);

        bodyRemover = ar;

        wingman = new WingManSprite("ship_0008.png", batch, world, this, bodyRemover);
        browningMusic = Gdx.audio.newMusic(Gdx.files.internal("browning.mp3"));
        burstMusic = Gdx.audio.newMusic(Gdx.files.internal("burst.mp3"));

        enemyManager = em;
    }

    /**
     * Tracks plane movement using keyboard input, max velocity and position bounds
     */
    public void planeMovement(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && planeBody.getLinearVelocity().x < MAX_VEL && planeBody.getPosition().x < boundRightX){
            planeBody.applyForceToCenter(addedForce, 0f, true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && planeBody.getLinearVelocity().x > -MAX_VEL && planeBody.getPosition().x > boundLeftX) {
            planeBody.applyForceToCenter(-addedForce, 0f, true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && planeBody.getLinearVelocity().y < MAX_VEL && planeBody.getPosition().y < boundTopY) {
            planeBody.applyForceToCenter(0f, addedForce, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && planeBody.getLinearVelocity().y > -MAX_VEL && planeBody.getPosition().y > boundBottomY){
            planeBody.applyForceToCenter(0f, -addedForce, true);
        }


        sprite.setCenter(planeBody.getPosition().x, planeBody.getPosition().y);

    }

    /**
     * Weapon control for autonomous machine gun weapon control
     */
    public void machineGunWeaponControl(){

        machineGunFireTimer += Gdx.graphics.getDeltaTime();

        if(canShoot){
            bulletManager.add(new Bullet("tracer.png", renderer, world).setPosition(planeBody.getPosition().x, planeBody.getPosition().y));
            canShoot = false;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            browningMusic.setLooping(true);
            browningMusic.play();
        } else{
            browningMusic.pause();
        }

        if(machineGunFireTimer > machineGunFireDelay){
            canShoot = true;
            machineGunFireTimer -= machineGunFireDelay;
        }


        for(int i = 0; i < bulletManager.size(); i++){
            if(!bulletManager.get(i).isActive){
                bodyRemover.add(bulletManager.get(i).body);
                bulletManager.remove(i);
                i--;
            } else{
                bulletManager.get(i).update();
            }
        }

    }

    /**
     * Weapon control for manual burst weapon control
     */
    public void burstWeaponControl(){
        timeSeconds += Gdx.graphics.getDeltaTime();
        burstTimer += Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && canShoot){
            bulletManager.add(new Bullet("tracer.png", renderer, world).setPosition(sprite.getX() + 267, sprite.getY() + 200));
            shotFirst = true;
            canShoot = false;
            burstTimer = 0;
            burstMusic.play();
        }

        if(shotFirst && burstTimer > burstDelay){
            bulletManager.add(new Bullet("tracer.png", renderer, world).setPosition(sprite.getX() + 267, sprite.getY() + 200));
            shotFirst = false;
            shotSecond = true;
            burstTimer = 0;
        }

        if(shotSecond && burstTimer > burstDelay){
            bulletManager.add(new Bullet("tracer.png", renderer, world).setPosition(sprite.getX() + 267, sprite.getY() + 200));
            shotSecond = false;
            shotThird = true;
            burstTimer = 0;
        }

        if(shotThird && burstTimer > burstDelay){
            bulletManager.add(new Bullet("tracer.png", renderer, world).setPosition(sprite.getX() + 267, sprite.getY() + 200));
            shotThird = false;
            shotFourth = true;
            burstTimer = 0;
        }

        if(shotFourth && burstTimer > burstDelay){
            bulletManager.add(new Bullet("tracer.png", renderer, world).setPosition(sprite.getX() + 267, sprite.getY() + 200));
            shotFourth = false;
            shotFifth = true;
            burstTimer = 0;
        }

        if(shotFifth){
            shotFirst = false;
            shotSecond = false;
            shotThird = false;
            shotFourth = false;
            shotFifth = false;
            timeSeconds = 0;
            canShoot = false;
            burstMusic.stop();
        }

        if(timeSeconds > weaponFireDelay){
            canShoot = true;
            timeSeconds -= weaponFireDelay;
        }

        for(int i = 0; i < bulletManager.size(); i++){
            if(!bulletManager.get(i).isActive){
                bodyRemover.add(bulletManager.get(i).body);
                bulletManager.remove(i);
                i--;
            } else{
                bulletManager.get(i).update();
            }
        }

    }


    /**
     * Update method that tracks movement, weapon control and lives
     * Called every frame in main game class
     */
    public void update(){
        planeMovement();
        weaponControl();
        setSpeed();
        invincibilityTimer += Gdx.graphics.getDeltaTime();
        if(invincibilityTimer < invincibilityTime){
            lives = 4;
        }
        if(isWingManActive){
            wingman.update();
        }
        sprite.draw(renderer);
        reset();
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)){
            lives -= 1;
        }
    }

    /**
     * Switches between weapon firemodes
     */
    public void weaponControl(){
        if(firemode == FIREMODE.BURST){
            burstWeaponControl();
        } else if (firemode == FIREMODE.MACHINEGUN){
            machineGunWeaponControl();
            machineGunTimeSeconds += Gdx.graphics.getDeltaTime();
        }

        if(machineGunTimeSeconds > machineGunTimeLimit){
            firemode = FIREMODE.BURST;
            machineGunTimeSeconds -= machineGunTimeLimit;
        }
    }

    /**
     * Resets plane when a life is lost
     * If a wingman is active, the wingman powerup is deactivated
     */
    public void reset(){
        if(!isWingManActive && shouldReset) {
            if (Plane.isAbleToReset) {
                planeBody.setTransform(300, 80, 0f);
                lives--;
                shouldReset = false;
            }
        } else if (shouldReset) {
            destroyWingman();
            shouldReset = false;
        }

    }

    /**
     * Increases speed when a {@link com.mygdx.game.PowerUps.ExtraSpeed} power up is collected
     */
    public void setExtraSpeed(){
        extraSpeedActive = true;
    }

    /**
     * Adds an extra life to the player when a {@link com.mygdx.game.PowerUps.ExtraLife} power up is collected
     */
    public void addLife(){
        if(lives < 4){
            lives+=1;
        }
    }

    /**
     * Controls extra speed and time when extra speed is active
     */
    public void setSpeed(){
        if(extraSpeedActive){
            addedForce = 1200;
            MAX_VEL = 4000;
            extraSpeedTimer += Gdx.graphics.getDeltaTime();
            if(extraSpeedTimer > extraSpeedTimeLimit){
                extraSpeedActive = false;
                addedForce = 400;
                MAX_VEL = 2000;
                extraSpeedTimer -= extraSpeedTimeLimit;
            }
        }
    }

    /**
     * Resets every value when game is restarted from end screens
     */
    public void restart(){
        BluePlaneSprite.lives = 4;
        isWingManActive = false;
        invincibilityTimer = 0;
        shouldReset = true;
    }

    /**
     * Clears all enemies when a {@link com.mygdx.game.PowerUps.CarpetBomb} powerup is picked up
     */
    public void clearEnemies(){
        enemyManager.clearAllEnemies();
    }

    /**
     * Decides if player should reset
     */
    public void resetToggle(){
        shouldReset = true;
    }

    /**
     * returns player sprite which handles image information
     * @return player sprite
     */
    public Sprite getSprite(){
        return sprite;
    }

    /**
     * Spawns a wingman when a {@link com.mygdx.game.PowerUps.WingMan} power up is collected
     */
    public void spawnWingman(){
        isWingManActive = true;
    }

    /**
     * Destroys wingman when time limit is up
     */
    public void destroyWingman(){
        isWingManActive = false;
    }

    /**
     * Returns if a wingman is currently spawned in the game
     * @return current state of wingman
     */
    public boolean getIsWingmanActive(){
        return isWingManActive;
    }

    /**
     * Returns position of sprite for wingman movement
     * @return Position of plane
     */
    public Vector2 getPos(){
        return planeBody.getTransform().getPosition();
    }

    /**
     * Sets firemode to machine gun mode when a {@link com.mygdx.game.PowerUps.MachineGun} power up is collected
     */
    public void setFireMode(){
        firemode = FIREMODE.MACHINEGUN;
    }

    /**
     * Enum that tracks firemode of plane
     */
    public enum FIREMODE{
        BURST, MACHINEGUN
    }

}