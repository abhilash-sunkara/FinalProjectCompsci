package com.mygdx.game.Background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {

    public float rightX;
    public float leftX;
    public float topY;
    public float bottomY;

    public float updateX;
    public float updateY;
    public float updateWidth;
    public float updateHeight;

    public Texture buttonUp;
    public Texture buttonDown;
    public Texture shownTexture;

    public boolean isClicked;

    public SpriteBatch batch;

    public Button(float maxRX, float maxLX, float maxTY, float maxBY, Texture BU, Texture BD, SpriteBatch sb, float ux, float uy, float uw, float uh){
        rightX = maxRX;
        leftX = maxLX;
        topY = maxTY;
        bottomY = maxBY;

        updateX = ux;
        updateY = uy;
        updateWidth = uw;
        updateHeight = uh;

        buttonUp = BU;
        buttonDown = BD;
        shownTexture = BU;

        batch = sb;
    }

    public void update(){
        batch.draw(shownTexture, updateX, updateY, updateWidth, updateHeight);
    }

    public void clickButton(){
        if(!isClicked){
            updateHeight -= 10;
        }
        shownTexture = buttonDown;
        isClicked = true;
        reset();
    }

    public void reset(){
        isClicked = false;
        shownTexture = buttonUp;
    }
}
