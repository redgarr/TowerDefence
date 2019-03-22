package tower;

import engine.GameController;
import graphics.Sprite;

public class SimpleTower extends AbstractTower 
{

	public SimpleTower(int x, int y, GameController controller) 
	{
		super(25, 500, 100, 100, x, y, controller); //damage, attackspeed, range
		sprite = Sprite.tower_simple;
	}

}
