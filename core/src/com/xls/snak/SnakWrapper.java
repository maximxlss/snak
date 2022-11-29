package com.xls.snak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.xls.snak.utils.Direction;
import com.xls.snak.utils.StateManager;

import javax.swing.plaf.nimbus.State;

public class SnakWrapper {
    Texture snake_graphics_img;
    Array<TextureRegion> snake_tiles;
    TextureRegion game_over;
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
        snake_tiles = new Array<>();
        snake_tiles.add(null);
        snake_tiles.add(new TextureRegion(snake_graphics_img, 1, 1, 16, 16));
        snake_tiles.add(new TextureRegion(snake_graphics_img, 18, 1, 16, 16));
        snake_tiles.add(new TextureRegion(snake_graphics_img, 34, 1, 16, 16));
        snake_tiles.add(new TextureRegion(snake_graphics_img, 52, 1, 16, 16));
        snake_tiles.add(new TextureRegion(snake_graphics_img, 69, 1, 16, 16));
        snake_tiles.add(new TextureRegion(snake_graphics_img, 87, 1, 16, 16));
        game_over = new TextureRegion(snake_graphics_img, 1, 19, 99, 16);
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
                if (logic.IsGameOver())
                    StateManager.currentState = new Menu();
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
                break;
            case GameOver:
                if (StateManager.data_store.highscore < logic.GetScore()) {
                    StateManager.data_store.highscore = logic.GetScore();
                    Preferences prefs = Gdx.app.getPreferences("highscore");
                    prefs.putLong("score", StateManager.data_store.highscore);
                    prefs.flush();
                }
                break;
        }
    }

    public void render(SpriteBatch batch, float x, float y, float width, float height) {
        timer += Gdx.graphics.getDeltaTime();
        if (timer >= move_time) {
            Move();
            timer = 0;
        }
        batch.draw(checkerboard, x, y, width, height);
        logic.render(batch, x, y, width, height, snake_tiles);
        font.getData().setScale(7, 7);
        font.draw(batch, String.valueOf(logic.GetScore()), 5, 75);
        if (logic.IsGameOver()) {
            float middle_x = x + width / 2;
            float middle_y = y + height / 2;
            float msg_scale = width / game_over.getRegionWidth() * 0.75f;
            batch.draw(game_over,
                    middle_x - game_over.getRegionWidth() * msg_scale / 2,
                    middle_y - game_over.getRegionHeight() * msg_scale / 2,
                    game_over.getRegionWidth() * msg_scale,
                    game_over.getRegionHeight() * msg_scale);
        }

    }

    public void dispose() {
        snake_graphics_img.dispose();
        checkerboard.dispose();
        font.dispose();
    }
}
