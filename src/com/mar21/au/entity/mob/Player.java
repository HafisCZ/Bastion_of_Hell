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

	private double classSpeed = 1;
	private Keyboard input;
	private int anim = 0;
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 3);
	private AnimatedSprite animSprite = down;

	public Player(Keyboard input) {
		this.input = input;
		this.walkspeed = classSpeed;
		this.sprite = Sprite.player0;
		resetsh(HoleProjectile.RATE);
	}

	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		this.walkspeed = classSpeed;
		sprite = Sprite.player0;
		resetsh(HoleProjectile.RATE);
	}

	public void update() {
		animSprite.update(this);

		double xa = 0, ya = 0;
		if (anim < 7500) anim++;
		else anim = 0;

		if (input.up) {
			ya -= walkspeed;
			animSprite = up;
		} else if (input.down) {
			ya += walkspeed;
			animSprite = down;
		}

		if (input.left) {
			xa -= walkspeed;
			animSprite = left;
		} else if (input.right) {
			xa += walkspeed;
			animSprite = right;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			moving = true;
		} else {
			moving = false;
		}

		clear();
		updateShooting();
	}

	private void updateShooting() {
		updatesh(1);
		if (Mouse.getButton() == 1 && rate <= 0) {
			double dx = Mouse.getX() - Game.getWWidth() / 2;
			double dy = Mouse.getY() - Game.getWHeight() / 2;
			double d = Math.atan2(dy, dx);
			shoot(x, y, d, 1);
			resetsh(HoleProjectile.RATE);
		}
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderPlayer((int) x - sprite.getW() / 2, (int) y - sprite.getH() / 2, sprite);
	}
}
