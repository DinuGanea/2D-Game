package net.bestwebart.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.bestwebart.game.entity.mob.BadTonny;
import net.bestwebart.game.entity.mob.Mob;
import net.bestwebart.game.entity.mob.Mob.MobType;
import net.bestwebart.game.entity.mob.Player;
import net.bestwebart.game.entity.mob.PlayerMP;
import net.bestwebart.game.entity.mob.Tonny;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.input.KeyboardHandler;
import net.bestwebart.game.input.MouseHandler;
import net.bestwebart.game.input.WindowHandler;
import net.bestwebart.game.level.Level;
import net.bestwebart.game.net.Client;
import net.bestwebart.game.net.Server;
import net.bestwebart.game.net.packets.Packet00Login;
import net.bestwebart.game.net.packets.Packet05AddNPC;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 500;
    public static final int HEIGHT = WIDTH / 16 * 9;
    public static final int SCALE = 2;

    private static final Dimension SIZE = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);

    private static final String NAME = "Java 2D Game";

    private boolean shouldRender = false;
    private boolean running = false;

    private static int xScroll, yScroll;

    public static Graphics g;
    public static Level level;
    public static Game game;

    private final JFrame frame;
    private Thread thread;
    private final Screen screen;

    public Player player;
    public KeyboardHandler key;
    public MouseHandler mouse;
    public WindowHandler window;

    public Client client;
    public Server server;

    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private final int pixels[] = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public Game() {
	setPreferredSize(SIZE);

	game = this;
	frame = new JFrame(NAME);
	screen = new Screen(WIDTH, HEIGHT);
	level = new Level("/levels/test.png");
	key = new KeyboardHandler();
	mouse = new MouseHandler();
	window = new WindowHandler(this);

	int run_server = JOptionPane.showConfirmDialog(null, "Run the server ? ");
	if (run_server == 0) {
	    server = new Server(this);
	    client = new Client(this, "localhost");
	    client.start();
	} else if (run_server == 1) {
	    try {
		client = new Client(this, JOptionPane.showInputDialog(null, "Enter IP: "));
		client.start();
	    } catch (HeadlessException e) {
		e.printStackTrace();
	    } catch (NumberFormatException e) {
		e.printStackTrace();
	    }

	} else {
	    level.addEntity(new Tonny(200, 100, 100));
	    level.addEntity(new BadTonny(240, 200, 100));
	}

	String username = JOptionPane.showInputDialog(null, "Enter Username:");
	if (username.trim() == "") {
	    username = "Player";
	}

	player = new PlayerMP(10, 10, username, key, mouse, null, -1);
	level.addEntity(player);
	Packet00Login packet = new Packet00Login(player.getUsername(), player.getX(), player.getY(), player.getHP(), player.getUniqueID());
	packet.writeData(client);
	
	
	if (server != null) {
	    server.addConnections((PlayerMP) player, packet);
	    server.start();
	    Tonny tonny = new Tonny(100, 100, 100);
	    Packet05AddNPC packetNPC = new Packet05AddNPC((int)tonny.getX(), (int)tonny.getY(), tonny.getHP(), tonny.getUniqueID(), MobType.TONNY.getID());
	    packetNPC.writeData(client);
	    
	    BadTonny bad_tonny = new BadTonny(170, 200, 100);
	    packetNPC = new Packet05AddNPC((int)bad_tonny.getX(), (int)bad_tonny.getY(), bad_tonny.getHP(), bad_tonny.getUniqueID(), MobType.BAD_TONNY.getID());
	    packetNPC.writeData(client);
	}


	addKeyListener(key);
	addMouseListener(mouse);
	addMouseMotionListener(mouse);
	frame.addWindowListener(window);
    }

    public synchronized void start() {

	running = true;
	thread = new Thread(this, "main");
	thread.start();
    }

    public synchronized void stop() {
	running = false;
	try {
	    thread.join();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    public void run() {
	long lastTime = System.nanoTime();
	long now = 0l;
	double nsPerUpdate = 1_000_000_000 / 60;
	double delta = 0d;

	long timer = System.currentTimeMillis();

	int updatesCount = 0, framesCount = 0;

	requestFocus();

	while (running) {

	    now = System.nanoTime();
	    delta += (now - lastTime) / nsPerUpdate;
	    lastTime = now;
	    while (delta >= 1) {
		updatesCount++;
		update();
		delta--;
		shouldRender = true;
	    }
	    if (shouldRender) {
		framesCount++;
		render();
		shouldRender = false;
	    }

	    if (System.currentTimeMillis() - timer > 1000) {
		frame.setTitle(NAME + " | " + updatesCount + " ups  " + framesCount + " fps");
		updatesCount = framesCount = 0;
		timer = System.currentTimeMillis();
	    }

	}

	stop();
    }

    private void update() {
	key.update();
	if (!key.pause && !key.menu) {
	    level.update();
	}
    }

    private void render() {
	BufferStrategy bs = getBufferStrategy();
	if (bs == null) {
	    createBufferStrategy(3);
	    return;
	}

	screen.clear();

	xScroll = (int) ((Mob) player).getX() - WIDTH / 2 + 16;
	yScroll = (int) ((Mob) player).getY() - HEIGHT / 2 + 16;

	if (xScroll < 0) {
	    xScroll = 0;
	}
	if (yScroll < 0) {
	    yScroll = 0;
	}
	if (xScroll > level.getLevelWidth() - WIDTH) {
	    xScroll = level.getLevelWidth() - WIDTH;
	}
	if (yScroll > level.getLevelHeight() - HEIGHT) {
	    yScroll = level.getLevelHeight() - HEIGHT;
	}

	g = bs.getDrawGraphics();
	g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

	level.renderMap(xScroll, yScroll, screen);
	level.renderEntities(screen);

	for (int i = 0; i < pixels.length; i++) {
	    pixels[i] = screen.pixels[i];
	}

	screen.renderBar(10, 10, g);

	if (key.pause) {
	    Color mask = new Color(0x99000000, true);
	    g.setColor(mask);
	    g.fillRect(0, 0, Game.getWindowWidth(), Game.getWindowHeight());

	    g.setColor(Color.white);
	    g.setFont(new Font("Arial", Font.PLAIN, 20));

	    g.drawString("PAUSE", Game.getWindowWidth() / 2 - 40, Game.getWindowHeight() / 2 - 10);
	} else if (key.menu) {

	    // menu.setVisible(true);

	} else {

	}

	g.dispose();
	bs.show();

    }

    public Level getLevel() {
	return level;
    }

    public static int getWindowWidth() {
	return WIDTH * SCALE;
    }

    public static int getWindowHeight() {
	return HEIGHT * SCALE;
    }

    public static int getScale() {
	return SCALE;
    }

    public static int getXScroll() {
	return xScroll;
    }

    public static int getYScroll() {
	return yScroll;
    }

    public Player getPlayer() {
	return player;
    }

    // public static JPanel menu = new JPanel();

    public static void main(String[] args) {
	Game game = new Game();
	game.frame.setResizable(true);
	game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	game.frame.setLayout(new BorderLayout());
	game.frame.add(game, BorderLayout.CENTER);

	// Menu TEST
	/*
	 * menu.setLayout(new GridLayout(1, 4)); menu.add(new
	 * JButton("Button 1")); menu.add(new JButton("Button 2")); menu.add(new
	 * JButton("Button 3")); menu.add(new JButton("Button 4"));
	 * game.frame.add(menu, BorderLayout.SOUTH); menu.setVisible(false);
	 */

	game.frame.pack();
	game.frame.setVisible(true);
	// game.frame.setUndecorated(true);
	// game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	game.frame.setLocationRelativeTo(null);
	game.start();
    }

}
