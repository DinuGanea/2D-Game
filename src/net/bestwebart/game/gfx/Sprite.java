package net.bestwebart.game.gfx;

public class Sprite {

    public final int SIZE;
    private int x, y;

    private SpriteSheet sheet;

    public int pixels[];

    public static final Sprite GRASS = new Sprite(0, 0, SpriteSheet.TILES, 16);
    public static final Sprite WALL = new Sprite(0, 1, SpriteSheet.TILES, 16);

    public static final Sprite CRACKS = new Sprite(0, 0, SpriteSheet.CRACKS, 1, 8, 16);
    
    public static final Sprite HEART = new Sprite(0, 0, SpriteSheet.OTHER, 16);

    public static final Sprite VOID = new Sprite(0x0066CC, 16);

    public static final Sprite PLAYER_DOWN = new Sprite(0, 0, SpriteSheet.PLAYER, 1, 3, 32);
    public static final Sprite PLAYER_UP = new Sprite(1, 0, SpriteSheet.PLAYER, 1, 3, 32);
    public static final Sprite PLAYER_SIDE = new Sprite(2, 0, SpriteSheet.PLAYER, 1, 3, 32);

    public static final Sprite TONNY_DOWN = new Sprite(0, 0, SpriteSheet.TONNY, 1, 3, 32);
    public static final Sprite TONNY_UP = new Sprite(1, 0, SpriteSheet.TONNY, 1, 3, 32);
    public static final Sprite TONNY_SIDE = new Sprite(2, 0, SpriteSheet.TONNY, 1, 3, 32);

    public static final Sprite BAD_TONNY_DOWN = new Sprite(0, 0, SpriteSheet.BAD_TONNY, 1, 3, 32);
    public static final Sprite BAD_TONNY_UP = new Sprite(1, 0, SpriteSheet.BAD_TONNY, 1, 3, 32);
    public static final Sprite BAD_TONNY_SIDE = new Sprite(2, 0, SpriteSheet.BAD_TONNY, 1, 3, 32);

    public static final Sprite SIMPLE_PROJECTILE = new Sprite(0, 0, SpriteSheet.PROJECTILES, 16);
    public static final Sprite LASER = new Sprite(0, 1, SpriteSheet.PROJECTILES, 16);

    public static final Sprite PARTICLE = new Sprite(0x000000, 2);
    public static final Sprite BLOOD = new Sprite(0xAF111C, 2);
    public static final Sprite SPARK = new Sprite(0xF6FF00, 1);
    
    public static final Sprite SPECIAL_POWERS = new Sprite(0, 0, SpriteSheet.SPECIAL_POWERS, 1, 4, 32);
    
    

    public Sprite(int x, int y, SpriteSheet sheet, int size) {
	this.SIZE = size;
	this.x = x * SIZE;
	this.y = y * SIZE;
	this.sheet = sheet;
	pixels = new int[SIZE * SIZE];
	loadSprite();
    }

    public Sprite(int color, int size) {
	this.SIZE = size;
	pixels = new int[SIZE * SIZE];
	for (int i = 0; i < SIZE * SIZE; i++) {
	    pixels[i] = color;
	}
    }

    public Sprite(int x, int y, SpriteSheet sheet, int width, int height, int size) {
	this.SIZE = size;
	this.x = x * SIZE;
	this.y = y * SIZE;
	this.sheet = sheet;
	pixels = new int[(width * SIZE) * (height * SIZE)];
	loadSprite(width, height);
    }

    private void loadSprite() {
	for (int y = 0; y < SIZE; y++) {
	    for (int x = 0; x < SIZE; x++) {
		pixels[x + y * SIZE] = sheet.pixels[(this.x + x) + (this.y + y) * sheet.width];
	    }
	}
    }

    private void loadSprite(int width, int height) {
	for (int h = 0; h < height; h++) {
	    for (int w = 0; w < width; w++) {
		for (int y = 0; y < SIZE; y++) {
		    
		    int yStart = this.y + h * SIZE;
		    int xStart = this.x + w * SIZE;
		    
		    int y0 = h * SIZE;
		    int x0 = w * SIZE;
		    
		    for (int x = 0; x < SIZE; x++) {
			pixels[(x0 + x) + (y0 + y) * (width * SIZE)] = sheet.pixels[(xStart + x) + (yStart + y) * sheet.width];
		    }
		}
	    }
	}

    }
}
