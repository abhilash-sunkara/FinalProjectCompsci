package com.mygdx.game.Collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Characters.BluePlaneSprite;
import com.mygdx.game.PowerUps.*;

public class PowerUpCollision implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureB().getUserData().getClass() == WingMan.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureA().getUserData();
            WingMan wing = (WingMan) contact.getFixtureB().getUserData();
            if(wing.getActive()){
                player.spawnWingman();
            }
            wing.destroy();
        } else if(contact.getFixtureB().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureA().getUserData().getClass() == WingMan.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureB().getUserData();
            WingMan wing = (WingMan) contact.getFixtureA().getUserData();
            if(wing.getActive()){
                player.spawnWingman();
            }
            wing.destroy();
        } else if(contact.getFixtureB().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureA().getUserData().getClass() == MachineGun.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureB().getUserData();
            MachineGun mg = (MachineGun) contact.getFixtureA().getUserData();
            if(mg.getActive()){
                player.setFireMode();
            }
            mg.destroy();
        } else if (contact.getFixtureA().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureB().getUserData().getClass() == MachineGun.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureA().getUserData();
            MachineGun mg = (MachineGun) contact.getFixtureB().getUserData();
            if(mg.getActive()){
                player.setFireMode();
            }
            mg.destroy();
        } else if (contact.getFixtureB().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureA().getUserData().getClass() == ExtraSpeed.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureB().getUserData();
            ExtraSpeed es = (ExtraSpeed) contact.getFixtureA().getUserData();
            if(es.getActive()){
                player.setExtraSpeed();
            }
            es.destroy();
        } else if (contact.getFixtureA().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureB().getUserData().getClass() == ExtraSpeed.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureA().getUserData();
            ExtraSpeed es = (ExtraSpeed) contact.getFixtureB().getUserData();
            if(es.getActive()){
                player.setExtraSpeed();
            }
            es.destroy();
        } else if (contact.getFixtureB().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureA().getUserData().getClass() == CarpetBomb.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureB().getUserData();
            CarpetBomb cb = (CarpetBomb) contact.getFixtureA().getUserData();
            if(cb.getActive()){
                player.clearEnemies();
            }
            cb.destroy();
        } else if (contact.getFixtureA().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureB().getUserData().getClass() == ExtraLife.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureA().getUserData();
            ExtraLife el = (ExtraLife) contact.getFixtureB().getUserData();
            if(el.getActive()){
                player.addLife();
            }
            el.destroy();
        } else if (contact.getFixtureB().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureA().getUserData().getClass() == ExtraLife.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureB().getUserData();
            ExtraLife el = (ExtraLife) contact.getFixtureA().getUserData();
            if(el.getActive()){
                player.addLife();
            }
            el.destroy();
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
