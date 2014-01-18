package net.bestwebart.game.entity.mob;

import java.util.List;

import net.bestwebart.game.Game;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.Node;
import net.bestwebart.game.level.tiles.AnimatedTile;
import net.bestwebart.game.level.tiles.Tile;
import net.bestwebart.game.util.Vector2i;

public class BadTonny extends Mob {

	private List<Node> path = null;
	private int time = 0;

	private double tlx, tly;
	private int tile_step, mob_steps;

	public BadTonny(int x, int y) {
		super(x, y, (AnimatedTile) Tile.BAD_TONNY_UP);
		speed = 1;
		tileNr = 0;
		flip = false;
		tlx = tly = 0;
		tile_step = 1;
		mob_steps = 1;

	}

	public void update() {

		double nx = 0d, ny = 0d;
		if (time > 10000)
			time = 0;
		else
			time++;

		if (time % 20 == 0) {
			int pX = (int) Game.level.getPlayer().x + 16 >> 4;
			int pY = (int) Game.level.getPlayer().y + 16 >> 4;
			if (pX != tlx || pY != tly) {
				path = Game.level.findPath(new Vector2i((int) x >> 4,
						(int) y >> 4), new Vector2i(pX, pY));
				tile_step = 1;
				tlx = pX;
				tly = pY;
			}
		}

		if (path != null && path.size() > 0) {
			int tx = (path.get(path.size() - tile_step).tile.getX() << 4) - 8;
			int ty = (path.get(path.size() - tile_step).tile.getY() << 4) - 8;

			if (tx > x)
				nx += speed;
			else if (tx < x)
				nx -= speed;
			if (ty > y)
				ny += speed;
			else if (ty < y)
				ny -= speed;

			mob_steps++;
			if (mob_steps == 16) {
				if ((path.size() - tile_step) > 0) {
					tile_step++;
					mob_steps = 0;
				} else {
					path = null;
					mob_steps = 0;
				}
			}

			animTile.update();
			tileNr = animTile.getCurrFrame();

		}

		if (ny > 0)
			animTile = (AnimatedTile) Tile.BAD_TONNY_DOWN;
		else if (ny < 0)
			animTile = (AnimatedTile) Tile.BAD_TONNY_UP;

		if (nx > 0) {
			animTile = (AnimatedTile) Tile.BAD_TONNY_SIDE;
			flip = true;
		} else if (nx < 0) {
			animTile = (AnimatedTile) Tile.BAD_TONNY_SIDE;
			flip = false;
		}

		if (nx == 0 && ny == 0) {
			tileNr = 0;
			animTile.setCurrFrameTo(0);
		} else {
			move(nx, ny);
		}
	}

	// public boolean isCollision(double x, double y) {
	// boolean collision = false;
	// for (int corner = 0; corner < 4; corner++) {
	// double xc = (x - corner % 2 * 15) / 16;
	// double yc = (y - corner / 2 * 15) / 16;
	// int xi = (corner % 2 == 0) ? (int) Math.floor(xc) : (int) Math.ceil(xc);
	// int yi = (corner / 2 == 0) ? (int) Math.floor(yc) : (int) Math.ceil(yc);
	// if (Game.level.getTile(xi, yi).isSolid()) return true;
	// }
	// return collision;
	// }

	public void render(Screen screen) {
		screen.renderAnimatedTiles((int) x, (int) y, animTile, flip, tileNr);
	}

}
