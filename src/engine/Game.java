package engine;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import graphics.Renderer;
import gui.GuiPanel;
import input.InputKeyboardListener;
import input.InputMouseListener;
import input.InputMouseMovementListener;
import routing.NoRouteFoundException;
import routing.PathfindingModule;
import tiles.Tile;
import tower.SimpleTower;


public class Game 
	extends Canvas 
		implements Runnable 
{
	
	public static int width = 1024; 
	public static int height = (width / 16 * 9);
	public static int scale = 1;
	public static int guiHeight = 200;
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	
	private PathfindingModule pathFinder;
	private Renderer renderer;
	private GameController controller;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game()
	{
		try 
		{
            // Set System L&F
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    	System.err.print(e);
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    	System.err.print(e);
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    	System.err.print(e);
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    	System.err.print(e);
	    }
		
		Dimension size = new Dimension(width * scale, (height) * scale);
		setPreferredSize(size);
		renderer = new Renderer(this, width, height);
		controller = new GameController(this);
		pathFinder = new PathfindingModule(this, controller);
		controller.setPathfinder(pathFinder);
		frame = new JFrame();
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		Dimension guiSize = new Dimension(width, guiHeight);
		frame.add(this);
		frame.add(new GuiPanel(guiSize, this, controller));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("Tower Defence");
		frame.pack();
		frame.setLocationRelativeTo(null);
		((JPanel)frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder());
		
		addMouseListener(new InputMouseListener(this, controller));
		addKeyListener(new InputKeyboardListener(this, controller));
		addMouseMotionListener(new InputMouseMovementListener(this, controller));
		
	}
	
	public synchronized void start()
	{
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop()
	{
		running = false;
		
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		game.start();
	}

	@Override
	public void run() {
		long timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int ticks = 0;
		while(running)
		{
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				frame.setTitle(ticks + " tps || " + frames + " fps");
				ticks = 0;
				frames = 0;
			}
		}
		stop();
	}

	private void render() 
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		renderer.clear();
		
		renderer.render(g);
		
		for(int i=0; i < pixels.length; i++)
		{
			pixels[i] = renderer.getPixels()[i];
		}
		//GAME START
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.drawImage(image, 0, 0, null);
		//GAME END
		g.dispose();
		bs.show();
	}

	private void tick() 
	{
		controller.tick();
	}

	public GameController getController() 
	{
		return controller;
	}

}
