package io.github.universityTycoon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floorDiv;

public class MainScreen implements Screen {
    GameModel gameModel;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    Texture backgroundTexture;


    Vector2 mousePos;
    boolean mouseDown;

    String time;



    // Everything that goes in create for an application listener, goes in here
    // Meaning all asset/variable assignments
    final ScreenManager game;
    public MainScreen(ScreenManager main) {
        this.game = main;
        gameModel = new GameModel();
    }



    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();


        mousePos = new Vector2(0,0);

        backgroundTexture = new Texture("images/map.png");
        gameModel.activeTiles = new Rectangle[1920 / gameModel.getTileSize()][840 / gameModel.getTileSize()];

        // start the playback of the background music
        // when the screen is shown
        // music.play();
    }

    @Override
    public void render(float v) {
        input();
        logic();
        batch.begin();
        draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        GameModel.viewport.update(width, height, true);
    }


    private void input() {

        if (Gdx.input.isTouched()) {
            mousePos.set(Gdx.input.getX(), Gdx.input.getY());
            mouseDown = true;
        }
        else {mouseDown = false;}
    }

    private void logic() {

        if (!gameModel.getIsPaused()) {
            gameModel.timeRemainingSeconds -= Gdx.graphics.getDeltaTime();
        }
        if (Math.round(gameModel.timeRemainingSeconds) == 0) {
            gameModel.isPaused = true;
        }
        // This is an example of how the game can be paused
        // To do so in a Main, use gameScreen.timeSeconds
        // and gameScreen.getTimeSeconds
        /*
        if (timeSeconds < 290) {
            pause();
            System.out.println(getTimeSeconds());
        }
        */

        time = String.valueOf(floorDiv((int) gameModel.getTimeRemainingSeconds(), 60))
            + ":" + String.format("%02d", (int) gameModel.getTimeRemainingSeconds() % 60);

    }


    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        GameModel.viewport.apply();

        batch.setProjectionMatrix(GameModel.viewport.getCamera().combined);


        batch.draw(backgroundTexture, 0, 2, 16, 7);
        gameModel.font.draw(batch, time, 7.6f, 8.5f);


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Rectangle[] activeTile : gameModel.getActiveTiles()) {
            for (Rectangle tile : activeTile) {
                if (tile != null) {
                    shapeRenderer.setColor(Color.RED);
                    shapeRenderer.rect(tile.x, tile.y, tile.width, tile.height);
                }
            }
        }
        shapeRenderer.end();

        if (mouseDown) {
            createTile();
        }
    }

    private void createTile() {
        int tileLocationX = ((int) mousePos.x / gameModel.getTileSize());
        int tileLocationY = ((int) mousePos.y / gameModel.getTileSize());
        Vector2 screenLocation = new Vector2(tileLocationX * gameModel.getTileSize(), tileLocationY * gameModel.getTileSize());



        Rectangle rect = new Rectangle();
        rect.set(screenLocation.x, 1080 - screenLocation.y - gameModel.getTileSize(), gameModel.getTileSize(), gameModel.getTileSize());
        gameModel.activeTiles[tileLocationX][tileLocationY] = rect;


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        shapeRenderer.end();
    }


    @Override
    public void pause() {
        gameModel.isPaused = true;
    }

    @Override
    public void resume() {
        gameModel.isPaused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}

