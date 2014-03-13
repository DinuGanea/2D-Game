package net.bestwebart.game.entity.mob;

import net.bestwebart.game.Game;
import net.bestwebart.game.entity.Entity;
import net.bestwebart.game.entity.projectile.Laser;
import net.bestwebart.game.entity.projectile.Projectile;
import net.bestwebart.game.entity.projectile.Projectile.ProjectileType;
import net.bestwebart.game.entity.projectile.SimpleProjectile;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.AnimatedTile;
import net.bestwebart.game.level.tiles.Tile;
import net.bestwebart.game.level.tiles.animated_tiles.Water;
import net.bestwebart.game.net.packets.Packet02Move;
import net.bestwebart.game.net.packets.Packet03Projectile;
import net.bestwebart.game.spawner.Spawner;
import net.bestwebart.game.spawner.Spawner.Type;

public abstract class Mob extends Entity {

    public enum MobType {
	TONNY(1), BAD_TONNY(2);

	private int id;

	private MobType(int id) {
	    this.id = id;
	}

	public int getID() {
	    return id;
	}
    }
    
    private boolean wasSwimming = false;

    protected boolean invisible, boost;

    protected double speed, normalSpeed;
    protected boolean moving;
    protected boolean flip;

    protected int tileNr;
    protected int dir;
    protected int shoots;
    protected double angle;
    protected int hp;

    protected int uniqueID;
    public int NPCType;

    protected AnimatedTile animTile, animTile_down, animTile_up, animTile_side;
    protected Projectile projectile;

    public Mob(int x, int y, AnimatedTile animTile) {
	super(x, y);
	this.uniqueID = hashCode();
	this.animTile = animTile;
    }

    public void setUniqueID(int uniqueID) {
	this.uniqueID = uniqueID;
    }

    public int getUniqueID() {
	return uniqueID;
    }

    public void move(double nx, double ny) {

	if (ny > 0) {
	    setDir(2);
	    animTile = animTile_down;
	} else if (ny < 0) {
	    setDir(0);
	    animTile = animTile_up;
	}
	if (nx > 0) {
	    setDir(3);
	    animTile = animTile_side;
	    flip = true;
	} else if (nx < 0) {
	    animTile = animTile_side;
	    flip = false;
	    setDir(1);
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

	Packet02Move packet = new Packet02Move((int) x, (int) y, dir, true, this.getUniqueID());
	packet.writeData(Game.game.client);

    }

    public void decreaseDamage(int points) {
	hp -= points;
	if (this instanceof Tonny) {
	    setSpeed(1.5);
	}
    }

    public int getHP() {
	return hp;
    }

    public void setHP(int hp) {
	this.hp = hp;
    }

    public AnimatedTile getTile() {
	return animTile;
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

    public void shoot(double dx, double dy, double x, double y, ProjectileType type, int source) {
	invisible = false;
	double angle = Math.atan2(dy, dx);
	Packet03Projectile packet = null;
	if (type == ProjectileType.SIMPLE) {
	    projectile = new SimpleProjectile(x, y, angle, this);
	    packet = new Packet03Projectile((int) x, (int) y, angle, this.getUniqueID(), ProjectileType.SIMPLE.getID());
	} else if (type == ProjectileType.LASER) {
	    projectile = new Laser(x, y, angle, this);
	    packet = new Packet03Projectile((int) x, (int) y, angle, this.getUniqueID(), ProjectileType.LASER.getID());
	}

	packet.writeData(Game.game.client);
	// Game.level.addEntity(projectile);
    }

    public boolean isInvisible() {
	return invisible;
    }

    public void resetSpeed() {
	speed = normalSpeed;
    }

    public void setSpeed(double speed) {
	this.speed = speed;
    }

    public void setDir(int dir) {
	this.dir = dir;
	switch (dir) {
	    case 0:
		animTile = animTile_up;
		break;
	    case 1:
		animTile = animTile_side;
		flip = false;
		break;
	    case 2:
		animTile = animTile_down;
		break;
	    case 3:
		animTile = animTile_side;
		flip = true;
		break;
	}
    }

    public void setMoving(boolean moving) {
	this.moving = moving;
    }

    public void setInvisible(boolean invisible) {
	this.invisible = invisible;
    }

    public boolean inWater() {
	if (Game.game.getLevel().getTile((int) x + 16 >> 4, (int) y + 30 >> 4) instanceof Water) {
	    return true;
	} else {
	    return false;
	}
    }

    private int walk_delay = 30;

    public void update() {
		
	if (hp <= 0) {
	    new Spawner((int) x + 16, (int) y + 16, 1000, Type.BLOOD);
	    this.remove();
	}

	if (shoots > 0) {
	    shoots--;
	}
	
	if (moving) {
	    animTile.update();
	    tileNr = animTile.getCurrFrame();
	} else if (inWater()) {
	    animTile.update();
	    tileNr = animTile.getCurrFrame();
	} else {
	    if (walk_delay <= 0) {
		animTile.setCurrFrameTo(0);
		tileNr = 0;
		walk_delay = 30;
	    }
	}

	walk_delay--;
    }
      
    public void render(Screen screen) {
	if (inWater()) {	    
	    this.animTile.setTransitionPeriod(240);
	    screen.renderAnimatedTiles((int) x, (int) y + 15, Tile.WAVES, false, tileNr);
	    screen.renderAnimatedTiles((int) x + 16, (int) y + 15, Tile.WAVES, true, tileNr);
	    
	    if (!wasSwimming) {
		screen.renderMobInWater((int) x, (int) y, animTile, flip, tileNr);
		wasSwimming = true;
	    } else {
		screen.renderMobInWater((int) x, (int) y + 5, animTile, flip, tileNr);		
	    }
	} else {  
	    this.animTile.resetTransitionPeriod();
	    if (wasSwimming) {
		screen.renderAnimatedTiles((int) x, (int) y - 5, animTile, flip, tileNr);
		this.animTile.resetTransitionPeriod();
		wasSwimming = false;
	    } else {
		screen.renderAnimatedTiles((int) x, (int) y, animTile, flip, tileNr);
	    }
	}
    }
}