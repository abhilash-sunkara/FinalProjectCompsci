package com.mygdx.game.Background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Generic Button class for Start, Restart and Info
 */

public class Button {

    /**
     * Right X bound for mouse clicks
     */
    public float rightX;
    /**
     * Left X bound for mouse clicks
     */
    public float leftX;
    /**
     * Top Y bound for mouse clicks
     */
    public float topY;
    /**
     * Bottom Y bound for mouse clicks
     */
    public float bottomY;

    /**
     * X position for rendering
     */
    public float updateX;
    /**
     * Y position for rendering
     */
    public float updateY;
    /**
     * Width value for rendering
     */
    public float updateWidth;
    /**
     * Height value for rendering
     */
    public float updateHeight;

    /**
     * Texture when button isn't clicked
     */
    public Texture buttonUp;
    /**
     * Texture when button is clicked
     */
    public Texture buttonDown;
    /**
     * Texture show when rendering, switches between {@link Button#buttonUp} and {@link Button#buttonDown}
     */
    public Texture shownTexture;

    /**
     * True when button is clicked, false when it isn't
     */
    public boolean isClicked;

    /**
     * SpriteBatch renderer to render in main scene
     */
    public SpriteBatch batch;

    /**
     * Constructor for Button
     * @param maxRX Max right bound
     * @param maxLX Max left bound
     * @param maxTY Max top bound
     * @param maxBY Max bottom bound
     * @param BU Texture for {@link Button#buttonUp}
     * @param BD Texture for {@link Button#buttonDown}
     * @param sb SpriteBatch renderer
     * @param ux x position for rendering
     * @param uy y position for rendering
     * @param uw width value for rendering
     * @param uh height value for rendering
     */
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

    /**
     * Called in main scene and draws button
     */
    public void update(){
        batch.draw(shownTexture, updateX, updateY, updateWidth, updateHeight);
    }

    /**
     * Sets isClicked to true and visually changes button to show this
     */
    public void clickButton(){
        if(!isClicked){
            updateHeight -= 10;
        }
        shownTexture = buttonDown;
        isClicked = true;
        reset();
    }

    /**
     * Resets visual changes and isClicked to false
     */
    public void reset(){
        isClicked = false;
        shownTexture = buttonUp;
        updateHeight += 10;
    }
}
