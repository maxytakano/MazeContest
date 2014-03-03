package strongpineapple.mazing.engine.runphase.events;

import java.util.Collection;

public abstract class RunPhaseEvent {
	private float time;

	/**
	 * @param time The time at which this event occurs.
	 */
	public RunPhaseEvent(float time) {
		this.time = time;
	}
	
	/**
	 * @return The time at which this event occurs.
	 */
	public float getTime() {
		return time;
	}

	/**
	 * @return The event that occurs first. If one event is null, this method
	 *         returns the other. Returns null if both events are null.
	 */
	public static RunPhaseEvent first(RunPhaseEvent event1, RunPhaseEvent event2) {
		if (event1 == null) {
			return event2;
		}
		
		if (event2 == null) {
			return event1;
		}
		
		return event1.getTime() < event2.getTime() ? event1 : event2;
	}
	
	/**
	 * @param events The events to compare. Events may be null and will not be considered.
	 * @return the first event, if there were any non null events. Returns null if there were no events.
	 */
	public static <T extends RunPhaseEvent> T first(Collection<T> events) {
		T result = null;
		for (T event : events) {
			if (event == null) continue;
			
			if (result == null || event.getTime() < result.getTime()) {
				result = event;
			}
		}
		
		return result;
	}
}
