package net.bestwebart.game.entity.particle;

import net.bestwebart.game.Game;
import net.bestwebart.game.entity.Entity;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.Tile;

public abstract class Particle extends Entity {
    
    protected Tile tile;
    
    protected double life;
    protected long lastTime;

    public Particle(double x, double y, Tile tile) {
	super(x, y);
	this.tile = tile;
    }
    
    protected boolean isCollision(double x, double y) {
	for (int corner = 0; corner < 4; corner++) {
	    double xc = (x - corner % 2 * 16) / 16;
	    double yc = (y - corner / 2 * 16) / 16;
	    int xi = (corner % 2 == 0) ? (int) Math.floor(xc) : (int) Math.ceil(xc);
	    int yi = (corner / 2 == 0) ? (int) Math.floor(yc) : (int) Math.ceil(yc);
	    if (Game.level.getTile(xi, yi).isSolid()) {
		return true;
	    }
	}
	return false;
    }
        
    public abstract void update();
    
    public abstract void render(Screen screen);

}
