package strongpineapple.mazing.engine.runphase;

import strongpineapple.mazing.engine.runphase.events.RunPhaseEvent;
import strongpineapple.mazing.engine.runphase.events.SlowEffectExpiredEvent;

public class SlowEffectExpirationProcessor implements RunPhaseProcessor {

	private Runner runner;
	private SlowEffectExpiredEvent calculatedEvent;
	
	public SlowEffectExpirationProcessor(Runner runner) {
		this.runner = runner;
	}

	@Override
	public RunPhaseEvent calculateSoonestEvent(float currentTime) {
		if (runner.slowExpiration == null) {
			return null;
		}
		
		calculatedEvent = new SlowEffectExpiredEvent(runner.slowExpiration);
		return calculatedEvent;
	}

	@Override
	public TrajectoryNode processCalculatedEvent(float currentTime) {
		runner.position = runner.position.cpy().add(runner.velocity.cpy().mul(calculatedEvent.getTime() - currentTime));
		runner.velocity = runner.velocity.cpy().nor().mul(runner.getBaseSpeed());
		runner.slowExpiration = null;
		return new TrajectoryNode(calculatedEvent.getTime(), runner.position, runner.velocity);
	}

}
