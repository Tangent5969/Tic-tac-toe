package com.tangent.tictactoe;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class Game {
    private enum Player {
        Noughts, Crosses
    }

    final public static int spaceSize = 100;
    final public static int boardSize = spaceSize * 3;
    final private static Random rand = new Random();
    final private static int[][] boardScores = {{1, 2, 4}, {8, 16, 32}, {64, 128, 256}};
    final private int[] winScores = {7, 56, 448, 73, 146, 292, 273, 84};

    private final boolean infinite;
    private final boolean ai;
    private Player current;
    private Player[][] board;
    private int[] scores;
    private int turnCount;
    private int[][] queue;
    private Player winner;

    Game(Boolean infinite, boolean ai, boolean first) {
        current = Player.Crosses;
        board = new Player[3][3];
        scores = new int[]{0, 0};
        queue = new int[6][2];
        turnCount = 0;
        this.infinite = infinite;
        this.ai = ai;
        if (!first)
            if (ai) go(rand.nextInt(3), rand.nextInt(3), false);
            else current = Player.Noughts;
    }

    public boolean go(int x, int y, boolean player) {
        if (board[x][y] != null) return false;
        board[x][y] = current;
        scores[current.ordinal()] += boardScores[x][y];

        if (infinite) {
            if (turnCount < 6) {
                queue[turnCount] = new int[]{x, y};
            } else {
                board[queue[0][0]][queue[0][1]] = null;
                scores[current.ordinal()] -= boardScores[queue[0][0]][queue[0][1]];
                for (int i = 0; i < 5; i++) {
                    queue[i] = queue[i + 1];
                }
                queue[5] = new int[]{x, y};
            }
        }
        turnCount++;

        for (int i : winScores) {
            if ((i & scores[current.ordinal()]) == i) {
                winner = current;
                Controller.modeSwitch();
                return true;
            }
        }
        if (turnCount == 9 && !infinite) {
            Controller.modeSwitch();
            return true;
        }

        if (current == Player.Noughts) current = Player.Crosses;
        else current = Player.Noughts;
        if (player && ai) {
            while (!go(rand.nextInt(3), rand.nextInt(3), false)) ;
        }
        return true;
    }

    public String getWinner() {
        if (winner == null) return "Draw!";
        return winner.name() + " Win!";
    }

    public void render(ShapeRenderer sr) {
        sr.setColor(Color.WHITE);
        sr.line(spaceSize, 0, spaceSize, boardSize);
        sr.line(spaceSize * 2, 0, spaceSize * 2, boardSize);
        sr.line(0, spaceSize, boardSize, spaceSize);
        sr.line(0, spaceSize * 2, boardSize, spaceSize * 2);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == Player.Crosses) {
                    sr.rectLine(x * spaceSize + spaceSize / 4, y * spaceSize + spaceSize / 4, (x + 1) * spaceSize - spaceSize / 4, (y + 1) * spaceSize - spaceSize / 4, 5);
                    sr.rectLine(x * spaceSize + spaceSize / 4, (y + 1) * spaceSize - spaceSize / 4, (x + 1) * spaceSize - spaceSize / 4, y * spaceSize + spaceSize / 4, 5);
                } else if (board[x][y] == Player.Noughts) {
                    sr.circle(x * spaceSize + spaceSize / 2, y * spaceSize + spaceSize / 2, spaceSize / 4);
                    sr.setColor(Color.BLACK);
                    sr.circle(x * spaceSize + spaceSize / 2, y * spaceSize + spaceSize / 2, spaceSize / 5);
                    sr.setColor(Color.WHITE);
                }
            }
        }
    }
}
