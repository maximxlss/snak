package com.xls.snak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.xls.snak.utils.Direction;
import com.xls.snak.utils.StateAdapter;
import com.xls.snak.utils.StateManager;

import javax.swing.plaf.nimbus.State;

import static com.xls.snak.utils.Drawing.*;

public class Menu implements StateAdapter {
    Texture atlas;
    TextureRegion Logo;
    TextureRegion NumberBox;
    TextureRegion UpArrow;
    TextureRegion UpArrowHover;
    TextureRegion DownArrow;
    TextureRegion DownArrowHover;
    TextureRegion PlayButton;
    TextureRegion PlayButtonHover;
    Rectangle logo_rect;
    Rectangle number_box_rect;
    Rectangle up_arrow_rect;
    Rectangle down_arrow_rect;
    Rectangle play_button_rect;
    BitmapFont font;

    public Menu() {
        atlas = new Texture("menu-graphics.png");
        Logo = new TextureRegion(atlas, 1, 1, 215, 85);
        PlayButton = new TextureRegion(atlas, 1, 89, 126, 55);
        PlayButtonHover = new TextureRegion(atlas, 1, 145, 126, 55);
        NumberBox = new TextureRegion(atlas, 129, 89, 120, 55);
        UpArrow = new TextureRegion(atlas, 129, 146, 28, 28);
        UpArrowHover = new TextureRegion(atlas, 159, 146, 28, 28);
        DownArrow = new TextureRegion(UpArrow); DownArrow.flip(false, true);
        DownArrowHover = new TextureRegion(UpArrowHover); DownArrowHover.flip(false, true);
        logo_rect = get_rect_with_scale_coord_from_center(Logo, 0, 250, 3);
        number_box_rect = get_rect_with_scale_coord_from_center(NumberBox, 30, 0, 3);
        up_arrow_rect = get_rect_with_scale_coord_from_center(UpArrow, -190, 40, 2.6f);
        down_arrow_rect = get_rect_with_scale_coord_from_center(DownArrow, -190, -40, 2.6f);
        play_button_rect = get_rect_with_scale_coord_from_center(PlayButton, 0, -200, 2.5f);
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font_0.png"), false);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                if (button != Input.Buttons.LEFT)
                    return false;
                Vector2 mouse = StateManager.viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                if (up_arrow_rect.contains(mouse.x, mouse.y)) {
                    StateManager.data_store.last_chosen_size += 1;
                    if (StateManager.data_store.last_chosen_size == 100)
                        StateManager.data_store.last_chosen_size -= 98;
                } else if (down_arrow_rect.contains(mouse.x, mouse.y)) {
                    StateManager.data_store.last_chosen_size -= 1;
                    if (StateManager.data_store.last_chosen_size == 1)
                        StateManager.data_store.last_chosen_size += 98;
                } else if (play_button_rect.contains(mouse.x, mouse.y)) {
                    StateManager.currentState = new SinglePlayer(StateManager.data_store.last_chosen_size, StateManager.data_store.last_chosen_size);
                } else return false;
                return true;
            }
        });
    }

    public void render(SpriteBatch batch, Vector2 mouse) {
        draw_with_rect(batch, Logo, logo_rect);
        draw_with_rect(batch, NumberBox, number_box_rect);
        draw_with_rect(batch, up_arrow_rect.contains(mouse.x, mouse.y) ? UpArrowHover : UpArrow, up_arrow_rect);
        draw_with_rect(batch, down_arrow_rect.contains(mouse.x, mouse.y) ? DownArrowHover : DownArrow, down_arrow_rect);
        draw_with_rect(batch, play_button_rect.contains(mouse.x, mouse.y) ? PlayButtonHover : PlayButton, play_button_rect);
        font.getData().setScale(13, 13);
        font.draw(batch, String.valueOf(StateManager.data_store.last_chosen_size), 455, 563);
        font.getData().setScale(5, 5);
        font.draw(batch, "High-score: " + StateManager.data_store.highscore, 10, 70);
    }

    public void dispose() {
        atlas.dispose();
    }
}
