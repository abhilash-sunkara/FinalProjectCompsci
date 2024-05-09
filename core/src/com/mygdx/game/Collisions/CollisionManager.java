package com.mygdx.game.Collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionManager implements ContactListener {

    BulletCollision bc = new BulletCollision();
    EnemyPlaneCollision epc = new EnemyPlaneCollision();
    PowerUpCollision puc = new PowerUpCollision();

    @Override
    public void beginContact(Contact contact) {
        bc.beginContact(contact);
        epc.beginContact(contact);
        puc.beginContact(contact);
    }

    @Override
    public void endContact(Contact contact) {
        bc.endContact(contact);
        epc.endContact(contact);
        puc.endContact(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        bc.preSolve(contact, oldManifold);
        epc.preSolve(contact, oldManifold);
        puc.preSolve(contact, oldManifold);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        bc.postSolve(contact, impulse);
        epc.postSolve(contact, impulse);
        puc.postSolve(contact, impulse);
    }
}
