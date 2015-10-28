package engine;
import java.awt.Point;
import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import actors.AbstractActor;
import actors.Actor;
import actors.HostileActor;
import events.TileChangeListener;
import events.TileSelectionChangedEvent;
import routing.PathfindingModule;
import tiles.FloorTile;
import tiles.RockTile;
import tiles.Tile;
import tower.SimpleTower;

public class GameController 
{
	int numTiles = 32*18;
	Tile[] tiles;
	List<Actor> actors;
	Game game;
	Tile selectedTile;
	private PathfindingModule pathFinder;
	
	private List<TileChangeListener> tileChangeListeners;
	
	public GameController(Game game) 
	{
		this.game = game;
		tiles = new Tile[numTiles];
		actors = new CopyOnWriteArrayList<Actor>();
		tileChangeListeners = new ArrayList<TileChangeListener>();
		
		generateMap();
		createActors(); 
	}
	
	public void generateAIActor()
	{
		Actor a = new HostileActor(this, pathFinder);
		actors.add(a);
	}
	
	
	private void createActors() 
	{
	}

	private void generateMap()
	{
		//32x18
		for(int i=0; i<numTiles; i++)
		{
			double row =  Math.floor(i/32);
			tiles[i] = new RockTile((i*32) % Game.width, (int)row*32);
		}
		placeTileAtPixels(16*32, 0*32, false);
		placeTileAtPixels(16*32, 1*32, false);
		placeTileAtPixels(16*32, 16*32, false);
		placeTileAtPixels(16*32, 17*32, false);
	}
	
	public void tick()
	{
		for(Actor a : actors)
		{
			a.tick();
			
			if(!a.isAlive())
			{
				killActor(a);
				actors.remove(a);
			}
		}
		
		for(Tile tile : tiles)
		{
			tile.tick();
		}
	}
	
	public void registerTileChangeListener(TileChangeListener listener)
	{
		tileChangeListeners.add(listener);
	}
	
	public void removeTileChangeListener(TileChangeListener listener)
	{
		tileChangeListeners.remove(listener);
	}
	
	public void fireTileChanged(Object source, Object data)
	{
		for(TileChangeListener listener : tileChangeListeners)
		{
			listener.tileSelectionChangedEvent(new TileSelectionChangedEvent(source, data));
		}
	}
	
	private void killActor(Actor a) 
	{
	}

	public Tile[] getTiles()
	{
		return tiles;
	}
	
	public List<Actor> getActors()
	{
		return actors;
	}
	
	public void sendActorToTile(Actor a, Tile t)
	{
		a.moveActorTo(t);
	}
	
	public Tile getTileAt(int x, int y)
	{
		if(x > 1 && y > 1 && x < Game.width / 32 && y < Game.height / 32)
		{
			int tileIndex = (x - 1) + ((y - 1) * 32);
			return tiles[tileIndex];
		}
		return null;
	}
	
	public void setTileAt(int x, int y, Tile tile)
	{
		int tileIndex = (x / 32) + (y / 32) * 32;
		Tile currTile = tiles[tileIndex];
		tiles[tileIndex] = tile;
		if(currTile == selectedTile)
		{
			fireTileChanged(this, tile);
		}
	}
	
	public Tile getTileAtPixels(int x, int y)
	{
		int tileIndex = (x / 32) + (y / 32) * 32;
		
		if(tileIndex >= tiles.length)
		{
			return null;
		}
		
		return tiles[tileIndex];
	}
	
	public List<Tile> getSurroundingTiles(Tile tile)
	{
		if(tile==null)
		{
			return new ArrayList();
		}
		List<Tile> tiles = new ArrayList<Tile>();
		Point point = tile.getCoordinates();
		
		Tile t = getTileAt((int)point.getX()-1, (int)point.getY());
		if(t != null && t instanceof FloorTile)
		{
			tiles.add(t);
		}
		
		t = getTileAt((int)point.getX()+1, (int)point.getY());
		if(t != null && t instanceof FloorTile)
		{
			tiles.add(t);
		}

		t = getTileAt((int)point.getX(), (int)point.getY()-1);
		if(t != null && t instanceof FloorTile)
		{
			tiles.add(t);
		}

		t = getTileAt((int)point.getX(), (int)point.getY()+1);
		if(t != null && t instanceof FloorTile)
		{
			tiles.add(t);
		}
		
		return tiles;
	}
	
	public void placeTileAtPixels(int x, int y, boolean tower)
	{
		Tile tile;
		if(tower)
		{
			tile = new SimpleTower(x - (x%32), y - (y%32), this);
		}
		else
		{
			tile = new FloorTile(x - (x%32), y - (y%32));
		}
		setTileAt(x, y, tile);
	}
	
	public Tile selectTileAtPixels(int x, int y)
	{
		Tile tile = getTileAtPixels(x, y);
		if(tile == null)
		{
			return null;
		}
		
		if(selectedTile != null)
		{
			selectedTile.setSelected(false);
		}
		
		tile.setSelected(true);
		selectedTile = tile;
		
		return selectedTile;
	}

	public void setPathfinder(PathfindingModule pathFinder) {
		this.pathFinder = pathFinder;
		
	}

	public void makeFloorLine() 
	{
		for(int i=2; i<=17; i++)
		{
			placeTileAtPixels(16*32, i*32, false);
		}
	}
}
