package com.mar21.au;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.mar21.au.entity.mob.Player;
import com.mar21.au.graphics.Screen;
import com.mar21.au.input.Keyboard;
import com.mar21.au.input.Mouse;
import com.mar21.au.level.Level;
import com.mar21.au.level.SpawnLevel;
import com.mar21.au.level.TileCoord;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static int width = 300;
	private static int height = width / 16 * 9;
	public static int scale = 3;

	public static final String NAME = "Bastion of Hell";
	public static final String VERSION = "[CC-BY-NC-ND] HafisCZ 2013/2014";

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = true;

	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public static int getWWidth() {
		return width * scale;
	}

	public static int getWHeight() {
		return height * scale;
	}

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = new SpawnLevel("/textures/levels/level.png");
		TileCoord pS = new TileCoord(15, 15);
		player = new Player(pS.x(), pS.y(), key);
		level.add(player);

		addKeyListener(key);
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
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
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;

		requestFocus();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}

			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}

	public void update() {
		key.update();
		level.update();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		double xScroll = player.getX() - screen.width / 2;
		double yScroll = player.getY() - screen.height / 2;
		level.render((int) xScroll, (int) yScroll, screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		{
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Verdana", 0, 14));
			g.drawString("X: " + player.getX() + " Y: " + player.getY(), 10, 20);
			g.drawString("X: " + Mouse.getX() + " Y: " + Mouse.getY() + " B: " + Mouse.getButton(), 10, 40);
			g.drawString("C: " + level.getPCount() + " | " + level.getRCount() + " | " + level.getECount() + " | " + level.getFCount(), 10, 60);
		}
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(NAME + " " + VERSION);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}

}