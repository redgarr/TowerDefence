package graphics;


import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import actors.Actor;
import engine.Game;
import tiles.Tile;

public class Renderer {

	private int width, height;
	public int[] pixels;
	Random rand;
	private Game game;
	
	public Renderer(Game game, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.game = game;
		pixels = new int[width*height];
		rand = new Random();
	}
	
	public void clear() 
	{
		for (int i=0; i < pixels.length; i++)
		{
			pixels[i] = 0;
		}
	}
	
	public void render(Graphics g)
	{
		drawTiles();
		drawGrid0();//60ms
		drawActors();

	}
	
	private void drawActors() 
	{ 
		Iterator<Actor> actors = game.getController().getActors().iterator();
		while(actors.hasNext())
		{
			Actor a = actors.next();
			a.render(pixels);
		}
	}

	public void drawTiles()
	{
		Tile[] tiles = game.getController().getTiles();
		for(Tile tile : tiles)
		{
			tile.updateSprite(tiles);
			tile.render(pixels);
		}
	}
	
	public void drawGrid0()
	{
		for(int y = 0; y<height; y+=32)
		{
			for(int x = 0; x<=width; x++)
			{
				pixels[x+y*width] = 0x111111;
			}
		}
		for(int x = 0; x<width; x+=32)
		{
			for(int y = 0; y<height; y++)
			{
				pixels[x+y*width] = 0x111111;
			}
		}
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
}
