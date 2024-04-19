package com.mygdx.game.Projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Bullet {
    //initial push
    public Sprite sprite;
    public SpriteBatch renderer;
    public boolean isActive = true;
    private BodyDef bd = new BodyDef();
    private World world;
    private Body body;
    private Fixture f;

    public Bullet(String imgFile, SpriteBatch spriteBatch, World world){
        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        renderer = spriteBatch;
        bd.type = BodyDef.BodyType.DynamicBody;
        this.world = world;
        body = world.createBody(bd);
        body.setUserData(sprite);
        body.setLinearDamping(2.0f);

        CircleShape cs = new CircleShape();
        cs.setRadius(6f);
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        Fixture fixture = body.createFixture(fd);
        fixture.setUserData(this);
        f=fixture;
    }

    public void bulletMovement(){
        body.setLinearVelocity(0f, 1000f);
        if(sprite.getY() > 500){
            isActive = false;
        }
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
    }

    public void update(){
        if(isActive){
            bulletMovement();
            sprite.draw(renderer);
        }
    }

    public Bullet setPosition(float x, float y){
        sprite.setPosition(x, y);
        body.setTransform(x, y, 0);
        return this;
    }

    public void destroy(){
        isActive = false;
        f.setSensor(true);
    }

}
