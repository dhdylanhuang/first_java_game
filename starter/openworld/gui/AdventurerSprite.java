package openworld.gui;

import java.awt.Color;
import java.awt.Graphics;

import openworld.adventurer.Adventurer;

public class AdventurerSprite extends TravellingSprite {

  private Adventurer adventurer;

  public AdventurerSprite(GameWorld gameWorld, Adventurer adventurer) {
    super(gameWorld, adventurer);
    this.adventurer = adventurer;
    this.label = null;
  }

  @Override
  protected void drawShape(int realX, int realY, Graphics g) {
    if (adventurer.isConscious()) {
      this.color = Color.green.brighter().brighter();
    } else {
      this.color = Color.green.darker().darker();
    }
    g.setColor(this.color);
    g.fillOval(realX, realY, boxSize, boxSize);
    g.setColor(Color.WHITE);
    g.drawOval(realX, realY, boxSize, boxSize);
  }

}
