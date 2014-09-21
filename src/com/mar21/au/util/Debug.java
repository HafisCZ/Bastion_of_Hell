package com.mar21.au.util;

import com.mar21.au.graphics.Screen;

public class Debug {

	private Debug() {

	}

	public static void drawRect(Screen s, int x, int y, int width, int height, boolean fixed) {
		s.drawRect(x, y, width, height, 0xff0000, fixed);
	}

	public static void drawRect(Screen s, int x, int y, int width, int height, int col, boolean fixed) {
		s.drawRect(x, y, width, height, col, fixed);
	}

}
