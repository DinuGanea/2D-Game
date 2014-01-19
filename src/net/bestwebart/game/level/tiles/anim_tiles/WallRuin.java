package net.bestwebart.game.level.tiles.anim_tiles;

import net.bestwebart.game.gfx.Sprite;
import net.bestwebart.game.level.tiles.AnimatedTile;

public class WallRuin extends AnimatedTile {

    public WallRuin(int id, Sprite sprite, boolean solid, int transitionPeriod, int frames) {
	super(id, sprite, solid, transitionPeriod, frames);
	setDestroyableTo(true);
	setSolidTo(true);
	currFrame = 1;
    }

    public void update() {
	if (currFrame >= framesNr - 1) {
	    setSolidTo(false);
	}
    }

    public void nextSprite() {
	currFrame++;
    }

}
