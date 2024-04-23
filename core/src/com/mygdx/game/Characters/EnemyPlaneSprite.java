package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Collisions.BulletCollision;
import com.mygdx.game.Projectiles.EnemyBullet;

import java.util.ArrayList;

public class EnemyPlaneSprite {

    private Sprite sprite;
    private SpriteBatch renderer;
    private BodyDef bd = new BodyDef();
    private World w;

    public Body body;

    private float MAX_VEL = 20f;

    private float timer = 0f;
    private float bulletTimer = 0f;
    private ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();

    private ArrayList<Body> bodyRemover;

    public boolean isActive = true;

    private Fixture f;

    public EnemyPlaneSprite(String imgFile, SpriteBatch batch, World world, ArrayList<Body> ar){
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
    }

    public void enemyMovement(){
        body.setLinearVelocity(0, -MAX_VEL);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
    }

    public EnemyPlaneSprite setStartPos(float x, float y){
        body.setTransform(new Vector2(x, y), 0);
        sprite.setPosition(x, y);
        return this;
    }

    public void weaponControl(){
        boolean canShoot = false;
        timer += Gdx.graphics.getDeltaTime();
        if(timer > 1.2){
            /* 
            enemyBullets.add(new EnemyBullet("jTracer.png", renderer, w).setPosition(sprite.getX(), sprite.getY()));
            
            bulletTimer += Gdx.graphics.getDeltaTime();
            for(int i = 0; i < 5; i++){
                if(bulletTimer > 0.5){
                    enemyBullets.add(new EnemyBullet("jTracer.png", renderer, w).setPosition(sprite.getX(), sprite.getY()));
                }
            }
            timer = 0;
            */

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

    public void destroy(){
        isActive = false;
        f.setSensor(true);
    }

    public void update(){
        if(isActive){
            enemyMovement();
            weaponControl();
            sprite.draw(renderer);
        }
    }

}
