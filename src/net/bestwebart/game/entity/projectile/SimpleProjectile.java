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
		this.xOffset = this.yOffset = (16 - 8) / 2;

		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);

	}

	@Override
	public void update() {
		if (!Game.level.isTileCollision((int) (x + nx), (int) (y + ny), size,
				xOffset, yOffset)) {
			move();
		} else {
			Game.level.changeTileAt((int) (x + nx) >> 4, (int) (y + ny) >> 4);
			remove();
			new Spawner((int) x + 8, (int) y + 8, 75, Type.PARTICLE);
			return;
		}
	}

	@Override
	public void move() {
		if (getFlownDistance() < range) {
			x += nx;
			y += ny;
		} else {
			remove();
		}
	}

	@Override
	public void render(Screen screen) {
		screen.render((int) x, (int) y, tile, false);
	}
}
