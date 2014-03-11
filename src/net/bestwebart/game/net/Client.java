package net.bestwebart.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import net.bestwebart.game.Game;
import net.bestwebart.game.entity.mob.BadTonny;
import net.bestwebart.game.entity.mob.Mob;
import net.bestwebart.game.entity.mob.Mob.MobType;
import net.bestwebart.game.entity.mob.PlayerMP;
import net.bestwebart.game.entity.mob.Tonny;
import net.bestwebart.game.entity.projectile.Laser;
import net.bestwebart.game.entity.projectile.Projectile;
import net.bestwebart.game.entity.projectile.Projectile.ProjectileType;
import net.bestwebart.game.entity.projectile.SimpleProjectile;
import net.bestwebart.game.net.packets.Packet;
import net.bestwebart.game.net.packets.Packet.PacketType;
import net.bestwebart.game.net.packets.Packet00Login;
import net.bestwebart.game.net.packets.Packet01Disconnect;
import net.bestwebart.game.net.packets.Packet02Move;
import net.bestwebart.game.net.packets.Packet03Projectile;
import net.bestwebart.game.net.packets.Packet04Tiles;
import net.bestwebart.game.net.packets.Packet05AddNPC;

public class Client extends Thread {

    private Game game;
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private int port;

    public Client(Game game, String ipAddress) {
	this.game = game;
	port = 1302;
	try {
	    socket = new DatagramSocket();
	    this.ipAddress = InetAddress.getByName(ipAddress);
	    System.out.println(this.ipAddress);
	} catch (SocketException e) {
	    e.printStackTrace();
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}
    }

    public void run() {
	while (true) {
	    byte data[] = new byte[4000];
	    DatagramPacket packet = new DatagramPacket(data, data.length);
	    try {
		socket.receive(packet);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    parseData(packet.getData(), packet.getAddress(), packet.getPort());
	}
    }

    private void parseData(byte[] data, InetAddress address, int port) {
	String message = new String(data).trim();
	PacketType type = Packet.getPacketType(message.substring(0, 2));
	Packet packet = null;
	switch (type) {
	    case INVALID:
		break;
	    case LOGIN:
		packet = new Packet00Login(data);
		handleLogin((Packet00Login) packet, address, port);
		break;
	    case DISCONNECT:
		packet = new Packet01Disconnect(data);
		System.out.println(((Packet01Disconnect) packet).getUsername() + " [" + address.getHostAddress() + ":" + port + "] disconnected!");
		game.getLevel().removePlayerMP(((Packet01Disconnect) packet).getUsername());
		break;
	    case MOVE:
		packet = new Packet02Move(data);
		handleMove((Packet02Move) packet);
		break;
	    case PROJECTILE:
		packet = new Packet03Projectile(data);
		addProjectile((Packet03Projectile) packet);
		break;
	    case TILES:
		packet = new Packet04Tiles(data);
		game.getLevel().setTiles(((Packet04Tiles) packet).getTiles());
		break;
	    case ADD_NPC:
		packet = new Packet05AddNPC(data);
		addNPC((Packet05AddNPC) packet);
		break;
	    default:
		break;

	}
    }

    private void addNPC(Packet05AddNPC packet) {
	int x = packet.getX();
	int y = packet.getY();
	int hp = packet.getHP();
	int uniqueID = packet.getUniqueID();
	int type = packet.getType();

	Mob mob = null;

	if (type == MobType.TONNY.getID()) {
	    mob = new Tonny(x, y, hp);
	} else if (type == MobType.BAD_TONNY.getID()) {
	    mob = new BadTonny(x, y, hp);
	}
	mob.setUniqueID(uniqueID);

	game.getLevel().addEntity(mob);

    }

    private void addProjectile(Packet03Projectile packet) {
	int x = packet.getX();
	int y = packet.getY();
	double angle = packet.getAngle();
	Mob mob = game.getLevel().getMobByHashCode(packet.getMobHash());
	int pType = packet.getProjectileId();
	Projectile projectile = null;
	if (pType == ProjectileType.SIMPLE.getID()) {
	    projectile = new SimpleProjectile(x, y, angle, mob);
	} else {
	    projectile = new Laser(x, y, angle, mob);
	}

	game.getLevel().addEntity(projectile);
    }

    private void handleMove(Packet02Move packet) {
	int x = packet.getX();
	int y = packet.getY();
	int dir = packet.getDir();
	boolean moving = packet.isMoving();
	int uniqueID = packet.getUniqueID();
	game.getLevel().moveMob(uniqueID, x, y, dir, moving);
    }

    private void handleLogin(Packet00Login packet, InetAddress address, int port) {
	System.out.println(packet.getUsername() + " [" + address.getHostAddress() + ":" + port + "] joined!");
	PlayerMP player = new PlayerMP((int) packet.getX(), (int) packet.getY(), packet.getUsername(), address, port);
	player.setUniqueID(packet.getUniqueID());
	player.setHP(packet.getHP());
	game.getLevel().addEntity(player);
    }

    public void sendData(byte[] data) {
	DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
	try {
	    socket.send(packet);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
