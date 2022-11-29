package com.xls.snak.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface StateAdapter {
    public void render(SpriteBatch batch, Vector2 mouse);
    public void dispose();
}
