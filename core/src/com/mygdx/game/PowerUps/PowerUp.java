package com.mygdx.game.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Powerup Class
 */
public class PowerUp {

    /**
     * Image file path for sprite texture
     */
    protected String imgFile;
    /**
     * Sprite object used for storing texture and images
     */
    protected Sprite sprite;
    /**
     * SpriteBatch object used for rendering sprite
     */
    protected SpriteBatch renderer;

    /**
     * BodyDef for collisions
     */
    protected BodyDef bf = new BodyDef();
    /**
     * Body for tracking collisions
     */
    protected Body planeBody;

    /**
     * World to track different bodies
     */
    protected World world;

    /**
     * Checks if power up is active
     */
    protected boolean isActive = true;

    /**
     * Constructor for PowerUp object
     * @param batch SpriteBatch used for rendering
     * @param world World used for tracking bodies
     * @param img Image filepath
     */
    public PowerUp(SpriteBatch batch, World world, String img){
        imgFile = img;
        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        renderer = batch;
        bf.type = BodyDef.BodyType.KinematicBody;
        this.world = world;
        planeBody = world.createBody(bf);
        planeBody.setUserData(sprite);
        planeBody.setLinearDamping(2.0f);
        planeBody.setUserData(this);

        float randomX = (float) Math.random() * 550;

        sprite.setPosition(randomX, 500);
        planeBody.setTransform(randomX, 500, 0f);

        CircleShape cs = new CircleShape();
        cs.setRadius(16f);
        FixtureDef fd = new FixtureDef();
        fd.filter.categoryBits = 0x0002;
        fd.filter.maskBits = 0x0003;
        fd.filter.groupIndex = -2;
        fd.shape = cs;
        Fixture fixture = planeBody.createFixture(fd);
        fixture.setUserData(this);

        fixture.setSensor(true);
    }


    /**
     * Update method that draws and moves powerup
     */
    public void update(){
        planeBody.setLinearVelocity(0f, -20f);
        sprite.setCenter(planeBody.getPosition().x, planeBody.getPosition().y);
        sprite.draw(renderer);
    }

    /**
     * checks if powerup is active
     * @return state of powerup
     */
    public boolean getActive(){
        return isActive;
    }

    /**
     * Destroys powerup
     */
    public void destroy(){
        isActive = false;
    }

}
