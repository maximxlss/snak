package com.xls.snak.utils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class StateManager extends ApplicationAdapter {
    OrthographicCamera camera;
    FitViewport viewport;
    SpriteBatch batch;
    public Array<StateAdapter> states;
    public StateAdapter currentState;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        viewport = new FitViewport(1000, 1000, camera);
        viewport.apply();
        batch = new SpriteBatch();
        states = new Array<>();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0.1f, 0.1f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        currentState.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (StateAdapter state: states) {
            state.dispose();
        }
        currentState.dispose();
    }
}
