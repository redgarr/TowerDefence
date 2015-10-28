package tower;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import graphics.Sprite;
import tiles.Tile;

public interface Tower extends Tile
{
	public double getDamage();
	public double getAttackSpeed();
	public double getRange();
	public double getCost();
}
