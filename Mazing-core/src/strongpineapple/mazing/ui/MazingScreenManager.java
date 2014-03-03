package strongpineapple.mazing.ui;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;

public class MazingScreenManager extends Game {
	public MainMenuScreen mainMenu;
	
	@Override
	public void create() {
		Gdx.graphics.setDisplayMode(500, 500, true);
		mainMenu = new MainMenuScreen(this);
		this.setScreen(mainMenu);
	}
}
