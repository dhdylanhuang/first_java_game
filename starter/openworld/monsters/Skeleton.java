package openworld.monsters;

import openworld.Coordinates;
import openworld.Damage;
import openworld.World;
import openworld.entityTypes.TravellingWorldEntity;

public class Skeleton extends Monster{

    public Skeleton(String name, Coordinates location, int maxHealth, World world, Damage attack, int speed) {
        super(name, location, maxHealth, world, attack, speed);
    }

    @Override
    public void encounter(TravellingWorldEntity traveller)
    {
        awake=true;
        super.encounter(traveller);
    }
}
