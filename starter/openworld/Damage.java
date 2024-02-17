package openworld;

import openworld.entityTypes.WorldEntity;

public class Damage {
    private int amount;
    private DamageType damageType;
    private WorldEntity source;

    public Damage(int amount, DamageType damageType) {
        this.amount = amount;
        this.damageType = damageType;
       
    }

    public int getAmount() {
        return amount;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public WorldEntity getSource()
    {
        return source;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Damage)
        {
            if(((Damage)obj).getAmount()==amount) 
            {
                if (((Damage)obj).getDamageType().equals(damageType))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return damageType + "(" + amount + ")";
    }


    
}
