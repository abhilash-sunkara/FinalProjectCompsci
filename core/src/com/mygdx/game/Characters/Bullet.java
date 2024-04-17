package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
    //initial push
    public Sprite sprite;
    public SpriteBatch renderer;
    public boolean isActive = true;

    public Bullet(String imgFile, SpriteBatch spriteBatch){
        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        renderer = spriteBatch;
        sprite.setScale(1);
    }

    public void bulletMovement(){
        sprite.translateY(8);
        if(sprite.getY() > 300){
            isActive = false;
        }
    }

    public void update(){
        if(isActive){
            bulletMovement();
            sprite.draw(renderer);
        }
    }

    public Bullet setPosition(float x, float y){
        sprite.setPosition(x, y);
        return this;
    }

}
