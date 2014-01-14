package net.bestwebart.game.entity;

import java.util.Random;

import net.bestwebart.game.gfx.Screen;

public abstract class Entity {
    
    public double x, y;
    protected boolean removed;
    
    protected static Random rand = new Random();
    
    public Entity(double x, double y) {
	this.x = x;
	this.y = y;
	removed = false;
    }
    
    public boolean isRemoved() {
	return removed;
    }
    
    public void remove() {
	removed = true;
    }
    
    
    
    public abstract void update();
    public abstract void render(Screen screen);

}
