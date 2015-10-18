package graphics;
import java.awt.Graphics;

public class Sprite 
{
	public final int SIZE;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	//Rocks
	public static Sprite rocks_top = new 			Sprite(32, 0, 0, SpriteSheet.tiles);
	public static Sprite rocks_mid = new 			Sprite(32, 1, 0, SpriteSheet.tiles);
	public static Sprite rocks_bot = new 			Sprite(32, 2, 0, SpriteSheet.tiles);
	public static Sprite rocks_topleft = new 		Sprite(32, 3, 0, SpriteSheet.tiles);
	public static Sprite rocks_topright = new 		Sprite(32, 4, 0, SpriteSheet.tiles);
	public static Sprite rocks_botleft = new 		Sprite(32, 5, 0, SpriteSheet.tiles);
	public static Sprite rocks_botright = new 		Sprite(32, 6, 0, SpriteSheet.tiles);
	public static Sprite rocks_left = new 			Sprite(32, 7, 0, SpriteSheet.tiles);
	public static Sprite rocks_right = new 			Sprite(32, 8, 0, SpriteSheet.tiles);
	public static Sprite rocks_topleftcorner = new 	Sprite(32, 10, 0, SpriteSheet.tiles);
	public static Sprite rocks_toprightcorner = new Sprite(32, 11, 0, SpriteSheet.tiles);
	public static Sprite rocks_botrightcorner = new Sprite(32, 12, 0, SpriteSheet.tiles);
	public static Sprite rocks_botleftcorner = new 	Sprite(32, 13, 0, SpriteSheet.tiles);
	public static Sprite tower_simple = new Sprite(32, 0, 0, SpriteSheet.towers);
	//Floor
	public static Sprite floor = new Sprite(32, 9, 0, SpriteSheet.tiles);
	//Actors
	public static Sprite actor_enemy_1 = new Sprite(32, 14, 0, SpriteSheet.tiles);
	public static Sprite actor_friendly_1 = new Sprite(32, 0, 0, SpriteSheet.actors);
	
	public Sprite(int size, int x, int y, SpriteSheet sheet)
	{
		SIZE = size;
		pixels = new int[SIZE*SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	private void load()
	{
		for(int y = 0; y < SIZE; y++)
		{
			for(int x=0; x < SIZE; x++)
			{
				pixels[x+y*SIZE] = sheet.pixels[(x+this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
	
	public static AnimatedSprite getFriendlyActor1Up()
	{
		return new AnimatedSprite(32, 0, 0, SpriteSheet.actors, 5);
	}

	public static AnimatedSprite getFriendlyActor1Down() 
	{
		return new AnimatedSprite(32, 1, 0, SpriteSheet.actors, 5);
	}
	
	public static AnimatedSprite getFriendlyActor1Left() 
	{
		return new AnimatedSprite(32, 3, 0, SpriteSheet.actors, 5);
	}
	
	public static AnimatedSprite getFriendlyActor1Right() 
	{
		return new AnimatedSprite(32, 2, 0, SpriteSheet.actors, 5);
	}

}