package com.mygdx.game.GameLevel;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


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
    }

 


    public void show(){
        if(renderPlane)
            return;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.ENTER) {
                    renderPlane = true;
                    propBgMusic = Gdx.audio.newMusic(Gdx.files.internal("prop.mp3"));
		            propBgMusic.setLooping(true);
                    propBgMusic.setVolume(0.5f);
		            propBgMusic.play();
                }
                return true;
            }
        });

        boolean inRightPosition = ((Gdx.input.getX() > 80 && Gdx.input.getX() < 557) && (Gdx.input.getY() > 237 && Gdx.input.getY() < 322));
        if(inRightPosition && Gdx.input.isTouched()) {
            renderPlane = true;
            propBgMusic = Gdx.audio.newMusic(Gdx.files.internal("prop.mp3"));
            propBgMusic.setLooping(true);
            propBgMusic.setVolume(0.5f);
            propBgMusic.play();
            System.out.println("Hello");
        }

    }


    @Override


    public void render(){



        show();
        //80 237
        //557 322
        


        elapsed += Gdx.graphics.getDeltaTime();
        super.batch.begin();
        stage.act();
        stage.draw();
        super.batch.draw(animation.getKeyFrame(elapsed),-500f,-200f);
        


        font.draw(super.batch, "Press Enter To Start", Gdx.graphics.getWidth() * .15f, Gdx.graphics.getHeight() * .75f);

        super.batch.draw(buttonImage,-65f,60f,buttonImage.getWidth()*.8f,buttonImage.getHeight()*.5f);

        super.batch.end();

        if(renderPlane) {
            super.render();
        }


    }
}
