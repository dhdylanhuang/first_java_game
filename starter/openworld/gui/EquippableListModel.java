package openworld.gui;

import javax.swing.AbstractListModel;

import openworld.adventurer.Inventory;
import openworld.item.EquippableItem;
import openworld.item.InventorySlotType;

public class EquippableListModel extends AbstractListModel<EquippableItem> {

    private InventorySlotType type;
    private Inventory inventory;

    public EquippableListModel(Inventory inventory, InventorySlotType type) {
        this.inventory = inventory;
        this.type = type;
    }

    @Override
    public int getSize() {
        return inventory.countItemsForSlot(type);
    }

    @Override
    public EquippableItem getElementAt(int index) {
        return inventory.getItemForSlot(type, index);
    }

    public void update() {
        fireContentsChanged(this, 0, getSize());
    }

}
