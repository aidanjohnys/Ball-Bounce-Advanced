package aidanjohnys.ballbounceadvanced;

import aidanjohnys.ballbounceadvanced.Simulation.SimulationScreen;
import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 480;

    @Override
    public void create() {
        setScreen(new SimulationScreen());
    }
}
