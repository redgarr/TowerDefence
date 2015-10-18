package tower;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

import actors.Actor;
import engine.Game;
import engine.GameController;
import graphics.Sprite;
import tiles.Tile;

public class AbstractTower implements Tower
{

	double damage;
	double attackSpeed;
	double range;
	
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
	
	public AbstractTower(double damage, double attackSpeed, double range, int x, int y, GameController controller) 
	{
		this.damage = damage;
		this.attackSpeed = attackSpeed;
		this.range = range;
		this.x = x;
		this.y = y;
		this.controller = controller;
		size = new Dimension(32, 32);
		centerPoint = new Point(x+size.width/2, y+size.height/2);
		lastShot = System.nanoTime();
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

	@Override
	public int getX() 
	{
		return x;
	}

	@Override
	public int getY() 
	{
		return y;
	}

	@Override
	public void setX(int x) 
	{
		this.x = x;
	}

	@Override
	public void setY(int y) 
	{
		this.y = y;
	}

	@Override
	public Dimension getSize() {
		return size;
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
	public Sprite getSprite() 
	{
		return sprite;
	}

	@Override
	public boolean isSelected() 
	{
		return isSelected;
	}

	@Override
	public void setSelected(boolean isSelected) 
	{
		this.isSelected = isSelected;
	}

	@Override
	public Point getCoordinates()
	{
		return new Point((int)Math.floor(x/32)+1, (int)Math.floor(y/32)+1);
	}
	
	@Override
	public Tile getParent() 
	{
		return parent;
	}

	@Override
	public void setParent(Tile from) 
	{
		this.parent = from;
	}

	@Override
	public Integer getFScore() 
	{
		return fScore;
	}

	@Override
	public void setFScore(Integer score) 
	{
		this.fScore = score;
	}

	@Override
	public void setPrevious(Tile t) 
	{
		this.previous = t;
	}

	@Override
	public void setNext(Tile t) 
	{
		this.next = t;
	}

	@Override
	public Tile getPrevious() 
	{
		return previous;
	}

	@Override
	public Tile getNext() 
	{
		return next;
	}

	@Override
	public void updateSprite(Tile[] tiles) 
	{
		sprite = Sprite.tower_simple;
	}

	
}
