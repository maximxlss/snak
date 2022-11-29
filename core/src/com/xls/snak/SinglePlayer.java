package com.xls.snak;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.xls.snak.utils.StateAdapter;

public class SinglePlayer implements StateAdapter {
    SnakWrapper snak;

    public SinglePlayer(int width, int height) {
        snak = new SnakWrapper(true, width, height);
    }

    public void render(SpriteBatch batch, Vector2 mouse) {
        snak.render(batch, 0, 0, 1000, 1000);
    }

    public void dispose() {
        snak.dispose();
    }
}

