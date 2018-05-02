package Sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class Bullet extends Sprite {
	
	int tX, tY, rise, run;
	long delay;
	long startTime;
	final int WIDTH = 6, HEIGHT = 6;
	
	public Bullet(int x, int y, int tX, int tY, long startTime, long delay) {
		super(x, y, 1);
		
		this.tX = tX;
		this.tY = tY;
		
		rise = y - tY;
		run = x - tX;
		
		this.startTime = startTime;
		
		this.delay = delay;
	}

	@Override
	public void render(Graphics g, ImageObserver io) {
		width = WIDTH;
		height = HEIGHT;
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}

	@Override
	public void update() {
		
		final int SPEED =  25;
		
		float angle = (float) Math.atan2(rise, run);
		
		my = (int) (SPEED * Math.sin(angle));
		mx = (int) (SPEED * Math.cos(angle));
		
		y -= my;
		x -= mx;
	}

}
