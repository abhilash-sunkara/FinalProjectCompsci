package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Background.Button;

/**
 * Mouse class that tracks button clicks
 */
public class MouseManager {

    /**
     * Mouse X position
     */
    public float mouseX;
    /**
     * Mouse Y position
     */
    public float mouseY;

    /**
     * Constructor
     */
    public MouseManager(){

    }

    /**
     * Updates mouse position
     */
    public void update(){
        mouseX = Gdx.input.getX();
        mouseY = 480 - Gdx.input.getY();
    }

    /**
     * Returns mouse position for debugging
     * @return Mouse position
     */
    public String getMousePos(){
        return "Mouse X : " + mouseX + "\nMouse Y : " + mouseY;
    }

    /**
     * Checks the mouse position to see if it is over a button
     * @param b Button object that is being checked
     * @return boolean that checks if mouse is on the button
     */
    public boolean checkPos(Button b){
        return (b.leftX < mouseX && mouseX < b.rightX) && (b.bottomY < mouseY && mouseY < b.topY);
    }

    /**
     * Checks if button is clicked by mouse
     * @param b Button object that is being checked
     * @return boolean that checks if button is clicked by mouse
     */
    public boolean checkMouseButtonClick(Button b){
        update();
        return (b.leftX < mouseX && mouseX < b.rightX) && (b.bottomY < mouseY && mouseY < b.topY) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
    }

}
