package openworld;

import java.util.Comparator;

import openworld.monsters.Monster;

public class LevelSort implements Comparator<Monster>{

    @Override
    public int compare(Monster o1, Monster o2) {
        if(o1.getLevel()-o2.getLevel()!=0)
        {
            return o1.getLevel()-o2.getLevel();
        }
        return new AlphabeticalSort().compare(o1, o2);
    }

}
