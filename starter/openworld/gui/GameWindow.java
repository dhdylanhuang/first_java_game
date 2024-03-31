package openworld.gui;

import java.awt.BorderLayout;
import java.io.PrintStream;

import java.lang.Thread;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import openworld.music.BackgroundMusic;

public class GameWindow extends JFrame {
	private static final GameWindow _INSTANCE = new GameWindow();
	private GameWorld gameWorld;
	private ControlPanel controlPanel;

	public static GameWindow getInstance() {
		return _INSTANCE;
	}

	private GameWindow() {
		super("Adventurer game 2.0");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setResizable(false);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// Don't care, it'll just not look like a native application
		}
		
		gameWorld = new GameWorld(this);
		controlPanel = new ControlPanel(gameWorld);
		
		JTextArea logArea = new JTextArea(10, 80);
		logArea.setEditable(false);
		logArea.setLineWrap(true);
		logArea.setWrapStyleWord(true);
		JScrollPane logScroll = new JScrollPane(logArea);

		System.setOut(new PrintStream(new JTextAreaOutputStream(logArea)));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(gameWorld);
		getContentPane().add(controlPanel, BorderLayout.EAST);
		getContentPane().add(logScroll, BorderLayout.SOUTH);
		pack();
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

    public void gameOver() {
		controlPanel.disableAll();
		JOptionPane.showMessageDialog(this, "The adventurer has fainted!", "Game over", JOptionPane.ERROR_MESSAGE);
    }

	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				_INSTANCE.setVisible(true);
				String[] musicLocations = {"starter/openworld/music/Ocarina of Time - Lost Woods [Slowed + Reverb].wav","starter/openworld/music/Dark Fantasy.wav 12-53-42-026.wav"};
				BackgroundMusic backgroundMusic = new BackgroundMusic();
				backgroundMusic.playMusic(musicLocations);
			}
		});
	}

}
