package com.mar21.au.graphics;

public class Sprite {

	public final int SIZE;
	private int x, y;
	private int h, w;
	public int[] pixels;
	protected SpriteSheet sheet;

	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite grass1 = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite grass2 = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite brick = new Sprite(16, 0, 1, SpriteSheet.tiles);
	public static Sprite brick1 = new Sprite(16, 1, 1, SpriteSheet.tiles);
	public static Sprite brick2 = new Sprite(16, 2, 1, SpriteSheet.tiles);
	public static Sprite mosaic = new Sprite(16, 0, 2, SpriteSheet.tiles);
	public static Sprite mosaic1 = new Sprite(16, 1, 2, SpriteSheet.tiles);

	public static Sprite voidSprite = new Sprite(16, 0x1b87e0);

	public static Sprite player0 = new Sprite(32, 0, 0, SpriteSheet.player);
	public static Sprite player1 = new Sprite(32, 1, 0, SpriteSheet.player);
	public static Sprite player2 = new Sprite(32, 2, 0, SpriteSheet.player);
	public static Sprite player0a = new Sprite(32, 0, 1, SpriteSheet.player);
	public static Sprite player0b = new Sprite(32, 0, 1, SpriteSheet.player);
	public static Sprite player1a = new Sprite(32, 1, 1, SpriteSheet.player);
	public static Sprite player1b = new Sprite(32, 1, 2, SpriteSheet.player);
	public static Sprite player2a = new Sprite(32, 2, 2, SpriteSheet.player);
	public static Sprite player2b = new Sprite(32, 2, 2, SpriteSheet.player);

	public static Sprite pFire = new Sprite(16, 2, 1, SpriteSheet.attack);
	public static Sprite p7 = new Sprite(16, 0, 2, SpriteSheet.attack);

	public static Sprite particle = new Sprite(2, 0x100000);

	protected Sprite(SpriteSheet sheet) {
		SIZE = -1;
		this.w = sheet.SPRITE_WIDTH;
		this.h = sheet.SPRITE_HEIGHT;
		this.sheet = sheet;
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		w = SIZE;
		h = SIZE;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}

	public Sprite(int size, int colour) {
		SIZE = size;
		w = SIZE;
		h = SIZE;
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}

	public Sprite(int w, int h, int c) {
		SIZE = -1;
		this.w = w;
		this.h = h;
		pixels = new int[w * h];
		setColour(c);
	}

	public Sprite(int[] pix, int w, int h) {
		SIZE = (w == h) ? w : -1;
		this.w = w;
		this.h = h;
		this.pixels = new int[pix.length];
		for (int i = 0; i < pix.length; i++) {
			this.pixels[i] = pix[i];
		}
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amount];
		int current = 0;
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];

		for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
			for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {

				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					}
				}

				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}

		return sprites;
	}

	private void setColour(int colour) {
		for (int i = 0; i < w * h; i++) {
			pixels[i] = colour;
		}
	}

	private void load() {
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				pixels[x + y * w] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
			}
		}
	}
}
