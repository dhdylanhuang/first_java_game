package openworld.gui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import openworld.adventurer.Adventurer;

public class AdventurerPanel extends JPanel {

	private StatsTableModel statsTableModel;
	private JTable statsTable;

	public AdventurerPanel(Adventurer adventurer, ControlPanel controlPanel) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		statsTableModel = new StatsTableModel(adventurer);
		statsTable = new JTable(statsTableModel);

		// https://stackoverflow.com/a/9821640
		statsTable.setPreferredScrollableViewportSize(statsTable.getPreferredSize());
		statsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane statsScroll = new JScrollPane(statsTable);
		statsScroll.setBorder(new TitledBorder("Stats"));

		add(statsScroll);
		add(Box.createVerticalGlue());
	}

	public void update() {
		statsTableModel.fireTableDataChanged();
	}

	public void disableAll() {
		statsTable.setEnabled(false);
	}

}
