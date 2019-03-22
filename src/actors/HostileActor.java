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

		setCurrentTile(controller.getTileAtCoords(controller.getSpawnTile().getCoordinates()));
		moveActorTo(controller.getTileAtCoords(controller.getDeathTile().getCoordinates()));
	}
	
	@Override
	public void tick() 
	{
		super.tick();
	}
	

}
