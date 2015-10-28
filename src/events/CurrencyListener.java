package events;

import engine.Game;
import engine.GameController;

public abstract class CurrencyListener 
{

	private Game game;
	private GameController controller;

	public CurrencyListener(Game game, GameController controller)
	{
		this.game = game;
		this.controller = controller;
		
	}
	
	public abstract void currencyChangeEvent(CurrencyChangeEvent e); 
	
	
}
