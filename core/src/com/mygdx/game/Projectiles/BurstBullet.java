package com.mygdx.game.Projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class BurstBullet {

    public Sprite sprite;
    public SpriteBatch renderer;
    public boolean isActive = true;
    private BodyDef bd = new BodyDef();
    private World world;
    public Body body;
    private Fixture f;

    private float timeToMove;

    private float timer;

    public BurstBullet(String imgFile, SpriteBatch spriteBatch, World world, float TTM){
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
        fixture.setUserData(this);
        f=fixture;

        timeToMove = TTM;

        System.out.println("created");
    }

    public void bulletMovement(){
        body.setLinearVelocity(0f, 1000f);
        if(sprite.getY() > 5000){
            isActive = false;
        }
        sprite.setPosition(body.getPosition().x - 40, body.getPosition().y);
    }

    public boolean canMove(){
        return timer >= timeToMove;
    }

    public void update(){
        if(!canMove()){
            timer += Gdx.graphics.getDeltaTime();
        }

        if(isActive && canMove()){
            //System.out.println("bullet updating : " + body.getPosition().y);
            bulletMovement();
            sprite.draw(renderer);
        }

        System.out.println("Bullet of TTM : " + timeToMove + ", Y pos of : " + body.getPosition().y);
    }

    public BurstBullet setPosition(float x, float y){
        sprite.setPosition(x-50, y-100);
        body.setTransform(x, y-100, 0);
        return this;
    }

    public void destroy(){
        isActive = false;
        //f.setSensor(true);
    }

}
