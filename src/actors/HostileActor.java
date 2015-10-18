package actors;

import engine.GameController;
import graphics.Sprite;
import routing.PathfindingModule;

public class HostileActor
	extends AbstractActor
{

	public HostileActor(GameController controller, PathfindingModule pathFinder) 
	{
		super(controller, pathFinder);
		setCurrentTile(controller.getTileAt(17,17));
		moveActorTo(controller.getTileAt(17,2));
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		
		
		
	}
	

}
