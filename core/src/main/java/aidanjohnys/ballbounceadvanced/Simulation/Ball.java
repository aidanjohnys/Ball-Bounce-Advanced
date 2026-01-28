package aidanjohnys.ballbounceadvanced.Simulation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static aidanjohnys.ballbounceadvanced.Simulation.SimulationScreen.BOX2D_SCALE;

public class Ball extends Actor {
    public static final int BALL_DIAMETER = 50;
    public static final float BODY_DENSITY = 0.5f;
    public static final float BODY_FRICTION = 1f;
    public static final float BODY_RESTITUTION = 1f;
    private final Sprite sprite;
    public final Body body;

    public Ball(Texture texture, World world, float spawnX, float spawnY)  {
        sprite = new Sprite(texture);
        sprite.setColor((float) (Math.random() * 0.7) + 0.3f, (float) (Math.random() * 0.7f) + 0.3f, (float) (Math.random() * 0.7f) + 0.3f, 1f);
        Vector2 position = new Vector2(spawnX, spawnY);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        float x = (position.x + (float) BALL_DIAMETER / 2) * BOX2D_SCALE;
        float y = (position.y + (float) BALL_DIAMETER / 2) * BOX2D_SCALE;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius((float) BALL_DIAMETER / 2 * BOX2D_SCALE);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = BODY_DENSITY;
        fixtureDef.friction = BODY_FRICTION;
        fixtureDef.restitution = BODY_RESTITUTION;
        body.createFixture(fixtureDef);
        circleShape.dispose();

        body.applyLinearImpulse((float) (Math.random() * 1), (float) (Math.random() * 1), 0, 0, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = (body.getPosition().x / BOX2D_SCALE) - (float) BALL_DIAMETER / 2 ;
        float y = (body.getPosition().y / BOX2D_SCALE) - (float) BALL_DIAMETER / 2 ;
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        body.applyAngularImpulse(0.2f * delta,true);
    }
}
