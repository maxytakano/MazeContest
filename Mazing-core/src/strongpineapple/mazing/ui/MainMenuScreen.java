package strongpineapple.mazing.ui;

import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.Screen;

public class MainMenuScreen implements Screen {
	MazingScreenManager screenManager;
	SpriteBatch batch = new SpriteBatch();
	Sprite sprite;
	TextureRegion texture;
	
	public MainMenuScreen(MazingScreenManager screenManager) {
		this.screenManager = screenManager;
		Gdx.input.setInputProcessor(new MainMenuInputProcessor());
		texture = new TextureRegion(new Texture(Gdx.files.internal("data/StartButton.png")));
		texture.flip(false, true);
		sprite = new Sprite(texture);
		sprite.setSize(200, 200);
		sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(true);
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		sprite.draw(batch);
		batch.end();
		
		// Uncomment to start game right away.
		// screenManager.setScreen(new SinglePlayerGameScreen(screenManager));
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new MainMenuInputProcessor());
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	private class MainMenuInputProcessor implements InputProcessor {

		@Override
		public boolean keyDown(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			if (sprite.getBoundingRectangle().contains(screenX, screenY)) {
				screenManager.setScreen(new SinglePlayerGameScreen(screenManager));
			}
			
			return true;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
}
