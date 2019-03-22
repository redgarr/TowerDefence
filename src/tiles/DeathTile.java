package tiles;

import graphics.Sprite;

public class DeathTile extends AbstractTile {

    public DeathTile(int x, int y) {
        super(x, y);
        sprite = Sprite.actor_friendly_1;
    }

    @Override
    public void updateSprite(Tile[] tiles) {

    }
}
