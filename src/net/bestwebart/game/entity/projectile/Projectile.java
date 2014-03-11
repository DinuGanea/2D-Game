package net.bestwebart.game.entity.projectile;

import net.bestwebart.game.Game;
import net.bestwebart.game.entity.Entity;
import net.bestwebart.game.entity.mob.Mob;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.Tile;
import net.bestwebart.game.spawner.Spawner;
import net.bestwebart.game.spawner.Spawner.Type;

public abstract class Projectile extends Entity {
    
    public enum ProjectileType {
	SIMPLE(1), LASER(2);
	
	private int id;
	
	private ProjectileType(int id) {
	    this.id = id;
	}
	
	public int getID() {
	    return id;
	}
    }
    
    protected double sx, sy, nx, ny;
    protected Tile tile;
    protected int rate, range, speed, power;
    protected double angle;
    protected Mob source;

    protected int size, xOffset, yOffset;

    public Projectile(double x, double y, Mob source) {
	super(x, y);
	this.sx = x;
	this.sy = y;
	this.source = source;
	
    }
    
   
    public void update() {
	int col_code = Game.level.isShootCollision((int) (x + nx), (int) (y + ny), size, xOffset, yOffset, this);
	switch (col_code) {
	    case 0:
		move();
		break;
	    case 1:
		remove();
		new Spawner((int) x + 8, (int) y + 8, 75, Type.PARTICLE);
		break;
	    case 2: 
		remove();
		new Spawner((int) x + 8, (int) y + 8, 75, Type.BLOOD);
		break;
	}
    }


    public double getFlownDistance() {
	double distance = Math.sqrt(Math.abs(Math.pow(x - sx, 2) + Math.pow(y - sy, 2)));
	return distance;
    }

    public int getRate() {
	return rate;
    }
    
    public int getPower() {
	return power;
    }
    
    public Mob getSource() {
	return source;
    }
    
/*    public static ProjectileType getType(int id) {
	for (ProjectileType p : ProjectileType.values()) {
	    if (p.id == id) {
		return p;
	    }
	}
	return null;
    }*/
    
    public void move() {
	
    }

    public abstract void render(Screen screen);
}
