package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Background.MovingBackgroundOcean;
import com.mygdx.game.Characters.BluePlaneSprite;
import com.mygdx.game.Collisions.BulletCollision;
import com.mygdx.game.Managers.EnemyManager;
import com.mygdx.game.Managers.UserInterfaceManager;

import java.util.ArrayList;

public class Plane extends ApplicationAdapter {
	SpriteBatch batch;

	OrthographicCamera camera;

	//private BluePlane player = new BluePlane();
	String bluePlaneImg = "ship_0000.png";
	BluePlaneSprite planeSprite;
	World world;
	EnemyManager boss;
	UserInterfaceManager ui;
	Box2DDebugRenderer debugRenderer;

	ArrayList<Body> bodyRemover = new ArrayList<>();
	ArrayList<Body> removedBodies = new ArrayList<>();

	MovingBackgroundOcean backgroundOcean;

	public static boolean isAbleToReset = false;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		world = new World(new Vector2(0, 0), true);

		planeSprite = new BluePlaneSprite(bluePlaneImg, batch, world, bodyRemover);
		boss = new EnemyManager(batch, world, bodyRemover);
		ui = new UserInterfaceManager(batch);
		debugRenderer = new Box2DDebugRenderer();
		//planeSprite = new BluePlaneSprite(bluePlaneImg, batch);
		//enemyPlaneSprite = new EnemyPlaneSprite(enemyPlaneImg, batch);
		//boss = new EnemyManager(batch, world);
		//player.create(bluePlaneImg);

		backgroundOcean = new MovingBackgroundOcean();

		world.setContactListener(new BulletCollision());

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		backgroundOcean.updateAndRender(Gdx.graphics.getDeltaTime(),batch);
		planeSprite.update();
		boss.update();
		ui.render();
		isAbleToReset = false;
		world.step(1/60f, 6, 2);
		world.step(1/60f, 6, 2);
		world.step(1/60f, 6, 2);
		world.step(1/60f, 6, 2);
		world.step(1/60f, 6, 2);
		isAbleToReset = true;
		removeAllInactive();
		//debugRenderer.render(world, camera.combined);

		batch.end();

	}



	public void removeAllInactive(){
		for(Body b : bodyRemover){
			if(!removedBodies.contains(b)) {
				world.destroyBody(b);
				removedBodies.add(b);
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}