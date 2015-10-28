package tower;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Iterator;
import java.util.List;

import actors.Actor;
import engine.Game;
import engine.GameController;
import graphics.Sprite;
import tiles.AbstractTile;
import tiles.Tile;

public class AbstractTower extends AbstractTile implements Tower 
{

	double damage;
	double attackSpeed;
	double range; 
	private double cost;
	
	public Sprite sprite;
	Dimension size;
	Point2D centerPoint;
	
	int x, y;
	private boolean isSelected;
	private Tile parent;
	private Integer fScore;
	private Tile previous;
	private Tile next;
	private GameController controller;
	private long lastShot;
	
	public AbstractTower(double damage, double attackSpeed, double range, double cost, int x, int y, GameController controller) 
	{
		super(x, y);
		this.damage = damage;
		this.attackSpeed = attackSpeed;
		this.range = range;
		this.cost = cost;
		this.x = x;
		this.y = y;
		this.controller = controller;
		size = new Dimension(32, 32);
		centerPoint = new Point(x+size.width/2, y+size.height/2);
		lastShot = System.nanoTime();
	}
	
	public BufferedImage getSpriteAsImage() 
	{
		BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
		int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		for(int i=0; i < pixels.length; i++)
		{
			if(i<32)
			{
				continue;
			}
			if(sprite != null)
			{
				pixels[i] = sprite.pixels[i];
			}
		}
		
		return image;
	}
	
	@Override
	public double getDamage() 
	{
		return damage;
	}

	@Override
	public double getAttackSpeed() 
	{
		return attackSpeed;
	}

	@Override
	public double getRange() 
	{
		return range;
	}

	private void shoot(Actor target)
	{
		long currentTime = System.nanoTime();
		 
		long passedTime = (currentTime - lastShot)/1000000;
		if(passedTime > attackSpeed)
		{
			target.inflictDamage(damage);
			lastShot = System.nanoTime();
		}
	}
	
	@Override
	public void tick() 
	{
		List<Actor> actors = controller.getActors();
		double closestTarget = Double.MAX_VALUE;
		Actor target = null;
		Iterator<Actor> iter = actors.iterator();
		while(iter.hasNext())
		{
			Actor actor = iter.next();
			if(actor.getCenterpoint().distance(centerPoint) <= range)
			{
				if(target == null)
				{
					target=actor;
				}
				double distance = actor.getCenterpoint().distance(centerPoint);
				
				if(distance < closestTarget)
				{
					target = actor;
				}
			}
			
			if(target != null)
			{
				shoot(target);
			}
		}
	}
	
	@Override
	public void render(int[] pixels) 
	{
		for(int y = 0; y < size.getWidth(); y++)
		{
			if(y < 0 || y >= size.getWidth()) 
				break;
			for(int x = 0; x < size.getHeight(); x++)
			{
				if(x < 0 || x >= size.getHeight()) 
					break;
				int pixelIndex = (int) (this.x+x+(this.y+y)*Game.width);
				if(pixels[pixelIndex] != 0xff00ff)
				{
					pixels[pixelIndex] = sprite.pixels[x+(y*sprite.SIZE)];
				}
			}
		}
	}
	
	@Override
	public void updateSprite(Tile[] tiles) 
	{
		sprite = Sprite.tower_simple;
	}

	public double getCost() {
		return cost;
	}

}
