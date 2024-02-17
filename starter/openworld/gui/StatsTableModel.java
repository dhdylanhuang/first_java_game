package openworld.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import openworld.Damage;
import openworld.DamageType;
import openworld.adventurer.Adventurer;
import openworld.item.InventorySlotType;

public class StatsTableModel extends AbstractTableModel {
    private Adventurer adventurer;

    public StatsTableModel(Adventurer adventurer) {
        this.adventurer = adventurer;
    }

    @Override
    public String getColumnName(int col) {
        if (col == 0) {
            return "Stat";
        } else if (col == 1) {
            return "Value";
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int getRowCount() {
        return rowLabels.length;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    private static final String[] rowLabels = new String[] { "Health", "Max health", "Attack 1", "Attack 2", "Attack 3",
            "Fire vuln", "Ice vuln", "Elec vuln", "Phys vuln", "Armour", "Sword" };

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return rowLabels[rowIndex];
        } else {
            List<Damage> attacks = adventurer.getAttacks();
            switch (rowIndex) {
                case 0:
                return adventurer.getCurrentHealth();
                
                case 1:
                return adventurer.getMaxHealth();

                case 2:
                return attacks.get(0);

                case 3:
                return (attacks.size() > 1) ? attacks.get(1) : null;

                case 4:
                return (attacks.size() > 2) ? attacks.get(2) : null;

                case 5:
                return adventurer.getDamageVulnerability(DamageType.FIRE);

                case 6:
                return adventurer.getDamageVulnerability(DamageType.ICE);

                case 7:
                return adventurer.getDamageVulnerability(DamageType.ELECTRIC);

                case 8:
                return adventurer.getDamageVulnerability(DamageType.PHYSICAL);

                case 9:
                return adventurer.getInventory().getEquipped(InventorySlotType.ARMOUR);

                case 10:
                return adventurer.getInventory().getEquipped(InventorySlotType.SWORD);

                default:
                return null;
            }
        }
    }

}