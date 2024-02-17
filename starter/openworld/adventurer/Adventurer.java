package openworld.adventurer;

import java.util.ArrayList;
import java.util.Scanner;

import openworld.Coordinates;
import openworld.Damage;
import openworld.DamageType;
import openworld.World;
import openworld.characters.Healer;
import openworld.characters.Wizard;
import openworld.entityTypes.TravellingWorldEntity;
import openworld.entityTypes.WorldEntity;
import openworld.gui.GameWindow;
import openworld.item.ElementalSword;
import openworld.item.HealingPotion;
import openworld.item.SimpleArmour;
import openworld.terrain.Mountain;

public class Adventurer extends TravellingWorldEntity {

    private int attackCapacity=3;
    private ArrayList<Damage> attacks = new ArrayList<>(attackCapacity);
    private Inventory inventory;

    public Adventurer(String name, Coordinates location, int maxHealth, World world, Damage attack) {
        super(name, location, maxHealth, world, attack);
        attacks.add(attack);
        inventory = new Inventory();
        inventory.add(new HealingPotion("Starter healing potion", this, "Single use heal for 20 points", 20, 5));
        inventory.add(new ElementalSword("Fiery Sword",this, "A flaming sword that gives you an extra attack and changes your vulnerability to fire", DamageType.FIRE, 30, 20));
        inventory.add(new ElementalSword("Icy Sword",this, "An icy that gives you an extra attack and changes your vulnerability to ice", DamageType.ICE, 30, 20));
        inventory.add(new ElementalSword("Electric Sword",this, "An electric sword that gives you an extra attack and changes your vulnerability to electricity", DamageType.ELECTRIC, 30, 20));
        inventory.add(new ElementalSword("Mighty Sword",this, "A mighty sword that gives you an extra attack and changes your physical vulnerability", DamageType.PHYSICAL, 30, 20));
        inventory.add(new SimpleArmour("Simple leather armour",this, "Basic physical protection of 20",20));
    }

    public void addAttack(Damage attack) {
        if (attacks.size()<attackCapacity) {
            attacks.add(attack);
        }
    }

     public void removeAttack(Damage damage) {
        attacks.remove(damage);
    }

    public void attack(WorldEntity target) {
        int attackCount=1;
        for (Damage attack:attacks)
        {
            System.out.println("Adventurer "+ name + "makes attack "+attackCount +" out of "+attacks.size());
            target.takeDamage(attack);
            attackCount++;
        }
    }

    @Override
    public void takeDamage(Damage damage) {
        super.takeDamage(damage);
        if (!isConscious()) {
            GameWindow.getInstance().gameOver();
        }
    }

    public ArrayList<Damage> getAttacks() {
        return attacks;
    }

    public Inventory getInventory() {
        return inventory;
    }
    
    @Override
    public String toString()
    {
        return super.toString()+inventory.toString();
    }

    public void takeTurn() {
        ArrayList options = location.findAllSafeMoves(world);
        if (!world.getLocationOptions().isEmpty()) {
            for (Object option : world.getLocationOptions()) {
                options.add(option);
            }
        }
        printOptions(options);
        Scanner userInput = new Scanner(System.in);
        while (!userInput.hasNext());
        try {
            int selection = Integer.parseInt(userInput.nextLine()) - 1;
            if (selection < options.size())
            {
                resolveTurn(options.get(selection));
            }
            else 
            {
                inventory.resolveAction(selection-options.size());
            }
        } catch (NumberFormatException e) {
            System.out.println("Can't make a number " + e.getMessage());
            takeTurn();
        }
        catch (IndexOutOfBoundsException e)
        {
             System.out.println("Not an option"+ e.getMessage());
             takeTurn();
        }
    }

    private void resolveTurn(Object option) {

        System.out.println("Resolving adventurer turn");
        if (option instanceof Coordinates) {
            option = (Coordinates) option;
            if (option.equals(Coordinates.NORTH_VECTOR)) {
                move(Coordinates.NORTH_VECTOR);
            } else if (option.equals(Coordinates.EAST_VECTOR)) {
                move(Coordinates.EAST_VECTOR);
            } else if (option.equals(Coordinates.SOUTH_VECTOR)) {
                move(Coordinates.SOUTH_VECTOR);
            } else if (option.equals(Coordinates.WEST_VECTOR)) {
                move(Coordinates.WEST_VECTOR);
            }
        } else if (option instanceof Healer) {
            ((Healer) option).encounter(this);
        } else if (option instanceof Wizard) {
            ((Wizard) option).encounter(this);
        } else if (option instanceof Mountain) {
            ((Mountain) option).explore(this);
        } else {
            System.out.println("Unrecogised option");
        }
    }

    public void printOptions(ArrayList options) {
        int choice = 1;
        System.out.println("-------------------------------");
        System.out.println("WORLD OPTIONS");
        for (Object option : options) {
            if (option instanceof Coordinates) {
                option = (Coordinates) option;
                if (option.equals(Coordinates.NORTH_VECTOR)) {
                    System.out.println(choice + ": Move North");
                    choice++;
                } else if (option.equals(Coordinates.EAST_VECTOR)) {
                    System.out.println(choice + ": Move East");
                    choice++;
                } else if (option.equals(Coordinates.SOUTH_VECTOR)) {
                    System.out.println(choice + ": Move South");
                    choice++;
                } else if (option.equals(Coordinates.WEST_VECTOR)) {
                    System.out.println(choice + ": Move West");
                    choice++;
                }
            } else if (option instanceof Healer) {
                System.out.println(choice + ": talk to" + ((Healer) option).getName());
                choice++;
            } else if (option instanceof Wizard) {
                System.out.println(choice + ": Talk to " + ((Wizard) option).getName());
                choice++;
            } else if (option instanceof Mountain) {
                System.out.println(choice + ": Explore " + ((Mountain) option).getName());
                choice++;
            } else {
                System.out.println(choice + " is not a recognised option");
                choice++;
            }
        }
        inventory.printActions(choice);
    }

   

}