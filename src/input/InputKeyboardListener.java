package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import audio.SoundClip;
import engine.Game;
import engine.GameController;

public class InputKeyboardListener 
	implements KeyListener
{

	private Game game;
	private GameController controller;

	public InputKeyboardListener(Game game, GameController controller) 
	{
		this.game = game;
		this.controller = controller;	
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
	}

}
