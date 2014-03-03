package strongpineapple.mazing.ui;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import strongpineapple.mazing.config.GameConfig;
import strongpineapple.mazing.engine.MazeBuilder;
import strongpineapple.mazing.engine.MazingGameListener;
import strongpineapple.mazing.engine.runphase.RunPhaseEngine;
import strongpineapple.mazing.network.User;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SinglePlayerGameScreen implements Screen, MazingGameListener {
	private static final String USERNAME = "Gamal";
	
	private MazeRenderer mazeRenderer;
	private MazeController mazeController;
	private HudRenderer hudRenderer;
	private InputMultiplexer inputMux = new InputMultiplexer();
	
	private SpriteBatch spriteBatch = new SpriteBatch();
	
	private MazeBuilder mazeBuilder;
	
	private HashMap<String, User> users = new HashMap<String, User>();
	private Map<MazeRenderer, User> runPhaseRenderers;
	
	private ScoreBoardRenderer scoreBoardRenderer;
	
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	private SinglePlayerGame game;
	private int round = 0;
	
	/**
	 * Starts a single player game.
	 * @param screenManager
	 */
	public SinglePlayerGameScreen(MazingScreenManager screenManager) {	
		Gdx.input.setInputProcessor(inputMux);

		this.scoreBoardRenderer = new ScoreBoardRenderer(UiConfig.HUD_X, 0, spriteBatch);
		this.scoreBoardRenderer.addPlayer(USERNAME);
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(true);
		spriteBatch.setProjectionMatrix(camera.combined);
		
		this.game = new SinglePlayerGame(this);
		this.game.start();
	}
	
	@Override
	public void startGame() {
		
	}
	
	@Override
	public void startRound() {
		scoreBoardRenderer.setCurrRound(++round);
	}
	
	@Override
	public void startBuildPhase(int buildPhaseSecs, MazeBuilder mazeBuilder) {
		this.mazeBuilder = mazeBuilder;
		runPhaseRenderers = new ConcurrentHashMap<MazeRenderer, User>();
		
		this.mazeRenderer = new MazeRenderer(UiConfig.SECONDARY_MAZE_WIDTH, 0, UiConfig.PRIMARY_MAZE_WIDTH, UiConfig.PRIMARY_MAZE_HEIGHT, 
				mazeBuilder.getWidth(), mazeBuilder.getHeight(), spriteBatch);
		mazeController = new MazeController(mazeRenderer, mazeBuilder);
		mazeBuilder.setListener(mazeController);
		
		hudRenderer = new HudRenderer(UiConfig.SECONDARY_MAZE_WIDTH, UiConfig.PRIMARY_MAZE_HEIGHT, spriteBatch, mazeBuilder, mazeController);
		hudRenderer.startBuildPhase(buildPhaseSecs);
		
		inputMux.addProcessor(mazeController);
		inputMux.addProcessor(hudRenderer);
		
		executor.schedule(new Runnable() {
			
			@Override
			public void run() {
				endBuildPhase();
			}
		}, buildPhaseSecs, TimeUnit.SECONDS);
	}
	
	private void endBuildPhase() {
		for (int i = 0; i < 3; i++) {
			User user = new User(i + "", i, 0);
			user.setMaze(mazeBuilder.toMaze());
			users.put(user.getName(), user);
		}
		
		User[] sortedUser = users.values().toArray(new User[0]);
		Arrays.sort(sortedUser, new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				return (int) (o2.getScore() - o1.getScore());
			}
		});
		
		for (int i = 0; i < 3 && i < sortedUser.length; i++) {
			float height = Gdx.graphics.getHeight() / 3;
			float y = height * i;
			float width = UiConfig.PRIMARY_MAZE_WIDTH / 2;
			MazeRenderer renderer = new MazeRenderer(0, y, width, height, GameConfig.MAZE_WIDTH, GameConfig.MAZE_HEIGHT, spriteBatch);
			renderer.addBlocks(sortedUser[i].getMaze().getBlocks());
			renderer.setEndPointTiles(GameConfig.RUNNER_START, GameConfig.RUNNER_END);
			runPhaseRenderers.put(renderer, sortedUser[i]);
		}
		
		inputMux.removeProcessor(mazeController);
		inputMux.removeProcessor(hudRenderer);
	}
	
	@Override
	public void startRunPhase(RunPhaseEngine engine) {
		engine.getRunDuration();
		mazeRenderer.startRunPhase(engine.getTrajectory(), engine.getEvents());
		hudRenderer.startRunPhase(engine.getRunDuration());
		
		Map<String, Float> scores = new HashMap<String, Float>();
		scores.put(USERNAME, engine.getRunDuration());
		scoreBoardRenderer.startRunPhase(scores);
		
		for (Entry<MazeRenderer, User> entry : runPhaseRenderers.entrySet()) {
			RunPhaseEngine engine2 = new RunPhaseEngine(entry.getValue().getMaze(), GameConfig.RUNNER_START, GameConfig.RUNNER_END);
			entry.getKey().startRunPhase(engine2.getTrajectory(), engine.getEvents());
		}
	}
	
	@Override
	public void endGame() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(float delta) {
		//Color clearColor = Color.BLUE;
		Color clearColor = new Color(.0f, .05f, .07f, 1f);
		Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, 255);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		
		mazeRenderer.render(delta);
		hudRenderer.render();
		scoreBoardRenderer.render();
		
		for (MazeRenderer renderer : runPhaseRenderers.keySet()) {
			renderer.render(delta);
		}
		
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(true);
		spriteBatch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
}
