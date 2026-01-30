package aidanjohnys.ballbounceadvanced.Simulation;

import aidanjohnys.ballbounceadvanced.Utils.TimeBasedQueue;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static aidanjohnys.ballbounceadvanced.Simulation.SimulationScreen.BOX2D_SCALE;
import static aidanjohnys.ballbounceadvanced.Simulation.SimulationScreen.MIN_SIM_FRAME_RATE;

public class Ball extends Actor {
    private static final int BALL_DIAMETER = 50;
    private static final float BODY_DENSITY = 0.5f;
    private static final float BODY_FRICTION = 1f;
    private static final float BODY_RESTITUTION = 0.9f;
    private static final float BODY_ENERGY = 0.4f;
    private static final float AIR_DENSITY = 0.005f;
    private static final float SPRITE_TRAIL_SPACING = 5f;
    private static final float SPRITE_TRAIL_ALIVE_TIME = 5.5f;
    private static final float SPRITE_TRAIL_OPACITY = 0.1f;
    private final Sprite sprite;
    private final Texture ballTexture;
    private final TimeBasedQueue<Vector2> spriteTrails;
    public final Body body;

    public Ball(Texture ballTexture, World world, float spawnX, float spawnY)  {
        this.ballTexture = ballTexture;
        sprite = new Sprite(ballTexture);
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

        spriteTrails = new TimeBasedQueue<>();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = (body.getPosition().x / BOX2D_SCALE) - (float) BALL_DIAMETER / 2 ;
        float y = (body.getPosition().y / BOX2D_SCALE) - (float) BALL_DIAMETER / 2 ;

        for (int i = 0; i < spriteTrails.size(); i++) {
            Sprite trailSprite = new Sprite(ballTexture);
            trailSprite.setPosition(spriteTrails.get(i).x, spriteTrails.get(i).y);
            trailSprite.setColor(sprite.getColor());
            float aliveTime = spriteTrails.getAliveTime(i);
            trailSprite.setAlpha(Math.max((SPRITE_TRAIL_ALIVE_TIME - aliveTime) / SPRITE_TRAIL_ALIVE_TIME * SPRITE_TRAIL_OPACITY, 0));
            trailSprite.draw(batch, parentAlpha);
        }

        Vector2 recentSprite = spriteTrails.peekLast();

        if (recentSprite != null) {
            float recentX = recentSprite.x;
            float recentY = recentSprite.y;
            float distance = (float) Math.sqrt(Math.pow(x - recentX, 2) + Math.pow(y - recentY, 2));

            if (distance > SPRITE_TRAIL_SPACING) {
                Sprite newSprite = new Sprite(ballTexture);
                newSprite.setPosition(x, y);
                spriteTrails.offer(new Vector2(x, y));
            }
        }

        else {
            spriteTrails.offer(new Vector2(x, y));
        }

        spriteTrails.updateDeltaTime(Math.min(Gdx.graphics.getDeltaTime(), 1f / MIN_SIM_FRAME_RATE));

        if (spriteTrails.peekAliveTime() > SPRITE_TRAIL_ALIVE_TIME) {
            spriteTrails.poll();
        }

        sprite.setPosition(x, y);
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        body.applyAngularImpulse(BODY_ENERGY * delta,true);
        applyAirFriction();
    }

    private void applyAirFriction() {
        Vector2 resistance = new Vector2(0,0);
        if (body.getLinearVelocity().x < 0f) {
            resistance.x = (float) Math.pow(body.getLinearVelocity().x, 2) * AIR_DENSITY;
        }

        else {
            resistance.x = (float) Math.pow(body.getLinearVelocity().x, 2) * -AIR_DENSITY;
        }

        if (body.getLinearVelocity().y < 0f) {
            resistance.y = (float) Math.pow(body.getLinearVelocity().y, 2) * AIR_DENSITY;
        }

        else {
            resistance.y = (float) Math.pow(body.getLinearVelocity().y, 2) * -AIR_DENSITY;
        }

        body.applyForce(resistance, new Vector2(0, 0), true);
    }
}
