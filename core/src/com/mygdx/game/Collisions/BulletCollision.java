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
        } else if(contact.getFixtureA().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureB().getUserData().getClass() == EnemyPlaneSprite.class){
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
            //System.out.println("hit enemy bullet");
        } else if(contact.getFixtureB().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureA().getUserData().getClass() == EnemyBullet.class) {
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureB().getUserData();
            EnemyBullet enemyBullet = (EnemyBullet) contact.getFixtureA().getUserData();
            if (enemyBullet.isActive && enemyBullet.getVelocity() < 0f) {
                player.resetToggle();
            }
            enemyBullet.destroy();
            //System.out.println("hit enemy bullet");
        } else if(contact.getFixtureA().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureB().getUserData().getClass() == WingMan.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureA().getUserData();
            WingMan wing = (WingMan) contact.getFixtureB().getUserData();
            if(wing.isActive){
                //System.out.println("spawn wingman");
                player.spawnWingman();
            }
            wing.destroy();

        } else if(contact.getFixtureB().getUserData().getClass() == BluePlaneSprite.class && contact.getFixtureA().getUserData().getClass() == WingMan.class){
            BluePlaneSprite player = (BluePlaneSprite) contact.getFixtureB().getUserData();
            WingMan wing = (WingMan) contact.getFixtureB().getUserData();
            if(wing.isActive){
                //System.out.println("spawn wingman");
                player.spawnWingman();
            }
            wing.destroy();
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
