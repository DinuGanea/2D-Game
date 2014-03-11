package net.bestwebart.game.net.packets;

import net.bestwebart.game.net.Client;
import net.bestwebart.game.net.Server;

public class Packet02Move extends Packet {

    private int x, y;
    private int dir;
    private int uniqueID;
    private boolean moving;

    public Packet02Move(byte[] data) {
	super(02);
	String dataString = readData(data);
	String[] dataArray = dataString.split(",");
	try {
	    x = Integer.parseInt(dataArray[0]);
	    y = Integer.parseInt(dataArray[1]);
	    dir = Integer.parseInt(dataArray[2]);
	    moving = (Integer.parseInt(dataArray[3]) == 1);
	    uniqueID = Integer.parseInt(dataArray[4]);
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	}
    }

    public Packet02Move(int x, int y, int dir, boolean moving, int uniqueID) {
	super(02);
	this.x = x;
	this.y = y;
	this.dir = dir;
	this.moving = moving;
	this.uniqueID = uniqueID;
    }

    public void writeData(Client client) {
	client.sendData(getData());
    }

    public void writeData(Server server) {
	server.sendToAll(getData());
    }

    public byte[] getData() {
	return ("02" + x + "," + y + "," + dir + "," + (moving ? 1 : 0) + "," + uniqueID).getBytes();
    }

    public int getX() {
	return x;
    }
    
    public int getY() {
	return y;
    }
    
    public int getDir() {
	return dir;
    }
    
    public boolean isMoving() {
	return moving;
    }
    
    public int getUniqueID() {
	return uniqueID;
    }
}
