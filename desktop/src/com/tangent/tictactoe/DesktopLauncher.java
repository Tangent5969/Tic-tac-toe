package com.tangent.tictactoe;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import static com.tangent.tictactoe.Game.boardSize;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowSizeLimits(boardSize + 1, boardSize + 1, -1, -1);
        config.setForegroundFPS(60);
        config.setTitle("Tic-tac-toe");
        new Lwjgl3Application(new Main(), config);
    }
}
