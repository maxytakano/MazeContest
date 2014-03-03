package strongpineapple.mazing.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreBoardRenderer {
	private static final BitmapFont font = new BitmapFont(true);
	private static final float SECOND_COL_START = 100;
	private static final float THIRD_COL_START = SECOND_COL_START + 75;
	private static final int MAX_NAME_LENGTH = 10;
	private static final CountdownTimer TIMER = new CountdownTimer();
	
	private float x;
	private float y;
	private int round;
	
	/**
	 * Scores for the current round.
	 */
	private Map<String, Float> roundScores;
	
	private String player;
	private ArrayList<String> playerNames = new ArrayList<String>(5);
	private HashMap<String, Float> totalPlayerScores = new HashMap<String, Float>();
	private SpriteBatch spritebatch;

	public ScoreBoardRenderer(float x, float y, SpriteBatch spriteBatch) {
		this.x = x;
		this.y = y;
		this.spritebatch = spriteBatch;
	}
	
	public void render() {
		CharSequence header1 = "Current Round: " + round;
		
		float yPos = y;
		float lineHeight = font.getLineHeight();
		
		font.setColor(255.0f, 255.0f, 255.0f, 0.0f);
		
		font.draw(spritebatch, header1, x, yPos);
		yPos += lineHeight;
		font.draw(spritebatch, "Player", x, yPos);
		font.draw(spritebatch, "Round Score", x + SECOND_COL_START - 20, yPos);
		font.draw(spritebatch, "Total Score", x + THIRD_COL_START - 10, yPos);
		
		yPos += lineHeight;
		
		for(int i = 0; i < playerNames.size(); i++){
			player = playerNames.get(i);
			float finalRoundScore = roundScores != null && roundScores.containsKey(player) ? roundScores.get(player) : 0;
			float currentRoundScore = Math.min(TIMER.getElapsedTime(), finalRoundScore);
			float currentTotalScore = totalPlayerScores.get(player) - finalRoundScore + currentRoundScore;
			
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMinimumIntegerDigits(3);
			decimalFormat.setMaximumIntegerDigits(3);
			decimalFormat.setMinimumFractionDigits(3);
			decimalFormat.setMaximumFractionDigits(3);
			
			String displayName = player.length() <= MAX_NAME_LENGTH ? player : player.substring(0, MAX_NAME_LENGTH + 1);
			font.draw(spritebatch, displayName, x, yPos);
			font.draw(spritebatch, decimalFormat.format(currentRoundScore), x + SECOND_COL_START, yPos);
			font.draw(spritebatch, decimalFormat.format(currentTotalScore), x + THIRD_COL_START, yPos);
			
			yPos += lineHeight;
		}
	}

	/**
	 * 
	 * @param roundScores
	 *            A map of the scores for this round. Key is player, value is
	 *            score. All players currently tracked by this scoreboard must
	 *            be uniquely represented in the keys or else undefined behavior
	 *            will result.
	 * 
	 */
	public void startRunPhase(Map<String, Float> roundScores) {
		this.roundScores = roundScores;
		for (String player : playerNames) {
			float score = totalPlayerScores.get(player) + roundScores.get(player);
			totalPlayerScores.put(player, score);
		}
		
		Map.Entry<String, Float> maxScore = null;
		for (Map.Entry<String, Float> entry : roundScores.entrySet()) {
			if (maxScore == null || entry.getValue() > maxScore.getValue()) {
				maxScore = entry;
			}
		}
		
		TIMER.start(maxScore.getValue());
	}
	
	public void addPlayer( String player ) {
		playerNames.add(player);
		totalPlayerScores.put(player, 0f);
	}
	
	public void setCurrRound( int round ){
		this.round = round;
		this.roundScores = null;
	}
}
