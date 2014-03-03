package strongpineapple.mazing.engine.runphase;

import strongpineapple.mazing.engine.runphase.events.RunPhaseEvent;

public interface RunPhaseProcessor {
	RunPhaseEvent calculateSoonestEvent(float currentTime);
	TrajectoryNode processCalculatedEvent(float currentTime);
}
