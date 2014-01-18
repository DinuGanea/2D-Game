package net.bestwebart.game.gfx;

import java.util.Arrays;

import net.bestwebart.game.level.tiles.Tile;

public class Screen {

	public int width, height;
	public int xOffset, yOffset;

	public int pixels[];

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void render(int xPos, int yPos, Tile tile, boolean flip) {
		xPos -= xOffset;
		yPos -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int yp = yPos + y;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xp = xPos + x;
				if (xp < 0 || xp >= width || yp < 0 || yp >= height) {
					continue;
				}
				int col = tile.sprite.pixels[x + y * tile.sprite.SIZE];
				if (col != 0xffff00ff) {
					pixels[xp + yp * width] = col;
				}
			}
		}
	}

	public void renderAnimatedTiles(int xPos, int yPos, Tile tile,
			boolean flip, int tileNr) {
		xPos -= xOffset;
		yPos -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int yp = yPos + y;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int flipX = (flip) ? tile.sprite.SIZE - x - 1 : x;
				int xp = xPos + x;
				if (xp < 0 || xp >= width || yp < 0 || yp >= height) {
					continue;
				}
				int col = tile.sprite.pixels[flipX
						+ (y + tileNr * tile.sprite.SIZE) * tile.sprite.SIZE];
				if (col != 0xffff00ff) {
					pixels[xp + yp * width] = col;
				}
			}
		}
	}

	// Only for test
	public void renderSprite(int xPos, int yPos, Sprite sprite) {
		for (int y = 0; y < 96; y++) {
			int yp = yPos + y;
			for (int x = 0; x < 32; x++) {
				int xp = xPos + x;
				// if (xp < 0 || xp >= width || yp < 0 || yp >= height)
				// continue;
				pixels[xp + yp * width] = sprite.pixels[x + y * 32];
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void clear() {
		Arrays.fill(pixels, 0);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
