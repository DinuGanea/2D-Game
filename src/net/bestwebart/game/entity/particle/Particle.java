package net.bestwebart.game.entity.particle;

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
        
    public abstract void update();
    
    public abstract void render(Screen screen);

}
