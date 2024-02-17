package openworld.entityTypes;


import openworld.Coordinates;
import openworld.Damage;
import openworld.DamageType;
import openworld.World;
import openworld.adventurer.Adventurer;

public abstract class WorldEntity {
    protected String name;
    protected Coordinates location;
    protected int maxHealth;
    protected int currentHealth;
    protected Damage attack;

    protected World world;
    public boolean conscious=true;
    int[] vulnerabilities;

    public WorldEntity(String name, Coordinates location, int maxHealth,  World world, Damage attack) {
        this.name = name;
        this.location = location;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.world = world;
        this.attack = attack;
        vulnerabilities=new int[(DamageType.values().length)];
        for (int i=0;i<vulnerabilities.length;i++)
        {
            vulnerabilities[i]=100;
        }
    }
    
    public boolean optionalInteraction() {
        return false;
    }

    public void takeDamage(Damage damage) {
        
        System.out.println("Taking damage of type " + damage.getDamageType().toString()+" with vulnerability " +getDamageVulnerability(damage.getDamageType()));
        System.out.println("Damage amount before resistance:"+ damage.getAmount());
        int amount = damage.getAmount()*getDamageVulnerability(damage.getDamageType())/100;
        System.out.println("After applying resistance:"+ amount);
        if (amount > 0) {
            this.currentHealth -= amount;
            if (this.currentHealth <= 0) {
                this.currentHealth = 0;
                conscious=false;
            }
        }
    }


    public int getDamageVulnerability(DamageType damageType)
    {
        switch (damageType){
         case FIRE:
            return vulnerabilities[0];
        case ICE: 
            return vulnerabilities[1];
        case ELECTRIC: 
            return vulnerabilities[2];
        case PHYSICAL:
            return vulnerabilities[3];
        }
        return -1;
    }


    public void setDamageVulnerability(DamageType damageType, int newVulnerabilty)
    {
        switch (damageType){
         case FIRE:
            vulnerabilities[0] = newVulnerabilty;
            break;
        case ICE: 
            vulnerabilities[1]=newVulnerabilty;
            break;
        case ELECTRIC: 
             vulnerabilities[2]= newVulnerabilty;
             break;
        case PHYSICAL:
             vulnerabilities[3]= newVulnerabilty;
             break;
        }
    }

    public abstract void attack(WorldEntity traveller); 

    
    public void encounter(TravellingWorldEntity traveller) {
        if (traveller instanceof Adventurer)
        {
            System.out.println("You encounter: " + toString() );
        }
        else
        {
            System.out.println(traveller.getName() +" encounters: " + toString() );
        }
    }

    @Override
    public String toString()
    {
        return name + " at " + location.toString()+ " Max Health: " + maxHealth + " Current Health: " + currentHealth;
    }

    public String getName() {
        return name;
    }

    public Coordinates getLocation() {
        return location;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public Damage getAttack() {
        return attack;
    }

    public World getWorld() {
        return world;
    }

    public boolean isConscious() {
        return conscious;
    }

    public void setCurrentHealth(int health) {
        currentHealth=health;
    }
}
