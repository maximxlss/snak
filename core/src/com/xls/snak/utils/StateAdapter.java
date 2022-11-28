package com.xls.snak.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface StateAdapter {
    public void render(SpriteBatch batch);
    public void dispose();
}
