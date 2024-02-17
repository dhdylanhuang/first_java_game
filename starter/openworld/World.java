package openworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import openworld.adventurer.Adventurer;
import openworld.characters.Healer;
import openworld.characters.NPC;
import openworld.characters.Wizard;
import openworld.music.BackgroundMusic;

import openworld.terrain.Grasslands;
import openworld.terrain.Mountain;
import openworld.terrain.Terrain;
import openworld.terrain.Volcano;
import openworld.entityTypes.TravellingWorldEntity;
import openworld.entityTypes.WorldEntity;
import openworld.monsters.Blob;
import openworld.monsters.Monster;
import openworld.monsters.Skeleton;

public class World {

    private int xDimension;
    private int yDimension;
    private ArrayList<Terrain> terrain = new ArrayList<>();
    private ArrayList<Monster> monsters = new ArrayList<>();

    private ArrayList<NPC> nonPlayerCharacters = new ArrayList<>();
    private Adventurer adventurer;
    public boolean gameOver = false;

    public World(int xDimension, int yDimension) {
        this.xDimension = xDimension;
        this.yDimension = yDimension;
    }

    public void run() {
        int turnTimer = 1;
        while (!gameOver) {
            System.out.println("-------------------------------");
            System.out.println("TURN:" + turnTimer);
            System.out.println("-------------------------------");
            printWorld();
            adventurer.takeTurn();
            nonPlayerCharactersMove();
            monsterMove();
            System.out.println("Adventurer:"+ adventurer.toString());
            turnTimer++;
        }
        System.out.println("Game Over!");
    }

    public ArrayList<WorldEntity> getLocationOptions() {
        ArrayList<WorldEntity> options = new ArrayList<>();
        for (Terrain t : getTerrainHere(adventurer.getLocation())) {
            if (t.isExplorable()) {
                options.add(t);
            }
        }
        for (NPC npc : getNPCHere(adventurer.getLocation())) {
            if (npc.isConscious()) {
                options.add(npc);
            }
        }
        return options;
    }

    public void initaliseWorld(boolean firstTime) {
        generateTerrain();
        generateMonsters();
        generateCharacters();
        if (firstTime) {
            adventurer = new Adventurer("Bob", new Coordinates(0, 0), 100, this, new Damage(10, DamageType.PHYSICAL));
        }
    }

    public void printWorld() {

        String[][] boxes = new String[xDimension + 1][yDimension + 1];
        for (int x = 0; x <= xDimension; x++) {
            for (int y = 0; y <= yDimension; y++) {
                Coordinates currentLocation = new Coordinates(x, y);
                ArrayList<String> entities = new ArrayList<>();
                if (adventurer.getLocation().equals(currentLocation)) {
                    entities.add("*ADV*");
                }
                for (Terrain t : terrain) {
                    if (t.getLocation().equals(currentLocation)) {
                        entities.add(getShortenedName(t.getName()));
                    }
                }
                for (Monster m : monsters) {
                    if (m.getLocation().equals(currentLocation)) {
                        if (m.isAwake()) {
                            entities.add(getShortenedName(m.getName()).toUpperCase());
                        } else {
                            entities.add(getShortenedName(m.getName()));
                        }
                    }
                }
                for (NPC npc : nonPlayerCharacters) {
                    if (npc.getLocation().equals(currentLocation)) {
                        if (npc.getLocation().equals(npc.getGoal())) {
                            entities.add(getShortenedName(npc.getName()));
                        } else {
                            entities.add(getShortenedName(npc.getName()).toUpperCase());
                        }
                    }
                }
                if (!entities.isEmpty()) {
                    String entityString = String.join(",", entities);
                    boxes[x][y] = entityString;
                }
            }
        }
        printTable(boxes);
    }

    public static void printTable(String[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            System.out.println("Empty grid.");
            return;
        }
        int numRows = grid.length;
        int numCols = grid[0].length;
        int maxCellWidth = 0;
        int maxCellHeight = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int cellWidth = grid[row][col].length();
                if (cellWidth > maxCellWidth) {
                    maxCellWidth = cellWidth;
                }
            }
            int cellHeight = 1;
            if (cellHeight > maxCellHeight) {
                maxCellHeight = cellHeight;
            }
        }
        int fixedColumnWidth = maxCellWidth + 2;
        for (int row = numRows - 1; row > -1; row--) {
            for (int col = 0; col < numCols; col++) {
                String cell = grid[row][col];
                System.out.printf("%-" + fixedColumnWidth + "s", cell);
            }
            System.out.println();
        }
    }

    private String getShortenedName(String fullName) {
        String shortenedName = fullName.substring(0, Math.min(fullName.length(), 3));
        char lastChar = fullName.charAt(fullName.length() - 1);
        return shortenedName + lastChar;
    }

    public void monsterMove() {
        Collections.sort(monsters, new LevelSort());
        for (Monster monster : monsters) {
            monster.takeTurn();
        }
    }

    public void nonPlayerCharactersMove() {
        Collections.sort(nonPlayerCharacters, new AlphabeticalSort());
        for (NPC npc : nonPlayerCharacters) {
            npc.takeTurn();
        }
    }

    public void resolveMove(TravellingWorldEntity traveller) {
        List<Terrain> terainHere = getTerrainHere(traveller.getLocation());
        for (Terrain t : terainHere) {
            if (t instanceof Grasslands) {
                t = (Grasslands) t;
                t.encounter(traveller);
            }
            else if(t instanceof Mountain)
            {
                ((Mountain)t).encounter(traveller);
            }
            else if(t instanceof Volcano)
            {
                ((Volcano)t).encounter(traveller);
            }
        }
        List<Monster> monstersHere = getMonstersHere(traveller.getLocation());
        for (Monster m : monstersHere) {
            if (m.equals(traveller)) {

            } else if (m instanceof Skeleton) {
                m = (Skeleton) m;
                m.encounter(traveller);
            } else if (m instanceof Blob) {
                m = (Blob) m;
                m.encounter(traveller);
            }
        }
        List<NPC> charactersHere = getNPCHere(traveller.getLocation());
        for (NPC npc : charactersHere) {
            if (npc.equals(traveller)) {

            } else if (npc instanceof Wizard) {
                npc = (Wizard) npc;
                npc.encounter(traveller);
            } else if (npc instanceof Healer) {
                npc = (Healer) npc;
                npc.encounter(traveller);
            }
        }
    }

    private List<NPC> getNPCHere(Coordinates location) {
        ArrayList<NPC> npcs = new ArrayList<>();
        for (NPC npc : nonPlayerCharacters) {
            if (npc.getLocation().equals(location)) {
                npcs.add(npc);
            }
        }
        return npcs;
    }

    private List<Monster> getMonstersHere(Coordinates location) {
        ArrayList<Monster> monstersHere = new ArrayList<>();
        for (Monster m : monsters) {
            if (m.getLocation().equals(location)) {
                monstersHere.add(m);
            }
        }
        return monstersHere;
    }

    public List<Terrain> getTerrainHere(Coordinates location) {
        ArrayList<Terrain> terrainHere = new ArrayList<>();
        for (Terrain t : terrain) {
            if (t.getLocation().equals(location)) {
                terrainHere.add(t);
            }
        }
        return terrainHere;
    }

    public void battle(TravellingWorldEntity location, TravellingWorldEntity traveller) {
        System.out.println("----BATTLE-----");
        System.out.println(traveller.getName() + " is fighting " + location.getName());
        System.out.println(location.toString());
        System.out.println(traveller.toString());

        while (location.getCurrentHealth() > 0 && traveller.getCurrentHealth() > 0) {
            location.attack(traveller);
            if (traveller.getCurrentHealth() > 0) {
                traveller.attack(location);
            }
            System.out.println(location.toString());
            System.out.println(traveller.toString());
        }
        if (location.isConscious()) {
            System.out.println(location.getName().toUpperCase() + " IS VICTORIOUS");
        } else if (traveller.isConscious()) {
            System.out.println(traveller.getName().toUpperCase() + " IS VICTORIOUS");
        } else {
            System.out.println("Somehow no one is conscious - this should not happen!");
        }

        System.out.println("----BATTLE OVER-----");
    }

    public static void main(String[] args) {
        World w = new World(7, 7);
        w.initaliseWorld(true);
        w.run();
       
    }

    public void clearWorld() {
        terrain.clear();
        monsters.clear();
        nonPlayerCharacters.clear();
    }

    public void generateTerrain() {
        int count=0;
        for (int x = 0; x <= xDimension; x++) {
            for (int y = 0; y <= yDimension; y++) {
                count++;
                if (count%4==0)
                {
                  terrain.add(new Mountain("Mountain", new Coordinates(x, y), 1000, this,
                        new Damage(0, DamageType.PHYSICAL)));  
                }
                else if (count%13==0)
                {
                    terrain.add(new Volcano("Volcano", new Coordinates(x, y), 1000, this,
                        new Damage(0, DamageType.PHYSICAL)));
                }
                else
                {
                terrain.add(new Grasslands("Grasslands", new Coordinates(x, y), 1000, this,
                        new Damage(0, DamageType.PHYSICAL)));
                }
            }
        }
    }

    public void generateMonsters() {
        int skeletonCount = 0;
        int blobCount = 0;
        int frequency = 7;
        int countUp = 0;
        for (int x = 0; x <= xDimension; x++) {
            for (int y = 0; y <= yDimension; y++) {
                countUp++;
                if (countUp == frequency) {
                    if (skeletonCount <= blobCount) {
                        monsters.add(new Skeleton("Skeleton " + skeletonCount, new Coordinates(x, y), 15, this,
                                new Damage(5, DamageType.PHYSICAL), 3));
                        skeletonCount++;
                        countUp = 0;
                    } else {
                        monsters.add(new Blob("Blob " + blobCount, new Coordinates(x, y), 20, this,
                                new Damage(8, DamageType.PHYSICAL), 4));
                        blobCount++;
                        countUp = 0;
                    }
                }

            }
        }
    }

    public void generateCharacters() {
        int healerCount = 0;
        int wizardCount = 0;
        int frequency = 15;
        int countUp = 0;
        for (int x = 0; x <= xDimension; x++) {
            for (int y = 0; y <= yDimension; y++) {
                countUp++;
                if (countUp == frequency) {
                    if (healerCount <= wizardCount) {
                        nonPlayerCharacters.add(new Healer("Healer " + healerCount, new Coordinates(x, y), 15, this,
                                new Damage(5, DamageType.PHYSICAL), randomCoordinates()));
                        healerCount++;
                        countUp = 0;
                    } else {
                        nonPlayerCharacters.add(new Wizard("Wizard " + wizardCount, new Coordinates(x, y), 20, this,
                                new Damage(8, DamageType.PHYSICAL), randomCoordinates()));
                        wizardCount++;
                        countUp = 0;
                    }
                }
            }
        }
    }

    private Coordinates randomCoordinates() {
        Random rand = new Random();
        return new Coordinates(rand.nextInt(xDimension), rand.nextInt(yDimension));
    }

    public int getxDimension() {
        return xDimension;
    }

    public int getyDimension() {
        return yDimension;
    }

    public Adventurer getAdventurer() {
        return adventurer;
    }

    public void setAdventurer(Adventurer adventurer) {
        this.adventurer = adventurer;
    }

    public ArrayList<Terrain> getTerrain() {
        return terrain;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public ArrayList<NPC> getNonPlayerCharacters() {
        return nonPlayerCharacters;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

}
