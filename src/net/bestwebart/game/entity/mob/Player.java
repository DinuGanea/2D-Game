package net.bestwebart.game.entity.mob;

import java.awt.Color;
import java.awt.Font;

import net.bestwebart.game.Game;
import net.bestwebart.game.entity.particle.Step;
import net.bestwebart.game.entity.projectile.Projectile.ProjectileType;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.gfx.Sprite;
import net.bestwebart.game.input.KeyboardHandler;
import net.bestwebart.game.input.MouseHandler;
import net.bestwebart.game.level.tiles.AnimatedTile;
import net.bestwebart.game.level.tiles.Tile;
import net.bestwebart.game.level.tiles.animated_tiles.MobTile;

public class Player extends Mob {

    private KeyboardHandler key;
    private MouseHandler mouse;

    private String username;
    private Font font;

    private int steps;

    private boolean step = false;

    public Player(int x, int y, KeyboardHandler key, MouseHandler mouse, String username) {
	super(x, y, (AnimatedTile) Tile.PLAYER_UP);
	this.key = key;
	this.mouse = mouse;
	speed = normalSpeed = 1;
	tileNr = 0;
	hp = 100;
	flip = false;
	this.username = username;
	font = new Font("Arial", Font.BOLD, 15);

	animTile_up = new MobTile(-1, Sprite.PLAYER_UP, true, 120, 3);
	animTile_down = new MobTile(-1, Sprite.PLAYER_DOWN, true, 120, 3);
	animTile_side = new MobTile(-1, Sprite.PLAYER_SIDE, true, 120, 3);
    }
        
        
    public void update() {
	super.update();

	if (mouse != null) {
	    updateShooting();
	}

	double nx = 0;
	double ny = 0;
	
	if (key != null) {

	    if (key.invisible) {
		invisible = true;
	    } else {
		invisible = false;
	    }

	    if (invisible) {
		if (steps > 0) {
		    steps--;
		}
		updateSteps();
	    }

	    if (key.up) {
		ny -= speed;
	    } else if (key.down) {
		ny += speed;
	    }

	    if (key.left) {
		nx -= speed;
		flip = false;
	    } else if (key.right) {
		nx += speed;
		flip = true;
	    }

	}

	if (nx != 0 || ny != 0) {
	    moving = true;
	    move(nx, ny);
	} else {
	    moving = false;
	}

    }

    public void updateShooting() {
	if (mouse.getClickedButton() == 1 && shoots <= 0) {
	    shoot(mouse);
	    shoots = projectile.getRate();
	}
    }

    private void shoot(MouseHandler mouse) {
	double dx = mouse.getX() - getRealX();
	double dy = mouse.getY() - getRealY();
	shoot(dx, dy, x, y, ProjectileType.SIMPLE, this.hashCode());
    }

    private void updateSteps() {
	if (steps <= 0) {
	    if (step) {
		Game.level.addEntity(new Step(x + 16, y + 28));
		step = false;
	    } else {
		Game.level.addEntity(new Step(x + 11, y + 28));
		step = true;
	    }
	    steps = 7;
	}
    }

    public boolean isInvisible() {
	return invisible;
    }

    public String getUsername() {
	return username;
    }

    public double getRealX() {
	return (x - Game.getXScroll()) * Game.SCALE;
    }

    public double getRealY() {
	return (y - Game.getYScroll()) * Game.SCALE;
    }

    public void render(Screen screen) {
	screen.renderText(username, (int) getRealX(), (int) getRealY() - 5, font, Color.black);
	screen.renderAnimatedTiles((int) x, (int) y, animTile, flip, tileNr);
    }

}
