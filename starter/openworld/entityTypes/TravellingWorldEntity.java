package openworld.entityTypes;

import openworld.Coordinates;
import openworld.Damage;
import openworld.World;

public abstract class TravellingWorldEntity extends WorldEntity{

    public TravellingWorldEntity(String name, Coordinates location, int maxHealth, World world, Damage attack) {
        super(name, location, maxHealth, world, attack);
    }

    public abstract void takeTurn();


    public void move(Coordinates vector)
    {
        System.out.println(name+" moves");
        location.addCoordinates(vector);
        world.resolveMove(this);
    }
    
}
