package bb.attract.backstory;

import bb.common.actor.model.Text;
import bb.common.scene.ScriptScene;

/**
 * Created by willie on 7/2/17.
 */
public class BackstoryScene extends ScriptScene {
	private static final String BACKSTORY =
			"BATTLE BALLOONS\n\n" +
			"IN THE YEAR 2112 THERE LIVES A GROUP OF FRIENDS:\n" +
			"THE BEAST SQUAD.\n\n" +
			"AT SCHOOL,\n" +
			"A WATER BALLOON FIGHT GETS OUT OF HAND.\n" +
			"THE BEAST SQUAD BREAKS TABLES AND GETS SOAKED.\n" +
			"THEY CAN'T GET TOWELS.\n" +
			"THE ONLY DRY PERSON IS A GIRL NAMED LEXI.\n\n" +
			"CAN YOU SAVE HER FROM GETTING...\n" +
			"GROUNDED?";

	private Text backstoryText;

	public BackstoryScene() {
		initActors();
	}

	@Override
	public void doScript(int counter) {

		// TODO Possible refactoring target. [WLW]
		if (counter == 0) {
			getTexts().add(backstoryText);
		} else if (counter >= 450) {
			setActive(false);
		}
	}

	private void initActors() {
		this.backstoryText = new Text(this, BACKSTORY, 20, 40);
	}
}