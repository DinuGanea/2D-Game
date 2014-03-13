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
import net.bestwebart.game.net.packets.Packet06ToggleInvisible;

public class Player extends Mob {

    private KeyboardHandler key;
    private MouseHandler mouse;
    
    private ProjectileType pType;

    private String username;
    private Font font;

    private int steps;
    private int points;

    private boolean keyInvisible = false;
    private boolean keyBoost = false;

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
	points = 0;

	pType = ProjectileType.SIMPLE;
	
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

	    
	    if (key.butt1) {
		pType = ProjectileType.SIMPLE;
	    } else if (key.butt2){
		pType = ProjectileType.LASER;
		
	    }

	    keyInvisible = true;
	    if (key.invisible && points - 2 >= 0) {
		if (invisible) {
		    keyInvisible = false;
		}
		invisible = true;
		points -= 2;
	    } else {
		if (!invisible) {
		    keyInvisible = false;
		}
		invisible = false;
	    }

	    if (invisible) {
		if (steps > 0) {
		    steps--;
		}
		updateSteps();
	    }

	    if (keyInvisible) {
		Packet06ToggleInvisible packetInvisible = new Packet06ToggleInvisible(this.getUniqueID(), invisible);
		packetInvisible.writeData(Game.game.client);
	    }

	    if (key.boost && points > 0) {
		boost = true;
		points--;
		speed = 2;
	    } else {
		boost = false;
		resetSpeed();
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
	    if (projectile != null && projectile.getRate() > 10) {
		shoots = projectile.getRate();
	    } else {
		shoots = 20;
	    }
	}
    }

    private void shoot(MouseHandler mouse) {
	double dx = mouse.getX() - getRealX();
	double dy = mouse.getY() - getRealY();
	
	if (pType == ProjectileType.LASER && points - 10 >= 0) {
	    shoot(dx, dy, x, y, ProjectileType.LASER, this.getUniqueID());
	    points -= 10;
	} else if (pType == ProjectileType.SIMPLE) {
	    shoot(dx, dy, x, y, ProjectileType.SIMPLE, this.getUniqueID());
	}
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
    
    public void setUsername(String username) {
	this.username = username;
    }

    public double getRealX() {
	return (x - Game.getXScroll()) * Game.SCALE;
    }

    public double getRealY() {
	return (y - Game.getYScroll()) * Game.SCALE;
    }

    public int getPoints() {
	return points;
    }

    public void setPoints(int points) {
	this.points = points;
    }

    public void addPoints(int points) {
	this.points += points;
    }
    
    public ProjectileType getProjectileType() {
	return pType;
    }

    public void render(Screen screen) {
	screen.renderText(username, (int) getRealX(), (int) getRealY() - 5, font, Color.black);
	super.render(screen);
    }

}
