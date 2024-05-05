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

public class WingManSprite {

    private Sprite sprite;
    private SpriteBatch renderer;
    private BodyDef bf = new BodyDef();
    private Body planeBody;
    private World world;

    private ArrayList<Body> bodyRemover;
    ArrayList<Bullet> bulletManager = new ArrayList<>();
    float timeSeconds = 0f;
    float weaponFireDelay = 0.1f;
    private boolean canShoot = true;
    
    private BluePlaneSprite player;
    private float timeLimit = 2f;
    private float limitCounter = 0f;
    

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

    public void planeMovement(){
        if(Plane.isAbleToReset){
            planeBody.setTransform(player.getPos().x - 16, player.getPos().y - 16, 0);
        }
        sprite.setCenter(planeBody.getPosition().x, planeBody.getPosition().y);
    }

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

    public void update(){
        planeMovement();
        sprite.draw(renderer);
        weaponControl();
        timeLimitDespawn();
    }

    public void deactivate(){
        bulletManager.clear();
    }

}
