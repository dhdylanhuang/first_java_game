package openworld.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import openworld.Coordinates;
import openworld.characters.Healer;
import openworld.characters.Wizard;
import openworld.entityTypes.WorldEntity;
import openworld.terrain.Mountain;
import openworld.terrain.Terrain;

public class MovementAdapter extends KeyAdapter {

    private GameWorld gameWorld;
    private GameWindow gameWindow;


    public MovementAdapter(GameWorld gameWorld, GameWindow gameWindow) {
        this.gameWorld = gameWorld;
        this.gameWindow = gameWindow;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                moveAdventurer(Coordinates.NORTH_VECTOR);
                break;

            case KeyEvent.VK_RIGHT:
                moveAdventurer(Coordinates.EAST_VECTOR);
                break;

            case KeyEvent.VK_DOWN:
                moveAdventurer(Coordinates.SOUTH_VECTOR);
                break;

            case KeyEvent.VK_LEFT:
                moveAdventurer(Coordinates.WEST_VECTOR);
                break;
        }
    }

    public void moveAdventurer(Coordinates vector) {
        if (!gameWorld.getAdventurer().isConscious() ) {
            return;
        }

        Coordinates adventurerLocation = gameWorld.getAdventurer().getLocation();
        Coordinates newLocation = new Coordinates(
                adventurerLocation.getX() + vector.getX(),
                adventurerLocation.getY() + vector.getY()
        );
        if (isValidMove(newLocation)) {
            gameWorld.getAdventurer().move(vector);
        }
        
        List<Terrain> terrain = gameWorld.getWorld().getTerrainHere(gameWorld.getAdventurer().getLocation());
        if (terrain.get(0) instanceof Mountain) {
            Mountain mountain = (Mountain) terrain.get(0);
 
            MountainDialog dlg = new MountainDialog(gameWindow, mountain);
            dlg.setVisible(true);

            if (dlg.getChoice() != null) {
                mountain.explore(gameWorld.getAdventurer());
            }
       }
        gameWindow.getControlPanel().update();
    }

    private boolean isValidMove(Coordinates newLocation) {
        int xDimension = gameWorld.getxDimension();
        int yDimension = gameWorld.getyDimension();
    
        return newLocation.getX() >= 0 && newLocation.getX() <= xDimension
                && newLocation.getY() >= 0 && newLocation.getY() <= yDimension;
    }
}
