package openworld.item;

import openworld.adventurer.Adventurer;

public abstract class Item {
    protected String name;
    protected Adventurer owner;
    protected String itemDescription;

    public Item (String name, Adventurer owner, String itemDescription)
    {
        this.name=name;
        this.owner=owner;
        this.itemDescription=itemDescription;
    }




    public String getDescription()
    {
        return itemDescription;
    }




    public String getName() {
        return name;
    }




    public Adventurer getOwner() {
        return owner;
    }




    public String getItemDescription() {
        return itemDescription;
    }

    @Override
    public String toString() {
        return name;
    }
    

}
