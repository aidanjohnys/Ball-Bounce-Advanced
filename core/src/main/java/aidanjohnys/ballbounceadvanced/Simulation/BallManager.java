package aidanjohnys.ballbounceadvanced.Simulation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

import static aidanjohnys.ballbounceadvanced.Main.SCREEN_HEIGHT;
import static aidanjohnys.ballbounceadvanced.Main.SCREEN_WIDTH;

public class BallManager extends Actor {
    private final ArrayList<Ball> balls;
    private final Texture ballTexture;
    private final World world;

    public BallManager(int numberOfBalls, Texture ballTexture, World world) {
        balls = new ArrayList<>();
        this.ballTexture = ballTexture;
        this.world = world;

        for (int i = 0; i < numberOfBalls; i++) {
            spawnBall();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (Ball b: balls) {
            b.draw(batch, parentAlpha);
        }
    }

    @Override
    public void act(float delta) {
        for (Ball b: balls) {
            b.act(delta);
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            spawnBall();
        }
    }

    private void spawnBall() {
        Ball ball = new Ball(ballTexture, world, SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f);
        balls.add(ball);
    }
}
