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
import events.CurrencyChangeEvent;
import events.CurrencyListener;
import events.TileChangeListener;
import events.TileSelectionChangedEvent;
import routing.PathfindingModule;
import tiles.FloorTile;
import tiles.RockTile;
import tiles.Tile;
import tower.SimpleTower;
import tower.Tower;

public class GameController 
{
	int numTiles = 32*18;
	Tile[] tiles;
	List<Actor> actors;
	Game game;
	Tile selectedTile;
	private PathfindingModule pathFinder;
	private int credits;
	private List<TileChangeListener> tileChangeListeners;
	private List<CurrencyListener> currencyListeners;
	private Tile activeTile;
	
	public GameController(Game game) 
	{
		this.game = game;
		this.activeTile = new SimpleTower(0, 0, this);
		tiles = new Tile[numTiles];
		actors = new CopyOnWriteArrayList<Actor>();
		tileChangeListeners = new ArrayList<TileChangeListener>();
		currencyListeners = new ArrayList<CurrencyListener>();
		
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
		Tile temp = activeTile;
		activeTile = new FloorTile(0,0);
		
		placeTileAtPixels(16*32, 0*32);
		placeTileAtPixels(16*32, 1*32);
		placeTileAtPixels(16*32, 16*32);
		placeTileAtPixels(16*32, 17*32);
		
		activeTile = temp;
	}
	
	public void tick()
	{
		for(Actor a : actors)
		{
			a.tick();
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
	
	public void registerCurrencyListener(CurrencyListener listener)
	{
		currencyListeners.add(listener);
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
	
<<<<<<< HEAD
	public void fireCurrencyChanged(Object source, Object data)
	{
		for(CurrencyListener listener : currencyListeners)
		{
			listener.currencyChangeEvent(new CurrencyChangeEvent(source, data));
		}
	}
	
	private void killActor(Actor a) 
	{
	}

=======
>>>>>>> f15fc206ccb6726242b6378dd359cabf9c995c8e
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
		if(tile instanceof Tower)
		{
			subtractCredits((int)((Tower) tile).getCost());
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
	
	public void placeTileAtPixels(int x, int y)
	{
		Tile tile = null;
		
		if(activeTile instanceof SimpleTower)
		{
			tile = new SimpleTower(x - (x%32), y - (y%32), this);
		}
		else
		if(activeTile instanceof FloorTile)
		{
			tile = new FloorTile(x - (x%32), y - (y%32));
		}
		else
		if(activeTile instanceof RockTile)
		{
			tile = new RockTile(x - (x%32), y - (y%32));
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
		Tile temp = activeTile;
		
		activeTile = new FloorTile(0, 0);
		
		for(int i=2; i<=17; i++)
		{
			placeTileAtPixels(16*32, i*32);
		}
		
		activeTile = temp;
	}

	public int getCredits() 
	{
		return credits;
	}

	public void setCredits(int credits) 
	{
		this.credits = credits;
		fireCurrencyChanged(this, this.credits);
	}
	
	public void subtractCredits(int credits)
	{
		this.credits -= credits;
		fireCurrencyChanged(this, this.credits);
	}
	
	public void addCredits(int credits)
	{
		this.credits += credits;
		fireCurrencyChanged(this, this.credits);
	}

	public void setActiveTile(Tile tile) 
	{
		activeTile = tile;
	}
}
