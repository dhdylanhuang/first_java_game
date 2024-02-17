package openworld.item;

import openworld.adventurer.Adventurer;

public class HealingPotion extends ConsumableItem {

    int potionStrength;

    public HealingPotion(String name, Adventurer owner, String itemDescription, int potionStrength, int uses) {
        super(name, owner, itemDescription, uses);
        this.potionStrength = potionStrength;
    }

    @Override
    public void use() {
        owner.setCurrentHealth(owner.getCurrentHealth() + potionStrength);
        uses -= 1;
        System.out.println(name + " was used to heal " + potionStrength + " and now has " + uses + " left.");
    }
}
