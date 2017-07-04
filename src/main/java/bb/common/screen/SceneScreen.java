package bb.common.screen;

import bb.common.BBConfig;
import bb.common.BBContext;
import bb.common.actor.view.ActorViewFactory;
import bb.common.scene.BBScene;
import bb.common.scene.BBScenePane;
import bb.framework.event.ScreenEvent;
import bb.framework.screen.AbstractScreen;
import bb.framework.util.Assert;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by willie on 7/3/17.
 */
public abstract class SceneScreen extends AbstractScreen {
	private BBScene scene;

	public SceneScreen(String name, BBConfig config, BBContext context, BBScene scene) {
		super(name, config, context);
		Assert.notNull(scene, "scene can't be null");
		this.scene = scene;
	}

	public BBScene getScene() {
		return scene;
	}

	/**
	 * By default, the screen is a scene pane. Override as desired.
	 *
	 * @return
	 */
	@Override
	public JComponent buildJComponent() {
		BBContext context = (BBContext) getContext();
		ActorViewFactory avf = context.getActorViewFactory();
		return new BBScenePane(avf, getScene()) {

			@Override
			public Dimension getPreferredSize() {
				return BBConfig.SCREEN_SIZE_PX;
			}
		};
	}

	@Override
	public ActionListener buildTimerHandler() {
		return new TimerHandler();
	}

	private class TimerHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (scene.isActive()) {
				scene.update();
				repaint();
			} else {
				fireScreenEvent(ScreenEvent.SCREEN_EXPIRED);
			}
		}
	}
}