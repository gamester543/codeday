package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Entity;
import game.GameManager;
import indy.ImprovedNoise;

public class Board extends JPanel{
	public  GameManager gm = GameManager.gm;
	public Board(JFrame f) {
		f.addKeyListener(gm.getPlayer());
	}
	private int tick = 0;
	@Override
	public void paintComponent(Graphics g2) {
		long start = System.currentTimeMillis();
		Graphics2D g = (Graphics2D) g2;
		g.clearRect(0, 0, getWidth(), getHeight());
		Point view = gm.getView();
		for(int i = 0; i < getWidth(); i+=10) {
			for(int z = 0; z < getHeight(); z+=10) {
				int val = (int) (255 * ImprovedNoise.noise((i + view.x) / 100.0, 
								(z + view.y) / 100.0, tick / 100.0));
				g.setColor(new Color(val/2, 0, val / 2 + 16));
				g.fillRect(i, z, 10, 10);
			}
		}
		tick++;
	
		gm.tick();
		
		List<Entity> entities = gm.getEntities();
		for(Entity e: entities) {			
			double angle = e.getAngle();
			BufferedImage img = e.getImg();
			if(img == null)continue;
			
			AffineTransform ori = g.getTransform();
			g.translate(getWidth() / 2 + e.getX() - view.x,
					getHeight() / 2 + e.getY() - view.y);
			g.rotate(angle);
			
			
			g.drawImage(img, - img.getWidth() / 2, 
					- img.getHeight() / 2, null);
			g.setTransform(ori);
		}
		
		try {
			long delay = System.currentTimeMillis() - start;
			g.setColor(Color.WHITE);
			g.drawString(delay + "ms", 10, 10);
			Thread.sleep(Math.max(0, 30 - delay));
		}catch(Exception e) {}
		repaint();
	}
}
