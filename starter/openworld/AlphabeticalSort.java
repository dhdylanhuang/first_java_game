package openworld;

import java.util.Comparator;

import openworld.entityTypes.WorldEntity;

public class AlphabeticalSort implements Comparator<WorldEntity>{

    @Override
    public int compare(WorldEntity o1, WorldEntity o2) {
        return o1.getName().compareTo(o2.getName());
    }

}
