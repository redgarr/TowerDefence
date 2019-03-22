package tiles;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import engine.Game;
import graphics.Sprite;

public abstract class AbstractTile 
	implements Tile 
{
	int x, y;
	boolean debugMode = false;
	Dimension size;
	public Sprite sprite;
	private boolean selected;
	
	//Parent tile saved for pathfinding
	private Tile parent;
	
	//F score for pathfinding
	private Integer fScore;
	
	//Previous and next tiles stored for pathfinding
	private Tile previous;
	private Tile next;
	
	public AbstractTile(int x, int y) 
	{
		this.x = x;
		this.y = y;
		size = new Dimension(32, 32);
	}
	
	public BufferedImage getSpriteAsImage() 
	{
		BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
		int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		for(int i=0; i < pixels.length; i++)
		{
			if(i<32)
			{
				continue;
			}
			if(sprite != null)
			{
				pixels[i] = sprite.pixels[i];
			}
		}
		
		return image;
	}
	
	public void setPrevious(Tile t)
	{
		previous = t;
	}
	
	public void setNext(Tile t)
	{
		next = t;
	}
	
	public Tile getPrevious()
	{
		return previous;
	}
	
	public Tile getNext()
	{
		return next;
	}
	
	public void setParent(Tile t)
	{
		parent = t;
	}
	
	public Tile getParent()
	{
		return parent;
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public Dimension getSize() {
		return size;
	}
	
	public Point getCoordinates()
	{
		return new Point((int)Math.floor(x/32), (int)Math.floor(y/32));
	}
	
	public void tick()
	{
		
	}
	
	@Override
	public void render(int[] pixels) 
	{
		if(debugMode)
		{
			Font f = new Font(Font.MONOSPACED, Font.PLAIN, 10);
			BufferedImage img = new BufferedImage(32,32, BufferedImage.TYPE_INT_RGB);
			img.getGraphics().setFont(f);
			
			String c = (int)getCoordinates().getX() + "," + (int)getCoordinates().getY();
			
			
			img.getGraphics().setColor(Color.RED);
			img.getGraphics().setFont(f);
			img.getGraphics().drawString(c, 0, 16);
			
			int[] coordinates = new int[32*32];
			
			img.getRGB(0, 0, img.getWidth(), img.getHeight(), coordinates, 0, img.getHeight());
			
			int j=0;
			for(int k : coordinates)
			{
				if(k!=-16777216 && k!=0)
				{
					coordinates[j] = 0xff0000;
				}
				j++;
			}
			
			int i = 0;
			for(int y = 0; y < size.getWidth(); y++)
			{
				if(y < 0 || y >= size.getWidth()) 
					break;
				for(int x = 0; x < size.getHeight(); x++)
				{
					if(x < 0 || x >= size.getHeight()) 
						break;
					pixels[(int) (this.x+x+(this.y+y)*Game.width)] = coordinates[i];
					i++;
				}
			}
		}
		
		
		for(int y = 0; y < size.getWidth(); y++)
		{
			if(y < 0 || y >= size.getWidth()) 
				break;
			for(int x = 0; x < size.getHeight(); x++)
			{
				if(x < 0 || x >= size.getHeight()) 
					break;
				int pixelIndex = (int) (this.x+x+(this.y+y)*Game.width);
				if(selected)
				{
					int value = Math.min(sprite.pixels[x+(y*sprite.SIZE)] * 2, 0xffffff);
					//Draw recticle
					int offset = 3;
					int axisLength = 4;
					if(x == offset)
					{
						if(y>=offset && y<= offset+axisLength || y>= size.getWidth() - axisLength - offset && y <= size.getWidth()-offset)
						{
							pixels[pixelIndex] = 0xffffff; //white
							continue;
						}
					}
					
					if(y == offset)
					{
						if(x >= offset && x <= offset+axisLength || x >= size.getWidth() - axisLength - offset && x <= size.getWidth()-offset)
						{
							pixels[pixelIndex] = 0xffffff; //white
							continue;
						}
					}
					
					if(x == size.getWidth() - offset)
					{
						if(y>=offset && y<= offset+axisLength || y>= size.getWidth() - axisLength - offset && y <= size.getWidth()-offset)
						{
							pixels[pixelIndex] = 0xffffff; //white
							continue;
						}
					}
					
					
					if(y == size.getWidth() - offset)
					{
						if(x >= offset && x <= offset+axisLength || x >= size.getWidth() - axisLength - offset && x <= size.getWidth()-offset)
						{
							pixels[pixelIndex] = 0xffffff; //white
							continue;
						}
					}
					
					if(pixels[pixelIndex] != 0xff0000) //pink
					{
						pixels[pixelIndex] = value;
					}
				}
				else
				{
					if(pixels[pixelIndex] != 0xff0000)
					{
						pixels[pixelIndex] = sprite.pixels[x+(y*sprite.SIZE)];
					}
				}
			}
		}
	}
	
	
	
	public abstract void updateSprite(Tile[] tiles);

	@Override
	public Sprite getSprite() 
	{
		return sprite;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Integer getFScore() {
		return fScore;
	}

	public void setFScore(Integer fScore) {
		this.fScore = fScore;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()+"(");
		sb.append("Coordinates=" + getCoordinates());
		sb.append("X=" + getX());
		sb.append("Y=" + getY());
		sb.append(")");
		return sb.toString();
	}
	
}
