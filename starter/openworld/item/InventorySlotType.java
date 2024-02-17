package openworld.item;

public enum InventorySlotType {
    ARMOUR(0),
    SWORD(1);

    InventorySlotType(int position)
    {
        this.position=position;
    }

    private final int position;

    public int getPosition()
    {
        return position;
    }
}
