package net.bestwebart.game.level.tiles.static_tiles;

import net.bestwebart.game.gfx.Sprite;
import net.bestwebart.game.level.tiles.StaticTile;

public class Wall extends StaticTile {

    public Wall(int id, Sprite sprite) {
	super(id, sprite);
	solid = true;
	destroyable = true;
	maxDamage = 100;
    }

}
