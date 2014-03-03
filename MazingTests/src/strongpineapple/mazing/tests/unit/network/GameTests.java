package strongpineapple.mazing.tests.unit.network;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;

import strongpineapple.mazing.core.MazingGame;
import strongpineapple.mazing.engine.Maze;
import strongpineapple.mazing.network.GameController;
import strongpineapple.mazing.network.GameView;

public class GameTests {

	@SuppressWarnings("unchecked")
	@Test
	public void testPhaseChanges() {
		MazingGame game = new MazingGame();
		GameController controller = game.addPlayer("user");
		GameView view = mock(GameView.class);
		controller.setGameView(view);
		
		game.startBuildPhase();
		verify(view).startBuildPhase(any(Maze.class));
		game.startRunPhase();
		verify(view).startRunPhase(any(List.class));
		game.startBuildPhase();
		verify(view, times(2)).startBuildPhase(any(Maze.class));
		game.startRunPhase();
		verify(view, times(2)).startRunPhase(any(List.class));
	}
}
