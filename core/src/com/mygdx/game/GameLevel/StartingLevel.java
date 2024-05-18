package com.mygdx.game.GameLevel;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Background.Button;
import com.mygdx.game.Managers.MouseManager;

/**
 * Class for start screen
 */
public class StartingLevel extends Plane {


    /**
     * Animated background object
     */
    private Animation<TextureRegion> animation;

    /**
     * Textures for animated background
     */
    private TextureRegion[] textureRegions;
    /**
     * Timer for animation
     */
    private float elapsed;

    /**
     * Font object for text
     */
    private BitmapFont font;


    /**
     * Background music
     */
    private Music propBgMusic;


    /**
     * Start button for game
     */
    public Button startButton;
    /**
     * Info menu button
     */
    public Button infoButton;
    /**
     * Back button to exit from info screen
     */
    public Button backButton;
    /**
     * Mouse manager to track clicks
     */
    public MouseManager mm;

    /**
     * Boolean that tracks whether info screen should be shown
     */
    public boolean showInfoScreen;

    /**
     * Initializes all objects
     */
    @Override
    public void create() {
        super.create();



        textureRegions = new TextureRegion[24];

        for(int i = 0; i < textureRegions.length;i++){
            textureRegions[i] = new TextureRegion(new Texture("StartScene/f8a9b79be62d45ca9729d3d57d8ddb922BOjr5F9YZonZE0M-" + i + ".jpg"));
        }

        animation = new Animation<>(.1175F,textureRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP);


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("monogram.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.borderWidth = 0f;
        params.borderColor = Color.BLACK;
        params.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        params.magFilter = Texture.TextureFilter.Nearest;
        params.minFilter = Texture.TextureFilter.Nearest;
        params.genMipMaps = true;
        params.size = 60;

        font = generator.generateFont(params);


        startButton = new Button(436, 200, 160, 80, new Texture(Gdx.files.internal("StartButton.png")), new Texture(Gdx.files.internal("ButtonDown.png")), batch, 200, 80, 240, 80);
        infoButton = new Button(380, 260, 250, 200, new Texture(Gdx.files.internal("InfoButton.png")), new Texture(Gdx.files.internal("ButtonDown.png")), batch, 260, 200, 120, 40);
        backButton = new Button(436, 200, 160, 80, new Texture(Gdx.files.internal("BackButton.png")), new Texture(Gdx.files.internal("ButtonDown.png")), batch, 200, 80, 240, 80);
        mm = new MouseManager();
    }


    /**
     * Shows and plays music
     */
    public void show(){
        if(!super.renderStartScene)
            return;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.ENTER) {
                    renderStartScene = false;
                    propBgMusic = Gdx.audio.newMusic(Gdx.files.internal("prop.mp3"));
		            propBgMusic.setLooping(true);
                    propBgMusic.setVolume(0.5f);
		            propBgMusic.play();
                }
                return true;
            }
        });
    }

    /**
     * Shows start screen
     */
    public void showStartScreen(){
        elapsed += Gdx.graphics.getDeltaTime();
        super.batch.begin();
        super.batch.draw(animation.getKeyFrame(elapsed), -500f, -200f);
        startButton.update();
        infoButton.update();
        if(mouseManager.checkMouseButtonClick(startButton)){
            startButton.clickButton();
            renderStartScene = false;
            propBgMusic = Gdx.audio.newMusic(Gdx.files.internal("prop.mp3"));
            propBgMusic.setLooping(true);
            propBgMusic.setVolume(.5f);
            propBgMusic.play();
        }
        if(mouseManager.checkMouseButtonClick(infoButton)){
            infoButton.clickButton();
            showInfoScreen = true;
        }
        font.getData().setScale(1f);
        font.draw(super.batch, "Press Enter To Start", Gdx.graphics.getWidth() * .15f, Gdx.graphics.getHeight() * .75f);
        super.batch.end();
    }

    /**
     * Shows info screen
     */
    public void showInfoScreen(){
        super.batch.begin();
        ScreenUtils.clear(0, 0, 0, 1);
        backButton.update();
        if(mouseManager.checkMouseButtonClick(backButton)){
            backButton.clickButton();
            showInfoScreen = false;
        }
        font.getData().setScale(0.5f);
        font.draw(super.batch, "Stop enemy planes from crossing the border \nwhile avoiding enemy fire \nCollect powerups to boost your performance", Gdx.graphics.getWidth() * .11f, Gdx.graphics.getHeight() * .75f);
        super.batch.end();
    }


    /**
     * Update-every-frame method that shows start and info screen
     */
    @Override
    public void render(){
        if(super.renderStartScene) {
            show();
            if(!showInfoScreen){
                showStartScreen();
            } else {
                showInfoScreen();
            }
<<<<<<< HEAD
            font.draw(super.batch, "Press Button To Start", Gdx.graphics.getWidth() * .15f, Gdx.graphics.getHeight() * .75f);
            super.batch.end();
=======

>>>>>>> 9ddacc1343f3d3c093546b81bc14e8fd2bb3598c
        } else {
            super.render();
        }


    }
}
