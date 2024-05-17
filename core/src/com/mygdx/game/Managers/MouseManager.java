package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Background.Button;

public class MouseManager {

    public float mouseX;
    public float mouseY;

    public MouseManager(){

    }

    public void update(){
        mouseX = Gdx.input.getX();
        mouseY = 480 - Gdx.input.getY();
    }

    public String getMousePos(){
        return "Mouse X : " + mouseX + "\nMouse Y : " + mouseY;
    }

    public boolean checkPos(Button b){
        return (b.leftX < mouseX && mouseX < b.rightX) && (b.bottomY < mouseY && mouseY < b.topY);
    }

    public boolean checkMouseButtonClick(Button b){
        update();
        return (b.leftX < mouseX && mouseX < b.rightX) && (b.bottomY < mouseY && mouseY < b.topY) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
    }

}
