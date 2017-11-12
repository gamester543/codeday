package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import game.blocks.Block;

public class GameManager {
	public static final GameManager gm = new GameManager();
	static {
		gm.init();
	}
	private List<Entity> entities;
	private List<Entity> futureEntity;
	
	private List<Bullet> bullets;
	private List<Ship> collidable;
	private Player p;
	public void init() {
		entities = new ArrayList<Entity>();
		futureEntity = new LinkedList<Entity>();
		bullets = new ArrayList<Bullet>();
		collidable = new ArrayList<Ship>();
		
		p = new Player();
		new Ship(105, 5);
	}
	public void tick() {
		for(Entity e: entities) {
			e.tick();
		}
		
		while(futureEntity.size() > 0)entities.add(futureEntity.remove(0));
		
		testCollisions();
		cleanup();
	}
	public void testCollisions() {
		for(Bullet b: bullets) {
			for(Ship s: collidable) {
				s.collide(b);
			}
		}
	}
	public void cleanup() {
		for(int i = 0; i < entities.size(); i++) {
			if(!entities.get(i).isAlive())entities.remove(i--);
		}
	}
	public void addEntity(Entity e) {
		futureEntity.add(e);
	}
	public void addBullet(Bullet b) {
		bullets.add(b);
	}
	public void addShip(Ship s) {
		collidable.add(s);
	}
	public List<Entity> getEntities(){
		return entities;
	}
	public Point getView() {
		return new Point((int) p.getX(), (int) p.getY());
	}
	public Player getPlayer() {
		return p;
	}
}
