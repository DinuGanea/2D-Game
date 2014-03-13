package net.bestwebart.game.entity.projectile;

import net.bestwebart.game.entity.mob.Mob;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.Tile;
import net.bestwebart.game.spawner.Spawner;
import net.bestwebart.game.spawner.Spawner.Type;

public class Laser extends Projectile {

    public Laser(double x, double y, double angle, Mob source) {
	super(x, y, source);
	this.angle = angle;
	this.tile = Tile.BLOOD;
	
	range = 300;
	speed = 6;
	rate = 5;
	size = 2;
	power = 20;
	
	xOffset = yOffset = 2;

	nx = speed * Math.cos(angle);
	ny = speed * Math.sin(angle);

    }

    public void update() {
	super.update();
    }

    public void move() {
	if (getFlownDistance() < range) {
	    x += nx;
	    y += ny;
	    new Spawner((int) x, (int) y, 10, Type.SPARK);
	} else {
	    remove();
	}
    }

    public void render(Screen screen) {
	screen.render((int) x, (int) y, tile, false);
    }
}
