package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Characters.BluePlaneSprite;

public class UserInterfaceManager {

    private SpriteBatch batch;
    BitmapFont font;

    private int playerLives;

    private Sprite playerLifeMeter;
    private Array<Texture> playerLifeImages = new Array<>();

    public UserInterfaceManager(SpriteBatch sb){
        batch = sb;
        generateFont();
        generatePlayerLifeImages();
    }

    public void generateFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("monogram.ttf"));
        FreeTypeFontParameter params = new FreeTypeFontParameter();

        params.borderWidth = 0f;
        params.borderColor = Color.BLACK;
        params.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        params.magFilter = Texture.TextureFilter.Nearest;
        params.minFilter = Texture.TextureFilter.Nearest;
        params.genMipMaps = true;
        params.size = 60;

        font = generator.generateFont(params);

        font.setColor(0, 0, 0, 1);
    }



    public void generatePlayerLifeImages(){
        for(int i = 0; i < 5; i++){
            playerLifeImages.add(new Texture(Gdx.files.internal("PlayerLifeMeter/PlayerLifeMeter" + i + ".png")));
        }

        playerLifeMeter = new Sprite(playerLifeImages.get(4));
        playerLifeMeter.setScale(4, 4);
        playerLifeMeter.setPosition(50, 12);

    }

    public void render(){
        renderPlayerLives();
    }

    public void renderPlayerLives(){
        //System.out.println(playerLives);
        //System.out.println(BluePlaneSprite.lives);
        if(playerLives != BluePlaneSprite.lives && BluePlaneSprite.lives >= 0){
            playerLives = BluePlaneSprite.lives;
            playerLifeMeter.setTexture(playerLifeImages.get(playerLives));
        }
        playerLifeMeter.draw(batch);
    }




}
