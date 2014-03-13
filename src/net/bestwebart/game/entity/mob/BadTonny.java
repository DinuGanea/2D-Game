package net.bestwebart.game.entity.mob;

import java.util.List;

import net.bestwebart.game.Game;
import net.bestwebart.game.entity.projectile.Projectile.ProjectileType;
import net.bestwebart.game.gfx.Sprite;
import net.bestwebart.game.level.Node;
import net.bestwebart.game.level.tiles.AnimatedTile;
import net.bestwebart.game.level.tiles.Tile;
import net.bestwebart.game.level.tiles.animated_tiles.MobTile;
import net.bestwebart.game.util.Vector2i;

public class BadTonny extends Mob {

    private List<Node> path = null;
    private int time = 0;

    private double tlx, tly;
    private int tile_step, mob_steps;
    
    private Mob follow = null;
    private int changeFollowTime = 0;

    public BadTonny(int x, int y, int life) {
	super(x, y, (AnimatedTile) Tile.BAD_TONNY_UP);
	speed = normalSpeed = 1;
	tileNr = 0;
	flip = false;
	tlx = tly = 0;
	tile_step = 1;
	mob_steps = 1;
	hp = life;

	NPCType = MobType.BAD_TONNY.getID();

	animTile_up = new MobTile(-1, Sprite.BAD_TONNY_UP, true, 120, 3);
	animTile_down = new MobTile(-1, Sprite.BAD_TONNY_DOWN, true, 120, 3);
	animTile_side = new MobTile(-1, Sprite.BAD_TONNY_SIDE, true, 120, 3);
    }

    public void update() {

	super.update();

	double nx = 0d, ny = 0d;

	if (Game.game.server != null) {

	    if (changeFollowTime <= 0) {
		follow = Game.level.getClosestMobToFollow(this.getUniqueID(), new Vector2i((int) this.x, (int) this.y));
		changeFollowTime = 60 * 20;
	    } 
	    changeFollowTime--;

	    if (time > 10000) {
		time = 0;
	    } else {
		time++;
	    }

	    if (follow != null && !follow.isRemoved()) {
		
		double dpx = follow.getX();
		double dpy = follow.getY();

		if (time % 20 == 0) {

		    int pX = (int) dpx + 16 >> 4;
		    int pY = (int) dpy + 16 >> 4;

		    double distance = new Vector2i((int) x, (int) y).getDistance(new Vector2i((int) dpx, (int) dpy));

		    if (distance < 100) {
			updateShooting(dpx, dpy);
		    }

		    if (pX != tlx || pY != tly) {
			path = Game.level.findPath(new Vector2i((int) x >> 4, (int) y >> 4), new Vector2i(pX, pY));
			tile_step = 1;
			tlx = pX;
			tly = pY;
		    }
		}

		if (path != null && path.size() > 0) {
		    int tx = (path.get(path.size() - tile_step).tile.getX() << 4) - 8;
		    int ty = (path.get(path.size() - tile_step).tile.getY() << 4) - 8;

		    if (tx > x) {
			nx += speed;
		    } else if (tx < x) {
			nx -= speed;
		    }
		    if (ty > y) {
			ny += speed;
		    } else if (ty < y) {
			ny -= speed;
		    }

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
		}
	    } else {
		tileNr = 0;
		animTile.setCurrFrameTo(0);
		changeFollowTime = 0;
	    }

	}

	if (nx == 0 && ny == 0) {
	    moving = false;
	} else {
	    move(nx, ny);
	}
    }

    public void updateShooting(double dpx, double dpy) {
	if (shoots <= 0) {
	    shoot(dpx + 16 - x, dpy + 16 - y, x, y + 10, ProjectileType.LASER, this.hashCode());
	    shoots = projectile.getRate();
	}
    }

    public boolean isCollision(double x, double y) {
	boolean collision = false;
	for (int corner = 0; corner < 4; corner++) {
	    double xc = (x - corner % 2 * 15) / 16;
	    double yc = (y - corner / 2 * 15) / 16;
	    int xi = (corner % 2 == 0) ? (int) Math.floor(xc) : (int) Math.ceil(xc);
	    int yi = (corner / 2 == 0) ? (int) Math.floor(yc) : (int) Math.ceil(yc);
	    if (Game.level.getTile(xi, yi).isSolid()) {
		return true;
	    }
	}
	return collision;
    }


}
