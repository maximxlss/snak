package com.xls.snak;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.xls.snak.utils.StateAdapter;

public class SinglePlayer implements StateAdapter {
    SnakWrapper snak;

    public SinglePlayer() {
        snak = new SnakWrapper(true, 12, 12);
    }

    public void render(SpriteBatch batch) {
        snak.render(batch, 0, 0, 1000, 1000);
    }

    public void dispose() {
        snak.dispose();
    }
}

