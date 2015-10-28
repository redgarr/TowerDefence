package actors;

import java.awt.Point;

import engine.GameController;
import graphics.Sprite;
import routing.PathfindingModule;

public class HostileActor
	extends AbstractActor
{

	public HostileActor(GameController controller, PathfindingModule pathFinder) 
	{
		super(controller, pathFinder);
		setCurrentTile(controller.getTileAtCoords(new Point(0,8)));
		moveActorTo(controller.getTileAtCoords(new Point(16,0)));
	}
	
	@Override
	public void tick() 
	{
		super.tick();
	}
	

}
