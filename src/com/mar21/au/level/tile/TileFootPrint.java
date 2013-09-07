package com.mar21.au.level.tile;

import com.mar21.au.graphics.Screen;
import com.mar21.au.graphics.Sprite;

public class TileFootPrint extends Tile {

	public TileFootPrint(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}

}
