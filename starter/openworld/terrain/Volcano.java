package openworld.terrain;

import openworld.Coordinates;
import openworld.Damage;
import openworld.World;
import openworld.entityTypes.TravellingWorldEntity;


public class Volcano extends Terrain {

    public Volcano(String name, Coordinates location, int maxHealth, World world, Damage attack) {
        super(name, location, maxHealth, world, attack);
    }


    @Override
    public void encounter(TravellingWorldEntity traveller)
    {
        TravellingWorldEntity t=(TravellingWorldEntity)traveller;
        Coordinates c = t.getLocation().findSafeMove(world);
        t.move(c);
    }

    
}
