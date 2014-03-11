package net.bestwebart.game.net.packets;

import net.bestwebart.game.net.Client;
import net.bestwebart.game.net.Server;

public class Packet04Tiles extends Packet {
    
    private int[][] tiles;
    
    public Packet04Tiles(byte[] data) {
	super(04);
	String[] dataArray = readData(data).split(",");
	tiles = new int[dataArray.length / 2 + 1][2];
	try {
	    for (int i = 0, j = 0; i < dataArray.length - 1; i += 2, j++) {
		tiles[j][0] = Integer.parseInt(dataArray[i]);
		tiles[j][1] = Integer.parseInt(dataArray[i + 1]);
	    }
	} catch(NumberFormatException e) {
	    e.printStackTrace();
	}
    }
    
    public Packet04Tiles(int[][] tiles) {
	super(04);
	this.tiles = tiles;
	
    }

    public void writeData(Client client) {
	client.sendData(getData());
    }

    public void writeData(Server server) {
	server.sendToAll(getData());
    }
    
    public byte[] getData() {
	String data = "04" + tiles[0][0] + "," + tiles[0][1];
	for (int i = 1; i < tiles.length; i++) {
	    data += "," + tiles[i][0];
	    data += "," + tiles[i][1];
	}
	return data.getBytes();
    }
    
    public int[][] getTiles() {
	return tiles;
    }
    

}
