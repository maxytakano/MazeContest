package strongpineapple.mazing.ui;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Textures {
	private static final Texture runner_endpoint = new Texture(Gdx.files.internal("data/libgdx.png"));
	public static final TextureRegion RUNNER_ENDPOINT = new TextureRegion(runner_endpoint);
	
	private static final Texture runner = new Texture(Gdx.files.internal("data/runner.png"));
	public static final TextureRegion RUNNER = new TextureRegion(runner);
	
	private static final Texture slow_tower = new Texture(Gdx.files.internal("data/slowtower.png"));
	public static final TextureRegion SLOW_TOWER = new TextureRegion(slow_tower);
	
	private static final Texture basic_block = new Texture(Gdx.files.internal("data/basicblock.png"));
	public static final TextureRegion BASIC_BLOCK = new TextureRegion(basic_block);
	
	private static final Texture basic_block_rock = new Texture(Gdx.files.internal("data/basicblockrock.png"));
	public static final TextureRegion BASIC_BLOCK_ROCK = new TextureRegion(basic_block_rock);
	
	private static final Texture slow_tower_rock = new Texture(Gdx.files.internal("data/slowtowerrock.png"));
	public static final TextureRegion SLOW_TOWER_ROCK = new TextureRegion(slow_tower_rock);
	
	private static final Texture basic_block_selected = new Texture(Gdx.files.internal("data/basicblockselected.png"));
	public static final TextureRegion BASIC_BLOCK_SELECTED = new TextureRegion(basic_block_selected);
	
	private static final Texture slow_tower_selected = new Texture(Gdx.files.internal("data/slowtowerselected.png"));
	public static final TextureRegion SLOW_TOWER_SELECTED = new TextureRegion(slow_tower_selected);
	
	static {
		RUNNER_ENDPOINT.flip(false, true);
		RUNNER.flip(false, true);
		SLOW_TOWER.flip(false, true);
		BASIC_BLOCK.flip(false, true);
		BASIC_BLOCK_ROCK.flip(false, true);
		SLOW_TOWER_ROCK.flip(false, true);
		BASIC_BLOCK_SELECTED.flip(false, true);
		SLOW_TOWER_SELECTED.flip(false, true);
	}
}
