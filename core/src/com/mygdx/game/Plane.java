package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Characters.BluePlane;
import com.mygdx.game.Characters.BluePlaneSprite;
import com.mygdx.game.Characters.Bullet;
import com.mygdx.game.Characters.EnemyPlaneSprite;

public class Plane extends ApplicationAdapter {
	SpriteBatch batch;

	OrthographicCamera camera;

	//private BluePlane player = new BluePlane();
	String bluePlaneImg = "ship_0000.png";
	String enemyPlaneImg = "ship_0001.png";
	BluePlaneSprite planeSprite;
	EnemyPlaneSprite enemyPlaneSprite;
	World world;
	Box2DDebugRenderer debugRenderer;

	@Override
	public void create () {
		Box2D.init();
		world = new World(new Vector2(0, 0), true);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		planeSprite = new BluePlaneSprite(bluePlaneImg, batch, world);
		debugRenderer = new Box2DDebugRenderer();
		debugRenderer.render(world, camera.combined);
		enemyPlaneSprite = new EnemyPlaneSprite(enemyPlaneImg, batch, world);
		//b = new Bullet("largeBullet.png", batch, world);
		//player.create(bluePlaneImg);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		planeSprite.update();
		//b.update();
		enemyPlaneSprite.update();
		world.step(1f/60f, 6, 2);
		//debugRenderer.render(world, camera.combined);
		batch.end();
	}


	
	@Override
	public void dispose () {
		batch.dispose();
	}


}
