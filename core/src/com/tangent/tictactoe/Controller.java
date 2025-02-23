package com.tangent.tictactoe;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Controller {
    public enum Method {
        Blank, Normal, Infinite, One, Two, Noughts, Crosses, Start
    }

    public enum Mode {
        Menu, Playing, Win
    }

    private static Mode mode = Mode.Menu;
    private static Game game;
    private static boolean[] variables;

    private static Button[] buttonList = new Button[7];


    public static void initialise() {
        variables = new boolean[]{false, false, true};
        buttonList[0] = new Button(50, 25, 90, 200, "Normal", Method.Normal);
        buttonList[0].setColour(Color.OLIVE);
        buttonList[1] = new Button(50, 25, 160, 200, "Infinite", Method.Infinite);
        buttonList[2] = new Button(50, 25, 90, 165, "1 Player", Method.One);
        buttonList[3] = new Button(50, 25, 160, 165, "2 Player", Method.Two);
        buttonList[3].setColour(Color.OLIVE);
        buttonList[4] = new Button(50, 25, 90, 130, "Noughts", Method.Noughts);
        buttonList[5] = new Button(50, 25, 160, 130, "Crosses", Method.Crosses);
        buttonList[5].setColour(Color.OLIVE);
        buttonList[6] = new Button(100, 25, 100, 95, "Start", Method.Start);
    }

    public static void modeSwitch() {
        switch (mode) {
            case Menu:
                mode = Mode.Playing;
                break;
            case Playing:
                mode = Mode.Win;
                break;
            case Win:
                mode = Mode.Menu;
                break;
        }
    }

    public static Mode getMode() {
        return mode;
    }

    public static Game getGame() {
        return game;
    }

    public static Button[] getButtonList() {
        return buttonList;
    }

    public static void render(ShapeRenderer sr) {
        switch (mode) {
            case Menu:
                for (Button button : buttonList) {
                    button.render(sr);
                }
                break;

            case Playing:
            case Win:
                game.render(sr);
                break;
        }
    }

    public static void renderText(SpriteBatch batch, BitmapFont font) {
        switch (mode) {
            case Menu:
                font.setColor(Color.WHITE);
                font.getData().setScale(1);
                for (Button button : buttonList) {
                    button.renderText(batch, font);
                }
                break;

            case Win:
                font.setColor(Color.RED);
                font.getData().setScale(2);
                GlyphLayout layout = new GlyphLayout(font, game.getWinner());
                font.draw(batch, layout, 150 - (layout.width / 2), 210 + (layout.height / 2));
        }
    }


    protected static void activateMethod(Method method) {
        switch (method) {
            case Blank:
                break;
            case Normal:
                variables[0] = false;
                buttonList[0].setColour(Color.OLIVE);
                buttonList[1].setColour(Color.GRAY);
                break;
            case Infinite:
                variables[0] = true;
                buttonList[1].setColour(Color.OLIVE);
                buttonList[0].setColour(Color.GRAY);
                break;
            case One:
                variables[1] = true;
                buttonList[2].setColour(Color.OLIVE);
                buttonList[3].setColour(Color.GRAY);
                break;
            case Two:
                variables[1] = false;
                buttonList[3].setColour(Color.OLIVE);
                buttonList[2].setColour(Color.GRAY);
                break;
            case Noughts:
                variables[2] = false;
                buttonList[4].setColour(Color.OLIVE);
                buttonList[5].setColour(Color.GRAY);
                break;
            case Crosses:
                variables[2] = true;
                buttonList[5].setColour(Color.OLIVE);
                buttonList[4].setColour(Color.GRAY);
                break;
            case Start:
                game = new Game(variables[0], variables[1], variables[2]);
                mode = Mode.Playing;
                break;
        }
    }
}
