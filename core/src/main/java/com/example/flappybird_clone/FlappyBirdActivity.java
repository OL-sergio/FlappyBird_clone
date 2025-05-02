package com.example.flappybird_clone;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
    private Texture imageTitle;

    private Random random;
    private int score = 0;
    private int bestScore = 0;
    private int gameStatus = 0;
    private  boolean touchScreen;
    private boolean passPipe = false;
    private BitmapFont textScoreResult;
    private BitmapFont textGameOverRecordScore;
    private BitmapFont textGameRestart;
    private Sound soundFlying;
    private Sound soundCollision;
    private Sound soundPoint;
    private Preferences preferences;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private Circle birdCircle;
    private Rectangle pipeTopRectangle;
    private Rectangle pipeBottomRectangle;

    private float widthDisplay;
    private float heightDisplay;
    private float wingsVariations = 0f;
    private float birdGravity = 2f;
    private float birdInitialVerticalPosition = 0f;
    private float birdInitialHorizontalPosition = 0f;
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
        imageTitle = new Texture("flappy_bird.png");
        soundFlying = Gdx.audio.newSound(Gdx.files.internal("som_asa.wav"));
        soundCollision = Gdx.audio.newSound(Gdx.files.internal("som_batida.wav"));
        soundPoint = Gdx.audio.newSound(Gdx.files.internal("som_pontos.wav"));

    }

    private void initializeObjects() {

        //Gdx.app.log("", "Jogo iniciado");
        random = new Random();
        batch = new SpriteBatch();
        widthDisplay =  Constants.VIRTUAL_WIDTH;
        heightDisplay = Constants.VIRTUAL_HEIGHT;
        birdInitialVerticalPosition = heightDisplay / 2f;
        pipePositionHorizontal = widthDisplay;
        pipesMargin = Constants.PIPES_MARGIN;

        textScoreResult = new BitmapFont();
        textScoreResult.setColor(Color.WHITE);
        textScoreResult.getData().setScale(5f);

        textGameRestart = new BitmapFont();
        textGameRestart.setColor(Color.GREEN);
        textGameRestart.getData().setScale(2f);

        textGameOverRecordScore = new BitmapFont();
        textGameOverRecordScore.setColor(Color.RED);
        textGameOverRecordScore.getData().setScale(2f);

        shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();
        pipeTopRectangle = new Rectangle();
        pipeBottomRectangle = new Rectangle();

        preferences = Gdx.app.getPreferences(Constants.FLAPPY_BIRD_RECORD_POINTS);
        bestScore = preferences.getInteger(Constants.MAX_SCORE, 0);

        camera = new OrthographicCamera();
        camera.position.set(Constants.VIRTUAL_WIDTH / 2f, Constants.VIRTUAL_HEIGHT / 2f, 0);
        viewport = new StretchViewport(Constants.VIRTUAL_WIDTH , Constants.VIRTUAL_HEIGHT, camera);

    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BITS);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT | Gdx.gl.GL_DEPTH_BUFFER_BIT);
        int error = Gdx.gl.glGetError();
        if (error != GL20.GL_NO_ERROR) {
            Gdx.app.log("OpenGL Error", "Code: $error");
        }


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
            pipeTopLongo.getWidth(), pipeTopLongo.getHeight()
        );

        pipeBottomRectangle.set(
            pipePositionHorizontal,
            heightDisplay / 2f - pipeBottomLongo.getHeight() / 2F - pipesMargin / 2f + pipePositionVertical,
            pipeBottomLongo.getWidth(), pipeBottomLongo.getHeight()
        );

        boolean birdCollidedTop = Intersector.overlaps(birdCircle, pipeTopRectangle);
        boolean birdCollidedBottom = Intersector.overlaps(birdCircle, pipeBottomRectangle);

        if ( birdCollidedTop ||  birdCollidedBottom ) {
            if( gameStatus == 2 ){
              // Gdx.app.log("Colisão", "Passaro colidiu com o cano");
                soundCollision.play();
                gameStatus = 3;

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

        if (gameStatus == 0 ){
            gameMenu();
        } else if ( gameStatus == 1 ) {
            gameWaiting();
        } else  if ( gameStatus == 2 ) {
            gameStart();
        } else if ( gameStatus == 3 ) {
            gameOverRestartGame();
        }
    }

    private void gameMenu() {
        if ( touchScreen ) {
            // Gdx.app.log("touch", "Tela tocada");
            gameStatus = 1;
            soundFlying.play();
        }

        if ( birdInitialVerticalPosition > 0 || touchScreen ) {
            birdInitialVerticalPosition = birdInitialVerticalPosition ;
        }

    }


    private void gameWaiting() {
        if ( touchScreen ) {
            // Gdx.app.log("touch", "Tela tocada");
            birdGravity= Constants.BIRD_JUMP_SIZE;
            gameStatus = 2;
            soundFlying.play();
        }
    }

    private void gameStart() {
        if ( touchScreen ) {
            // Gdx.app.log("touch", "Tela tocada");
            birdGravity= Constants.BIRD_JUMP_SIZE;
            gameStatus = 2;
            soundFlying.play();
        }

        pipePositionHorizontal -= Gdx.graphics.getDeltaTime() * Constants.PIPES_WIDTH_SPEED;
        if ( pipePositionHorizontal <- pipeTopLongo.getWidth() ) {
            pipePositionHorizontal = widthDisplay;
            pipePositionVertical = random.nextInt( Constants.PIPES_MAX_SIZE ) - random.nextInt(Constants.PIPE_RANDOM_SIZE)  ;
            Gdx.app.log("random", "Valor aleatorio: " + pipePositionVertical);
            passPipe = false;

        }

        if ( birdInitialVerticalPosition > 0 || touchScreen ) {
            birdInitialVerticalPosition = birdInitialVerticalPosition - birdGravity;
        }
        birdGravity++;

    }



    private void gameOverRestartGame() {

      /*
        if ( birdInitialVerticalPosition > 0 || touchScreen ) {
            birdInitialVerticalPosition = birdInitialVerticalPosition - birdGravity;
            birdGravity++;
        }
        */
        if (score > bestScore) {
            bestScore = score;
            preferences.putInteger(Constants.MAX_SCORE, bestScore);
            preferences.flush();

        }


        birdInitialHorizontalPosition -= Gdx.graphics.getDeltaTime() * Constants.BIRD_COLLISION_MOVEMENT;

        if ( touchScreen ) {
            gameStatus = 1;
            score = 0;
            birdGravity = 0f;
            birdInitialHorizontalPosition = 0f;
            birdInitialVerticalPosition = heightDisplay / 2f;
            pipePositionHorizontal = widthDisplay;
        }
    }

    private void drawTexture() {

        batch.setProjectionMatrix( camera.combined );

        batch.begin();

        batch.draw( background, 0f, 0f, widthDisplay, heightDisplay);
        batch.draw( birds[ (int) wingsVariations], 50f + birdInitialHorizontalPosition, birdInitialVerticalPosition - Constants.BIRD_START_POSITION );

        batch.draw(
            pipeTopLongo,
            pipePositionHorizontal,
            heightDisplay / 2f + pipesMargin / 2f + pipePositionVertical);

        batch.draw(
            pipeBottomLongo,
            pipePositionHorizontal,
            heightDisplay / 2f - pipeBottomLongo.getHeight() - pipesMargin/ 2f + pipePositionVertical);


        if ( gameStatus == 0 ){

            batch.draw(imageTitle, widthDisplay / 2f - gameOver.getWidth() / 2f,
                heightDisplay / 1.8f - gameOver.getHeight() / 2f);

            textGameRestart.draw(batch, "Toque para iniciar Jogo", widthDisplay / 1.85f - 200f,
                heightDisplay / 1.50f - gameOver.getHeight() / 2f - 180f);

            textGameOverRecordScore.draw(batch, "A melhor pontução: " + bestScore  + " pontos", widthDisplay / 1.84f - 200f,
                heightDisplay /  1.8f - gameOver.getHeight() / 2f - 100f);

        }

        if ( gameStatus == 2){

            textScoreResult.draw(
                batch, "" + score , widthDisplay / 2f , heightDisplay - 200f
            );

        }

        if ( gameStatus == 3 ){

            textScoreResult.draw(
                batch, "" + score , widthDisplay / 2f , heightDisplay - 200f
            );

            batch.draw(gameOver, widthDisplay / 2f - gameOver.getWidth() / 2f,
                heightDisplay / 1.8f - gameOver.getHeight() / 2f);

            textGameRestart.draw(batch, "Toque para reiniciar", widthDisplay / 1.8f - 200f,
                heightDisplay / 1.45f - gameOver.getHeight() / 2.2f - 210f);

            textGameOverRecordScore.draw(batch, "A melhor pontução: " + bestScore  + " pontos", widthDisplay / 1.79f - 200f,
                heightDisplay /  1.75f - gameOver.getHeight() / 2f - 110f);
        }
        batch.end();

    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        Gdx.app.log("dispose", "Remover conteudos");
    }
}
