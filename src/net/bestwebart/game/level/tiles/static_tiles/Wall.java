package net.bestwebart.game.level.tiles.static_tiles;

import net.bestwebart.game.gfx.Sprite;
import net.bestwebart.game.level.tiles.StaticTile;

public class Wall extends StaticTile {

    public Wall(int id, Sprite sprite) {
	super(id, sprite);
	setSolidTo(true);
	setDestroyableTo(true);
	maxDamage = 100;
    }

}
