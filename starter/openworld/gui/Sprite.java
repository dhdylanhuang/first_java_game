package openworld.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import openworld.Coordinates;
import openworld.entityTypes.WorldEntity;

public class Sprite {

    protected WorldEntity worldEntity;
	protected String label;
	protected GameWorld gameWorld;
	
	protected Color color;
	protected int boxSize;

	public Sprite(GameWorld gameWorld, WorldEntity worldEntity) {
		this.gameWorld = gameWorld;
        this.worldEntity = worldEntity;
		setGUIProperties();
	}
	
	protected void setGUIProperties() {
        switch (worldEntity.getClass().getSimpleName()) {
            case "Grasslands":
            this.color = Color.green.darker().darker();
            this.boxSize = GameWorld.SQUARE_HEIGHT;
            break;

            case "Mountain":
            this.color = Color.darkGray;
            this.boxSize = GameWorld.SQUARE_HEIGHT;
            break;

            case "Volcano":
            this.color = Color.red.darker().darker().darker();
            this.boxSize = GameWorld.SQUARE_HEIGHT;
            break;

            case "Blob":
            this.color = Color.blue;
            this.boxSize = GameWorld.SQUARE_HEIGHT / 2;
            this.label = worldEntity.getName();
            break;

            case "Skeleton":
            this.color = Color.white.darker();
            this.boxSize = GameWorld.SQUARE_HEIGHT / 3;
            this.label = worldEntity.getName();
            break;

            case "Healer":
            this.color = Color.yellow;
            this.boxSize = GameWorld.SQUARE_HEIGHT / 3;
            this.label = worldEntity.getName();
            break;

            case "Wizard":
            this.color = Color.magenta.darker().darker();
            this.boxSize = GameWorld.SQUARE_HEIGHT / 3;
            this.label = worldEntity.getName();
            break;

            case "Adventurer":
            this.color = Color.green.brighter().brighter();
            this.boxSize = GameWorld.SQUARE_HEIGHT / 2;
            this.label = worldEntity.getName();
            break;

            default:
            System.out.println("Not sure what this is: " + worldEntity.getClass().getSimpleName());
        }
    }
	
	public Coordinates getLocation() {
		return this.worldEntity.getLocation();
	}

    public WorldEntity getWorldEntity() {
        return this.worldEntity;
    }

    protected void drawShape(int realX, int realY, Graphics g) {
		// Draw an outlined rectangle with the correct colour
		g.setColor(this.color);
		g.fillRect(realX, realY, boxSize, boxSize);
		g.setColor(Color.WHITE);
		g.drawRect(realX, realY, boxSize, boxSize);
    }

    public Rectangle getBoundingBox() {
        Coordinates coordinates = worldEntity.getLocation();
        int realX = coordinates.getY() * GameWorld.SQUARE_WIDTH + (GameWorld.SQUARE_WIDTH / 2) - (boxSize / 2);
        int realY = (GameWorld.GRID_SIZE - coordinates.getX() - 1) * GameWorld.SQUARE_HEIGHT + (GameWorld.SQUARE_HEIGHT / 2) - (boxSize / 2);

        return new Rectangle(realX, realY, boxSize, boxSize);
    }

	public void paint(Graphics g) {
        // (0,0) in the bottom corner
        // X axis is north, Y axis is east
        // Really
        Coordinates coordinates = worldEntity.getLocation();
        int realX = coordinates.getY() * GameWorld.SQUARE_WIDTH + (GameWorld.SQUARE_WIDTH / 2) - (boxSize / 2);
        int realY = (GameWorld.GRID_SIZE - coordinates.getX() - 1) * GameWorld.SQUARE_HEIGHT + (GameWorld.SQUARE_HEIGHT / 2) - (boxSize / 2);

        drawShape(realX, realY, g);

        // Name on top
        if (label != null) {
            String labelToPaint = label;
            if (labelToPaint.length() > 4) {
                labelToPaint = labelToPaint.substring(0, 4);
            }
            g.drawString(labelToPaint, realX - 5, realY - 2);
        }
    }

}
