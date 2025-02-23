package com.tangent.tictactoe;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Button {

    private final int width;
    private final int height;
    private final int posX;
    private final int posY;
    final private Controller.Method method;
    private String text;
    private Color colour;


    public Button(int width, int height, int posX, int posY, Controller.Method method) {
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        this.colour = Color.GRAY;
        this.method = method;
        this.text = "";
    }

    public Button(int width, int height, int posX, int posY, String text, Controller.Method method) {
        this(width, height, posX, posY, method);
        this.text = text;
    }

    protected boolean collisionCheck(int mouseX, int mouseY) {
        return mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height;
    }

    protected boolean isPressed(int mouseX, int mouseY) {
        if (collisionCheck(mouseX, mouseY)) {
            Controller.activateMethod(method);
            return true;
        }
        return false;
    }

    public void render(ShapeRenderer sr) {
        sr.setColor(colour);
        sr.rect(posX, posY, width, height);
    }

    public void renderText(SpriteBatch batch, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout(font, text);
        font.draw(batch, layout, (posX + (float) width / 2) - (layout.width / 2), (posY + (float) height / 2) + (layout.height / 2));
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }
}


