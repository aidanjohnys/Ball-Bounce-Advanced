package aidanjohnys.ballbounceadvanced.Simulation;

import aidanjohnys.ballbounceadvanced.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.concurrent.TimeUnit;


public class SimulationScreen implements Screen {
    private final Stage stage;
    private final Viewport viewport;
    private final OrthographicCamera camera;

    // Box2D
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    public static final float BOX2D_SCALE = 0.01f;
    private static final float GRAVITY = 1f;
    private float accumulator = 0;
    private static final float TIME_STEP = 1/60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;
    public static final int MIN_SIM_FRAME_RATE = 30;


    public SimulationScreen() {
        AssetManager assetManager = new AssetManager();
        assetManager.load("texture/ball.png", Texture.class);
        assetManager.finishLoading();
        assetManager.get("texture/ball.png", Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        viewport = new FitViewport(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, camera);

        world = new World(new Vector2(0, -GRAVITY), true);
        debugRenderer = new Box2DDebugRenderer();

        stage = new Stage(viewport);
        BallManager ballManager = new BallManager(1, assetManager.get("texture/ball.png", Texture.class), world);
        stage.addActor(ballManager);

        WallManager wallManager = new WallManager(world);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        doPhysicsStep(delta);
        stage.act(delta);
        stage.draw();
        //debugRenderer.render(world, camera.combined.scale(1f/BOX2D_SCALE, 1f/BOX2D_SCALE, 1f/BOX2D_SCALE));

        if (Gdx.input.isKeyPressed(Input.Keys.PERIOD)) {
            camera.zoom += 1 * delta;
        }
    }

    private void doPhysicsStep(float deltaTime) {
        float frameTime = Math.min(deltaTime, 1f / MIN_SIM_FRAME_RATE);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void resize(int width, int height) {
        if(width <= 0 || height <= 0) return;

        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
