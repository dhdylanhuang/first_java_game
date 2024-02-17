package openworld.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import openworld.characters.NPC;
import openworld.monsters.Monster;

public class EnvironmentPanel extends JPanel implements ActionListener {
	
	private GameWorld gameWorld;
	
	private JButton respawnButton, startButton, stopButton;
	
	public EnvironmentPanel(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new TitledBorder("Monster and NPC controls"));
		
		respawnButton = new JButton("Respawn all");
		respawnButton.addActionListener(this);
		startButton = new JButton("Start moving");
		startButton.addActionListener(this);
		stopButton = new JButton("Stop moving");
		stopButton.addActionListener(this);
		
		add(respawnButton);
		add(startButton);
		add(stopButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == respawnButton) {
			gameWorld.respawnWorld();
		} else if (e.getSource() == startButton) {
			startButton.setEnabled(false);
	
			Thread gameThread = new Thread(() -> {
				while (!Thread.interrupted()) {
					for (Monster monster : gameWorld.getMonster()) {
						monster.takeTurn();
					}
					sleepForShortInterval();
					for (NPC npc : gameWorld.getNonPlayerCharacters()) {
						npc.takeTurn();
					}
					sleepForShortInterval();
				}
			});
	
			gameThread.start();
	
			gameWorld.setGameThread(gameThread);
		} else if (e.getSource() == stopButton) {
			if (gameWorld.getGameThread() != null) {
				gameWorld.getGameThread().interrupt();
			}
			startButton.setEnabled(true);
		}
	}
	
	private void sleepForShortInterval() {
		try {
			Thread.sleep(1000); 
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public void disableAll() {
		startButton.setEnabled(false);
		stopButton.setEnabled(false);
		respawnButton.setEnabled(false);
	}

}
