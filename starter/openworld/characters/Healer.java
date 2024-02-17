package openworld.characters;

import openworld.Coordinates;
import openworld.Damage;
import openworld.World;
import openworld.adventurer.Adventurer;
import openworld.entityTypes.TravellingWorldEntity;

import openworld.monsters.Monster;

public class Healer extends NPC {
    
    public Healer(String name, Coordinates location, int maxHealth, World world, Damage attack, Coordinates goal) {
        super(name, location, maxHealth, world, attack, goal);
    }

    public void encounter(TravellingWorldEntity traveller)
    {
        super.encounter(traveller);
        if(traveller instanceof Adventurer)
        {
            traveller.setCurrentHealth(traveller.getMaxHealth());
            System.out.println("Healed the adventurer");
        }
        else if(traveller instanceof Monster)
        {
            world.battle(this,traveller);
        }
    }
}
