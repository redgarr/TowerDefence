package tiles;

import graphics.Sprite;

public class FloorTile extends AbstractTile
{

	public FloorTile(int x, int y) 
	{
		super(x, y);
	}
	
	@Override
	public void updateSprite(Tile[] tiles) 
	{
		sprite = Sprite.floor;
	}

}
