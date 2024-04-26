package com.mygdx.game.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class WingMan {

    public String imgFile = "ship_0020.png";

    private Sprite sprite;
    private SpriteBatch renderer;

    private BodyDef bf = new BodyDef();
    private Body planeBody;

    private World world;

    public boolean isActive = true;

    public WingMan(SpriteBatch batch, World world){
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

        //sprite.setScale(0.01f, 0.01f);

        CircleShape cs = new CircleShape();
        cs.setRadius(16f);
        FixtureDef fd = new FixtureDef();
        fd.filter.categoryBits = 0x0002;
        fd.filter.maskBits = 0x0003;
        fd.filter.groupIndex = -2;
        fd.shape = cs;
        Fixture fixture = planeBody.createFixture(fd);
        fixture.setUserData(this);
        //System.out.println(fixture);
    }

    public void update(){
        planeBody.setLinearVelocity(0f, -20f);
        sprite.setCenter(planeBody.getPosition().x, planeBody.getPosition().y);
        sprite.draw(renderer);
    }

    public void destroy(){
        isActive = false;
    }







}
