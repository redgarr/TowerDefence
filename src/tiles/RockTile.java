package tiles;

import engine.Game;
import graphics.Sprite;

public class RockTile extends AbstractTile
{

	public RockTile(int x, int y) 
	{
		super(x, y);
	}
	
	public void updateSprite(Tile[] tiles)
	{
		int tileIndex = (x / 32) + (y / 32) * 32;
		
		//Top line
		if(y==0)
		{
			//Top left corner
			if(x==0)
			{
				sprite = Sprite.rocks_topleft;
				return;
			}
			//Top right corner
			else if(x==Game.width-size.getWidth())
			{
				sprite = Sprite.rocks_topright;
				return;
			}
			sprite = Sprite.rocks_top;
			return;
		}
		//Bottom line
		if(y==Game.height-size.getHeight())
		{
			//Bottom left
			if(x==0)
			{
				sprite = Sprite.rocks_botleft;
				return;
			}
			//Bottom right
			if(x==Game.width-size.getWidth())
			{
				sprite = Sprite.rocks_botright;
				return;
			}
			else
			{
				sprite = Sprite.rocks_bot;
				return;
			}
		}
		//Left side
		if(x==0)
		{
			sprite = Sprite.rocks_left;
			return;
		}
		//Right side
		if(x==Game.width-size.getWidth())
		{
			sprite = Sprite.rocks_right;
			return;
		}
		
		//Check for tile relations
		//XR
		//RF
		if(!(tiles[tileIndex+32+1] instanceof RockTile))
		{
			if(tiles[tileIndex+32] instanceof RockTile &&
					tiles[tileIndex+1] instanceof RockTile)
			{
				
				sprite = Sprite.rocks_botrightcorner;
				return;
			}
		}
		if(!(tiles[tileIndex+32-1] instanceof RockTile))
		{
			if(tiles[tileIndex+32] instanceof RockTile &&
					tiles[tileIndex-1] instanceof RockTile)
			{
				sprite = Sprite.rocks_botleftcorner;
				return;
			}
		}
		
		if(!(tiles[tileIndex-32+1] instanceof RockTile))
		{
			if(tiles[tileIndex-32] instanceof RockTile &&
					tiles[tileIndex+1] instanceof RockTile)
			{
				sprite = Sprite.rocks_toprightcorner;
				return;
			}
		}
		
		if(!(tiles[tileIndex-32-1] instanceof RockTile))
		{
			if(tiles[tileIndex-32] instanceof RockTile && 
					tiles[tileIndex-1] instanceof RockTile)
			{
				sprite = Sprite.rocks_topleftcorner;
				return;
			}
		}
		
		//FX
		//FF
		if(!(tiles[tileIndex-1] instanceof RockTile) &&
				!(tiles[tileIndex+32] instanceof RockTile))
		{
			sprite = Sprite.rocks_botleft;
			return;
		}
		
		//XF
		//FF
		if(!(tiles[tileIndex+1] instanceof RockTile) &&
				!(tiles[tileIndex+32] instanceof RockTile))
		{
			sprite = Sprite.rocks_botright;
			return;
		}
		
		//FF
		//XF
		if(!(tiles[tileIndex+1] instanceof RockTile) &&
				!(tiles[tileIndex-32] instanceof RockTile))
		{
			sprite = Sprite.rocks_topright;
			return;
		}
		
		//FF
		//FX
		if(!(tiles[tileIndex-1] instanceof RockTile) &&
				!(tiles[tileIndex-32] instanceof RockTile))
		{
			sprite = Sprite.rocks_topleft;
			return;
		}
		
		//Check left of
		if(!(tiles[tileIndex-1] instanceof RockTile))
		{
			sprite = Sprite.rocks_left;
			return;
		}
		//Check right of
		if(!(tiles[tileIndex+1] instanceof RockTile))
		{
			sprite = Sprite.rocks_right;
			return;
		}
		//Check below
		if(!(tiles[tileIndex+32] instanceof RockTile))
		{
			sprite = Sprite.rocks_bot;
			return;
		}
		//Check above
		if(!(tiles[tileIndex-32] instanceof RockTile))
		{
			sprite = Sprite.rocks_top;
			return;
		}
		//Middle tile
		else
		{
			sprite = Sprite.rocks_mid;
		}
	}
}
