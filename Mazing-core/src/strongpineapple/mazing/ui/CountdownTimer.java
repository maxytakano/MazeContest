package strongpineapple.mazing.ui;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CountdownTimer {
	/**
	 * Milliseconds between updates.
	 */
	private static final long DELTA = 134;
	private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	private ScheduledFuture<?> future;
	private float currentSeconds;
	private float totalCountDownSeconds;
	private long delta = DELTA;
	
	/**
	 * Starts counting down from <code>seconds</code>. Calling this after it has been called will reset the timer.
	 * @param seconds
	 */
	public void start(float seconds) {
		if (future != null) {
			future.cancel(false);
		}
		
		this.totalCountDownSeconds = seconds;
		this.currentSeconds = seconds;
		this.future = executor.scheduleAtFixedRate(new TimerCallback(), 0, delta, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * @return The time, in seconds, until the countdown reaches 0.
	 */
	public float getRemainingTime() {
		return currentSeconds;
	}
	
	/**
	 * @return The time that elapsed since the start of the countdown in seconds.
	 */
	public float getElapsedTime() {
		return totalCountDownSeconds - currentSeconds;
	}
	
	private class TimerCallback implements Runnable {
		@Override
		public void run() {
			currentSeconds -= delta / 1000f;
			
			if (currentSeconds <= 0) {
				currentSeconds = 0;
				future.cancel(false);
			}
		}
	}
}
