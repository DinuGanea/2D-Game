package net.bestwebart.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import net.bestwebart.game.entity.Entity;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.level.tiles.Tile;
import net.bestwebart.game.util.Vector2i;

public class Level {

    private int width, height;
    private BufferedImage image;

    private List<Entity> entities = new ArrayList<Entity>();

    private int tiles[];

    public Level(String path) {
	try {
	    image = ImageIO.read(Level.class.getResource(path));
	    width = image.getWidth();
	    height = image.getHeight();
	    tiles = new int[width * height];
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
		tiles[x + y * width] = getTileByColor(tilesPixels[x + y * width]);
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
	if (x < 0 || x >= width || y < 0 || y >= height) return Tile.VOID;
	return getTile(tiles[x + y * width]);
    }

    public Tile getTile(int i) {
	if (i < 0 || i >= Tile.tiles.length) return Tile.VOID;
	return Tile.tiles[i];
    }

    public int getTileByColor(int col) {
	switch (col) {
	case Tile.GRASS_COL:
	    return 0;
	case Tile.WALL_COL:
	    return 1;
	default:
	    return 2;
	}
    }

    public void update() {
	for (int i = 0; i < entities.size(); i++) {
	    if (!entities.get(i).isRemoved()) {
		entities.get(i).update();
	    } else {
		entities.remove(i);
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
		if (x < 0 || x >= width || y < 0 || y >= height) continue;
		getTile(tiles[x + y * width]).render(x, y, screen);
	    }
	}
    }

    public void renderEntities(Screen screen) {
	for (int i = 0; i < entities.size(); i++) {
	    entities.get(i).render(screen);
	}
    }

    public void addEntity(Entity e) {
	entities.add(e);
    }

    public Entity getPlayer() {
	return entities.get(0);
    }

    // UNFINISHED
    public boolean isTileCollision(int x, int y, int size, int xOffset, int yOffset) {
	for (int corner = 0; corner < 4; corner++) {
	    int xc = (x + corner % 2 * size + xOffset) >> 4;
	    int yc = (y + corner / 2 * size + yOffset) >> 4;
	    if (getTile(xc, yc).isSolid()) return true;
	}

	return false;
    }

    private Comparator<Node> nodeSort = new Comparator<Node>() {
	public int compare(Node n0, Node n1) {
	    if (n0.fCost > n1.fCost) return 1;
	    else if (n0.fCost < n1.fCost) return -1;
	    else return 0;
	}
    };

    public List<Node> findPath(Vector2i start, Vector2i finish) {

	List<Node> openList = new ArrayList<Node>();
	List<Node> closedList = new ArrayList<Node>();

	Node current = new Node(start, null, 0, start.getDistance(finish), getSolidNeighbours(start));
	openList.add(current);
	while (openList.size() > 0) {
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

		    if (getTile(cX + nbX, cY + nbY) == null || getTile(cX + nbX, cY + nbY).isSolid()) continue;
		    Vector2i nbV = new Vector2i(cX + nbX, cY + nbY);

		    if (getNodeFromList(nbV, closedList) != null) continue;

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
			    nbN = new Node(nbV, current, gCost, hCost, getSolidNeighbours(nbV));
			    openList.add(nbN);
			}
		    }
		}
	    }

	}
	closedList.clear();
	return null;
    }

    private List<Byte> getSolidNeighbours(Vector2i v) {
	List<Byte> solidNb = new ArrayList<Byte>();
	if (getTile(v.getX(), v.getY() - 1).isSolid()) {
	    solidNb.add((byte) 0);
	}
	if (getTile(v.getX() + 1, v.getY()).isSolid()) {
	    solidNb.add((byte) 1);	   
	}
	if (getTile(v.getX(), v.getY() + 1).isSolid()) {
	    solidNb.add((byte) 2);	   
	}
	if (getTile(v.getX() - 1, v.getY()).isSolid()) {
	    solidNb.add((byte) 3);	   
	}
	return solidNb;
    }

    private Node getNodeFromList(Vector2i v, List<Node> list) {
	for (Node node : list) {
	    if (node.tile.equals(v)) return node;
	}
	return null;
    }

}
