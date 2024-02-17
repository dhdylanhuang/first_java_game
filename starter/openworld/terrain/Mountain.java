package openworld.terrain;

import java.util.Random;

import openworld.Coordinates;
import openworld.Damage;
import openworld.DamageType;
import openworld.World;
import openworld.adventurer.Adventurer;
import openworld.entityTypes.TravellingWorldEntity;
import openworld.item.ConsumableItem;
import openworld.item.ElementalSword;
import openworld.item.EquippableItem;
import openworld.item.HealingPotion;
import openworld.item.Item;

public class Mountain extends Terrain {

    private static final Random RAND = new Random();

    public Mountain(String name, Coordinates location, int maxHealth, World world, Damage attack) {
        super(name, location, maxHealth, world, attack);
        explorable = true;
    }

    @Override
    public void encounter(TravellingWorldEntity traveller) {
        traveller.takeDamage(new Damage(10, DamageType.ICE));
    }

    @Override
    public boolean optionalInteraction() {
        return true;
    }

    public void explore(Adventurer adventurer) {
        if (RAND.nextBoolean()) {
            System.out.println("You explored and found something!");
            Item item = null;
            if (RAND.nextBoolean()) {
                item = new HealingPotion("Extra healing potion", adventurer,
                        "A small healing potion found in the mountains", 5, 1);
                adventurer.getInventory().add((ConsumableItem)item);
            } else {
                int element = RAND.nextInt(DamageType.values().length);
                DamageType damageType = DamageType.values()[element];
                item = new ElementalSword("Magic sword (" + damageType + ")", adventurer,
                        "A magical sword with great power", damageType, 50, 50);
                adventurer.getInventory().add((EquippableItem)item);
            }
            System.out.println("Here's what you found: " + item);
        } else {
            System.out.println("You explored but found nothing");
        }
    }

}
