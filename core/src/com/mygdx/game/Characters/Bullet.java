package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
        cs.setRadius(32f);
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        Fixture fixture = body.createFixture(fd);
        fixture.setUserData(this);
        //fixture.setFilterData(new Filter());
    }

    /*
    public void bulletMovement(){
        sprite.translateY(8);
        if(sprite.getY() > 300){
            isActive = false;
        }
    }
    */


    public void bulletMovement(){
        body.setLinearVelocity(0f, 100f);
        if(sprite.getY() > 500){
            isActive = false;
        }
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
    }

    public void update(){
        //System.out.println(f);
        if(isActive){
            //planeMovement();
            bulletMovement();
            sprite.draw(renderer);
        }
    }

    /*
    public void planeMovement(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            body.applyForceToCenter(1000.0f, 0f, true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            body.applyForceToCenter(-1000.0f, 0f, true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) ) {
            body.applyForceToCenter(0f, 1000.0f, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            body.applyForceToCenter(0f, -1000.0f, true);
        }
        //System.out.println(planeBody.getPosition().x);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
    }
    */


    public Bullet setPosition(float x, float y){
        sprite.setPosition(x, y);
        body.setTransform(x, y, 0);
        return this;
    }

    public void destroy(){
        isActive = false;
    }

}
