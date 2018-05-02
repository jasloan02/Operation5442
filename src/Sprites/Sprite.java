package Sprites;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public abstract class Sprite {
	
	public int x, y, width, height;
	public int mx;
	public int my;
	int hp;
	
	public Sprite(int x, int y, int hp) {
		this.x = x;
		this.y = y;
		this.hp = hp;
	}
	
	public void setHP(int hp) { this.hp = hp; }
	public int getHP() { return hp; }
	
	public int getHeight() { return height; }
	public int getWidth() { return width; }
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public abstract void render(Graphics g, ImageObserver io);
	public abstract void update();
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
