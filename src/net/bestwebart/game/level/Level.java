package net.bestwebart.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import net.bestwebart.game.entity.Entity;
import net.bestwebart.game.entity.mob.Mob;
import net.bestwebart.game.entity.particle.Particle;
import net.bestwebart.game.entity.projectile.Projectile;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.Tile;
import net.bestwebart.game.util.Vector2i;

public class Level {

    private int width, height;
    private BufferedImage image;
    

    private final List<Mob> mobs = new ArrayList<Mob>();
    private final List<Particle> particles = new ArrayList<Particle>();
    private final List<Projectile> projectiles = new ArrayList<Projectile>();

    private int tiles[][];

    public Level(String path) {
	try {
	    image = ImageIO.read(Level.class.getResource(path));
	    width = image.getWidth();
	    height = image.getHeight();
	    tiles = new int[width * height][2];
	    loadLevelFromFile(path);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void loadLevelFromFile(String path) {
	int tilesPixels[] = new int[width * height];
	image.getRGB(0, 0, width, height, tilesPixels, 0, width);

	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		tiles[x + y * width][0] = getTileByColor(tilesPixels[x + y * width]);
	    }
	}

    }

    public int getLevelWidth() {
	return width * 16;
    }

    public int getLevelHeight() {
	return height * 16;
    }

    public Tile getTile(int x, int y) {
	if (x < 0 || x >= width || y < 0 || y >= height) {
	    return Tile.VOID;
	}
	return getTile(tiles[x + y * width][0]);
    }

    public Tile getTile(int i) {
	if (i < 0 || i >= Tile.tiles.length) {
	    return Tile.VOID;
	}
	return Tile.tiles[i];
    }

    public int getTileByColor(int col) {
	switch (col) {
	    case Tile.GRASS_COL:
		return 1;
	    case Tile.WALL_COL:
		return 2;
	    default:
		return 0;
	}
    }

    public void damageTile(int x, int y, int damage) {
	if (x < 0 || x >= width || y < 0 || y >= height) {
	    return;
	}
	if (getTile(tiles[x + y * width][0]).isSolid() && getTile(tiles[x + y * width][0]).canBeDamaged()) {
	    tiles[x + y * width][1] += damage;
	    if (getTile(tiles[x + y * width][0]).getMaxDamage() <= tiles[x + y * width][1]) {
		changeTileAt(x, y, Tile.GRASS);
	    }
	}
    }
    
    public void changeTileAt(int x, int y, Tile newTile) {
	tiles[x + y * width][0] = newTile.getID();
	tiles[x + y * width][1] = 0;
    }

    public void update() {
	for (int i = 0; i < mobs.size(); i++) {
	    if (!mobs.get(i).isRemoved()) {
		mobs.get(i).update();
	    } else {
		mobs.remove(i);
	    }
	}
	
	for (int i = 0; i < particles.size(); i++) {
	    if (!particles.get(i).isRemoved()) {
		particles.get(i).update();
	    } else {
		particles.remove(i);
	    }
	}	
	
	for (int i = 0; i < projectiles.size(); i++) {
	    if (!projectiles.get(i).isRemoved()) {
		projectiles.get(i).update();
	    } else {
		projectiles.remove(i);
	    }
	}
    }

    public void renderMap(int xScroll, int yScroll, Screen screen) {
	screen.setOffset(xScroll, yScroll);
	int x0 = xScroll >> 4;
	int x1 = (xScroll + screen.width + 16) >> 4;
	int y0 = yScroll >> 4;
	int y1 = (yScroll + screen.height + 16) >> 4;

	for (int y = y0; y < y1; y++) {
	    for (int x = x0; x < x1; x++) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
		    continue;
		}
		getTile(tiles[x + y * width][0]).render(x, y, screen);
		if (tiles[x + y * width][1] > 0) {
		    screen.renderCracks(x << 4, y << 4, tiles[x + y * width][1]);
		}
	    }
	}
	
    }

    public void renderEntities(Screen screen) {
	
	for (Particle particle : particles) {
	    particle.render(screen);
	}	
	
	for (Projectile projectile : projectiles) {
	    projectile.render(screen);
	}
	
	for (Mob mob : mobs) {
	    if (!mob.isInvisible()) {
		mob.render(screen);
	    }
	}
	
    }

    public void addEntity(Entity e) {
	if (e instanceof Mob) {
	    mobs.add((Mob) e);
	} else if (e instanceof Particle) {
	    particles.add((Particle) e);
	} else if (e instanceof Projectile) {
	    projectiles.add((Projectile) e);
	}
    }

    public Mob getPlayer() {
	return mobs.get(0);
    }
    
    
    public boolean isMobCollision(int x, int y) {
	for (int i = 1; i < mobs.size(); i++) {
	    int mx = (int) mobs.get(i).x;
	    int my = (int) mobs.get(i).y;
	    if (x > mx + 10 && x < mx + 20 && y > my - 5 && y < my + 30) {
		return true;
	    }
	}
	return false;
    }
    
    

    // UNFINISHED
    public int isShootCollision(int x, int y, int size, int xOffset, int yOffset) {
	if (isMobCollision(x, y)) {
		return 2;
	}
	
	for (int corner = 0; corner < 4; corner++) {
	    int xc = (x + corner % 2 * size + xOffset) >> 4;
	    int yc = (y + corner / 2 * size + yOffset) >> 4;
	    if (getTile(xc, yc).isSolid()) {
		damageTile(xc, yc, 30);
		return 1;
	    }
	}

	return 0;
    }

    private final Comparator<Node> nodeSort = new Comparator<Node>() {
	public int compare(Node n0, Node n1) {
	    if (n0.fCost > n1.fCost) {
		return 1;
	    } else if (n0.fCost < n1.fCost) {
		return -1;
	    } else {
		return 0;
	    }
	}
    };

    public List<Node> findPath(Vector2i start, Vector2i finish) {

	List<Node> openList = new ArrayList<Node>();
	List<Node> closedList = new ArrayList<Node>();

	Node current = new Node(start, null, 0, start.getDistance(finish));
	openList.add(current);
	
	int iterations = 0;
	
	while (openList.size() > 0) {
	    if (iterations > 100) {
		break;
	    } 
	    
	    iterations++;
	    Collections.sort(openList, nodeSort);
	    current = openList.get(0);
	    openList.remove(current);
	    if (current.tile.equals(finish)) {
		List<Node> path = new ArrayList<Node>();
		while (current.parent != null) {
		    path.add(current);
		    current = current.parent;
		}
		openList.clear();
		closedList.clear();
		return path;
	    } else {
		closedList.add(current);
		int cX = current.tile.getX();
		int cY = current.tile.getY();
		for (int i = 0; i < 9; i++) {
		    if (i == 4) {
			continue;
		    }
		    int nbX = (i % 3) - 1;
		    int nbY = (i / 3) - 1;

		    if (getTile(cX + nbX, cY + nbY) == null || getTile(cX + nbX, cY + nbY).isSolid()) {
			continue;
		    }
		    Vector2i nbV = new Vector2i(cX + nbX, cY + nbY);

		    if (getNodeFromList(nbV, closedList) != null) {
			continue;
		    }

		    double gCost = current.gCost + current.tile.getDistance(nbV);

		    Node nbN = getNodeFromList(nbV, openList);
		    if (nbN == null || nbN.gCost > gCost) {
			double hCost = nbV.getDistance(finish);
			if (nbN != null) {
			    nbN.parent = current;
			    nbN.gCost = gCost;
			    nbN.hCost = hCost;
			    nbN.fCost = gCost + hCost;
			} else {
			    nbN = new Node(nbV, current, gCost, hCost);
			    openList.add(nbN);
			}
		    }
		}
	    }

	}
	closedList.clear();
	return null;
    }

    private Node getNodeFromList(Vector2i v, List<Node> list) {
	for (Node node : list) {
	    if (node.tile.equals(v)) {
		return node;
	    }
	}
	return null;
    }

}
