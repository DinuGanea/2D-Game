package net.bestwebart.game.entity.projectile;

import net.bestwebart.game.Game;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.Tile;

public class SimpleProjectile2 extends Projectile {

	public SimpleProjectile2(double x, double y, double angle) {
		super(x, y);
		this.angle = angle;
		this.tile = Tile.SIMPLE_PROJECTILE;
		this.range = 200;
		this.speed = 10;
		this.rate = 20;

		this.size = 8;
		this.xOffset = this.yOffset = (16 - 8) / 2;

		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);

	}

	public void update() {
		// if (!isExceedingFlyDistance()) move(nx, ny); else remove();
	}

	public void render(Screen screen) {
		screen.render((int) x, (int) y, tile, false);
	}

	public void move(double nx, double ny) {
		while (nx != 0 && ny != 0) {
			if (nx > 1) {
				if (!Game.level.isTileCollision((int) x + getSignedValue(nx),
						(int) y, size, xOffset, yOffset))
					x += getSignedValue(nx);
				nx -= getSignedValue(nx);
			} else {
				if (!Game.level.isTileCollision((int) (x + nx), (int) y, size,
						xOffset, yOffset))
					x += nx;
				nx = 0;
			}
			if (ny > 1) {
				if (!Game.level.isTileCollision((int) x, (int) y
						+ getSignedValue(ny), size, xOffset, yOffset))
					y += getSignedValue(ny);
				ny -= getSignedValue(ny);
			} else {
				if (!Game.level.isTileCollision((int) x, (int) (y + ny), size,
						xOffset, yOffset))
					y += ny;
				ny = 0;
			}
		}
	}

}
