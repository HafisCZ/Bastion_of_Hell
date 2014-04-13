package com.mar21.au.graphics;

import java.util.Random;

import com.mar21.au.entity.mob.Chaser;
import com.mar21.au.entity.mob.Mob;
import com.mar21.au.level.tile.Tile;

public class Screen {

	public int width, height;
	public int[] pixels;

	public final int MAP_SIZE = 8;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;

	public int xOffset, yOffset;

	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];

	private Random random = new Random();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];

		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xffffff);
			tiles[0] = 0;
		}

	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void fillScreen(int col) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = col;
			}
		}
	}

	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getH(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getW(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getW()];
			}
		}
	}

	public void renderSpriteSheet(int xp, int yp, SpriteSheet sheet,
			boolean fixed, boolean transparency) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sheet.HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < sheet.WIDTH; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				if (transparency
						&& sheet.pixels[x + y * sheet.WIDTH] == 0xffff00ff)
					continue;
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.WIDTH];
			}
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.getH(); y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.getW(); x++) {
				int xa = x + xp;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				if (tile.sprite.pixels[x + y * tile.sprite.getW()] != 0xffff00ff) {
					pixels[xa + ya * width] = tile.sprite.pixels[x + y
							* tile.sprite.getW()];
				}
			}
		}
	}

	public void renderTile(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.getH(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getW(); x++) {
				int xa = x + xp;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getW()];
			}
		}
	}

	public void renderTile(int xp, int yp, Sprite sprite, boolean transparent) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.getH(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getW(); x++) {
				int xa = x + xp;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				if (transparent
						&& sprite.pixels[x + y * sprite.getW()] != 0xffff00ff) {
					pixels[xa + ya * width] = sprite.pixels[x + y
							* sprite.getW()];
				}
			}
		}
	}

	public void renderMob(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.getH(); y++) {
			int ya = y + yp;
			int ys = y;
			for (int x = 0; x < sprite.getW(); x++) {
				int xa = x + xp;
				int xs = x;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[xs + ys * sprite.getW()];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderPlayer(int xp, int yp, Sprite sprite, int flip) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.getH(); y++) {
			int ya = y + yp;
			int ys = y;
			if (flip == 2 || flip == 3) {
				ys = sprite.getH() - (y + 1);
			}
			for (int x = 0; x < sprite.getW(); x++) {
				int xa = x + xp;
				int xs = x;
				if (flip == 1 || flip == 3) {
					xs = sprite.getW() - (x + 1);
				}
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[xs + ys * sprite.getW()];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderPlayer(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.getH(); y++) {
			int ya = y + yp;
			int ys = y;
			for (int x = 0; x < sprite.getW(); x++) {
				int xa = x + xp;
				int xs = x;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[xs + ys * sprite.getW()];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void renderMob(int xp, int yp, Mob mob) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < mob.getSprite().getH(); y++) {
			int ya = y + yp;
			int ys = y;
			for (int x = 0; x < mob.getSprite().getW(); x++) {
				int xa = x + xp;
				int xs = x;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = mob.getSprite().pixels[xs + ys
						* mob.getSprite().getW()];
				if (mob instanceof Chaser && col == 0xff303030) {
					col = 0xffBA0015;
				}
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderMob(int xp, int yp, Mob mob, int fromCol, int toCol) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < mob.getSprite().getH(); y++) {
			int ya = y + yp;
			int ys = y;
			for (int x = 0; x < mob.getSprite().getW(); x++) {
				int xa = x + xp;
				int xs = x;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = mob.getSprite().pixels[xs + ys
						* mob.getSprite().getW()];
				if (col == fromCol)
					col = toCol;
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderMob(int xp, int yp, Mob mob, int[] fromCol, int[] toCol) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < mob.getSprite().getH(); y++) {
			int ya = y + yp;
			int ys = y;
			for (int x = 0; x < mob.getSprite().getW(); x++) {
				int xa = x + xp;
				int xs = x;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = mob.getSprite().pixels[xs + ys
						* mob.getSprite().getW()];
				if (fromCol.length == toCol.length) {
					for (int i = 0; i < fromCol.length; i++) {
						if (col == fromCol[i])
							col = toCol[i];
					}
				}
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void drawRect(int xp, int yp, int width, int height, int color,
			boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int x = xp; x < xp + width; x++) {
			if (x < 0 || x >= this.width || yp >= this.height)
				continue;
			if (yp > 0)
				pixels[x + yp * this.width] = color;
			if (yp + height >= this.height)
				continue;
			if (yp + height > 0)
				pixels[x + (yp + height) * this.width] = color;
		}
		for (int y = yp; y <= yp + height; y++) {
			if (xp >= this.width || y < 0 || y >= this.height)
				continue;
			if (xp > 0)
				pixels[xp + y * this.width] = color;
			if (xp + width >= this.width)
				continue;
			if (xp + width > 0)
				pixels[(xp + width) + y * this.width] = color;
		}
	}
}
