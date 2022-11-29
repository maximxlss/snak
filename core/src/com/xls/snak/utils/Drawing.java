package com.xls.snak.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Drawing {
    public static Rectangle get_rect_with_scale_coord_from_center(TextureRegion txt, float x, float y, float scale) {
        return new Rectangle(500 + x - txt.getRegionWidth() * scale / 2, 500 + y - txt.getRegionHeight() * scale / 2, txt.getRegionWidth() * scale, txt.getRegionHeight() * scale);
    }

    public static void draw_with_rect(SpriteBatch batch, TextureRegion txt, Rectangle rect) {
        batch.draw(txt, rect.x, rect.y, rect.width, rect.height);
    }

    public static void draw_with_scale_coord_from_center(SpriteBatch batch, TextureRegion txt, float x, float y, float scale) {
        Rectangle rect = get_rect_with_scale_coord_from_center(txt, x, y, scale);
        draw_with_rect(batch, txt, rect);
    }
}
