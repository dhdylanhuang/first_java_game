package openworld.gui;

import openworld.Coordinates;
import openworld.entityTypes.TravellingWorldEntity;

public class TravellingSprite extends Sprite {

    public TravellingSprite(GameWorld gameWorld, TravellingWorldEntity worldEntity) {
        super(gameWorld, worldEntity);
    }

    public void move(Coordinates vector) {
        ((TravellingWorldEntity)worldEntity).move(vector);
    }
}
