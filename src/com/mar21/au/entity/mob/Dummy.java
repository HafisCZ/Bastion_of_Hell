package com.mar21.au.entity.mob;

import com.mar21.au.graphics.Screen;
import com.mar21.au.graphics.Sprite;

public class Dummy extends Mob {
	
	public Dummy(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.player0;
	}

	public void update() {
	}

	public void render(Screen screen) {
		screen.renderMob(x, y, sprite);
	}

}
