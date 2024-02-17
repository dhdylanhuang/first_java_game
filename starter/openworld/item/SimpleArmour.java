package openworld.item;

import openworld.DamageType;
import openworld.adventurer.Adventurer;

public class SimpleArmour extends EquippableItem{

    private int protection;

    public SimpleArmour(String name, Adventurer owner, String itemDescription, int protection) {
        super(name, owner, itemDescription, InventorySlotType.ARMOUR);
        this.protection=protection;
    }

    @Override
    public void equip() {
        owner.setDamageVulnerability(DamageType.PHYSICAL, owner.getDamageVulnerability(DamageType.PHYSICAL)-protection);
        System.out.println("Equipped "+ name + " "+ itemDescription+" new vulnerability:"+owner.getDamageVulnerability(DamageType.PHYSICAL) );
    }

    @Override
    public void unequip() {
        owner.setDamageVulnerability(DamageType.PHYSICAL, owner.getDamageVulnerability(DamageType.PHYSICAL)+protection);
        System.out.println("Unequipped "+ name + " "+ itemDescription);
    }
    
}
