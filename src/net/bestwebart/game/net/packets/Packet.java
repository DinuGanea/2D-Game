package net.bestwebart.game.net.packets;

import net.bestwebart.game.net.Client;
import net.bestwebart.game.net.Server;

public abstract class Packet {
    
    public static enum PacketType {
	INVALID(-1), LOGIN(00), DISCONNECT(01), MOVE(02), PROJECTILE(03), 
	TILES(04), ADD_NPC(05), INVISIBLE(06), RESPAWN(07);
	
	private int id;
	
	private PacketType(int id) {
	    this.id = id;
	}
	
	public int getPacketId() {
	    return id;
	}
    }
    
    private int packetId;
    
    public Packet(int packetId) {
	this.packetId = packetId;
    }
    
    public String readData(byte[] data) {
	String message = new String(data).trim().substring(2);
	return message;
    }
    
    public static PacketType getPacketType(String packetId) {
	int id = -1;
	try {
	    id = Integer.parseInt(packetId);
	} catch(NumberFormatException e) {
	    e.printStackTrace();
	}
	for (PacketType type : PacketType.values()) {
	    if (type.getPacketId() == id) {
		return type;
	    }
	}
	return PacketType.INVALID;
    }
    
    public abstract void writeData(Client client);
  
    public abstract void writeData(Server client);
}
