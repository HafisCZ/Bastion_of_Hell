package com.mar21.au.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int SIZE;
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int width, height;
	public int[] pixels;

	public static SpriteSheet tiles = new SpriteSheet("/textures/sheets/spritesheet.png", 256);

	public static SpriteSheet attack = new SpriteSheet("/textures/sheets/projectiles.png", 48);

	public static SpriteSheet player = new SpriteSheet("/textures/sheets/player.png", 128, 96);

	public static SpriteSheet wanderer = new SpriteSheet("/textures/sheets/skeleton.png", 128, 96);

	public static SpriteSheet player_down = new SpriteSheet(player, 0, 0, 1, 3, 32);
	public static SpriteSheet player_up = new SpriteSheet(player, 1, 0, 1, 3, 32);
	public static SpriteSheet player_left = new SpriteSheet(player, 2, 0, 1, 3, 32);
	public static SpriteSheet player_right = new SpriteSheet(player, 3, 0, 1, 3, 32);

	public static SpriteSheet wanderer_down = new SpriteSheet(wanderer, 0, 0, 1, 3, 32);
	public static SpriteSheet wanderer_up = new SpriteSheet(wanderer, 1, 0, 1, 3, 32);
	public static SpriteSheet wanderer_left = new SpriteSheet(wanderer, 2, 0, 1, 3, 32);
	public static SpriteSheet wanderer_right = new SpriteSheet(wanderer, 3, 0, 1, 3, 32);

	private Sprite[] sprites;

	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		if (width == height) SIZE = width;
		else SIZE = -1;
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		pixels = new int[w * h];
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
			}
		}
		sprites = new Sprite[width * height];
		int frame = 0;
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritepixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						spritepixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritepixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}

	public Sprite[] getSprites() {
		return sprites;
	}

	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		SPRITE_WIDTH = size;
		SPRITE_HEIGHT = size;
		load();
	}

	public SpriteSheet(String path, int w, int h) {
		this.path = path;
		SIZE = -1;
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
		load();
	}

	public int[] getPixels() {
		return pixels;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}