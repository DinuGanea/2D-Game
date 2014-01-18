package net.bestwebart.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import net.bestwebart.game.entity.mob.BadTonny;
import net.bestwebart.game.entity.mob.Player;
import net.bestwebart.game.entity.mob.Tonny;
import net.bestwebart.game.gfx.Screen;
import net.bestwebart.game.input.KeyboardHandler;
import net.bestwebart.game.input.MouseHandler;
import net.bestwebart.game.level.Level;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final int SCALE = 2;

	private static final Dimension SIZE = new Dimension(WIDTH * SCALE, HEIGHT
			* SCALE);

	private static final String NAME = "Java 2D Game";

	private boolean shouldRender = false;
	private boolean running = false;

	private static int xScroll, yScroll;

	public static Level level;

	private JFrame frame;
	private Thread thread;
	private Screen screen;
	private KeyboardHandler key;
	private MouseHandler mouse;
	private Player player;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private int pixels[] = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData();

	public Game() {
		setPreferredSize(SIZE);

		frame = new JFrame(NAME);
		screen = new Screen(WIDTH, HEIGHT);
		level = new Level("/levels/test.png");
		key = new KeyboardHandler();
		mouse = new MouseHandler();
		player = new Player(key, mouse);

		level.addEntity(player);

		// level.addEntity(new Tonny(10, 10));
		// level.addEntity(new BadTonny(240, 230));

		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
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
				frame.setTitle(NAME + " | " + updatesCount + " ups  "
						+ framesCount + " fps");
				updatesCount = framesCount = 0;
				timer = System.currentTimeMillis();
			}

		}

		stop();
	}

	private void update() {
		key.update();
		level.update();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();

		xScroll = (int) player.x - WIDTH / 2 + 16;
		yScroll = (int) player.y - HEIGHT / 2 + 16;

		if (xScroll < 0)
			xScroll = 0;
		if (yScroll < 0)
			yScroll = 0;
		if (xScroll > level.getLevelWidth() - WIDTH)
			xScroll = level.getLevelWidth() - WIDTH;
		if (yScroll > level.getLevelHeight() - HEIGHT)
			yScroll = level.getLevelHeight() - HEIGHT;

		level.renderMap(xScroll, yScroll, screen);
		level.renderEntities(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();

		bs.show();

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

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setVisible(true);
		// game.frame.setUndecorated(true);
		// game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		game.frame.setLocationRelativeTo(null);
		game.start();
	}

}
