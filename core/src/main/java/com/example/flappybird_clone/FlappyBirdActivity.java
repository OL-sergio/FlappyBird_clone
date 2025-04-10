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
    private Texture[] birds;
    private Texture background;

    private float widthDisplay;
    private float heightDisplay;
    private float wingsVariations = 0.0f;

    @Override
    public void create() {
        //Gdx.app.log("", "Jogo iniciado");
        batch = new SpriteBatch();

        birds = new Texture[3];
        birds[0] = new Texture("passaro1.png");
        birds[1] = new Texture("passaro2.png");
        birds[2] = new Texture("passaro3.png");

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

        if (wingsVariations > 3)
            wingsVariations = 0;

            batch.draw(background, 0, 0, widthDisplay, heightDisplay);
            batch.draw(birds[ (int) wingsVariations],30, heightDisplay/2);

            float flappingWingSpeed = Gdx.graphics.getDeltaTime() * 7;

            wingsVariations += flappingWingSpeed;
            //Gdx.app.log("variations", "variation: " +  );
            movY++;
            movX++;

        batch.end();
    }

    @Override
    public void dispose() {
        Gdx.app.log("dispose", "Remover conteudos");
    }
}
