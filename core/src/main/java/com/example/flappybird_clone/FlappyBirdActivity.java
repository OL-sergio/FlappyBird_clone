package com.example.flappybird_clone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FlappyBirdActivity extends ApplicationAdapter {


    private SpriteBatch batch;
    private Texture[] birds;
    private Texture background;
    private Texture pipeTop;
    private Texture pipeTopLongo;
    private Texture pipeBottom;
    private Texture pipeBottomLongo;
    private Random random;
    private int score = 0;
    private boolean passPipe = false;


    private float widthDisplay;
    private float heightDisplay;
    private float wingsVariations = 0f;
    private float birdGravityJump = 2f;
    private float birdInitialVerticalPosition;
    private float pipePositionHorizontal;
    private float pipePositionVertical ;
    private float pipesMargin;

    private BitmapFont textSoreResult;



    @Override
    public void create() {
        initializeTexture();
        initializeObjects();
    }

    private void initializeTexture() {

        birds = new Texture[3];
        birds[0] = new Texture("passaro1.png");
        birds[1] = new Texture("passaro2.png");
        birds[2] = new Texture("passaro3.png");
        background = new Texture("fundo.png");

        pipeTop = new Texture("cano_topo.png");
        pipeTopLongo = new Texture("cano_topo_maior.png");

        pipeBottom = new Texture("cano_baixo.png");
        pipeBottomLongo = new Texture("cano_baixo_maior.png");

    }

    private void initializeObjects() {

        //Gdx.app.log("", "Jogo iniciado");
        random = new Random();
        batch = new SpriteBatch();
        widthDisplay = Gdx.graphics.getWidth();
        heightDisplay = Gdx.graphics.getHeight();
        birdInitialVerticalPosition = heightDisplay / 2f;
        pipePositionHorizontal = widthDisplay;
        pipesMargin = 260f;

        textSoreResult = new BitmapFont();
        textSoreResult.setColor(Color.WHITE);
        textSoreResult.getData().setScale(6f);

    }

    @Override
    public void render() {
        verifyEstateOfGame();
        scoreValidation();
        drawTexture();
    }

    private void scoreValidation() {

        if ( pipePositionHorizontal < 50f - birds[0].getWidth() ) {
            if ( !passPipe ) {
                score++;
                passPipe = true;
            }
        }
    }

    private void verifyEstateOfGame() {

        pipePositionHorizontal -= Gdx.graphics.getDeltaTime() * 200f;
        if ( pipePositionHorizontal < -pipeTop.getWidth() ) {
            pipePositionHorizontal = widthDisplay;
            pipePositionVertical = random.nextInt(900 ) - 200f ;
            passPipe = false;
        }

        boolean touchScreen = Gdx.input.isTouched();
        if ( touchScreen ) {
            // Gdx.app.log("touch", "Tela tocada");
            birdGravityJump = -15f;
        }
        if ( birdInitialVerticalPosition > 0 || touchScreen ) {
            birdInitialVerticalPosition = birdInitialVerticalPosition - birdGravityJump;
        }


        wingsVariations += Gdx.graphics.getDeltaTime() * 10f;
        if ( wingsVariations> 3){
            wingsVariations = 0f;
        }

        birdGravityJump++;
    }

    private void drawTexture() {


        batch.begin();

        batch.draw( background, 0f, 0f, widthDisplay, heightDisplay);
        batch.draw( birds[ (int) wingsVariations], 50f, birdInitialVerticalPosition );

        batch.draw(
            pipeTop,
            pipePositionHorizontal,
            heightDisplay / 2f + pipesMargin / 2f + pipePositionVertical);

        batch.draw(
            pipeBottom,
            pipePositionHorizontal,
            heightDisplay / 2f - pipeBottom.getHeight() - pipesMargin/ 2f + pipePositionVertical);
        textSoreResult.draw(batch, "Pontos: " + score , widthDisplay / 2f - 200f, heightDisplay - 200f
        );
        batch.end();

    }

    @Override
    public void dispose() {
        Gdx.app.log("dispose", "Remover conteudos");
    }
}
