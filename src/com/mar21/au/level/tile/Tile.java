package com.mar21.au.level.tile;

import com.mar21.au.graphics.Screen;
import com.mar21.au.graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;

	// WALKABLE BLOCKS
	public static Tile grass = new TileGrass(Sprite.grass);
	public static Tile grass1 = new TileGrass1(Sprite.grass1);
	public static Tile grass2 = new TileGrass2(Sprite.grass2);
	// WALKABLE OVER-BLOCKS
	// UNWALKABLE BLOCKS
	public static Tile rock = new TileRock(Sprite.rock);
	public static Tile planks = new TilePlanks(Sprite.planks);
	public static Tile doors = new TileDoors(Sprite.doors);
	// NULL BLOCKS
	public static Tile voidTile = new TileVoid(Sprite.voidSprite);

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public void render(int x, int y, Screen screen) {
	}

	public boolean solid() {
		return false;
	}

	public boolean layer() {
		return false;
	}

}
