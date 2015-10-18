package routing;
import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import engine.Game;
import engine.GameController;
import tiles.FloorTile;
import tiles.Tile;

public class PathfindingModule 
{

	private GameController controller;
	private Game game;
	
	public PathfindingModule(Game game, GameController gameController) 
	{
		this.game = game;
		this.controller = gameController;

	}
	
	public Tile lowestFInOpen(List<Tile> open)
	{
		Tile returnTile = null;
		
		for(Tile t : open)
		{
			if(returnTile == null || (returnTile.getFScore() > t.getFScore()))
			{
				returnTile = t;
			}
		}
		
		return returnTile;
	}
	
	public List<Tile> findPathTo(Tile from, Tile target)
		throws NoRouteFoundException
	{
		List<Tile> closed = new LinkedList<Tile>();
		List<Tile> open = new LinkedList<Tile>();
		
		open.add(from);
		from.setFScore(0);
		
		boolean done = false;
		Tile current;
		
		for(Tile t : controller.getSurroundingTiles(from))
		{
			if(t instanceof FloorTile)
			{
				open.add(t);
				t.setPrevious(from);
				t.setFScore(1);
			}
		}
		
		open.remove(from);
		closed.add(from);
		
		while(!done)
		{
			current = lowestFInOpen(open);
			closed.add(current);
			open.remove(current);
			
			if(current == target) //found goal
			{
				List<Tile> returnList = new LinkedList<Tile>();
				
				Tile temp = current;
				returnList.add(current);
				while(temp.getPrevious() != null)
				{
					temp = temp.getPrevious();
					returnList.add(temp);
				}
				Collections.reverse(returnList);
				
				for(Tile t : controller.getTiles())
				{
					t.setPrevious(null);
					t.setFScore(0);
				}
				
				return returnList;
			}
			
			for(Tile t : controller.getSurroundingTiles(current))
			{
				if(!open.contains(t) && !closed.contains(t)) //tile not in open list
				{
					t.setPrevious(current);
					t.setFScore(current.getFScore()+1); //Set score of tile to current + 1
					open.add(t);
				}
				else //node is in open list
				{
					if(t.getFScore() > current.getFScore() + 1)
					{
						t.setPrevious(current);
						t.setFScore(current.getFScore() + 1);
					}
				}
			}
			if(open.isEmpty())
			{
				done = true;
			}
		}
		throw new NoRouteFoundException();
	}
	
	
	
}
