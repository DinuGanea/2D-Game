package net.bestwebart.game.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

import net.bestwebart.game.Game;
import net.bestwebart.game.entity.mob.Player;
import net.bestwebart.game.entity.projectile.Projectile.ProjectileType;
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

    public void renderAnimatedTiles(int xPos, int yPos, Tile tile, boolean flip, int tileNr) {
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
		int col = tile.sprite.pixels[flipX + (y + tileNr * tile.sprite.SIZE) * tile.sprite.SIZE];
		if (col != 0xffff00ff) {

		    pixels[xp + yp * width] = col;
		}
	    }
	}
    }
    
    public void renderMobInWater(int xPos, int yPos, Tile tile, boolean flip, int tileNr) {
	xPos -= xOffset;
	yPos -= yOffset;
	for (int y = 0; y < tile.sprite.SIZE / 2 + 2; y++) {
	    int yp = yPos + y;
	    for (int x = 0; x < tile.sprite.SIZE; x++) {
		int flipX = (flip) ? tile.sprite.SIZE - x - 1 : x;
		int xp = xPos + x;
		if (xp < 0 || xp >= width || yp < 0 || yp >= height) {
		    continue;
		}
		int col = tile.sprite.pixels[flipX + (y + tileNr * tile.sprite.SIZE) * tile.sprite.SIZE];
		if (col != 0xffff00ff) {

		    pixels[xp + yp * width] = col;
		}
	    }
	}
    }    

    // Only for test
    public void renderSprite(int xPos, int yPos, Sprite sprite) {
	for (int y = 0; y < 16; y++) {
	    int yp = yPos + y;
	    for (int x = 0; x < 16; x++) {
		int xp = xPos + x;
		int col = sprite.pixels[x + y * 16];
		if (col != 0xffff00ff) {
		    pixels[xp + yp * width] = col;
		}
	    }
	}
    }

    public void renderCracks(int xPos, int yPos, int damage) {
	xPos -= xOffset;
	yPos -= yOffset;
	int damageSprite = damage / 10;
	if (damageSprite > 7) {
	    damageSprite = 7;
	}

	for (int y = 0; y < Sprite.CRACKS.SIZE; y++) {
	    int yp = yPos + y;
	    for (int x = 0; x < Sprite.CRACKS.SIZE; x++) {
		int xp = xPos + x;
		if (xp < 0 || xp >= width || yp < 0 || yp >= height) {
		    continue;
		}
		int col = Sprite.CRACKS.pixels[x + (y + damageSprite * Sprite.CRACKS.SIZE) * Sprite.CRACKS.SIZE];
		if (col == 0xff000000) {
		    pixels[xp + yp * width] = col;
		}
	    }
	}
    }

    public void renderBar(int xPos, int yPos, Graphics g) {
	Player p = (Player) Game.level.getPlayer();
	if (p != null && !p.isRemoved()) {

	    if (p.getHP() > 0) {
		int pHP = Game.level.getPlayer().getHP();
		int points = ((Player)Game.level.getPlayer()).getPoints();

		Color col = new Color(0x99C9C9C9, true);
		g.drawRect(xPos, yPos, 100, 10);
		col = new Color(0xAAFF0000, true);
		g.setColor(col);
		g.fillRect(xPos, yPos, pHP, 11);
		
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.setColor(Color.yellow);
		g.drawString(points + "", xPos, yPos + 30);
		
		g.setColor(Color.green);
		String projectile = "Canon";
		if (((Player)Game.level.getPlayer()).getProjectileType() == ProjectileType.LASER) {
		    projectile = "Laser";
		}
		g.drawString(projectile, xPos, yPos + 60);
	
	    }

	} else {
	    Color mask = new Color(0x99000000, true);
	    g.setColor(mask);
	    g.fillRect(0, 0, Game.getWindowWidth(), Game.getWindowHeight());

	    g.setColor(Color.red);
	    g.setFont(new Font("Arial", Font.PLAIN, 20));

	    g.drawString("GAME OVER!", Game.getWindowWidth() / 2 - 70, Game.getWindowHeight() / 2 - 40);
	    g.drawString("Respawn in " + (Game.game.respawn_in / 60) + " seconds" , Game.getWindowWidth() / 2 - 100, Game.getWindowHeight() / 2);
	}

    }

    public void renderText(String txt, int x, int y, Font font, Color color) {
	Game.g.setColor(color);
	Game.g.setFont(font);
	Game.g.drawString(txt, x, y);
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
