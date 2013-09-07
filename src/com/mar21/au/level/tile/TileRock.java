package com.mar21.au.level.tile;

import com.mar21.au.graphics.Screen;
import com.mar21.au.graphics.Sprite;

public class TileRock extends Tile {

	public TileRock(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean solid() {
		return true;
	}
	
}
