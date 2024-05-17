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

public class BluePlaneSprite {

    public Sprite sprite;
    private final SpriteBatch renderer;
    ArrayList<Bullet> bulletManager = new ArrayList<>();
    private boolean shotFirst;
    private boolean shotSecond;
    private boolean shotThird;
    private boolean shotFourth;
    private boolean shotFifth;
    private float burstTimer;
    private float burstDelay = 0.075f;

    private FIREMODE firemode = FIREMODE.BURST;
    private final float machineGunTimeLimit = 5f;
    private float machineGunTimeSeconds = 0f;

    float timeSeconds = 0f;
    float weaponFireDelay = 1f;

    float machineGunFireTimer = 0f;
    float machineGunFireDelay = 0.2f;

    boolean extraSpeedActive = false;
    float extraSpeedTimeLimit = 3f;
    float extraSpeedTimer = 0f;
    private boolean canShoot = true;
    private final BodyDef bf = new BodyDef();
    private final Body planeBody;
    private float MAX_VEL = 2000f;
    private float addedForce = 400;
    private final World world;
    private final Music browningMusic;
    private final Music burstMusic;

    public static int lives = 4;
    private boolean shouldReset = false;

    private final ArrayList<Body> bodyRemover;

    public boolean isWingManActive = false;
    WingManSprite wingman;

    EnemyManager enemyManager;

    public float invincibilityTime = 1f;
    public float invincibilityTimer = 0f;

    private final float boundTopY = 440;
    private final float boundBottomY = 40;
    private final float boundRightX = 600;
    private final float boundLeftX = 36;

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

        //System.out.println("X Vel : " + planeBody.getLinearVelocity().x +", Y Vel : " + planeBody.getLinearVelocity().y);

        sprite.setCenter(planeBody.getPosition().x, planeBody.getPosition().y);

    }

    public void machineGunWeaponControl(){

        machineGunFireTimer += Gdx.graphics.getDeltaTime();

        //System.out.println(canShoot);

        if(canShoot){
            //System.out.println("spawning");
            //System.out.println("Sprite X : " + sprite.getX() + " Sprite Y : " + sprite.getY());
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
                //System.out.println("updating");
            }
        }

    }

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

    public void weaponControl(){
        if(firemode == FIREMODE.BURST){
            burstWeaponControl();
            //System.out.println("Firing Burst Mode");
        } else if (firemode == FIREMODE.MACHINEGUN){
            machineGunWeaponControl();
            machineGunTimeSeconds += Gdx.graphics.getDeltaTime();
            //System.out.println("Firing MachineGun Mode");
        }

        if(machineGunTimeSeconds > machineGunTimeLimit){
            firemode = FIREMODE.BURST;
            machineGunTimeSeconds -= machineGunTimeLimit;
        }
    }

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

    public void setExtraSpeed(){
        extraSpeedActive = true;
    }

    public void addLife(){
        if(lives < 4){
            lives+=1;
        }
    }

    public void removeLife(){
        lives -= 1;
    }

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

    public void restart(){
        BluePlaneSprite.lives = 4;
        //shouldReset = true;
        isWingManActive = false;
        invincibilityTimer = 0;
        shouldReset = true;
        //planeBody.setTransform(300, 80, 0);
        System.out.println("ran restart");
    }

    public void clearEnemies(){
        enemyManager.clearAllEnemies();
    }

    public void resetToggle(){
        shouldReset = true;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void spawnWingman(){
        isWingManActive = true;
    }

    public void destroyWingman(){
        isWingManActive = false;
    }

    public boolean getIsWingmanActive(){
        return isWingManActive;
    }

    public Vector2 getPos(){
        return planeBody.getTransform().getPosition();
    }

    public void setFireMode(){
        firemode = FIREMODE.MACHINEGUN;
    }

    public enum FIREMODE{
        BURST, MACHINEGUN
    }

}