package com.williewheeler.bb.common.scene;

import com.williewheeler.bb.common.BBConfig;
import com.williewheeler.bb.common.actor.model.Animal;
import com.williewheeler.bb.common.actor.model.Balloon;
import com.williewheeler.bb.common.actor.model.Beat;
import com.williewheeler.bb.common.actor.model.Bengy;
import com.williewheeler.bb.common.actor.model.BigBalloon;
import com.williewheeler.bb.common.actor.model.Bully;
import com.williewheeler.bb.common.actor.model.Judo;
import com.williewheeler.bb.common.actor.model.Lexi;
import com.williewheeler.bb.common.actor.model.Obstacle;
import com.williewheeler.bb.common.actor.model.Teacher;
import com.williewheeler.bb.common.actor.model.Text;
import com.williewheeler.bb.common.actor.model.Turntables;
import com.williewheeler.bb.common.actor.model.YardDuty;
import com.williewheeler.retroge.actor.model.Actor;
import com.williewheeler.retroge.actor.model.ActorLifecycleState;
import com.williewheeler.retroge.actor.model.Player;
import com.williewheeler.retroge.event.GameEvent;
import com.williewheeler.retroge.event.GameListener;
import com.williewheeler.retroge.scene.CollisionDetector;
import com.williewheeler.retroge.scene.Scene;
import com.williewheeler.retroge.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by willie on 7/2/17.
 */
public class BBScene implements Scene {
	private static final Logger log = LoggerFactory.getLogger(BBScene.class);

	private Player player;

	private final List<List<? extends Actor>> allActors = new ArrayList<>();
	private final List<Lexi> lexis = new LinkedList<>();
	private final List<Balloon> balloons = new LinkedList<>();
	private final List<Obstacle> obstacles = new LinkedList<>();
	private final List<Judo> judos = new LinkedList<>();
	private final List<Bully> bullies = new LinkedList<>();
	private final List<YardDuty> yardDuties = new LinkedList<>();
	private final List<Teacher> teachers = new LinkedList<>();
	private final List<Bengy> bengies = new LinkedList<>();
	private final List<Turntables> turntables = new LinkedList<>();
	private final List<Beat> beats = new LinkedList<>();
	private final List<Animal> animals = new LinkedList<>();
	private final List<BigBalloon> bigBalloons = new LinkedList<>();
	private final List<Text> texts = new LinkedList<>();

	private final CollisionDetector collisionDetector = new BBCollisionDetector();
	private final List<GameListener> gameListeners = new LinkedList<>();

	private boolean active = true;

	public BBScene() {
		allActors.add(lexis);
		allActors.add(balloons);
		allActors.add(obstacles);
		allActors.add(judos);
		allActors.add(bullies);
		allActors.add(yardDuties);
		allActors.add(teachers);
		allActors.add(bengies);
		allActors.add(turntables);
		allActors.add(beats);
		allActors.add(animals);
		allActors.add(bigBalloons);
		allActors.add(texts);
	}
	
	@Override
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public int getMinWorldX() {
		return 0;
	}
	
	@Override
	public int getMaxWorldX() {
		return BBConfig.WORLD_SIZE.width - 1;
	}
	
	@Override
	public int getMinWorldY() {
		return 0;
	}
	
	@Override
	public int getMaxWorldY() {
		return BBConfig.WORLD_SIZE.height - 1;
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public List<List<? extends Actor>> getAllActors() {
		return allActors;
	}

	public List<Lexi> getLexis() {
		return lexis;
	}

	public List<Balloon> getBalloons() {
		return balloons;
	}

	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	public List<Judo> getJudos() {
		return judos;
	}

	public List<Bully> getBullies() {
		return bullies;
	}

	public List<YardDuty> getYardDuties() {
		return yardDuties;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public List<Bengy> getBengies() {
		return bengies;
	}

	public List<Turntables> getTurntables() {
		return turntables;
	}

	public List<Beat> getBeats() {
		return beats;
	}

	public List<Animal> getAnimals() {
		return animals;
	}

	public List<BigBalloon> getBigBalloons() {
		return bigBalloons;
	}

	public List<Text> getTexts() {
		return texts;
	}

	public void addGameListener(GameListener listener) {
		Assert.notNull(listener, "listener can't be null");
		gameListeners.add(listener);
		log.trace("BBScene has {} GameListeners:", gameListeners.size());
		gameListeners.forEach(gl -> {
			log.trace("  {}", gl);
		});
	}
	
	@Override
	public void update() {
		garbageCollectActors();
		updateActors();
		collisionDetector.checkCollisions(this);
	}
	
	@Override
	public void fireGameEvent(GameEvent event) {
		gameListeners.forEach(listener -> listener.handleEvent(event));
	}

	private void garbageCollectActors() {
		allActors.forEach(actors -> {
			for (ListIterator<? extends Actor> it = actors.listIterator(); it.hasNext();) {
				Actor actor = it.next();
				if (actor.getState() == ActorLifecycleState.GONE) {
					it.remove();
				}
			}
		});
	}

	private void updateActors() {
		allActors.forEach(actors -> actors.forEach(actor -> actor.update()));
	}
}
