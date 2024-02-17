package openworld.terrain;

import openworld.Coordinates;
import openworld.Damage;
import openworld.World;
import openworld.entityTypes.WorldEntity;

public abstract class Terrain extends WorldEntity{

    public boolean explorable=false;

    public Terrain(String name, Coordinates location, int maxHealth, World world, Damage attack) {
        super(name, location, maxHealth, world, attack);
    }

    public boolean isExplorable()
    {
        return explorable;
    }

    public void attack(WorldEntity traveller) {
        
    }


}
