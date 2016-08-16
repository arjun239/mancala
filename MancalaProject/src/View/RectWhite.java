package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

public class RectWhite implements PitStyle {

    @Override
    public void setColor(Component component) {
	component.setBackground(Color.white);
    }

    @Override
    public void setShape(Graphics g, int w, int h) {
	g.fillRect(0, 0, w, h);
    }

    @Override
    public void drawBorder(Graphics g, int w, int h) {
	g.drawRect(0, 0, w, h);
    }

}
