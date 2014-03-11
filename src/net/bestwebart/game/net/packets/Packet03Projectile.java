package net.bestwebart.game.net.packets;

import net.bestwebart.game.net.Client;
import net.bestwebart.game.net.Server;

public class Packet03Projectile extends Packet {

    private int x, y;
    private double angle;
    private int mobHash;
    private int projectileId;

    public Packet03Projectile(byte[] data) {
	super(03);
	String dataString = readData(data);
	String[] dataArray = dataString.split(",");
	try {
	    
	    x = Integer.parseInt(dataArray[0]);
	    y = Integer.parseInt(dataArray[1]);
	    angle = Double.parseDouble(dataArray[2]);
	    mobHash = Integer.parseInt(dataArray[3]);
	    projectileId = Integer.parseInt(dataArray[4]);
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	}
    }

    public Packet03Projectile(int x, int y, double angle, int mobHash, int projectileId) {
	super(03);
	this.x = x;
	this.y = y;
	this.angle = angle;
	this.mobHash = mobHash;
	this.projectileId = projectileId;
    }

    public void writeData(Client client) {
	client.sendData(getData());
    }

    public void writeData(Server server) {
	server.sendToAll(getData());
    }

    public byte[] getData() {
	return ("03" + x + "," + y + "," + angle + "," + mobHash + "," + projectileId).getBytes();
    }
    
    public int getX() {
	return x;
    }
    
    public int getY() {
	return y;
    }
    
    public double getAngle() {
	return angle;
    }
    
    public int getMobHash() {
	return mobHash;
    }
    
    public int getProjectileId() {
	return projectileId;
    }
}
