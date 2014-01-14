package net.bestwebart.game.level;

import java.util.List;

import net.bestwebart.game.util.Vector2i;

public class Node {
    
    public Vector2i tile;
    public Node parent;
    
    public double gCost, hCost, fCost;
    public List<Byte> solidNb;
    
    public Node(Vector2i tile, Node parent, double gCost, double hCost, List<Byte> solidNb) {
	this.tile = tile;
	this.parent = parent;
	this.gCost = gCost;
	this.hCost = hCost;
	this.fCost = gCost + hCost;
	this.solidNb = solidNb;
    }
        
}
