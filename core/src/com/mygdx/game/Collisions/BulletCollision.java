package com.mygdx.game.Collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Projectiles.Bullet;
import com.mygdx.game.Characters.EnemyPlaneSprite;

public class BulletCollision implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getUserData().getClass() == EnemyPlaneSprite.class && contact.getFixtureB().getUserData().getClass() == Bullet.class){
            Bullet b = (Bullet) contact.getFixtureB().getUserData();
            if(b.isActive){
                EnemyPlaneSprite es = (EnemyPlaneSprite) contact.getFixtureA().getUserData();
                es.destroy();
            }
            b.destroy();
        } else if(contact.getFixtureB().getUserData().getClass() == EnemyPlaneSprite.class && contact.getFixtureA().getUserData().getClass() == Bullet.class){
            Bullet b = (Bullet) contact.getFixtureA().getUserData();
            if(b.isActive){
                EnemyPlaneSprite es = (EnemyPlaneSprite) contact.getFixtureB().getUserData();
                es.destroy();
            }
            b.destroy();
        }

        /*
        System.out.println("I am a " + contact.getFixtureA().getFilterData().categoryBits);
        System.out.println("I should be getting by " + contact.getFixtureA().getFilterData().maskBits);
        System.out.println("My class is " + contact.getFixtureA().getUserData());
        System.out.println("Got hit by " + contact.getFixtureB().getFilterData().categoryBits);
        System.out.println("Which should be hitting " + contact.getFixtureB().getFilterData().maskBits);
        System.out.println("My class is " + contact.getFixtureB().getUserData());
        */

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
