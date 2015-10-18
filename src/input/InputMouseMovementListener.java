package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import engine.Game;
import engine.GameController;
import tiles.Tile;

public class InputMouseMovementListener 
	implements MouseMotionListener
{
	private Game game;
	private GameController controller;

	public InputMouseMovementListener(Game game, GameController controller) 
	{
		this.game = game;
		this.controller = controller;	
		
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{	
		
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		Tile tile = controller.selectTileAtPixels(e.getX(), e.getY());
		controller.fireTileChanged(tile, tile);
	}


}
