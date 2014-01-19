package net.bestwebart.game.util;

public class Vector2i {

    private int x, y;

    public Vector2i(int x, int y) {
	this.setX(x);
	this.setY(y);
    }

    public int getY() {
	return y;
    }

    public Vector2i setY(int y) {
	this.y = y;
	return this;
    }

    public int getX() {
	return x;
    }

    public Vector2i setX(int x) {
	this.x = x;
	return this;
    }

    public double getDistance(Vector2i v) {
	int x = this.x - v.x;
	int y = this.y - v.y;
	return Math.sqrt(x * x + y * y);
    }

    @Override
    public boolean equals(Object o) {
	if (o instanceof Vector2i) {
	    Vector2i v = (Vector2i) o;
	    if (this.x == v.x && this.y == v.y) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public String toString() {
	return this.x + " " + this.y;
    }

}
