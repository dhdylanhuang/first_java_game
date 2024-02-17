package openworld.gui;

import javax.swing.AbstractListModel;

import openworld.adventurer.Inventory;
import openworld.item.ConsumableItem;

public class ConsumableListModel extends AbstractListModel<ConsumableItem> {

    private Inventory inventory;

    public ConsumableListModel(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getSize() {
        return inventory.getConsumables().size();
     }

    @Override
    public ConsumableItem getElementAt(int index) {
        return inventory.getConsumables().get(index);
    }
    
    public void update() {
        fireContentsChanged(this, 0, inventory.getConsumables().size());
    }
}
