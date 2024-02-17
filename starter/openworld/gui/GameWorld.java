package openworld.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import openworld.World;
import openworld.adventurer.Adventurer;
import openworld.characters.NPC;
import openworld.monsters.Monster;
import openworld.terrain.Terrain;

public class GameWorld extends JPanel {
	// Dimensions
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public static final int GRID_SIZE = 8;
	public static final int SQUARE_WIDTH = WIDTH / GRID_SIZE;
	public static final int SQUARE_HEIGHT = HEIGHT / GRID_SIZE;

    private World world;

	public final static Random RANDOM = new Random();

	private List<Sprite> sprites;
	private AdventurerSprite adventurerSprite;

	private Thread gameThread;

	public GameWorld(GameWindow window) {
		// GUI properties
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		// World class uses size-1 for its size
        world = new World(GRID_SIZE - 1, GRID_SIZE - 1);
        world.initaliseWorld(true);

		createSprites(true);

		// Do this or tooltips never show up
		setToolTipText("");

		// Keyboard commands
		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(new MovementAdapter(this, window));
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				requestFocusInWindow();
			}
		});

		// Make sure to repaint regularly
		Timer timer = new Timer();
		TimerTask repaintTask = new TimerTask() {
			@Override
			public void run() {
				repaint();
				Toolkit.getDefaultToolkit().sync();
			}
		};
		timer.schedule(repaintTask, 10, 10);
	}

	private void createSprites(boolean firstTime) {
		if (firstTime) {
			adventurerSprite = new AdventurerSprite(this, world.getAdventurer());
		}
        sprites = new ArrayList<>();
        for (Terrain terrain : world.getTerrain()) {
            sprites.add(new Sprite(this, terrain));
        }
        for (Monster monster : world.getMonsters()) {
            sprites.add(new TravellingSprite(this, monster));
        }
        for (NPC npc : world.getNonPlayerCharacters()) {
            sprites.add(new TravellingSprite(this, npc));
        }
	}

	public Adventurer getAdventurer() {
		return world.getAdventurer();
	}

	public ArrayList<Monster> getMonster() {
		return world.getMonsters();
    }

	public ArrayList<NPC> getNonPlayerCharacters() {
        return world.getNonPlayerCharacters();
    }

	public World getWorld() {
		return world;
	}

	public void respawnWorld() {
		sprites.clear();
		world.clearWorld();
		world.initaliseWorld(false);
		createSprites(false);
		repaint();
	}

	public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }

	public Thread getGameThread() {
        return gameThread;
    }

	@Override
	protected void paintComponent(Graphics g) {
		// Draw the background first
		super.paintComponent(g);

		// Draw grid lines (I think this may still be buggy but it works on square grids
		// so I'll live with it)
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < (GRID_SIZE - 1); i++) {
			int yPos = (i + 1) * SQUARE_WIDTH;
			int xPos = (i + 1) * SQUARE_HEIGHT;
			g.drawLine(xPos, 0, xPos, WIDTH);
			g.drawLine(0, yPos, HEIGHT, yPos);
		}

		for (Sprite sprite : sprites) {
			sprite.paint(g);
		}
   		adventurerSprite.paint(g);
	}

	@Override
	public String getToolTipText(MouseEvent event) {
		// Figure out if there's a sprite at this location that's not Terrain
		for (Sprite sprite : sprites) {
			if (sprite instanceof TravellingSprite) {
				if (sprite.getBoundingBox().contains(event.getPoint())) {
					return sprite.getWorldEntity().toString();
				}
			}
		}

		return null;
	}

	public int getxDimension() {
		return world.getxDimension();
	}

	public int getyDimension() {
		return world.getyDimension();
	}

}
