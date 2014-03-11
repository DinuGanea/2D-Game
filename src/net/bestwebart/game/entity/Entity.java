package net.bestwebart.game.entity;

import java.util.Random;

import net.bestwebart.game.gfx.Screen;

public abstract class Entity {
    
    

    protected double x, y;
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
    
    public double getX() {
	return x;
    }
    
    public double getY() {
	return y;
    }
        
    public void setX(double x) {
	this.x = x;
    }
    
    public void setY(double y) {
	this.y = y;
    }

    public abstract void update();

    public abstract void render(Screen screen);

}
