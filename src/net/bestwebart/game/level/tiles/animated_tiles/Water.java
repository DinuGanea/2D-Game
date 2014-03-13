package net.bestwebart.game.level.tiles.animated_tiles;

import net.bestwebart.game.gfx.Sprite;
import net.bestwebart.game.level.tiles.AnimatedTile;

public class Water extends AnimatedTile {

    public Water(int id, Sprite sprite, boolean solid, int transitionPeriod, int frames) {
	super(id, sprite, solid, transitionPeriod, frames);	
	solid = false;
    }

}
