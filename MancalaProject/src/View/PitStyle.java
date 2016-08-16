package View;

import java.awt.Component;
import java.awt.Graphics;

public interface PitStyle {
    public void setColor(Component component);

    public void setShape(Graphics g, int w, int h);

    public void drawBorder(Graphics g, int w, int h);
}
