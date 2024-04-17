package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Characters.BluePlane;
import com.mygdx.game.Characters.BluePlaneSprite;
import com.mygdx.game.Characters.EnemyPlaneSprite;

public class Plane extends ApplicationAdapter {
	SpriteBatch batch;

	OrthographicCamera camera;

	//private BluePlane player = new BluePlane();
	String bluePlaneImg = "ship_0000.png";
	String enemyPlaneImg = "ship_0001.png";
	BluePlaneSprite planeSprite;
	EnemyPlaneSprite enemyPlaneSprite;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		planeSprite = new BluePlaneSprite(bluePlaneImg, batch);
		enemyPlaneSprite = new EnemyPlaneSprite(enemyPlaneImg, batch);
		//player.create(bluePlaneImg);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		planeSprite.update();
		enemyPlaneSprite.update();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}


}