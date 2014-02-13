package net.bestwebart.game.entity.particle;

import net.bestwebart.game.entity.Entity;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.Tile;

public class Step extends Entity {

    protected Tile tile;

    protected double life;
    protected long lastTime;

    public Step(double x, double y) {
	super(x, y);
	tile = Tile.PARTICLE;
	this.life = 300 + rand.nextDouble() * 100;
	lastTime = System.currentTimeMillis();
    }

    public void update() {
	if ((System.currentTimeMillis() - lastTime) > life) {
	    remove();
	} else {
	}
    }

    public void move(double nx, double ny) {
    }
    

    public void render(Screen screen) {
	screen.render((int) x, (int) y, tile, false);
    }

}
