package com.mar21.au.graphics;

public class Font {

	private static SpriteSheet font = new SpriteSheet("/textures/fonts/arial.png", 16);
	private static Sprite[] characters = Sprite.split(font);

	private static String charIndex = "ABCDEFGHIJKLM" + //
			"NOPQRSTUVWXYZ" + //
			"abcdefghijklm" + //
			"nopqrstuvwxyz" + //
			"0123456789.,'" + //
			"'\"\";:!@$%()-+";

	private static String loweredCharIndex = "gjpqy";
	private static String halfLoweredCharIndex = ",;";

	public Font() {

	}

	public void render(int x, int y, String text, Screen screen) {
		render(x, y, 0, text, screen);
	}

	public void render(int x, int y, int color, String text, Screen screen) {
		render(x, y, 0, color, text, screen);
	}

	public void render(int x, int y, int spacing, int color, String text, Screen screen) {
		int xOffset = 0;
		int line = 0;
		for (int i = 0; i < text.length(); i++) {
			xOffset += 16 + spacing;
			int yOffset = 0;
			char currentChar = text.charAt(i);
			if (loweredCharIndex.indexOf(currentChar) != -1) yOffset = 4;
			if (halfLoweredCharIndex.indexOf(currentChar) != -1) yOffset = 2;
			if (currentChar == '\n') {
				line++;
				xOffset = 0;
			}
			int index = charIndex.indexOf(currentChar);
			if (index == -1) continue;
			screen.renderTextCharacter(x + xOffset, y + line * 20 + yOffset, characters[index], color, false, true);
		}
	}

}
