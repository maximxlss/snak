package com.xls.snak;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.xls.utils.Direction;

public class SnakGame extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture snake_graphics_img;
	Array<TextureRegion> snake_graphics;
	SnakLogic logic;
	short last_pressed_dir = -1;
	float timer = 0;
	float move_time = 0.5f;
	
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);
		batch = new SpriteBatch();
		snake_graphics_img = new Texture("snake-graphics.png");
		snake_graphics = new Array<>();
		snake_graphics.add(new TextureRegion(snake_graphics_img, 64 * 1, 64 * 1, 64, 64));
		snake_graphics.add(new TextureRegion(snake_graphics_img, 64 * 0, 64 * 3, 64, 64));
		snake_graphics.add(new TextureRegion(snake_graphics_img, 64 * 3, 64 * 2, 64, 64));
		snake_graphics.add(new TextureRegion(snake_graphics_img, 64 * 2, 64 * 1, 64, 64));
		snake_graphics.add(new TextureRegion(snake_graphics_img, 64 * 0, 64 * 0, 64, 64));
		snake_graphics.add(new TextureRegion(snake_graphics_img, 64 * 3, 64 * 0, 64, 64));
		logic = new SnakLogic(true, 10, 10);
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
					case Input.Keys.UP:
						last_pressed_dir = Direction.Up;
						Move();
						timer = 0;
						break;
					case Input.Keys.RIGHT:
						last_pressed_dir = Direction.Right;
						Move();
						timer = 0;
						break;
					case Input.Keys.DOWN:
						last_pressed_dir = Direction.Down;
						Move();
						timer = 0;
						break;
					case Input.Keys.LEFT:
						last_pressed_dir = Direction.Left;
						Move();
						timer = 0;
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

	@Override
	public void render() {
		timer += Gdx.graphics.getDeltaTime();
		if (timer >= move_time) {
			Move();
			timer = 0;
		}
		ScreenUtils.clear(0, 0.1f, 0.1f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		logic.render(batch, 0, 0, 800, 800, snake_graphics);
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		snake_graphics_img.dispose();
	}
}
