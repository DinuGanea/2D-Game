package net.bestwebart.game.level.tiles.static_tiles;

import net.bestwebart.game.gfx.Sprite;
import net.bestwebart.game.level.tiles.StaticTile;

public class Tree extends StaticTile {

    public Tree(int id, Sprite sprite) {
	super(id, sprite);
	solid = true;
    }

}
