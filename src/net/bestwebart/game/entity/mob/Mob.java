package net.bestwebart.game.entity.mob;

import net.bestwebart.game.Game;
import net.bestwebart.game.entity.Entity;
import net.bestwebart.game.entity.projectile.Projectile;
import net.bestwebart.game.entity.projectile.SimpleProjectile;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.input.MouseHandler;
import net.bestwebart.game.level.tiles.AnimatedTile;

public abstract class Mob extends Entity {

	protected double speed;
	protected boolean moving;
	protected int dir;
	protected boolean flip;

	protected int tileNr;

	protected int shoots;
	protected double angle;

	protected AnimatedTile animTile;
	protected Projectile projectile;

	public Mob(int x, int y, AnimatedTile animTile) {
		super(x, y);
		this.animTile = animTile;
	}

	public void move(double nx, double ny) {
		if (ny > 0) {
			dir = 2;
		} else if (ny < 0) {
			dir = 0;
		}
		if (nx > 0) {
			dir = 3;
		} else if (nx < 0) {
			dir = 1;
		}
		while (nx != 0) {
			if (nx > 1) {
				if (!isCollision(x + getSignedValue(nx), y)) {
					x += getSignedValue(nx);
				}
				nx -= getSignedValue(nx);
			} else {
				if (!isCollision(x + nx, y)) {
					x += nx;
				}
				nx = 0;
			}
		}

		while (ny != 0) {
			if (ny > 1) {
				if (!isCollision(x, y + getSignedValue(ny))) {
					y += getSignedValue(ny);
				}
				ny -= getSignedValue(ny);
			} else {
				if (!isCollision(x, y + ny)) {
					y += ny;
				}
				ny = 0;
			}
		}
	}

	public boolean isCollision(double x, double y) {
		boolean collision = false;
		for (int corner = 0; corner < 4; corner++) {
			int xc = (int) (x + corner % 2 * 15 + 8) >> 4;
			int yc = (int) (y + corner / 2 * 10 + 22) >> 4;
			if (Game.level.getTile(xc, yc).isSolid()) {
				return true;
			}
		}
		return collision;
	}

	private int getSignedValue(double n) {
		return (int) (Math.abs(n) / n);
	}

	protected void shoot(MouseHandler mouse) {
		double dx = mouse.getX() - (x - Game.getXScroll()) * Game.SCALE;
		double dy = mouse.getY() - (y - Game.getYScroll()) * Game.SCALE;

		double angle = Math.atan2(dy, dx);

		projectile = new SimpleProjectile(x, y, angle);
		Game.level.addEntity(projectile);
	}

	@Override
	public abstract void update();

	@Override
	public abstract void render(Screen screen);

}
