package aidanjohnys.ballbounceadvanced.Simulation;

import com.badlogic.gdx.physics.box2d.World;

public class WallManager {
   private final Wall leftWall;
    private final Wall rightWall;
    private final Wall topWall;
    private final Wall bottomWall;

    public WallManager(World world) {
        leftWall = new Wall(Wall.Wall_Type.left, world);
        rightWall = new Wall(Wall.Wall_Type.right, world);
        topWall = new Wall(Wall.Wall_Type.top, world);
        bottomWall = new Wall(Wall.Wall_Type.bottom, world);
    }
}
