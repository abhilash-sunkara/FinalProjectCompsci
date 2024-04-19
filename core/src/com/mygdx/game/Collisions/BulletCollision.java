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
            b.destroy();
            EnemyPlaneSprite es = (EnemyPlaneSprite) contact.getFixtureA().getUserData();
            es.destroy();
        } else if(contact.getFixtureB().getUserData().getClass() == EnemyPlaneSprite.class && contact.getFixtureA().getUserData().getClass() == Bullet.class){
            Bullet b = (Bullet) contact.getFixtureA().getUserData();
            b.destroy();
            EnemyPlaneSprite es = (EnemyPlaneSprite) contact.getFixtureB().getUserData();
            es.destroy();
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
