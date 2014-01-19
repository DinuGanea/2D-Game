package net.bestwebart.game.level.tiles;

import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.gfx.Sprite;
import net.bestwebart.game.level.tiles.anim_tiles.WallRuin;
import net.bestwebart.game.level.tiles.static_tiles.Grass;
import net.bestwebart.game.level.tiles.static_tiles.Wall;

public abstract class Tile {

    protected int id;
    public Sprite sprite;

    protected boolean solid = false, destroyable = false, damaged = false;
    protected Tile tile_for_damage = null;

    public static Tile tiles[] = new Tile[100];

    public static final Tile WALL_RUIN = new WallRuin(10, Sprite.WALL_RUIN, false, -1, 3);

    public static final Tile VOID = new StaticTile(0, Sprite.VOID);
    public static final Tile GRASS = new Grass(1, Sprite.GRASS);
    public static final Tile WALL = new Wall(2, Sprite.WALL);

    public static final Tile PLAYER_UP = new AnimatedTile(20, Sprite.PLAYER_UP, false, 120, 3);
    public static final Tile PLAYER_DOWN = new AnimatedTile(21, Sprite.PLAYER_DOWN, false, 120, 3);
    public static final Tile PLAYER_SIDE = new AnimatedTile(22, Sprite.PLAYER_SIDE, false, 120, 3);

    public static final Tile TONNY_UP = new AnimatedTile(23, Sprite.TONNY_UP, false, 120, 3);
    public static final Tile TONNY_DOWN = new AnimatedTile(24, Sprite.TONNY_DOWN, false, 120, 3);
    public static final Tile TONNY_SIDE = new AnimatedTile(25, Sprite.TONNY_SIDE, false, 120, 3);

    public static final Tile BAD_TONNY_UP = new AnimatedTile(26, Sprite.BAD_TONNY_UP, false, 120, 3);
    public static final Tile BAD_TONNY_DOWN = new AnimatedTile(27, Sprite.BAD_TONNY_DOWN, false, 120, 3);
    public static final Tile BAD_TONNY_SIDE = new AnimatedTile(28, Sprite.BAD_TONNY_SIDE, false, 120, 3);

    public static final Tile SIMPLE_PROJECTILE = new StaticTile(50, Sprite.SIMPLE_PROJECTILE);

    public static final Tile PARTICLE = new StaticTile(70, Sprite.PARTICLE);

    public static final int GRASS_COL = 0xff00ff00;
    public static final int WALL_COL = 0xff151f25;

    public Tile(int id, Sprite sprite, boolean solid) {
	this.id = id;
	if (tiles[id] != null) {
	    throw new RuntimeException("Duplicated tile:" + this + " at position:" + id);
	}
	this.sprite = sprite;
	this.solid = solid;
	tiles[id] = this;
    }

    public int getID() {
	return id;
    }

    public abstract void update();

    public abstract void render(int x, int y, Screen screen);

    public boolean isSolid() {
	return solid;
    }

    public boolean canBeDamaged() {
	return destroyable;
    }

    public void setSolidTo(boolean solid) {
	this.solid = solid;
    }

    public void changeSpriteTo(Sprite sprite) {
	this.sprite = sprite;
    }

    public void setDestroyableTo(boolean destroyable) {
	this.destroyable = destroyable;
    }

    public Tile getDamageTile() {
	return tile_for_damage;
    }

    public void setDamagedTileTo(Tile damaged) {
	this.tile_for_damage = damaged;
    }

    public void damage() {
	damaged = true;
    }

    public boolean isDamaged() {
	return damaged;
    }

}
