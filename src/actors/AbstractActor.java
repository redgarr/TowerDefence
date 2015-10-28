package actors;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

import audio.SoundClip;
import engine.Game;
import engine.GameController;
import graphics.AnimatedSprite;
import graphics.Sprite;
import routing.NoRouteFoundException;
import routing.PathfindingModule;
import tiles.Tile;

public class AbstractActor implements Actor
{
	private int x, y;
	private Tile currentTile;
	Dimension size;
	AnimatedSprite sprite;
	private int velX, velY;
	private Tile targetTile;
 	private boolean alive = true;
	private GameController controller;
	private List<Tile> path;
	private boolean moving;
	private PathfindingModule pathFinder;
	private double health = 100;
	private double maxHealth = 100;
	
	private AnimatedSprite upSprite = Sprite.getFriendlyActor1Up();
	private AnimatedSprite downSprite = Sprite.getFriendlyActor1Down();
	private AnimatedSprite leftSprite = Sprite.getFriendlyActor1Left();
	private AnimatedSprite rightSprite = Sprite.getFriendlyActor1Right();
	
	
	public AbstractActor(GameController controller, PathfindingModule pathFinder) 
	{
		this.controller = controller;
		this.pathFinder = pathFinder;
		size = new Dimension(32, 32);
		sprite = Sprite.getFriendlyActor1Up();
	}
	
	public int getY() 
	{
		return y;
	}

	public void setY(int y) 
	{
		this.y = y;
	}

	public int getX() 
	{
		return x;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public void render(int[] pixels)
	{
		for(int y=0; y<size.getHeight(); y++)
		{
			if(y < 0 || y >= size.getWidth()) 
				break;
			for(int x=0; x<size.getWidth(); x++)
			{
				if(x < 0 || x >= size.getHeight()) 
					break;
				int pixelIndex = (int) (this.x+x+(this.y+y)*Game.width);
				if(sprite.getSprite().pixels[x+(y*sprite.SIZE)] == -65281)
				{
					pixels[this.x+5 + this.y+5*Game.width] = 0xff00ff;
					continue;
				}
				pixels[pixelIndex] = sprite.getSprite().pixels[x+(y*sprite.SIZE)];
			}
		}
		drawHealthBar(pixels);
	}
	
	public void drawHealthBar(int[] pixels)
	{
		
		int startWidth = 5;
		int endWidth = 27;
		int startHeight = 6;
		int endHeight = 10;
		
		for(int i = startWidth; i<=endWidth; i++)
		{
			pixels[x+i+(y-startHeight)*Game.width] = 0x000000;
		}
		for(int i = startWidth; i<=endWidth; i++)
		{
			pixels[x+i+(y-endHeight)*Game.width] = 0x000000;
		}
		for(int i=startHeight; i<=endHeight; i++)
		{
			pixels[x+startWidth+(y-i)*Game.width] = 0x000000;
		}
		for(int i=startHeight; i<=endHeight; i++)
		{
			pixels[x+endWidth+(y-i)*Game.width] = 0x000000;
		}
		
		int steps = endWidth - startWidth;
		double stepsPerHp = (steps/maxHealth);
		
		for(int i=startWidth; i<=health*stepsPerHp+startWidth; i++)
		{
			for(int k=startHeight; k<=endHeight; k++)
			{
				pixels[x+i+(y-k)*Game.width] = 0x00ff00;
			}
		}
	}
	
	public void tick()
	{
		if(currentTile.getCoordinates().getX() == 17 && currentTile.getCoordinates().getY() == 2)
		{
			die();
		}
		
		currentTile = controller.getTileAtPixels(x, y);
		if(path != null && currentTile != path.get(path.size()-1) && !moving)
		{
			targetTile = path.get(path.indexOf(currentTile)+1);
		}
		
		if(currentTile == targetTile)
		{
			if(x == currentTile.getX() && y == currentTile.getY())
			{
				moving = false;
			}
			int vertMove, horMove;
			//find direction
			horMove = targetTile.getX() - x;
			vertMove = targetTile.getY() - y;
			
			velX = horMove > 0 ? 2 : -2;
			velY = vertMove > 0 ? 2 : -2;
			
			if(vertMove == 0)
			{
				velY = 0;
			}
			if(horMove == 0)
			{
				velX = 0;
			}
			
		}
		else if(targetTile != null  && currentTile != null)
		{
			moving = true;
			int vertMove, horMove;
			//find direction
			horMove = targetTile.getX() - currentTile.getX();
			vertMove = targetTile.getY() - currentTile.getY();
			//walk to the next tile
			
			velX = horMove > 0 ? 2 : -2;
			velY = vertMove > 0 ? 2 : -2;
			
			if(vertMove == 0)
			{
				velY = 0;
			}
			if(horMove == 0)
			{
				velX = 0;
			}
		}
		
		x = x + velX;
		y = y + velY;
		
		if(moving)
		{
			if(velY != 0 )
			{
				if(velY > 0)
				{
					sprite = downSprite;
				}
				else
				{
					sprite = upSprite;
				}
			}
			if(velX != 0)
			{
				if(velX > 0)
				{
					sprite = rightSprite;
				}
				else
				{
					sprite = leftSprite;
				}
			}
		}
	}

	public Tile getCurrentTile() 
	{
		return currentTile;
	}

	public void setCurrentTile(Tile currentTile) 
	{
		x = currentTile.getX();
		y = currentTile.getY();
		this.currentTile = currentTile;
	}
	
	public void moveActorTo(Tile tile)
	{
		try 
		{
			List<Tile> path = pathFinder.findPathTo(currentTile, tile);
			moveToTargetTile(tile, path);
		} 
		catch (NoRouteFoundException e) 
		{
			System.out.println("No route found.");
			alive = false;
		}
	}

	@Override
	public void setVelX(int velX) 
	{
		this.velX = velX;
	}

	@Override
	public void setVelY(int velY) 
	{
		this.velY = velY;
	}

	@Override
	public int getVelX() 
	{
		return velX;
	}

	@Override
	public int getVelY() 
	{
		return velY;
	}

	public Tile getTargetTile() {
		return targetTile;
	}

	private void moveToTargetTile(Tile targetTile, List<Tile> path) 
	{
		this.path = path;
	}

	public boolean isMoving() {
		return moving;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean isAlive) {
		this.alive = isAlive;
	}

	@Override
	public Point2D getCenterpoint() 
	{
		return new Point((int)(x+size.getWidth()/2), (int)(y+size.getHeight()/2));
	}

	
	/**
	 * Inflict damage on this actor. if damage is greater than health this will kill the actor.
	 * @param damage, the damage that will be done on this actor
	 */
	@Override
	public void inflictDamage(double damage) 
	{
		this.health -= damage;
		SoundClip.stab.play();
		if(health <=0)
		{
			die();
		}
	}

	private void die()
	{
		alive = false;
		controller.getActors().remove(this);
		SoundClip.pain.play();
	}

	@Override
	public double getHealth() {
		return health;
	}

}
