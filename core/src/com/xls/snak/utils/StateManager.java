package com.xls.snak.utils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.xls.snak.DataStore;

import javax.swing.plaf.nimbus.State;
import javax.xml.crypto.Data;

public class StateManager extends ApplicationAdapter {
    public static OrthographicCamera camera;
    public static FitViewport viewport;
    SpriteBatch batch;
    public static Array<StateAdapter> states;
    public static StateAdapter currentState;

    public static DataStore data_store = new DataStore();

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        viewport = new FitViewport(1000, 1000, camera);
        viewport.apply();
        batch = new SpriteBatch();
        states = new Array<>();
        Preferences prefs = Gdx.app.getPreferences("highscore");
        if(prefs.contains("score")) data_store.highscore = prefs.getLong("score");
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
        Vector2 mouse = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        currentState.render(batch, mouse);
        batch.end();
    }

    @Override
    public void dispose() {
        Preferences prefs = Gdx.app.getPreferences("highscore");
        prefs.putLong("score", data_store.highscore);
        prefs.flush();
        batch.dispose();
        for (StateAdapter state: states) {
            state.dispose();
        }
        currentState.dispose();
    }
}
