package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnemyPlaneSprite {

    private Sprite sprite;
    private SpriteBatch renderer;
    private int directionModifier = 1;

    public EnemyPlaneSprite(String imgFile, SpriteBatch batch){
        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        renderer = batch;
        sprite.setFlip(false, true);
        sprite.setY(300);
    }

    public void enemyMovement(){
        sprite.translateX(2 * directionModifier);
        if(sprite.getX() > 600){
            directionModifier *= -1;
        } else if (sprite.getX() < 0){
            directionModifier *= -1;
        }
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
