package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import Sprites.Bullet;
import Sprites.Chest;
import Sprites.Enemy;
import Sprites.Player;

public class Game_Manager extends JPanel implements ActionListener {
	
	JFrame frame;
	Player player;
	Timer timer;
	ArrayList<Enemy> enemies;
	Random rand;
	Rectangle bounds;
	final int SCREEN_W = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), SCREEN_H = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	long startTime2 = System.nanoTime() / 1000000000, delay2 = 15;
	
	JButton ridArmor;
	
	ArrayList<Chest> chests;
	
	public Game_Manager() {
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ridArmor = new JButton("Rid of Armor");
		ridArmor.setMaximumSize(new Dimension(300, 100));
		ridArmor.addActionListener(this);
		
		this.setBackground(new Color(153, 38, 38));
		
		setFocusable(true);
		addKeyListener(new TAdapter());
		addMouseListener(new MAdapter());
		
		frame.add(this);
		
		timer = new Timer(10, this);
		timer.restart();
		
		player = new Player(100, 100);
		enemies = new ArrayList<>();
		
		rand = new Random();
		
		bounds = new Rectangle(player.getWidth(), player.getHeight(), SCREEN_W - player.getWidth(), SCREEN_H - player.getHeight());
		
		chests = new ArrayList<>();
		
		for (int x = 0; x <= 2 - 1; x++) {
			chests.add(new Chest(rand.nextInt(SCREEN_W - 50), rand.nextInt(SCREEN_H - 50), 100));
		}
		
		enemies.add(new Enemy(300, 500));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		player.render(g, this);
		
		for (Enemy e : enemies) {
			e.render(g, this);
		}
		
		for (Chest c : chests) {
			c.render(g, this);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if (ae.getSource() == ridArmor) {
			
		}
		
		player.update();
		
		updateEnemies();
		updateChests();
		
		repaint();
	}
	
	public void play() { frame.setVisible(true); }
	
	public void updateEnemies() {
		
		int index = -1, index2 = -1, index3 = -1;
		boolean remove = false, remove2 = false, remove3 = false;
		
		//enemy update
		
		for (Enemy e : enemies) {
			e.updatePos(player.x, player.y);
			e.update();
				
			if (e.getHP() <= 0) {
				remove = true;
				index = enemies.indexOf(e);
			}
			
			for (Bullet b : player.getBullets()) { 
				if (b.getBounds().intersects(e.getBounds())) {
					e.setHP(e.getHP() - 20);
					player.addKill();
					remove2 = true;
					index2 = player.getBullets().indexOf(b);
				}
				
				for (int c = 0; c < chests.size(); c++) {
					if (b.getBounds().intersects(chests.get(c).getBounds())) {
						remove2 = true;
						index2 = player.getBullets().indexOf(b);
						chests.remove(c);
					}
				}
			}
			
			if (e.getBounds().intersects(player.getBounds())) {
				index = enemies.lastIndexOf(e);
				remove = true;
				int loss = (int) ((player.getHP() - (10 / (player.armor + 1) + 1)));
				if (loss < 0) loss = 0;
				player.setHP(loss);
			}
		}
		
		if (index >= 0 && remove) enemies.remove(index);
		if (index2 >= 0 && remove2) player.getBullets().remove(index2);
		if (index3 >= 0 && remove3) enemies.remove(index3);
		
		//generate enemies
		int count = 120 - (int) Math.pow(player.getKills()/3, 0.95);
		
		if (count <= 1) count = 1;
		
		if (rand.nextInt(count) == 0 || enemies.size() < 1) {
			enemies.add(new Enemy(rand.nextInt((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()), rand.nextInt((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight())));
		}
	}
	
	public void updateChests() {
		int index = -1;
		
		for (Chest c : chests) {
			if (player.getBounds().intersects(c.getBounds())) {
				index = chests.lastIndexOf(c);
				int randInt = rand.nextInt(3);
				if (randInt == 0) player.ammo += 50;
				else if (randInt == 1) player.armor += 1;
				else if (randInt == 2) player.setHP(player.getHP() + 10);
			}
		}
		
		if (index != -1) chests.remove(index);
		
		if (System.nanoTime() / 1000000000 - startTime2 >= delay2) {
			chests.add(new Chest(rand.nextInt(SCREEN_W - 50), rand.nextInt(SCREEN_H - 50), 100));
			startTime2 = System.nanoTime() / 1000000000;
		}
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new Game_Manager().play();
			}
		});
	}
	
	public class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent ke) {
			player.keyPressed(ke);
		}
		
		@Override
		public void keyReleased(KeyEvent ke) {
			player.keyReleased(ke);
		}
	}
	
	public class MAdapter implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent me) {}
		@Override
		public void mouseEntered(MouseEvent me) {
			player.updateMouse(me.getX(), me.getY());
		}
		@Override
		public void mouseExited(MouseEvent me) {
			player.updateMouse(me.getX(), me.getY());
		}
		@Override
		public void mousePressed(MouseEvent me) {
			player.updateMouse(me.getX(), me.getY());
			if (me.getButton() == MouseEvent.BUTTON1 && player.ammo > 0) {
				player.shoot();
			}
			if (me.getButton() == MouseEvent.BUTTON3 && player.ammo >= 3) {
				player.scatterShoot(me.getX(), me.getY());
			}
		}
		@Override
		public void mouseReleased(MouseEvent me) {
			player.updateMouse(me.getX(), me.getY());
			if (me.getButton() == MouseEvent.BUTTON1) player.setFiring(false);
		}
		
	}

}
