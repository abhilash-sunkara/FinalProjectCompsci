package com.mygdx.game.Projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Enemy bullet object
 */
public class EnemyBullet {

    /**
     * Sprite object that contains images
     */
    public Sprite sprite;
    /**
     * SpriteBatch used to render sprite
     */
    public SpriteBatch renderer;
    /**
     * checks is bullet is active
     */
    public boolean isActive = true;
    /**
     * Body definition for collision
     */
    private final BodyDef bd = new BodyDef();
    /**
     * World object to track active bodies
     */
    private final World world;
    /**
     * Body object for collisions
     */
    public Body body;
    /**
     * Fixture object to control collisions
     */
    private final Fixture f;

    /**
     * Constructor for Bullet
     * @param imgFile image filepath
     * @param spriteBatch SpriteBatch to render sprites
     * @param world World to track bodies
     */
    public EnemyBullet(String imgFile, SpriteBatch spriteBatch, World world){
        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        sprite.setScale(0.046875f);
        renderer = spriteBatch;
        bd.type = BodyDef.BodyType.DynamicBody;
        this.world = world;

        body = world.createBody(bd);
        body.setLinearDamping(2.0f);
        body.setUserData(sprite);

        CircleShape cs = new CircleShape();
        cs.setRadius(6f);
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.filter.categoryBits = 0x0002;
        fd.filter.maskBits = 0x0003;
        fd.filter.groupIndex = -2;
        Fixture fixture = body.createFixture(fd);
        fixture.setUserData(this);
        f = fixture;


    }

    /**
     * moves bullet using constant velocity
     */
    public void bulletMovement(){
        body.setLinearVelocity(0, -80);
        sprite.setPosition(body.getPosition().x - 36, body.getPosition().y);
    }


    /**
     * updates movement and drawing
     */
    public void update(){
        if(isActive){
            bulletMovement();
            sprite.draw(renderer);
        }
    }

    /**
     * initializes position of bullet
     * @param x x value
     * @param y y value
     * @return self bullet
     */
    public EnemyBullet setPosition(float x, float y){
        sprite.setPosition(x, y);
        body.setTransform(x, y, 0);
        return this;
    }

    /**
     * destroys bullet
     */
    public void destroy(){
        isActive = false;
        //System.out.println("destroyed bullet");
       // f.setSensor(true);
    }

    /**
     * returns velocity of bullet
     * @return velocity value
     */
    public float getVelocity(){
        return body.getLinearVelocity().y;
    }



}
