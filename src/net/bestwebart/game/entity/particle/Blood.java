package net.bestwebart.game.entity.particle;

import net.bestwebart.game.Game;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.Tile;

public class Blood extends Particle {
    
    protected double xd, yd;
    protected double z, zd;

    public Blood(double x, double y) {
	super(x, y, Tile.BLOOD);
	this.life = 1000 + rand.nextDouble() * 20;
	lastTime = System.currentTimeMillis();
	xd = rand.nextGaussian();
	yd = rand.nextGaussian();
	z = rand.nextFloat();
    }

    public void update() {
	if ((System.currentTimeMillis() - lastTime) > life) {
	    remove();
	} else {
	    zd -= 0.1;
	    if (z < 0) {
		z = -0.5;
		zd *= 0.1;
		xd *= 0.1;
		yd *= 0.1;
	    }
	    move((x + xd), (y + yd) + (z + zd));
	}
    }

    public void move(double nx, double ny) {
	if (isCollision(nx, ny)) {
	    xd *= -0.1;
	    yd *= -0.1;
	    zd *= -0.1;
	} else {
	    x += xd;
	    y += yd;
	    z += zd;
	}
    }

    protected boolean isCollision(double x, double y) {
	for (int corner = 0; corner < 4; corner++) {
	    double xc = (x - corner % 2 * 16) / 16;
	    double yc = (y - corner / 2 * 16) / 16;
	    int xi = (corner % 2 == 0) ? (int) Math.floor(xc) : (int) Math.ceil(xc);
	    int yi = (corner / 2 == 0) ? (int) Math.floor(yc) : (int) Math.ceil(yc);
	    if (Game.level.getTile(xi, yi).isSolid()) {
		return true;
	    }
	}
	return false;
    }

    public void render(Screen screen) {
	screen.render((int) x, (int) y - (int) z, tile, false);
    }

}
