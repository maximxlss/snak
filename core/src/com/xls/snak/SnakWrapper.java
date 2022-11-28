package com.xls.snak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.xls.snak.utils.Direction;

public class SnakWrapper {
    Texture snake_graphics_img;
    Array<TextureRegion> snake_graphics;
    SnakLogic logic;
    short last_pressed_dir = -1;
    float timer = 0;
    float move_time = 0.3f;
    BitmapFont font;
    Texture checkerboard;
    Color checkerboard_color1 = new Color(0.1f, 0.2f, 0.3f, 1f);
    Color checkerboard_color2 = new Color(0.1f, 0.3f, 0.2f, 1f);

    public SnakWrapper(boolean wrapping, int width, int height) {
        snake_graphics_img = new Texture("snake-graphics.png");
        snake_graphics = new Array<>();
        snake_graphics.add(new TextureRegion(snake_graphics_img, 0, 0, 0, 0));
        snake_graphics.add(new TextureRegion(snake_graphics_img, 16 * 0, 0, 16, 16));
        snake_graphics.add(new TextureRegion(snake_graphics_img, 16 * 1, 0, 16, 16));
        snake_graphics.add(new TextureRegion(snake_graphics_img, 16 * 2, 0, 16, 16));
        snake_graphics.add(new TextureRegion(snake_graphics_img, 16 * 3, 0, 16, 16));
        snake_graphics.add(new TextureRegion(snake_graphics_img, 16 * 4, 0, 16, 16));
        snake_graphics.add(new TextureRegion(snake_graphics_img, 16 * 5, 0, 16, 16));
        Pixmap pix = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                if ((x + y) % 2 == 0)
                    pix.setColor(checkerboard_color1);
                else
                    pix.setColor(checkerboard_color2);
                pix.drawPixel(x, y);
            }
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font_0.png"), false);
        checkerboard = new Texture(pix);
        pix.dispose();

        this.logic = new SnakLogic(true, width, height);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        last_pressed_dir = Direction.Up;
                        break;
                    case Input.Keys.RIGHT:
                        last_pressed_dir = Direction.Right;
                        break;
                    case Input.Keys.DOWN:
                        last_pressed_dir = Direction.Down;
                        break;
                    case Input.Keys.LEFT:
                        last_pressed_dir = Direction.Left;
                        break;
                }
                return true;
            }
        });
    }

    private void Move() {
        if (logic.IsGameOver())
            return;
        SnakLogic.Event e = logic.Move(last_pressed_dir);
        switch (e) {
            case AteFruit:
                logic.SpawnFruit();
        }
    }

    public void render(SpriteBatch batch) {
        timer += Gdx.graphics.getDeltaTime();
        if (timer >= move_time) {
            Move();
            timer = 0;
        }
        batch.draw(checkerboard, 0, 0, 800, 800);
        logic.render(batch, 0, 0, 800, 800, snake_graphics);
        font.getData().setScale(7, 7);
        font.draw(batch, String.valueOf(logic.GetScore()), 5, 75);
    }

    public void dispose() {
        snake_graphics_img.dispose();
        checkerboard.dispose();
        font.dispose();
    }
}
