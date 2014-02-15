package net.bestwebart.game.level.tiles.animated_tiles;

import net.bestwebart.game.gfx.Sprite;
import net.bestwebart.game.level.tiles.AnimatedTile;

public class MobTile extends AnimatedTile {

    public MobTile(int id, Sprite sprite, boolean solid, int transitionPeriod, int frames) {
	super(id, sprite, solid, transitionPeriod, frames);	
	mob = true;
	solid = true;
    }

}
