package openworld.monsters;

import openworld.Coordinates;
import openworld.Damage;
import openworld.DamageType;
import openworld.World;
import openworld.entityTypes.TravellingWorldEntity;


public class Blob extends Monster{
    public Blob(String name, Coordinates location, int maxHealth, World world, Damage attack, int speed) {
        super(name, location, maxHealth, world, attack, speed);
    }

    @Override
    public void encounter(TravellingWorldEntity traveller)
    {
        if (traveller instanceof Blob)
        {
            merge((Blob)traveller);
        }
        else
        {
            super.encounter(traveller);
        }
    }

    public void merge(Blob encounter)
    {
        maxHealth+=encounter.getMaxHealth();
        currentHealth+=encounter.getCurrentHealth();
        gainXp(encounter.getXp());
        encounter.takeDamage(new Damage(encounter.getMaxHealth(),DamageType.PHYSICAL));
    }
}
