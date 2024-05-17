package com.mygdx.game.GameLevel;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Background.Button;
import com.mygdx.game.Managers.MouseManager;


public class StartingLevel extends Plane {
    

    private Animation<TextureRegion> animation;

    private TextureRegion[] textureRegions;

    private int textureCount;

    private int timePast;

    private float elapsed;

    private BitmapFont font;

    private boolean renderPlane;

    private Music propBgMusic;

    private Stage stage;

    private Label outputLabel;

    private TextButton button;

    private Texture buttonImage;

    public Button startButton;
    public Button infoButton;
    public Button backButton;
    public MouseManager mm;

    public boolean showInfoScreen;
    @Override
    public void create() {
        super.create();

        buttonImage = new Texture("start.png");

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

        textureCount = 0;

        timePast = 0;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin mySkin = new Skin(Gdx.files.internal("TestSkin/glassy-ui.json"));
        button = new TextButton("TestButton", mySkin, "small");
        button.setSize(100,30);
        button.setPosition(400-125, 240);
        button.setTransform(true);
        button.setScale(1f);


        outputLabel = new Label("Press a Button", mySkin, "black");
        outputLabel.setSize(100, 30);
        outputLabel.setPosition(400 - 125, 240);
        outputLabel.setAlignment(1);
        //stage.addActor(outputLabel);

        stage.addActor(button);

        startButton = new Button(436, 200, 160, 80, new Texture(Gdx.files.internal("StartButton.png")), new Texture(Gdx.files.internal("ButtonDown.png")), batch, 200, 80, 240, 80);
        infoButton = new Button(380, 260, 250, 200, new Texture(Gdx.files.internal("InfoButton.png")), new Texture(Gdx.files.internal("ButtonDown.png")), batch, 260, 200, 120, 40);
        backButton = new Button(436, 200, 160, 80, new Texture(Gdx.files.internal("BackButton.png")), new Texture(Gdx.files.internal("ButtonDown.png")), batch, 200, 80, 240, 80);
        mm = new MouseManager();
    }

 


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


    @Override
    public void render(){
        if(super.renderStartScene) {
            show();
            if(!showInfoScreen){
                showStartScreen();
            } else {
                showInfoScreen();
            }

        } else {
            super.render();
        }


    }
}
