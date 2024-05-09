package com.mygdx.game.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class PowerUp {

    protected String imgFile;
    protected Sprite sprite;
    protected SpriteBatch renderer;

    protected BodyDef bf = new BodyDef();
    protected Body planeBody;

    protected World world;

    protected boolean isActive = true;

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

    public void update(){
        planeBody.setLinearVelocity(0f, -20f);
        sprite.setCenter(planeBody.getPosition().x, planeBody.getPosition().y);
        sprite.draw(renderer);
    }

    public boolean getActive(){
        return isActive;
    }

    public void destroy(){
        isActive = false;
    }

}
