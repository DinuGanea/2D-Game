package net.bestwebart.game.entity.projectile;

import net.bestwebart.game.Game;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.Tile;
import net.bestwebart.game.spawner.Spawner;
import net.bestwebart.game.spawner.Spawner.Type;

public class SimpleProjectile extends Projectile {

    public SimpleProjectile(double x, double y, double angle) {
	super(x, y);
	this.angle = angle;
	this.tile = Tile.SIMPLE_PROJECTILE;
	this.range = 200;
	this.speed = 4;
	this.rate = 20;

	this.size = 8;
	this.xOffset = this.yOffset = 8;

	nx = speed * Math.cos(angle);
	ny = speed * Math.sin(angle);

    }

    public void update() {
	int col_code = Game.level.isShootCollision((int) (x + nx), (int) (y + ny), size, xOffset, yOffset);
	switch (col_code) {
	    case 0:
		move();
		break;
	    case 1:
		remove();
		new Spawner((int) x + 8, (int) y + 8, 75, Type.PARTICLE);
		break;
	    case 2: 
		remove();
		new Spawner((int) x + 8, (int) y + 8, 75, Type.BLOOD);
		break;
	}
    }

    public void move() {
	if (getFlownDistance() < range) {
	    x += nx;
	    y += ny;
	} else {
	    remove();
	}
    }

    public void render(Screen screen) {
	screen.render((int) x, (int) y, tile, false);
    }
}
