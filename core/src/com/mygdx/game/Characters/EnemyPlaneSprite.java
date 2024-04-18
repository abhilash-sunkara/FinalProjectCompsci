package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Collisions.BulletCollision;

public class EnemyPlaneSprite {

    private Sprite sprite;
    private SpriteBatch renderer;
    private int directionModifier = 1;
    private BodyDef bd = new BodyDef();

    private Body body;

    private float MAX_VEL = 1000f;

    public EnemyPlaneSprite(String imgFile, SpriteBatch batch, World world){
        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        renderer = batch;
        sprite.setFlip(false, true);
        sprite.setY(300);

        bd.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bd);
        body.setTransform(0, 300, 0);

        CircleShape cs = new CircleShape();
        cs.setRadius(6f);
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        Fixture fixture = body.createFixture(fd);
        fixture.setUserData(this);
        //System.out.println("Enemy fixture : " + fixture);

        world.setContactListener(new BulletCollision());
    }

    /*
    public void enemyMovement(){
        sprite.translateX(2 * directionModifier);
        if(sprite.getX() > 600){
            directionModifier *= -1;
        } else if (sprite.getX() < 0){
            directionModifier *= -1;
        }
    }
    */


    public void enemyMovement(){
        body.setLinearVelocity(MAX_VEL * directionModifier, 0);
        if(body.getPosition().x > 600 && MAX_VEL > 0){
            MAX_VEL *= -1;
        } else if(body.getPosition().x < 0 && MAX_VEL < 0){
            MAX_VEL *= -1;
        }
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
    }



    /*
    public void bulletCollision(){
        if(sprite.getBoundingRectangle().overlaps())
    }
    */


    public void update(){
        enemyMovement();
        sprite.draw(renderer);
    }

}
