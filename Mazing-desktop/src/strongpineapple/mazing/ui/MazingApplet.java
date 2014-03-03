package strongpineapple.mazing.ui;

import com.badlogic.gdx.backends.lwjgl.LwjglApplet;

@SuppressWarnings("serial")
public class MazingApplet extends LwjglApplet {
	public MazingApplet() {
		super(new MazingScreenManager(), false);
	}
}
