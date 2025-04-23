package com.example.flappybird_clone;

import static com.example.flappybird_clone.Constants.PIPES_MAX_SIZE;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.PrimitiveIterator;
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
    private Texture gameOver;

    private Random random;
    private int score = 0;
    private int gameStatus = 0;
    private float birdGravityHorizontal = 0f;
    private  boolean touchScreen;
    private boolean passPipe = false;
    private BitmapFont textScoreResult;
    private BitmapFont textGameOverScoreResult;
    private BitmapFont textGameRestart;
    private Sound soundFlying;
    private Sound soundCollision;
    private Sound soundPoint;


    private ShapeRenderer shapeRenderer;
    private Circle birdCircle;
    private Rectangle pipeTopRectangle;
    private Rectangle pipeBottomRectangle;

    private float widthDisplay;
    private float heightDisplay;
    private float wingsVariations = 0f;
    private float birdGravity = 2f;
    private float birdInitialVerticalPosition;
    private float pipePositionHorizontal;
    private float pipePositionVertical ;
    private float pipesMargin;


    @Override
    public void create() {
        initializeObjectOnScreen();
        initializeObjects();
    }

    private void initializeObjectOnScreen() {

        birds = new Texture[3];
        birds[0] = new Texture("passaro1.png");
        birds[1] = new Texture("passaro2.png");
        birds[2] = new Texture("passaro3.png");
        background = new Texture("fundo.png");
        pipeTop = new Texture("cano_topo.png");
        pipeTopLongo = new Texture("cano_topo_maior.png");
        pipeBottom = new Texture("cano_baixo.png");
        pipeBottomLongo = new Texture("cano_baixo_maior.png");
        gameOver = new Texture("game_over.png");
        soundFlying = Gdx.audio.newSound(Gdx.files.internal("som_asa.wav"));
        soundCollision = Gdx.audio.newSound(Gdx.files.internal("som_batida.wav"));
        soundPoint = Gdx.audio.newSound(Gdx.files.internal("som_pontos.wav"));

    }

    private void initializeObjects() {

        //Gdx.app.log("", "Jogo iniciado");
        random = new Random();
        batch = new SpriteBatch();
        widthDisplay = Gdx.graphics.getWidth();
        heightDisplay = Gdx.graphics.getHeight();
        birdInitialVerticalPosition = heightDisplay / 2f;
        pipePositionHorizontal = widthDisplay;
        pipesMargin = Constants.PIPES_MARGIN;

        textScoreResult = new BitmapFont();
        textScoreResult.setColor(Color.WHITE);
        textScoreResult.getData().setScale(6f);

        textGameRestart = new BitmapFont();
        textGameRestart.setColor(Color.GREEN);
        textGameRestart.getData().setScale(3f);

        textGameOverScoreResult = new BitmapFont();
        textGameOverScoreResult.setColor(Color.RED);
        textGameOverScoreResult.getData().setScale(3f);

        shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();
        pipeTopRectangle = new Rectangle();
        pipeBottomRectangle = new Rectangle();

    }

    @Override
    public void render() {

        verifyEstateOfGame();
        scoreValidation();
        drawTexture();
        birdPipeCollision();

    }

    private void birdPipeCollision() {

        birdCircle.set(Constants.BIRD_START_WIDTH + birds[0].getWidth() / 2f ,
            birdInitialVerticalPosition + birds[0].getHeight() / 2f ,birds[0].getWidth() / 2f);

        pipeTopRectangle.set(
            pipePositionHorizontal, heightDisplay / 2f + pipesMargin / 2f + pipePositionVertical,
            pipeTop.getWidth(), pipeTop.getHeight()
        );

        pipeBottomRectangle.set(
            pipePositionHorizontal,
            heightDisplay / 2f - pipeBottom.getHeight() - pipesMargin / 2f + pipePositionVertical,
            pipeBottom.getWidth(), pipeBottom.getHeight()
        );

        boolean birdCollidedTop = Intersector.overlaps(birdCircle, pipeTopRectangle);
        boolean birdCollidedBottom = Intersector.overlaps(birdCircle, pipeBottomRectangle);

        if ( birdCollidedTop ||  birdCollidedBottom ) {
            if( gameStatus == 1 ){
              // Gdx.app.log("Colisão", "Passaro colidiu com o cano");
                soundCollision.play();
                gameStatus = 2;

            }

        }

        // Desenhar o circulo do passaro e os retangulos dos canos
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        shapeRenderer.circle(50 + birds[0].getWidth() / 2f ,
            birdInitialVerticalPosition + birds[0].getHeight() / 2f ,
            birds[0].getWidth() / 2f
        );

        shapeRenderer.rect(
            pipePositionHorizontal, heightDisplay / 2f + pipesMargin / 2f + pipePositionVertical,
            pipeTop.getWidth(), pipeTop.getHeight()
        );

        shapeRenderer.rect(
            pipePositionHorizontal,
            heightDisplay / 2f - pipeBottom.getHeight() - pipesMargin / 2f + pipePositionVertical,
            pipeBottom.getWidth(), pipeBottom.getHeight()
        );
        shapeRenderer.end();*/
    }



    private void scoreValidation() {

        if ( pipePositionHorizontal < 50 - birds[0].getWidth() ) {
            if ( !passPipe ) {
                score++;
                passPipe = true;
                soundPoint.play();

            }
        }

        wingsVariations += Gdx.graphics.getDeltaTime() * Constants.BIRD_WINGS_SPEED;
        if ( wingsVariations> 3){
            wingsVariations = 0f;
        }

    }

    private void verifyEstateOfGame() {
        touchScreen = Gdx.input.isTouched();
        if ( gameStatus == 0 ) {
            gameWaiting();
        } else  if ( gameStatus == 1 ) {
            gameStart();
        } else if ( gameStatus == 2 ) {
            gameOverRestartGame();
        }
    }

    private void gameStart() {
        if ( touchScreen ) {
            // Gdx.app.log("touch", "Tela tocada");
            birdGravity= Constants.BIRD_JUMP_SIZE;
            gameStatus = 1;
            soundFlying.play();
        }

        pipePositionHorizontal -= Gdx.graphics.getDeltaTime() * Constants.PIPES_WIDTH_SPEED;
        if ( pipePositionHorizontal < -pipeTop.getWidth() ) {
            pipePositionHorizontal = widthDisplay;
            pipePositionVertical = random.nextInt( Constants.PIPES_MAX_SIZE ) - Constants.PIPE_RANDOM_SIZE ;
            Gdx.app.log("random", "Valor aleatorio: " + pipePositionVertical);
            passPipe = false;

        }

        if ( birdInitialVerticalPosition > 0 || touchScreen ) {
            birdInitialVerticalPosition = birdInitialVerticalPosition - birdGravity;
        }

        birdGravity++;
    }

    private void gameWaiting() {
        if ( touchScreen ) {
            // Gdx.app.log("touch", "Tela tocada");
            birdGravity= Constants.BIRD_JUMP_SIZE;
            gameStatus = 1;
            soundFlying.play();
        }
    }

    private void gameOverRestartGame() {

        if ( birdInitialVerticalPosition > 0 || touchScreen ) {
            birdInitialVerticalPosition = birdInitialVerticalPosition - birdGravity;
           // birdGravity++;
        }


        if ( touchScreen ) {
            gameStatus = 0;
            score = 0;
            birdGravity = 0f;
            birdInitialVerticalPosition = heightDisplay / 2f;
            pipePositionHorizontal = widthDisplay;
        }
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
        textScoreResult.draw(batch, "Pontos: " + score , widthDisplay / 2f - 200f, heightDisplay - 200f
        );

        if ( gameStatus == 2 ){
            batch.draw(gameOver, widthDisplay / 2f - gameOver.getWidth() / 2f,
                heightDisplay / 2f - gameOver.getHeight() / 2f);

            textGameRestart.draw(batch, "Toque para reiniciar", widthDisplay / 2f - 200f,
                heightDisplay / 2f - gameOver.getHeight() / 2f - 200f);

            textGameOverScoreResult.draw(batch, "A melhor pontução: " + score + " pontos", widthDisplay / 2f - 190f,
                heightDisplay / 2f - gameOver.getHeight() / 2f - 100f);
        }
        batch.end();

    }

    @Override
    public void dispose() {
        Gdx.app.log("dispose", "Remover conteudos");
    }
}
