package net.bestwebart.game.level.tiles;

import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.gfx.Sprite;

public class StaticTile extends Tile {

    public StaticTile(int id, Sprite sprite, boolean solid) {
	super(id, sprite, solid);
    }

    public void update() {
    }

    public void render(int x, int y, Screen screen) {
	screen.render(x << 4, y << 4, this, false);
    }

}
