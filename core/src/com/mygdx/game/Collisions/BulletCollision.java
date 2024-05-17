package com.mygdx.game.Collisions;

import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Characters.BluePlaneSprite;
import com.mygdx.game.PowerUps.WingMan;
import com.mygdx.game.Projectiles.Bullet;
import com.mygdx.game.Characters.EnemyPlaneSprite;
import com.mygdx.game.Projectiles.EnemyBullet;

/**
 * Controls enemy collisions with player bullets
 */
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
