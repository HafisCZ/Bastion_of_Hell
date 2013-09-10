package com.mar21.au.entity.mob;

import com.mar21.au.Game;
import com.mar21.au.entity.projectile.HoleProjectile;
import com.mar21.au.entity.projectile.Projectile;
import com.mar21.au.graphics.AnimatedSprite;
import com.mar21.au.graphics.Screen;
import com.mar21.au.graphics.Sprite;
import com.mar21.au.graphics.SpriteSheet;
import com.mar21.au.input.Keyboard;
import com.mar21.au.input.Mouse;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	private AnimatedSprite test = new AnimatedSprite(SpriteSheet.player_down, 3);

	private int rate = 0;

	public Player(Keyboard input) {
		this.input = input;
		sprite = Sprite.player0;
		rate = HoleProjectile.RATE;
	}

	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = Sprite.player0;
		rate = HoleProjectile.RATE;
	}

	public void update() {
		test.update();
		if (rate > 0)
			rate--;
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

		clear();
		updateShooting();
	}

	private void updateShooting() {
		if (Mouse.getButton() == 1 && rate <= 0) {
			double dx = Mouse.getX() - Game.getWWidth() / 2;
			double dy = Mouse.getY() - Game.getWHeight() / 2;
			double d = Math.atan2(dy, dx);
			shoot(x, y, d);
			rate = HoleProjectile.RATE;
		}
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved())
				level.getProjectiles().remove(i);
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
		sprite = test.getSprite();
		screen.renderPlayer(x - sprite.SIZE / 2, y - sprite.SIZE / 2, sprite,
				flip);
	}
}
