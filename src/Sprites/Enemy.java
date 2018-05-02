package Sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class Enemy extends Sprite {
	
	int pX, pY;
	final int WIDTH = 30, HEIGHT = 30;
	final int SPEED = 3;
	
	public Enemy(int x, int y) {
		super(x, y, 100);
		hp = 20;
	}

	@Override
	public void render(Graphics g, ImageObserver io) {
		width = WIDTH;
		height = HEIGHT;
		
		g.setColor(Color.GRAY);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}

	@Override
	public void update() {
		
		int rise = y - pY;
		int run = x - pX;
		
		float angle = (float) Math.atan2(rise, run);
		
		my = (int) (-SPEED * Math.sin(angle));
		mx = (int) (-SPEED * Math.cos(angle));
		
		x += mx;
		y += my;
	}
	
	public void updatePos(int pX, int pY) {
		this.pX = pX;
		this.pY = pY;
	}

}
