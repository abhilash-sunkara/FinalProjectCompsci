package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameLevel.Plane;
import com.mygdx.game.Projectiles.Bullet;

import java.util.ArrayList;

/**
 * Wingman object that spawns when {@link com.mygdx.game.PowerUps.WingMan} is collected
 */
public class WingManSprite {

    /**
     * Sprite object that handles rendering and images
     */
    private final Sprite sprite;
    /**
     * SpriteBatch object to render sprite in main scene
     */
    private final SpriteBatch renderer;
    /**
     * Body Definition for collisions
     */
    private final BodyDef bf = new BodyDef();
    /**
     * Body for collision detection and movement
     */
    private final Body planeBody;
    /**
     * World object to track active bodies
     */
    private final World world;

    /**
     * Body remover to remove inactive bullets
     */
    private final ArrayList<Body> bodyRemover;
    /**
     * Bullet Manager that tracks active fired bullets
     */
    ArrayList<Bullet> bulletManager = new ArrayList<>();
    /**
     * Timer that tracks when wingman can shoot
     */
    float timeSeconds = 0f;
    /**
     * Time between individual shots
     */
    float weaponFireDelay = 0.1f;
    /**
     * Tracks if wingman is able to shoot bullets
     */
    private boolean canShoot = true;

    /**
     * Player object reference for movement tracking
     */
    private final BluePlaneSprite player;
    /**
     * Time limit for how long wingman is active
     */
    private final float timeLimit = 2f;
    /**
     * Tracks how long wingman has been active for
     */
    private float limitCounter = 0f;


    /**
     * WingManSprite constructor
     * @param imgFile Image filepath for render image
     * @param batch SpriteBatch for rendering in main scene
     * @param world World that tracks all active bodies
     * @param p Player object
     * @param br BodyRemover arraylist
     */
    public WingManSprite(String imgFile, SpriteBatch batch, World world, BluePlaneSprite p, ArrayList<Body> br){

        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        renderer = batch;
        bf.type = BodyDef.BodyType.DynamicBody;
        this.world = world;
        planeBody = world.createBody(bf);
        planeBody.setUserData(this);
        planeBody.setLinearDamping(2.0f);
        planeBody.setTransform(-16f, -16f, 0f);

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

        bodyRemover = br;

        player = p;
    }

    /**
     * plane movement based on player position
     */
    public void planeMovement(){
        if(Plane.isAbleToReset){
            planeBody.setTransform(player.getPos().x - 16, player.getPos().y - 16, 0);
        }
        sprite.setCenter(planeBody.getPosition().x, planeBody.getPosition().y);
    }

    /**
     * Autonomous weapon control
     */
    public void weaponControl(){
        timeSeconds += Gdx.graphics.getDeltaTime();

        if(canShoot){
            bulletManager.add(new Bullet("tracer.png", renderer, world).setPosition(sprite.getX(), sprite.getY()));
            canShoot = false;
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
     * Tracks time limit and automatically despawns wingman if it is active for too long
     */
    public void timeLimitDespawn(){
        if(player.getIsWingmanActive()){
            limitCounter += Gdx.graphics.getDeltaTime();
            if(limitCounter > timeLimit){
                player.destroyWingman();
                deactivate();
                limitCounter -= timeLimit;
            }
        }
    }

    /**
     * Called in player object every frame
     * Controls movement, timers and weapon control
     */
    public void update(){
        planeMovement();
        sprite.draw(renderer);
        weaponControl();
        timeLimitDespawn();
    }

    /**
     * Removes all active bullets when wingman despawns
     */
    public void deactivate(){
        bulletManager.clear();
    }

}
