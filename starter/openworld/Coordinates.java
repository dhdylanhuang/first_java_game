package openworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Coordinates {
    public final static Coordinates NORTH_VECTOR = new Coordinates(1, 0);
    public final static Coordinates SOUTH_VECTOR = new Coordinates(-1, 0);
    public final static Coordinates EAST_VECTOR = new Coordinates(0, 1);
    public final static Coordinates WEST_VECTOR = new Coordinates(0, -1);
    public final static Coordinates STATIONARY = new Coordinates(0, 0);

    public static final Map<String, Coordinates> COORDS_MAP;
    static {
        COORDS_MAP = new HashMap<>();
        COORDS_MAP.put("NORTH", NORTH_VECTOR);
        COORDS_MAP.put("EAST", EAST_VECTOR);
        COORDS_MAP.put("SOUTH", SOUTH_VECTOR);
        COORDS_MAP.put("WEST", WEST_VECTOR);
    }

    private int x;
    private int y;


    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void addCoordinates(Coordinates vector) {
        x += vector.getX();
        y += vector.getY();
    }


    public Coordinates findSafeMove(World world) {
        if (x + 1 <= world.getxDimension()) {
            return NORTH_VECTOR;
        } else if (y + 1 <= world.getyDimension()) {
            return EAST_VECTOR;
        } else if (x - 1 >= 0) {
            return SOUTH_VECTOR;
        } else if (y - 1 >= 0) {
            return WEST_VECTOR;
        }
        return STATIONARY;
    }

    public ArrayList<Coordinates> findAllSafeMoves(World world) {
        ArrayList<Coordinates>options = new ArrayList<>();
        if (x + 1 <= world.getxDimension()) {
            options.add(NORTH_VECTOR);
        } 
         if (y + 1 <= world.getyDimension()) {
            options.add(EAST_VECTOR);
        } 
         if (x - 1 >= 0) {
            options.add(SOUTH_VECTOR);
        }
         if (y - 1 >= 0) {
            options.add(WEST_VECTOR);
        }
        return options;
    }


    public Coordinates getNextStepTo(Coordinates destination) {
        int xDiff = destination.getX() - x;
        int yDiff = destination.getY() - y;
        if (xDiff == 0 && yDiff == 0) {
            return STATIONARY;
        }
        if (Math.abs(xDiff) >= Math.abs(yDiff)) {
            if (xDiff > 0) {
                return NORTH_VECTOR;
            } else {
                return SOUTH_VECTOR;
            }
        } else {
            if (yDiff > 0) {
                return EAST_VECTOR;
            } else {
                return WEST_VECTOR;
            }
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates check = (Coordinates) obj;
        if (x == check.getX() && y == check.getY()) {
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString()
    {
        return "Coord("+x+","+y+")";
    }
}
