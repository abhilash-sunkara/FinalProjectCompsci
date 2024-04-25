package com.mygdx.game.GameLevel;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class StartingLevel extends Plane {
    

    private Animation<TextureRegion> animation;

    private TextureRegion[] textureRegions;

    private int textureCount;

    private int timePast;

    private float elapsed;

    private BitmapFont font;

    private boolean renderPlane;

    private Music propBgMusic;

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

        textureCount = 0;

        timePast = 0;
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
    }


    @Override


    public void render(){

        show();

        elapsed += Gdx.graphics.getDeltaTime();
        super.batch.begin();
        super.batch.draw(animation.getKeyFrame(elapsed),-500f,-200f);
        font.draw(super.batch, "Press Enter To Start", Gdx.graphics.getWidth() * .15f, Gdx.graphics.getHeight() * .75f);
        super.batch.end();



        if(renderPlane) {

            super.render();
        }

    }


}
