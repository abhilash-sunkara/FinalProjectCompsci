package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Projectiles.EnemyBullet;

import java.util.ArrayList;

/**
 * Enemy Plane Class
 */
public class EnemyPlaneSprite {

    /**
     * Sprite to hold images
     */
    private final Sprite sprite;
    /**
     * SpriteBatch to render sprite
     */
    private final SpriteBatch renderer;
    /**
     * Body Definition for collisions
     */
    private final BodyDef bd = new BodyDef();
    /**
     * World to track bodies
     */
    private final World w;

    /**
     * Body ot detect collisions
     */
    public Body body;

    /**
     * Max velocity
     */
    private final float MAX_VEL = 20f;

    /**
     * Timer to allow enemy plane to shoot
     */
    private float timer = 0f;
    /**
     * ArrayList to store all bullets
     */
    private final ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();

    /**
     * Arraulist to remove inactive bodies
     */
    private final ArrayList<Body> bodyRemover;

    /**
     * Checks if enemy is active
     */
    public boolean isActive = true;


    /**
     * Fixture to control collisions
     */
    private final Fixture f;

    /**
     * X velocity
     */
    private float X_VEL = 20;

    /**
     * Movement timer
     */
    private float pastTime;

    /**
     * checks if enemy should move
     */
    private float elapsedTime;

    /**
     * Constructor for enemy
     * @param imgFile Image filepath
     * @param batch SpriteBatch to render images
     * @param world World to track bodies
     * @param ar ArrayList for removed bodies
     * @param canMove decides if enemy can move from side to side
     */
    public EnemyPlaneSprite(String imgFile, SpriteBatch batch, World world, ArrayList<Body> ar, boolean canMove){
        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        renderer = batch;
        sprite.setFlip(false, true);
        sprite.setY(300);

        bd.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bd);
        body.setTransform(0, 400, 0);
        w= world;

        CircleShape cs = new CircleShape();
        cs.setRadius(16f);
        FixtureDef fd = new FixtureDef();
        fd.filter.categoryBits = 0x0002;
        fd.filter.maskBits = 0x0003;
        fd.filter.groupIndex = -2;
        fd.shape = cs;
        Fixture fixture = body.createFixture(fd);
        fixture.setUserData(this);

        f = fixture;


        bodyRemover = ar;



        if(canMove){
            int rand = (int)(Math.random() * 2) + 1;
            X_VEL = rand > 1 ? 20 : -20;
        }else{
            X_VEL = 0;
        }
    }

    /**
     * Controls movement
     */
    public void enemyMovement(){
        body.setLinearVelocity(X_VEL, -MAX_VEL);
        elapsedTime += Gdx.graphics.getDeltaTime();
        System.out.println(elapsedTime);
        if((Math.abs(Math.abs(body.getPosition().x)-0) < 1 || Math.abs(Math.abs(body.getPosition().x)-625) < 10) && elapsedTime - pastTime > .5 ) {
            pastTime = elapsedTime;
            X_VEL = -X_VEL;
        }
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
    }

    /**
     * Initializes position
     * @param x x position
     * @param y y position
     * @return reference to self
     */
    public EnemyPlaneSprite setStartPos(float x, float y){
        body.setTransform(new Vector2(x, y), 0);
        sprite.setPosition(x, y);
        return this;
    }

    /**
     * Controls weapon and shooting
     */
    public void weaponControl(){
        boolean canShoot = false;
        timer += Gdx.graphics.getDeltaTime();
        if(timer > 1.2){
            canShoot = true;
            timer = 0;

        }

        if(canShoot){
            Timer.schedule(new Timer.Task(){
                public void run(){
                    enemyBullets.add(new EnemyBullet("jTracer.png", renderer, w).setPosition(sprite.getX(), sprite.getY()));
                }
            }, 0.75f);
            canShoot = false;

        }

        for(int i = 0; i < enemyBullets.size(); i++){
            if(!enemyBullets.get(i).isActive){
                enemyBullets.remove(i);
                i--;
            } else{
                enemyBullets.get(i).update();
            }
        }
    }

    /**
     * destroys enemy
     */
    public void destroy(){
        isActive = false;

      //  f.setSensor(true);

        for(EnemyBullet eb : enemyBullets){
            eb.destroy();
            bodyRemover.add(eb.body);
        }

    }


    /**
     * update-every-frame method that controls movement and weapons
     */
    public void update(){
        if(isActive){
            enemyMovement();
            weaponControl();
            sprite.draw(renderer);
        }
    }

    /**
     * checks if enemy is out of bounds
     * @return boolean that is true if enemy is offscreen
     */
    public boolean isOutOfBounds(){

        return body.getPosition().y < 0;

    }

    /**
     * Gets position of enemy
     * @return position of enemy
     */
    public Vector2 getPosition(){
        return body.getPosition();
    }
}



