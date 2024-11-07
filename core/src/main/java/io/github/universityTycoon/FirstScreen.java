package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class FirstScreen implements Screen {

    GameModel gameModel;
    SpriteBatch batch;

    Vector2 touchPos;
    Array<Sprite> dropSprites;



    // Everything that goes in create for an application listener, goes in here
    // Meaning all asset/variable assignments
    final ScreenManager game;
    public FirstScreen(ScreenManager main) {
        this.game = main;
        gameModel = new GameModel();

    }



    @Override
    public void show() {
        batch = new SpriteBatch();

        // start the playback of the background music
        // when the screen is shown
        // music.play();
    }

    @Override
    public void render(float v) {
        input();
        logic();
        draw();
    }

    @Override
    public void resize(int width, int height) {

        GameModel.viewport.update(width, height, true);
    }


    private void input() {
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            GameModel.viewport.unproject(touchPos);
            // ABuilding.setCenterX(touchPos.x); use this to place a building with the mouse
        }

    }

    private void logic() {


        float delta = Gdx.graphics.getDeltaTime();

    }


    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        GameModel.viewport.apply();
        batch.setProjectionMatrix(GameModel.viewport.getCamera().combined);
        batch.begin();

        gameModel.font.draw(batch, "Sentence goes here", 1, 1.5f);

        batch.end();
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

