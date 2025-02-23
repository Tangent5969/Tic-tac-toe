package com.tangent.tictactoe;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.tangent.tictactoe.Controller.*;

public class Main extends ApplicationAdapter {
    private ShapeRenderer sr;
    private SpriteBatch batch;
    private BitmapFont font;
    private Camera camera;
    private FitViewport viewport;

    @Override
    public void create() {
        Gdx.graphics.setContinuousRendering(false);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                Vector3 coords = viewport.unproject(new Vector3(x, y, 0));
                if (coords.x < 0 || coords.x > Game.boardSize || coords.y < 0 || coords.y > Game.boardSize)
                    return false;

                switch (getMode()) {
                    case Menu:
                        for (Button current : Controller.getButtonList()) {
                            if (current.isPressed((int) coords.x, (int) coords.y)) {
                                return true;
                            }
                        }
                        break;

                    case Playing:
                        Controller.getGame().go((int) coords.x / Game.spaceSize, (int) coords.y / Game.spaceSize, true);
                        return true;

                    case Win:
                        modeSwitch();
                        break;
                }
                return false;
            }
        });

        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Game.boardSize, Game.boardSize, camera);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        Controller.initialise();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply();
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        Controller.render(sr);
        sr.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        Controller.renderText(batch, font);
        batch.end();
    }

    @Override
    public void dispose() {
        sr.dispose();
        batch.dispose();
        font.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.setScreenPosition(viewport.getScreenX(), viewport.getScreenY());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }
}
