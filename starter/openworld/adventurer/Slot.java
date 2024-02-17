package openworld.adventurer;

import openworld.item.EquippableItem;
import openworld.item.InventorySlotType;


public class Slot {
    InventorySlotType slotType;
    EquippableItem equippedItem=null;

    public InventorySlotType getSlotType() {
        return slotType;
    }


    public EquippableItem getEquippedItem() {
        return equippedItem;
    }

    public void setEquippedItem(EquippableItem equippedItem) {
        this.equippedItem = equippedItem;
    }


    public Slot(InventorySlotType slotType, EquippableItem equippedItem)
    {
        this.slotType=slotType;
        this.equippedItem=equippedItem;
    }
    

}
