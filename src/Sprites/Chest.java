package Sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class Chest extends Sprite {
	
	final int WIDTH = 40, HEIGHT = 25;

	public Chest(int x, int y, int hp) {
		super(x, y, hp);
	}

	@Override
	public void render(Graphics g, ImageObserver io) {
		g.setColor(new Color(186, 100, 50));
		g.fillRect(x, y, WIDTH, HEIGHT);
		width = WIDTH;
		height = HEIGHT;
	}

	@Override
	public void update() {
		
	}

}
