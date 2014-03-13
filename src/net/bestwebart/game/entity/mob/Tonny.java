package net.bestwebart.game.entity.mob;

import net.bestwebart.game.Game;
import net.bestwebart.game.gfx.Sprite;
import net.bestwebart.game.level.tiles.AnimatedTile;
import net.bestwebart.game.level.tiles.Tile;
import net.bestwebart.game.level.tiles.animated_tiles.MobTile;

public class Tonny extends Mob {

    private long time;
    private double nx, ny;

    public Tonny(int x, int y, int life) {
	super(x, y, (AnimatedTile) Tile.TONNY_UP);
	speed = normalSpeed = 1;
	tileNr = 0;
	time = 0;
	hp = life;

	NPCType = MobType.TONNY.getID();

	animTile_up = new MobTile(-1, Sprite.TONNY_UP, true, 120, 3);
	animTile_down = new MobTile(-1, Sprite.TONNY_DOWN, true, 120, 3);
	animTile_side = new MobTile(-1, Sprite.TONNY_SIDE, true, 120, 3);
    }

    public void update() {

	super.update();

	if (Game.game.server != null) {
	    time = (time > 1000000000) ? 0 : time + 1;
	    if (time % 60 == 0) {
		nx = (rand.nextInt(3) - 1) * speed;
		ny = (rand.nextInt(3) - 1) * speed;

		if (isCollision(x + nx, y)) {
		    nx *= -speed;
		}
		if (isCollision(x, ny + y)) {
		    ny *= -speed;
		}

		if (rand.nextInt(4) == 0) {
		    nx = 0;
		    ny = 0;
		}
	    }
	}

	if (nx == 0 && ny == 0) {
	    moving = false;
	} else {
	    move(nx, ny);
	}

    }
}
