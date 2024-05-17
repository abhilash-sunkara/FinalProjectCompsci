package com.mygdx.game.PowerUps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

/**
 * ExtraLife powerup class
 */
public class ExtraLife extends PowerUp{

    public ExtraLife(SpriteBatch sb, World w, String img){
        super(sb, w, img);
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
