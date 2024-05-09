package com.mygdx.game.Collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Characters.BluePlaneSprite;
import com.mygdx.game.Characters.EnemyPlaneSprite;
import com.mygdx.game.Projectiles.EnemyBullet;

public class EnemyPlaneCollision implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureB().getUserData().getClass() == EnemyPlaneSprite.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureA().getUserData();
            EnemyPlaneSprite enemy = (EnemyPlaneSprite) contact.getFixtureB().getUserData();
            if(enemy.isActive){
                player.resetToggle();
            }
            enemy.destroy();
        } else if (contact.getFixtureB().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureA().getUserData().getClass() == EnemyPlaneSprite.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureB().getUserData();
            EnemyPlaneSprite enemy = (EnemyPlaneSprite) contact.getFixtureA().getUserData();
            if(enemy.isActive){
                player.resetToggle();
            }
            enemy.destroy();
        } else if(contact.getFixtureA().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureB().getUserData().getClass() == EnemyBullet.class) {
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureA().getUserData();
            EnemyBullet enemyBullet = (EnemyBullet) contact.getFixtureB().getUserData();
            if (enemyBullet.isActive && enemyBullet.getVelocity() < 0f) {
                player.resetToggle();
            }
            enemyBullet.destroy();
        } else if(contact.getFixtureB().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureA().getUserData().getClass() == EnemyBullet.class) {
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureB().getUserData();
            EnemyBullet enemyBullet = (EnemyBullet) contact.getFixtureA().getUserData();
            if (enemyBullet.isActive && enemyBullet.getVelocity() < 0f) {
                player.resetToggle();
            }
            enemyBullet.destroy();
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
