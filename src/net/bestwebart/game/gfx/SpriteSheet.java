package net.bestwebart.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

    private String path;
    public int width, height;
    public int pixels[];

    public static final SpriteSheet TILES = new SpriteSheet("/sprites/tiles.png");

    public static final SpriteSheet WALL_RUIN = new SpriteSheet("/sprites/wall_ruines.png");

    public static final SpriteSheet PLAYER = new SpriteSheet("/sprites/player.png");
    public static final SpriteSheet TONNY = new SpriteSheet("/sprites/tonny.png");
    public static final SpriteSheet BAD_TONNY = new SpriteSheet("/sprites/bad_tonny.png");

    public static final SpriteSheet PROJECTILES = new SpriteSheet("/sprites/projectiles.png");

    public SpriteSheet(String path) {
	this.path = path;
	load();
    }

    private void load() {
	try {
	    BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
	    width = image.getWidth();
	    height = image.getHeight();
	    pixels = new int[width * height];
	    image.getRGB(0, 0, width, height, pixels, 0, width);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
