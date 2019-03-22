package input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import engine.Game;
import engine.GameController;

public class InputMouseListener 
	implements MouseListener
{

	private Game game;
	private GameController controller;

	public InputMouseListener(Game game, GameController controller) 
	{
		this.game = game;
		this.controller = controller;
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) 
	{
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		switch(e.getButton())
		{
			//Left click
			case MouseEvent.BUTTON1: controller.placeTileAtPixels(e.getX(), e.getY());
									break;
			//Mouse wheel click
			case MouseEvent.BUTTON2: controller.generateAIActor(); //Generate an AI actor
									break;
			//Right click	
			case MouseEvent.BUTTON3: controller.testTile(e.getX(), e.getY());
									break;
			case 4: controller.generateAIActor(); //Generate an AI actor
					break;
			default: 
					break;
					 
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
	}
	
	

}
