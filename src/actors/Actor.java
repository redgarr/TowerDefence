package actors;
import java.awt.geom.Point2D;
import java.util.List;

import tiles.Tile;

public interface Actor 
{
	int getX();
	int getY();
	Point2D getCenterpoint();
	void setX(int x);
	void setY(int y);
	void setVelX(int velX);
	void setVelY(int velY);
	int getVelX();
	int getVelY();
	void inflictDamage(double damage);
	double getHealth();
	void render(int[] pixels);
	void tick();
	Tile getCurrentTile();
	void setCurrentTile(Tile tile);
	boolean isMoving();
	void moveActorTo(Tile tile);
	boolean isAlive();
}
