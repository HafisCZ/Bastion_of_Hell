package com.mar21.au.entity.mob;

import com.mar21.au.graphics.Screen;
import com.mar21.au.graphics.Sprite;
import com.mar21.au.input.Keyboard;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;

	public Player(Keyboard input) {
		this.input = input;
		sprite = Sprite.player0;
	}

	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = Sprite.player0;
	}

	public void update() {
		int xa = 0, ya = 0;
		if (anim < 7500)
			anim++;
		else
			anim = 0;
		if (input.up)
			ya--;
		if (input.down)
			ya++;
		if (input.left)
			xa--;
		if (input.right)
			xa++;

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void render(Screen screen) {
		int flip = 0;
		if (dir == 0) {
			sprite = Sprite.player0;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player0a;
				} else {
					sprite = Sprite.player0b;
				}
			}
		}
		if (dir == 1 || dir == 3) {
			sprite = Sprite.player1;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player1a;
				} else {
					sprite = Sprite.player1b;
				}
			}
		}
		if (dir == 2) {
			sprite = Sprite.player2;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player2a;
				} else {
					sprite = Sprite.player2b;
				}
			}
		}
		if (dir == 3) {
			flip = 1;
		}
		screen.renderPlayer(x - sprite.SIZE / 2, y - sprite.SIZE / 2, sprite,
				flip);
	}
}
