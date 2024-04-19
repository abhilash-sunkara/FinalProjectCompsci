package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Characters.EnemyPlaneSprite;
import java.util.ArrayList;

public class EnemyManager{
    private ArrayList<EnemyPlaneSprite> enemies; 
    SpriteBatch batch;
    float time;

    public EnemyManager(SpriteBatch input){
        batch = input;
    }
