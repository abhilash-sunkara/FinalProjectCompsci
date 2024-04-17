package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class BluePlaneSprite {

    private Sprite sprite;
    private SpriteBatch renderer;
    ArrayList<Bullet> bulletManager = new ArrayList<>();
    float timeSeconds = 0f;
    float weaponFireDelay = 1f;
    private boolean canShoot = true;

    public BluePlaneSprite(String imgFile, SpriteBatch batch){
        sprite = new Sprite(new Texture(Gdx.files.internal(imgFile)));
        renderer = batch;
    }

    public void planeMovement(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            sprite.translateY(2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            sprite.translateY(-2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            sprite.translateX(2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            sprite.translateX(-2);
        }
    }

    public void weaponControl(){

        timeSeconds += Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && canShoot){
            bulletManager.add(new Bullet("largeBullet.png", renderer).setPosition(sprite.getX() + 16, sprite.getY() + 20));
            canShoot = false;
        }
        if(timeSeconds > weaponFireDelay){
            canShoot = true;
            timeSeconds -= weaponFireDelay;
        }

        for(int i = 0; i < bulletManager.size(); i++){
            if(!bulletManager.get(i).isActive){
                bulletManager.remove(i);
                i--;
            } else{
                bulletManager.get(i).update();

            }
        }

    }

    public void update(){
        planeMovement();
        weaponControl();
        sprite.draw(renderer);
    }

}
