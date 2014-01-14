package net.bestwebart.game.level.tiles;

import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.gfx.Sprite;

public abstract class Tile {

    protected int id;
    public Sprite sprite;

    protected boolean solid = false;

    public static Tile tiles[] = new Tile[100];
    
    public static final Tile GRASS = new StaticTile(0, Sprite.GRASS, false);
    public static final Tile WALL = new StaticTile(1, Sprite.WALL, true);
    public static final Tile VOID = new StaticTile(2, Sprite.VOID, true);
    
    public static final Tile PLAYER_UP = new AnimatedTile(20, Sprite.PLAYER_UP, false, 120, 3);
    public static final Tile PLAYER_DOWN = new AnimatedTile(21, Sprite.PLAYER_DOWN, false, 120, 3);
    public static final Tile PLAYER_SIDE = new AnimatedTile(22, Sprite.PLAYER_SIDE, false, 120, 3);
    
    public static final Tile TONNY_UP = new AnimatedTile(23, Sprite.TONNY_UP, false, 120, 3);
    public static final Tile TONNY_DOWN = new AnimatedTile(24, Sprite.TONNY_DOWN, false, 120, 3);
    public static final Tile TONNY_SIDE = new AnimatedTile(25, Sprite.TONNY_SIDE, false, 120, 3);
    
    public static final Tile BAD_TONNY_UP = new AnimatedTile(26, Sprite.BAD_TONNY_UP, false, 120, 3);
    public static final Tile BAD_TONNY_DOWN = new AnimatedTile(27, Sprite.BAD_TONNY_DOWN, false, 120, 3);
    public static final Tile BAD_TONNY_SIDE = new AnimatedTile(28, Sprite.BAD_TONNY_SIDE, false, 120, 3);   
    
    public static final Tile SIMPLE_PROJECTILE = new StaticTile(50, Sprite.SIMPLE_PROJECTILE, true);
    
    public static final Tile PARTICLE = new StaticTile(70, Sprite.PARTICLE, false);
    
    public static final int GRASS_COL = 0xff00ff00;
    public static final int WALL_COL = 0xff151f25;

    public Tile(int id, Sprite sprite, boolean solid) {
	this.id = id;
	if (tiles[id] != null) throw new RuntimeException("Duplicated tile:" + this + " at position:" + id);
	this.sprite = sprite;
	this.solid = solid;
	tiles[id] = this;
    }

    public abstract void update();

    public abstract void render(int x, int y, Screen screen);

    public boolean isSolid() {
	return solid;
    }

}
