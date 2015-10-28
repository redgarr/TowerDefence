package graphics;

public class AnimatedSprite
	extends Sprite
{
	int frames, current;
	Sprite[] spriteFrames;
	long lastTime;
	int frameLength = 100;
	
	public AnimatedSprite(int size, int x, int y, SpriteSheet sheet, int frames) 
	{
		super(size, x, y, sheet);
		
		this.frames = frames;
		this.spriteFrames = new Sprite[frames];
		
		for(int i=0; i<frames; i++)
		{
			spriteFrames[i] = new Sprite(32, x, y+i, sheet);
		}
	} 
	
	public Sprite getSprite()
	{
		if(current == frames-1)
		{
			current = 0;
		}
		else
		{
			if(System.currentTimeMillis() - lastTime > frameLength)
			{
				current++;
				lastTime = System.currentTimeMillis();
			}
		}
		
		return spriteFrames[current];
	}

}
