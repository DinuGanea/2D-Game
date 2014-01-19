package net.bestwebart.game.level.tiles;

import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.gfx.Sprite;

public class StaticTile extends Tile {

    public StaticTile(int id, Sprite sprite) {
	super(id, sprite, false);
    }

    @Override
    public boolean isSolid() {
	return solid;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(int x, int y, Screen screen) {
	screen.render(x << 4, y << 4, this, false);
    }

}
