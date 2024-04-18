package com.mygdx.game.Collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Characters.BluePlaneSprite;
import com.mygdx.game.Characters.Bullet;
import com.mygdx.game.Characters.EnemyPlaneSprite;

public class BulletCollision implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getUserData().getClass() == EnemyPlaneSprite.class && contact.getFixtureB().getUserData().getClass() == Bullet.class){
            Bullet b = (Bullet) contact.getFixtureB().getUserData();
            b.destroy();
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
