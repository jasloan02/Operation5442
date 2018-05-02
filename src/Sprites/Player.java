package Sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Player extends Sprite {
	
	ArrayList<Bullet> bullets;
	final int WIDTH = 30, HEIGHT = 40;
	int kills = 0;
	public static long ammo = 100;
	public static int armor = 0;
	long startTime = System.nanoTime() / 1000000000, delay = 2;
	boolean firing = false;
	int tX, tY;
	
	public Player(int x, int y) {
		super(x, y, 100);
		
		bullets = new ArrayList<>();
		hp = 100;
	}

	@Override
	public void render(Graphics g, ImageObserver io) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, (int) WIDTH, (int) HEIGHT);
		width = WIDTH;
		height = HEIGHT;
		
		g.drawString("Ammo: " + ammo, 20, 20);
		g.drawString("Armor: " + armor, 20, 35);
		g.drawString("Health: " + hp, 20, 50);
		
		if (armor == 1) g.setColor(Color.RED);
		else if (armor == 2) g.setColor(Color.GREEN);
		else if (armor >= 3) g.setColor(Color.BLUE);
		
		g.fillRect(x, (int) (y + (HEIGHT - 15)), (int) WIDTH, (int) HEIGHT - 15);
		
		g.setColor(Color.BLACK);
		for (Bullet b : bullets) {
			b.render(g, io);
		}
	}

	@Override
	public void update() {
		x += mx;
		y += my;
		
		int index = -1;
		
		for (Bullet b : bullets) {
			b.update();
			
			if (System.nanoTime() - b.startTime >= b.delay) index = bullets.indexOf(b);
		}
		
		if (index >= 0) bullets.remove(index);
		
		if ((System.nanoTime() / 1000000000) - startTime >= delay) {
			ammo += 5;
			startTime = System.nanoTime() / 1000000000;
		}
		
		if (hp <= 0) System.exit(0);
	}
	
	public void updateMouse(int tX, int tY) {
		this.tX = tX;
		this.tY = tY;
	}
	
	public void shoot() {
		bullets.add(new Bullet((int) (x + WIDTH/2), (int) (y + HEIGHT/2), tX, tY, System.nanoTime(), 990000000));
		ammo -= 1;
	}
	
	public void scatterShoot(int tX, int tY) {
		long shotDelay = 100000000;
		bullets.add(new Bullet((int) (x + WIDTH/2), (int) (y + HEIGHT/2), tX + 40, tY - 40, System.nanoTime(), shotDelay));
		bullets.add(new Bullet((int) (x + WIDTH/2), (int) (y + HEIGHT/2), tX, tY, System.nanoTime(), shotDelay));
		bullets.add(new Bullet((int) (x + WIDTH/2), (int) (y + HEIGHT/2), tX - 40, tY + 40, System.nanoTime(), shotDelay));
		ammo -= 3;
	}
	
	public ArrayList<Bullet> getBullets() { return bullets; }
	public void addKill() { kills += 1; }
	public int getKills() { return kills; }
	
	public void setFiring(boolean firing) { this.firing = firing; }
	
	public void keyPressed(KeyEvent ke) {
		int code = ke.getKeyCode();
		final int SPEED = 5;
		
		if (code == KeyEvent.VK_W) my = -SPEED;
		if (code == KeyEvent.VK_A) mx = -SPEED; 
		if (code == KeyEvent.VK_S) my = SPEED;
		if (code == KeyEvent.VK_D) mx = SPEED; 
		if (code == KeyEvent.VK_ESCAPE) System.exit(0);
	}
	
	public void keyReleased(KeyEvent ke) {
		int code = ke.getKeyCode();
		
		if (code == KeyEvent.VK_W) my = 0;
		if (code == KeyEvent.VK_A) mx = 0; 
		if (code == KeyEvent.VK_S) my = 0;
		if (code == KeyEvent.VK_D) mx = 0; 
	}

}
