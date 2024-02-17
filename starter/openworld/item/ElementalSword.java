package openworld.item;

import openworld.Damage;
import openworld.DamageType;
import openworld.adventurer.Adventurer;

public class ElementalSword extends EquippableItem {

    private DamageType element;
    private int attackBuff, defenseBuff;

    public ElementalSword(String name, Adventurer owner, String itemDescription, DamageType element, int attackBuff, int defenseBuff) {
        super(name, owner, itemDescription, InventorySlotType.SWORD);
        this.element = element;
        this.attackBuff = attackBuff;
        this.defenseBuff = defenseBuff;
    }

    @Override
    public void equip() {
        owner.addAttack(new Damage(attackBuff, element));
        owner.setDamageVulnerability(element, owner.getDamageVulnerability(element)-defenseBuff);

    }

    @Override
    public void unequip() {
        owner.removeAttack(new Damage(attackBuff, element));
        owner.setDamageVulnerability(element, owner.getDamageVulnerability(element)+defenseBuff);
    }
}
