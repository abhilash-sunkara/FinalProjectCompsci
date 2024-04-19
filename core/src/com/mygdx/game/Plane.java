package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Background.MovingBackgroundOcean;
import com.mygdx.game.Characters.BluePlaneSprite;
import com.mygdx.game.Managers.EnemyManager;

public class Plane extends ApplicationAdapter {
	SpriteBatch batch;

	OrthographicCamera camera;

	//private BluePlane player = new BluePlane();
	String bluePlaneImg = "ship_0000.png";
	String enemyPlaneImg = "ship_0001.png";
	BluePlaneSprite planeSprite;
	World world;
	EnemyManager boss;
	Box2DDebugRenderer debugRenderer;

	MovingBackgroundOcean backgroundOcean;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		world = new World(new Vector2(0, 0), true);

		planeSprite = new BluePlaneSprite(bluePlaneImg, batch, world);
		boss = new EnemyManager(batch, world);
		debugRenderer = new Box2DDebugRenderer();
		//planeSprite = new BluePlaneSprite(bluePlaneImg, batch);
		//enemyPlaneSprite = new EnemyPlaneSprite(enemyPlaneImg, batch);
		boss = new EnemyManager(batch, world);
		//player.create(bluePlaneImg);

		backgroundOcean = new MovingBackgroundOcean();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		planeSprite.update();
		//planeSprite.update();
		//enemyPlaneSprite.update();
		backgroundOcean.updateAndRender(Gdx.graphics.getDeltaTime(),batch);
		planeSprite.update();
		boss.update();
		world.step(1/60f, 6, 2);
		world.step(1/60f, 6, 2);
		world.step(1/60f, 6, 2);
		world.step(1/60f, 6, 2);
		world.step(1/60f, 6, 2);
		//debugRenderer.render(world, camera.combined);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}