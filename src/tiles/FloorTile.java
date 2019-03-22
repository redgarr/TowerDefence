package tiles;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import graphics.Sprite;

public class FloorTile extends AbstractTile
{

	public FloorTile(int x, int y) 
	{
		super(x, y);
		sprite = Sprite.floor;
	}
	
	@Override
	public void updateSprite(Tile[] tiles) 
	{
	}

}
