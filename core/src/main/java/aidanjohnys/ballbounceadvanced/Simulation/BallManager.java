package aidanjohnys.ballbounceadvanced.Simulation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class BallManager extends Actor {
    private final ArrayList<Ball> balls;

    public BallManager(int numberOfBalls, Texture ballTexture, World world) {
        balls = new ArrayList<>();

        for (int i = 0; i < numberOfBalls; i++) {
            Ball ball = new Ball(ballTexture, world);
            balls.add(ball);
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
    }
}
