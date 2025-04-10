package com.example.flappybird_clone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FlappyBirdActivity extends ApplicationAdapter {

    private int movX = 0;
    private int movY = 0;

    private SpriteBatch batch;
    private Texture bird;
    private Texture background;

    private float widthDisplay;
    private float heightDisplay;

    @Override
    public void create() {
        //Gdx.app.log("", "Jogo iniciado");
        batch = new SpriteBatch();
        bird = new Texture("passaro1.png");
        background = new Texture("fundo.png");
        widthDisplay = Gdx.graphics.getWidth();
        heightDisplay = Gdx.graphics.getHeight();

    }

    @Override
    public void render() {

        /*
        counter++;
        Gdx.app.log("render", "Jogo rendering: " + counter);
         */

        batch.begin();

        batch.draw(background, 0, 0,   widthDisplay, heightDisplay);
        batch.draw(bird, movX,600);

        movY++;
        movX++;

        batch.end();

    }

    @Override
    public void dispose() {
        Gdx.app.log("dispose", "Remover conteudos");
    }
}
