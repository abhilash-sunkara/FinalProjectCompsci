package com.mygdx.game.Background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MovingBackgroundOcean {
    public static final int DEFAULT_SPEED = 80;
    public static final int ACCELERATION = 60;
    public static final int GOAL_REACH_ACCELERATION = 200;

    Texture image;
    float y1,y2;
    public int speed;
    float imageScale;
    boolean speedFixed;
    int goalSpeed;



    public MovingBackgroundOcean() {
        image = new Texture("OceanBackground.jpeg");

        y1 = 0;

        speed = 0;
        goalSpeed = DEFAULT_SPEED;
        imageScale = 800 / image.getHeight();
        y2 = image.getHeight() * imageScale;
        speedFixed = true;


    }

    public void updateAndRender (float deltaTime, SpriteBatch batch) {
        //Speed adjustment to reach goal
        if (speed < goalSpeed) {
            speed += GOAL_REACH_ACCELERATION * deltaTime;
            if (speed > goalSpeed)
                speed = goalSpeed;
        } else if (speed > goalSpeed) {
            speed -= GOAL_REACH_ACCELERATION * deltaTime;
            if (speed < goalSpeed)
                speed = goalSpeed;
        }

        if (!speedFixed)
            speed += ACCELERATION * deltaTime;

        y1 -= speed * deltaTime;
        y2 -= speed * deltaTime;

        //if image reached the bottom of screen and is not visible, put it back on top
        if (y1 + image.getHeight()*imageScale <= 0) {
            System.out.println(y1);

            y1 = y2 + image.getHeight() * imageScale;
        }
        if (y2 + image.getHeight() * imageScale <= 0)
            y2 = y1 + image.getHeight() * imageScale;

        //Render
        batch.draw(image, 0, y1, 800, image.getHeight() * imageScale);
        batch.draw(image, 0, y2, 800, image.getHeight() * imageScale);


    }

    public void setSpeed (int goalSpeed) {
        this.goalSpeed = goalSpeed;
    }

    public void setSpeedFixed (boolean speedFixed) {
        this.speedFixed = speedFixed;
    }
}
