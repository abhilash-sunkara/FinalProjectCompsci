package com.mygdx.game.Projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class EnemyBullet {

    public Sprite sprite;
    public SpriteBatch renderer;
    public boolean isActive = true;
    private BodyDef bd = new BodyDef();
    private World world;
    private Body body;
    private Fixture f;

    public EnemyBullet(String imgFile, SpriteBatch spriteBatch, World world){
        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        sprite.setScale(0.0625f);
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

    public void bulletMovement(){
        body.setLinearVelocity(0, -80);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
    }

    public void update(){
        if(isActive){
            bulletMovement();
            sprite.draw(renderer);
        }
    }

    public EnemyBullet setPosition(float x, float y){
        sprite.setPosition(x, y);
        body.setTransform(x, y, 0);
        return this;
    }

    public void destroy(){
        isActive = false;
        f.setSensor(true);
    }



}
