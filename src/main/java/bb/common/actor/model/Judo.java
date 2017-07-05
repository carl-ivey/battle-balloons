package bb.common.actor.model;

import bb.framework.actor.ActorLifecycleState;
import bb.framework.actor.brain.ActorBrain;

/**
 * Created by willie on 7/2/17.
 */
public class Judo extends AbstractActor {
	private static final int WIDTH = 5;
	private static final int HEIGHT = 11;
	private static final int SPEED = 2;
	private static final int SCORE = 100;

	public static final int ENTER_TTL = 20;
	public static final int EXIT_TTL = 5;

	// TODO Move entering/exiting up to AbstractActor? [WLW]
	private int enterTtl = ENTER_TTL - 1;
	private int exitTtl = EXIT_TTL - 1;

	public Judo(ActorBrain brain, int x, int y) {
		super(brain, x, y, WIDTH, HEIGHT);
		setSpeed(SPEED);
	}

	@Override
	public int getScore() {
		return SCORE;
	}

	public int getEnterTtl() {
		return enterTtl;
	}

	public int getExitTtl() {
		return exitTtl;
	}

	@Override
	public void updateBodyEntering() {
		this.enterTtl--;
		if (enterTtl < 0) {
			setState(ActorLifecycleState.ACTIVE);
		}
	}

	@Override
	public void updateBodyActive() {
		doMove();
	}

	@Override
	public void updateBodyExiting() {
		this.exitTtl--;
		if (exitTtl < 0) {
			setState(ActorLifecycleState.GONE);
		}
	}
}
