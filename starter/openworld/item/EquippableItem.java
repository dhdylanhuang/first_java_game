package openworld.item;

import openworld.adventurer.Adventurer;

public abstract class EquippableItem extends Item{

    InventorySlotType slot;
    public EquippableItem(String name, Adventurer owner, String itemDescription, InventorySlotType slot) {
        super(name, owner, itemDescription);
        this.slot=slot;
    }
 
    public abstract void equip();

    public abstract void unequip();

    public InventorySlotType getSlotType() {
        return slot;
    }

    public void setSlot(InventorySlotType slot) {
        this.slot = slot;
    }
    
}
