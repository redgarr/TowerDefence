package actors;

public class AbstractLevel 
{

	private Actor[] actors;
	private int index;
	
	public AbstractLevel(Actor... actors)
	{
		this.actors = actors;
	}
	
	public Actor getNext()
	{
		Actor retVal = actors[index];
		index++;
		return retVal;
	}
}
