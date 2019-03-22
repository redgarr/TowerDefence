package tiles;

import graphics.Sprite;

public class SpawnTile extends AbstractTile {

    public SpawnTile(int x, int y) {
        super(x, y);
        sprite = Sprite.actor_enemy_1;
    }

    @Override
    public void updateSprite(Tile[] tiles) {
    }

}
