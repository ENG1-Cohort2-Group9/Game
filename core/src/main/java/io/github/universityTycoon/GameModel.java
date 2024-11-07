package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class GameModel {

    // Game variables
    static FitViewport viewport;
    private final float YEARS_PER_MINUTE = 1f;
    private final int STARTING_YEAR = 2024; // Don't make this < 1970 I beg

    final float START_TIME_SECONDS = 300;
    public float timeRemainingSeconds = START_TIME_SECONDS;


    public BitmapFont font;

    //1 is firstScreen, 2 Mainscreen, update this, functions using this if more screens are added.
    public static int currentScreen;

    public int tileSize = 30;
    public Rectangle[][] activeTiles;

    static float worldWidth;
    static float worldHeight;

    public boolean isPaused;
    // Objects
    GameState gameState;
    EventManager eventManager;
    GameEventListener eventListener;
    ScoreCalculator scoreCalculator;
    AudioSelector audioSelector;
    MapController mapController;

    public GameModel() {

        currentScreen = 0;
        eventListener = new GameEventListener(this::handleEvent); // If you're confused, look into "Java listener pattern" (I am also confused)
        eventManager = new EventManager(eventListener);
        scoreCalculator = new ScoreCalculator();
        audioSelector = new AudioSelector();
        mapController = new MapController();
        viewport = new FitViewport(16, 9);

        worldWidth = viewport.getWorldWidth();

        worldHeight = viewport.getWorldWidth();

        isPaused = false;

        font = new BitmapFont(Gdx.files.internal("ui/font.fnt"),
            Gdx.files.internal("ui/font.png"), false);

        //font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.003f, 0.003f);
    }
    public enum GameState {
        inProgress,
        paused,
        inMenu,
        // ... extend as necessary
    }




    public float getTimeElapsed() {
        return START_TIME_SECONDS - timeRemainingSeconds;
    }

    public float getTimeRemainingSeconds() {
        return timeRemainingSeconds;
    }

    public int getTileSize() {
        return tileSize;
    }

    public Rectangle[][] getActiveTiles() {
        return activeTiles;
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getTimeSeconds() {
        return 0;
    }

    public static int getCurrentScreen() {
        return currentScreen;
    }

    public static float getWorldWidth() {
        return worldWidth;
    }

    public static float getWorldHeight() {
        return worldHeight;
    }

    public void handleEvent(GameEvent event) {
        // Do lots of things probably
    }

    // Converts the value in the timer to the relative game time (for example, after 2 minutes of real world time, the game year might be 2026)
    // MAKE SURE TO USE TIME ELAPSED, NOT TIME REMAINING
    public LocalDateTime getGameTimeGMT(float timeElapsedSeconds) {
        // Not bothering with leap days for now. Only difference will be the game ends an in-game day or two early, but time will still be 5 minutes
        long inGameSeconds = (long)(timeElapsedSeconds * (365 * 24 * 60) * YEARS_PER_MINUTE);
        long startOfYearSeconds = LocalDateTime.of(STARTING_YEAR, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);

        return LocalDateTime.ofEpochSecond(inGameSeconds + startOfYearSeconds, 0, ZoneOffset.UTC);
    }
}
