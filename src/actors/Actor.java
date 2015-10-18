package actors;
import java.awt.geom.Point2D;
import java.util.List;

import tiles.Tile;

public interface Actor 
{
	public int getX();
	public int getY();
	public Point2D getCenterpoint();
	public void setX(int x);
	public void setY(int y);
	public void setVelX(int velX);
	public void setVelY(int velY);
	public int getVelX();
	public int getVelY();
	public void inflictDamage(double damage);
	public double getHealth();
	public void render(int[] pixels);
	public void tick();
	public Tile getCurrentTile();
	public void setCurrentTile(Tile tile);
	public boolean isMoving();
	void moveActorTo(Tile tile);
	boolean isAlive();
}
