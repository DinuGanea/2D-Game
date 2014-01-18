package net.bestwebart.game.level.tiles;

import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.gfx.Sprite;

public class AnimatedTile extends Tile {

	protected int transitionPeriod;
	protected long lTransitionTime;
	protected int currFrame, framesNr;

	public AnimatedTile(int id, Sprite sprite, boolean solid,
			int transitionPeriod, int frames) {
		super(id, sprite, solid);
		this.transitionPeriod = transitionPeriod;
		this.lTransitionTime = System.currentTimeMillis();
		currFrame = 0;
		framesNr = frames;
	}

	@Override
	public void update() {
		if (System.currentTimeMillis() - lTransitionTime >= transitionPeriod) {
			currFrame = (currFrame >= framesNr - 1) ? 0 : currFrame + 1;
			lTransitionTime = System.currentTimeMillis();
		}
	}

	@Override
	public void render(int x, int y, Screen screen) {
		screen.renderAnimatedTiles(x << 4, y << 4, this, false, currFrame);
	}

	public void setCurrFrameTo(int nr) {
		currFrame = nr;
	}

	public int getCurrFrame() {
		return currFrame;
	}

	public void nextFrame() {
		if (currFrame < framesNr - 1) {
			currFrame++;
		}
	}

}
