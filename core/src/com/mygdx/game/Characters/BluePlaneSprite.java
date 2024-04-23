package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.Plane;
import com.mygdx.game.Projectiles.Bullet;

import java.util.ArrayList;

public class BluePlaneSprite {

    private Sprite sprite;
    private SpriteBatch renderer;
    ArrayList<Bullet> bulletManager = new ArrayList<>();
    float timeSeconds = 0f;
    float weaponFireDelay = 0.1f;
    private boolean canShoot = true;
    private BodyDef bf = new BodyDef();
    private Body planeBody;
    private final float MAX_VEL = 1000f;
    private World world;

    public static int lives = 3;
    private boolean shouldReset = false;

    private ArrayList<Body> bodyRemover;


    public BluePlaneSprite(String imgFile, SpriteBatch batch, World world, ArrayList<Body> ar){
        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        renderer = batch;
        bf.type = BodyType.DynamicBody;
        this.world = world;
        planeBody = world.createBody(bf);
        planeBody.setUserData(sprite);
        planeBody.setLinearDamping(2.0f);

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
    }

    /*
    public void planeMovement(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            sprite.translateY(2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            sprite.translateY(-2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            sprite.translateX(2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            sprite.translateX(-2);
        }
    }
    */

    public void planeMovement(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && planeBody.getLinearVelocity().x < MAX_VEL){
            planeBody.applyForceToCenter(400.0f, 0f, true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && planeBody.getLinearVelocity().x > -MAX_VEL) {
            planeBody.applyForceToCenter(-400.0f, 0f, true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && planeBody.getLinearVelocity().y < MAX_VEL) {
            planeBody.applyForceToCenter(0f, 400.0f, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && planeBody.getLinearVelocity().y > -MAX_VEL){
            planeBody.applyForceToCenter(0f, -400.0f, true);
        }
        //System.out.println(planeBody.getLinearVelocity().x);
        sprite.setCenter(planeBody.getPosition().x, planeBody.getPosition().y);
    }

    public void weaponControl(){

        timeSeconds += Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && canShoot){
            bulletManager.add(new Bullet("tracer.png", renderer, world).setPosition(sprite.getX() + 16, sprite.getY() + 40));
            canShoot = false;
            //System.out.println("body.x : " + planeBody.getPosition().x);
            //System.out.println("sprite.x : " + sprite.getX());
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
        sprite.draw(renderer);
        reset();
    }

    public void reset(){
        if(shouldReset && Plane.isAbleToReset){
            planeBody.setTransform(0, 0, 0f);
            lives--;
            shouldReset = false;
        }
    }

    public void resetToggle(){
        shouldReset = true;
    }

    public Sprite getSprite(){
        return sprite;
    }

}
