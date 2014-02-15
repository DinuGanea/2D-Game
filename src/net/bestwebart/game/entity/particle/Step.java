package net.bestwebart.game.entity.particle;

import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.Tile;

public class Step extends Particle {

    public Step(double x, double y) {
	super(x, y, Tile.PARTICLE);
	this.life = 300 + rand.nextDouble() * 100;
	lastTime = System.currentTimeMillis();
    }

    public void update() {
	if ((System.currentTimeMillis() - lastTime) > life) {
	    remove();
	} else {
	}
    }

    public void render(Screen screen) {
	screen.render((int) x, (int) y, tile, false);
    }

}
