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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.xls.snak.utils.Direction;

public class SnakGame extends ApplicationAdapter {
	OrthographicCamera camera;
	FitViewport viewport;
	SpriteBatch batch;
	SnakWrapper snak;
	
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);
		viewport = new FitViewport(800, 800, camera);
		viewport.apply();
		batch = new SpriteBatch();
		snak = new SnakWrapper(true, 12, 12);
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
		snak.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		snak.dispose();
	}
}
