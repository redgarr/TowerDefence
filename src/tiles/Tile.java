package tiles;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import graphics.Sprite;

public interface Tile 
{
	public int getX();
	public int getY();
	public void setX(int x);
	public void setY(int y);
	public Dimension getSize();
	public void render(int[] pixels);
	public void tick();
	public Sprite getSprite();
	public boolean isSelected();
	public void setSelected(boolean sel);
	public void updateSprite(Tile[] tiles);
	public Point getCoordinates();
	public Tile getParent();
	public void setParent(Tile from);
	public Integer getFScore();
	public void setFScore(Integer score);
	public void setPrevious(Tile t);
	public void setNext(Tile t);
	public Tile getPrevious();
	public Tile getNext();
	
}
