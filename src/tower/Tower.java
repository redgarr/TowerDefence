package tower;

import graphics.Sprite;
import tiles.Tile;

public interface Tower extends Tile
{
	public double getDamage();
	public double getAttackSpeed();
	public double getRange();
	
}
