package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BluePlane {

    Texture img;
    Rectangle rect;

    public void create(String imgFile){
        img = new Texture(Gdx.files.internal(imgFile));

        rect = new Rectangle();
        rect.x = 800 / 2 -64 / 2;
        rect.y = 20;
        rect.width = 64;
        rect.height = 64;

    }

    public void update(SpriteBatch spriteRender){
        planeMovement();
        spriteRender.draw(img, rect.x, rect.y);

    }

    public void planeMovement(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            rect.y += 200 * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            rect.y -= 200 * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            rect.x += 200 * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            rect.x -= 200 * Gdx.graphics.getDeltaTime();
        }
    }

}
