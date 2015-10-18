package events;

import engine.Game;
import engine.GameController;

public abstract class TileChangeListener 
{

	private Game game;
	private GameController controller;

	public TileChangeListener(Game game, GameController controller) 
	{
		this.game = game;
		this.controller = controller;
	}
	
	public abstract void tileSelectionChangedEvent(TileSelectionChangedEvent e);
	
	
}
