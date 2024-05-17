package com.mygdx.game.PowerUps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

/**
 * CarpetBomb powerup class
 */
public class CarpetBomb extends PowerUp{

    public CarpetBomb(SpriteBatch batch, World world, String img) {
        super(batch, world, img);
    }

    public void update(){
        super.update();
    }

    public boolean getActive(){
        return super.getActive();
    }

    public void destroy(){
        super.destroy();
    }
}
