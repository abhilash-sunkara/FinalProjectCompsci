package com.mygdx.game.Projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Bullet {

    public Sprite sprite;
    public SpriteBatch renderer;
    public boolean isActive = true;
    private BodyDef bd = new BodyDef();
    private World world;
    public Body body;
    private Fixture f;

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

    public void bulletMovement(){
        body.setLinearVelocity(0f, 1000f);
        if(sprite.getY() > 5000){
            isActive = false;
        }
        sprite.setPosition(body.getPosition().x - 40, body.getPosition().y);
    }

    public void update(){
        if(isActive){
            //System.out.println("bullet updating : " + body.getPosition().y);
            bulletMovement();
            sprite.draw(renderer);
        }
    }

    public Bullet setPosition(float x, float y){
        sprite.setPosition(x-50, y-100);
        body.setTransform(x, y-100, 0);
        return this;
    }

    public void destroy(){
        isActive = false;
        //f.setSensor(true);
    }

}
