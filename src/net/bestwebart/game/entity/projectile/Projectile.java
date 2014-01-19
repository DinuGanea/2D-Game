package net.bestwebart.game.entity.projectile;

import net.bestwebart.game.entity.Entity;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.Tile;

public abstract class Projectile extends Entity {

	protected double sx, sy, nx, ny;
	protected Tile tile;
	protected int rate, range, speed;
	protected double angle;

	protected int size, xOffset, yOffset;

	public Projectile(double x, double y) {
		super(x, y);
		this.sx = x;
		this.sy = y;
	}

	public abstract void update();

	public abstract void render(Screen screen);

	public void move() {

	}

	public double getFlownDistance() {
		double distance = Math.sqrt(Math.abs(Math.pow(x - sx, 2)
				+ Math.pow(y - sy, 2)));
		return distance;
	}

	protected int getSignedValue(double n) {
		return (int) (Math.abs(n) / n);
	}

	public int getRate() {
		return rate;
	}

}
