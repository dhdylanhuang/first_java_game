package openworld.gui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {

	private EnvironmentPanel environmentPanel;
	private AdventurerPanel adventurerPanel;
	private InventoryPanel inventoryPanel;

	public ControlPanel(GameWorld gameWorld) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		environmentPanel = new EnvironmentPanel(gameWorld);
		add(environmentPanel);
		
		Box middleBox = Box.createHorizontalBox();
		inventoryPanel = new InventoryPanel(gameWorld.getAdventurer().getInventory(), this);
		adventurerPanel = new AdventurerPanel(gameWorld.getAdventurer(), this);

		middleBox.add(inventoryPanel);
		middleBox.add(adventurerPanel);
		add(middleBox);
	}

	public AdventurerPanel getAdventurerPanel() {
		return adventurerPanel;
	}

	public void disableAll() {
		environmentPanel.disableAll();
		adventurerPanel.disableAll();
		inventoryPanel.disableAll();
	}

	public void update() {
		adventurerPanel.update();
		inventoryPanel.updateLists();
	}

}
