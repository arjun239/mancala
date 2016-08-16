package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

public class CircleRed implements PitStyle {

    @Override
    public void setColor(Component component) {
	component.setBackground(Color.pink);
    }

    @Override
    public void setShape(Graphics g, int w, int h) {
	g.fillOval(0, 0, w, h);
    }

    @Override
    public void drawBorder(Graphics g, int w, int h) {
	g.drawOval(0, 0, w, h);
    }
}
