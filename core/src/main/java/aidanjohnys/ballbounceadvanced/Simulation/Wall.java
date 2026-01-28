package aidanjohnys.ballbounceadvanced.Simulation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static aidanjohnys.ballbounceadvanced.Main.SCREEN_HEIGHT;
import static aidanjohnys.ballbounceadvanced.Main.SCREEN_WIDTH;
import static aidanjohnys.ballbounceadvanced.Simulation.SimulationScreen.BOX2D_SCALE;

public class Wall {
    private static final int WALL_DEPTH = 20;

    public Wall(Wall_Type wallType, World world) {
        Vector2 position = new Vector2();

        switch (wallType) {
            case left:
                position.x = -WALL_DEPTH;
                position.y = 0;
                break;

            case right:
                position.x = SCREEN_WIDTH;
                position.y = 0;
                break;

            case top:
                position.x = 0;
                position.y = SCREEN_HEIGHT ;
                break;

            case bottom:
                position.x = 0;
                position.y = -WALL_DEPTH;
                break;
        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        float x = position.x * BOX2D_SCALE;
        float y = position.y * BOX2D_SCALE;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        switch (wallType) {
            case left:
            case right:
                shape.set(verticalWall());
                break;

            case top:
            case bottom:
                shape.set(horizontalWall());
                break;
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private Vector2[] verticalWall() {
        return new Vector2[] {
            new Vector2(0,0),
            new Vector2(WALL_DEPTH * BOX2D_SCALE, 0),
            new Vector2(WALL_DEPTH * BOX2D_SCALE, SCREEN_HEIGHT * BOX2D_SCALE),
            new Vector2(0, SCREEN_HEIGHT * BOX2D_SCALE),
        };
    }

    private Vector2[] horizontalWall() {
        return new Vector2[] {
            new Vector2(0, 0),
            new Vector2(0, WALL_DEPTH * BOX2D_SCALE),
            new Vector2(SCREEN_WIDTH * BOX2D_SCALE, WALL_DEPTH * BOX2D_SCALE),
            new Vector2(SCREEN_WIDTH * BOX2D_SCALE, 0)
        };
    }

    public enum Wall_Type {
        left,
        right,
        top,
        bottom
    }
}
