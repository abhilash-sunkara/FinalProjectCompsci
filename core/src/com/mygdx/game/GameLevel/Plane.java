package com.mygdx.game.GameLevel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Background.Button;
import com.mygdx.game.Background.RestartButton;
import com.mygdx.game.Characters.BluePlaneSprite;
import com.mygdx.game.Characters.WingManSprite;
import com.mygdx.game.Background.MovingBackgroundOcean;
import com.mygdx.game.Collisions.CollisionManager;
import com.mygdx.game.Managers.EnemyManager;
import com.mygdx.game.Managers.MouseManager;
import com.mygdx.game.Managers.PowerUpManager;
import com.mygdx.game.Managers.UserInterfaceManager;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class Plane extends Game {
	public SpriteBatch batch;

	OrthographicCamera camera;

	String bluePlaneImg = "p-51.png";
	BluePlaneSprite planeSprite;
	World world;
	EnemyManager boss;
	UserInterfaceManager ui;
	PowerUpManager powerUps;
	Box2DDebugRenderer debugRenderer;
	MouseManager mouseManager;

	Button restartButton;
	ArrayList<Body> bodyRemover = new ArrayList<>();
	ArrayList<Body> removedBodies = new ArrayList<>();

	Array<Body> removeNonMovingBodies = new Array<>();

	MovingBackgroundOcean backgroundOcean;

	public static boolean isAbleToReset = false;
	private BitmapFont font;

	private float timer;
	private float timeToWin = 30;

	public boolean renderStartScene = true;
	public boolean playGame = true;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		world = new World(new Vector2(0, 0), true);



		boss = new EnemyManager(batch, world, bodyRemover);
		planeSprite = new BluePlaneSprite(bluePlaneImg, batch, world, bodyRemover, boss);
		planeSprite.sprite.setScale(0.08f);
		ui = new UserInterfaceManager(batch);
		powerUps = new PowerUpManager(batch, world);
		mouseManager = new MouseManager();
		restartButton = new RestartButton(436, 200, 160, 80, new Texture(Gdx.files.internal("RestartButton.png")), new Texture(Gdx.files.internal("ButtonDown.png")), batch, 200, 80, 240, 80);

		debugRenderer = new Box2DDebugRenderer();
		backgroundOcean = new MovingBackgroundOcean();

		world.setContactListener(new CollisionManager());

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("monogram.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

		params.borderWidth = 0f;
		params.borderColor = Color.BLACK;
		params.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
		params.magFilter = Texture.TextureFilter.Nearest;
		params.minFilter = Texture.TextureFilter.Nearest;
		params.genMipMaps = true;
		params.size = 60;
		params.color = Color.WHITE;

		font = generator.generateFont(params);
	}

	public boolean shouldPlay(){
		//System.out.println("Lives : " + BluePlaneSprite.lives);
		//System.out.println("Enemies escaped : " + EnemyManager.enemiesEscaped);
		return (BluePlaneSprite.lives > 0 && EnemyManager.enemiesEscaped < 8) && (timer < timeToWin);
	}

	public boolean shouldLose(){
		return BluePlaneSprite.lives <= 0 || EnemyManager.enemiesEscaped >= 8;
	}

	public boolean shouldWin(){
		return timer > timeToWin;
	}

	public void render () {
		timer += Gdx.graphics.getDeltaTime();

		//System.out.println("Should Play : " + shouldPlay());
		//System.out.println("Should Lose : " + shouldLose());

		if(shouldPlay()) {

			ScreenUtils.clear(1, 1, 1, 1);
			batch.begin();
			updateManagers();
			isAbleToReset = false;
			world.step(1 / 60f, 12, 2);
			world.step(1 / 60f, 12, 2);
			world.step(1 / 60f, 12, 2);
			world.step(1 / 60f, 12, 2);
			world.step(1 / 60f, 12, 2);
			//debugRenderer.render(world, camera.combined);
			isAbleToReset = true;
			removeAllInactive();
			batch.end();
		}else{
			if(shouldLose()) {
				showEndScreen();
			} else if(shouldWin()) {
				showWinScreen();
			}
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
			batch.begin();
			batch.dispose();
			batch.end();
		}

	}

	public void updateManagers(){
		backgroundOcean.updateAndRender(Gdx.graphics.getDeltaTime(), batch);
		planeSprite.update();
		boss.update();
		ui.render();
		powerUps.update();
	}

	public void showEndScreen(){
		Gdx.gl.glClearColor(.25f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(new Texture("GameOverScreen.jpeg"), -150f, 00f, 800f, 480f);
		font.draw(batch, "Game Over", Gdx.graphics.getWidth() * .35f, Gdx.graphics.getHeight() * .75f);
		restartButton.update();
		if(mouseManager.checkMouseButtonClick(restartButton)){
			restartButton.clickButton();
			restart();
		}
		batch.end();
	}

	public void showWinScreen(){
		Gdx.gl.glClearColor(.25f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(new Texture("WinScreen.png"), -50f, 00f, 800f, 480f);
		font.draw(batch, "You win", Gdx.graphics.getWidth() * .35f, Gdx.graphics.getHeight() * .75f);
		restartButton.update();
		if(mouseManager.checkMouseButtonClick(restartButton)){
			restartButton.clickButton();
			restart();
		}
		batch.end();
	}

	public void restart(){
		planeSprite.restart();
		boss.restart();
		powerUps.restart();
		timer = 0;
	}

	public void removeAllInactive(){
		world.getBodies(removeNonMovingBodies);
		for(Body b : removeNonMovingBodies){
			if(b.getUserData() != null && (b.getUserData().getClass() != BluePlaneSprite.class && b.getUserData().getClass() != WingManSprite.class) && b.getLinearVelocity().isZero()){
				world.destroyBody(b);
				removedBodies.add(b);
			} else if (b.getLinearVelocity().isZero() && b.getUserData() == null){
				world.destroyBody(b);
				removedBodies.add(b);
			}
		}
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