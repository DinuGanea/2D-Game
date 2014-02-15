package net.bestwebart.game.entity.mob;

import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.AnimatedTile;
import net.bestwebart.game.level.tiles.Tile;

public class Tonny extends Mob {

    private long time;
    private double nx, ny;

    public Tonny(int x, int y) {
	super(x, y, (AnimatedTile) Tile.TONNY_UP);
	speed = 1;
	tileNr = 0;
	time = 0;
    }

    public void update() {
	time = (time > 1000000000) ? 0 : time + 1;

	animTile.update();
	tileNr = animTile.getCurrFrame();

	if (time % 60 == 0) {
	    nx = (rand.nextInt(3) - 1) * speed;
	    ny = (rand.nextInt(3) - 1) * speed;

	    if (isCollision(x + nx, y)) {
		nx *= -speed;
	    }
	    if (isCollision(x, ny + y)) {
		ny *= -speed;
	    }

	    if (ny > 0) {
		animTile = (AnimatedTile) Tile.TONNY_DOWN;
	    } else if (ny < 0) {
		animTile = (AnimatedTile) Tile.TONNY_UP;
	    }

	    if (nx > 0) {
		animTile = (AnimatedTile) Tile.TONNY_SIDE;
		flip = true;
	    } else if (nx < 0) {
		animTile = (AnimatedTile) Tile.TONNY_SIDE;
		flip = false;
	    }

	    if (rand.nextInt(4) == 0) {
		nx = 0;
		ny = 0;
	    }
	}

	if (nx == 0 && ny == 0) {
	    tileNr = 0;
	    animTile.setCurrFrameTo(0);
	} else {
	    move(nx, ny);
	}

    }

    public void render(Screen screen) {
	screen.renderAnimatedTiles((int) x, (int) y, animTile, flip, tileNr);
    }

}
