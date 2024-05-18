package com.mygdx.game.Projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Bullet object
 */
public class Bullet {

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
    private BodyDef bd = new BodyDef();
    /**
     * World object to track active bodies
     */
    private World world;
    /**
     * Body object for collisions
     */
    public Body body;
    /**
     * Fixture object to control collisions
     */
    private Fixture f;

    /**
     * Constructor for Bullet
     * @param imgFile image filepath
     * @param spriteBatch SpriteBatch to render sprites
     * @param world World to track bodies
     */
    public Bullet(String imgFile, SpriteBatch spriteBatch, World world){
        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        sprite.setScale(0.046875f);
        renderer = spriteBatch;
        bd.type = BodyDef.BodyType.DynamicBody;
        this.world = world;
        body = world.createBody(bd);
        body.setUserData(sprite);
        body.setLinearDamping(2.0f);

        CircleShape cs = new CircleShape();
        cs.setRadius(10f);
        FixtureDef fd = new FixtureDef();
        fd.filter.categoryBits = 0x0003;
        fd.filter.maskBits = 0x0002;
        fd.filter.groupIndex = -1;
        fd.shape = cs;
        Fixture fixture = body.createFixture(fd);
        fixture.setSensor(true);
        fixture.setUserData(this);
        f=fixture;

        //System.out.println("created");
    }

    /**
     * moves bullet using constant velocity
     */
    public void bulletMovement(){
        body.setLinearVelocity(0f, 1000f);
        if(sprite.getY() > 5000){
            isActive = false;
        }
        sprite.setPosition(body.getPosition().x - 40, body.getPosition().y);
    }

    /**
     * updates movement and drawing
     */
    public void update(){
        if(isActive){
            //System.out.println("bullet updating : " + body.getPosition().y);
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
    public Bullet setPosition(float x, float y){
        sprite.setPosition(x-50, y-100);
        body.setTransform(x, y-100, 0);
        return this;
    }

    /**
     * destroys bullet
     */
    public void destroy(){
        isActive = false;
        //f.setSensor(true);
    }

}
