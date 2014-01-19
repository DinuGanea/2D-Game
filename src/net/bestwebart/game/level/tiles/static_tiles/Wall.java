package net.bestwebart.game.level.tiles.static_tiles;

import net.bestwebart.game.gfx.Sprite;
import net.bestwebart.game.level.tiles.StaticTile;
import net.bestwebart.game.level.tiles.Tile;

public class Wall extends StaticTile {

    public Wall(int id, Sprite sprite) {
	super(id, sprite);
	setSolidTo(true);
	setDestroyableTo(true);
	setDamagedTileTo(Tile.WALL_RUIN);
    }

}
