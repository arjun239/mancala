package View;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

class PitView extends JButton {
    private static final long serialVersionUID = 1L;
    PitStyle pStyle;

    public PitView(int stoneNum, PitStyle pStyle) {
	Dimension size = getPreferredSize();
	size.width = size.height = 80;
	setPreferredSize(size);
	setContentAreaFilled(false);
	this.pStyle = pStyle;
	pStyle.setColor(this);
	String disText = "<html><h3  style=\"font:500 12px Simsun;color:#F00;line-height:150%;\">";
       
	for (int i = 1; i <= stoneNum; i++) {
	    disText = disText + ".";
	    if (i % 4 == 0) {
		disText = disText + "<br>";
               
	    }
	}
	disText = disText + "</h3></html>";
        
	this.setText(disText);
    }

    /**
     * Refresh the pit's number of stones
     * the refresh method feeds this one the new number of stones
     * @param num
     */
    public void rePlaceStone(int num) {
	String disText = "<html><h3  style=\"font:500 12px Simsun;color:#F00;line-height:150%;\">";
	for (int i = 1; i <= num; i++) {
	    disText = disText + ".";
	    if (i % 4 == 0) {
		disText = disText + "<br>";
	    }
	}
	disText = disText + "</h3></html>";
	this.setText(disText);
    }

    @Override
    protected void paintComponent(Graphics g) {
	if (getModel().isArmed()) {
	    g.setColor(Color.lightGray);
	} else {
	    g.setColor(getBackground());
	}
	this.pStyle.setShape(g, getSize().width - 1, getSize().height - 1);
	super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
	g.setColor(getForeground());
	this.pStyle.drawBorder(g, getSize().width - 1, getSize().height - 1);
    }

    Shape shape;
//checks if the mouse point lies inside the pit
    public boolean contains(int x, int y) {
	if (shape == null || !shape.getBounds().equals(getBounds())) {
	    shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
	}
	return shape.contains(x, y);
    }
}