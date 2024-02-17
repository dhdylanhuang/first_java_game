package openworld.characters;

import openworld.Coordinates;
import openworld.Damage;
import openworld.DamageType;
import openworld.World;
import openworld.adventurer.Adventurer;
import openworld.entityTypes.TravellingWorldEntity;
import openworld.monsters.Monster;

public class Wizard extends NPC{

    public Wizard(String name, Coordinates location, int maxHealth, World world, Damage attack, Coordinates goal) {
        super(name, location, maxHealth, world, attack, goal);
    }

    public void encounter(TravellingWorldEntity traveller)
    {
        super.encounter(traveller);
        if(traveller instanceof Adventurer)
        {
            Adventurer adventurer = (Adventurer)traveller;
            adventurer.addAttack(new Damage(10, DamageType.FIRE)); 
        }
        else if(traveller instanceof Monster)
        {
            world.battle(this,traveller);
        }
    }  
}
