package openworld.item;

import openworld.adventurer.Adventurer;

public abstract class ConsumableItem extends Item{

    protected int uses;

    public ConsumableItem(String name, Adventurer owner, String itemDescription, int uses) {
        super(name, owner, itemDescription);
        this.uses=uses;
    }

    public abstract void use();

    public int getUses() {
        return uses;
    }

    public String toString() {
        return name + " [" + uses + "]";
    }

  
    
}
