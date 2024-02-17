package openworld.adventurer;

import java.util.ArrayList;
import java.util.List;

import openworld.item.ConsumableItem;
import openworld.item.EquippableItem;
import openworld.item.InventorySlotType;

public class Inventory {

    private ArrayList<EquippableItem> slots;
    private ArrayList<ConsumableItem> consumables = new ArrayList<>();
    private ArrayList<EquippableItem> equipableItems = new ArrayList<>();

    public Inventory() {
        slots = new ArrayList<EquippableItem>(InventorySlotType.values().length);
        for (int i = 0; i < InventorySlotType.values().length; i++) {
            slots.add(null);
        }
        consumables = new ArrayList<ConsumableItem>();
    }

    public EquippableItem getEquipped(InventorySlotType toFind) {
        return slots.get(toFind.getPosition());
    }

    public void equipItem(EquippableItem item) {
        EquippableItem previousItem = slots.get(item.getSlotType().getPosition());
        if (previousItem != null) {
            previousItem.unequip();
            equipableItems.add(previousItem);
        }
        slots.set(item.getSlotType().getPosition(), item);
        item.equip();
        equipableItems.remove(item);
    }

    public void useItem(int index) {
        consumables.get(index).use();
        if (consumables.get(index).getUses() == 0) {
            consumables.remove(index);
        }
    }

    public void printActions(int startingIndex) {
        System.out.println("INVENTORY OPTIONS");
        for (ConsumableItem consumable : consumables) {
            System.out.println(startingIndex + ": " + consumable.getDescription());
            startingIndex++;
        }
        System.out.println("EQUIPABLE ITEMS");
        for (EquippableItem equipables : equipableItems) {
            System.out.println(startingIndex + ": " + equipables.getDescription());
            startingIndex++;
        }
    }

    public void resolveAction(int selection) {
        if (selection < consumables.size()) {
            useItem(selection);
        } else {
            equipItem(equipableItems.get(selection - consumables.size()));
        }
    }

    public void add(EquippableItem item) {
        equipableItems.add(item);
    }

    public void add(ConsumableItem item) {
        consumables.add(item);
    }

    public List<ConsumableItem> getConsumables() {
        return consumables;
    }

    public String toString() {
        String description = "";
        if (slots.get(InventorySlotType.ARMOUR.getPosition()) != null) {
            description += " Armour:" + slots.get(InventorySlotType.ARMOUR.getPosition()).getName();
        } else {
            description += "Armour: none equipped";
        }
        if (slots.get(InventorySlotType.SWORD.getPosition()) != null) {
            description += " Sword:" + slots.get(InventorySlotType.SWORD.getPosition()).getName();
        } else {
            description += " Sword: none equipped";
        }

        for (ConsumableItem consumable : consumables) {
            description += "(" + consumable.getName() + ":" + consumable.getUses() + ")";
        }
        for (EquippableItem equipables : equipableItems) {
            description += "(" + equipables.getName() + ")";
        }
        return description;
    }

    public int countItemsForSlot(InventorySlotType type) {
        return (int) equipableItems.stream().filter(i -> i.getSlotType() == type).count();
    }

    public EquippableItem getItemForSlot(InventorySlotType type, int index) {
        return (EquippableItem) equipableItems.stream().filter(i -> i.getSlotType() == type).toArray()[index];
    }

}
