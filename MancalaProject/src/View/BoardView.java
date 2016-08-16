package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controller.Mancala;

/**
 * 
 * This class represent a game board, can accept player's requests, and send to
 * controller , then controller remind the view to refresh
 *
 */
public class BoardView extends JFrame implements ActionListener, ViewListener {
    private static final long serialVersionUID = 1L;
    private JButton undo;
    private JLabel curPlayer;
    private PitView[] pitsView;
    private Mancala mancala;// controller
    JFrame parent;

    public BoardView(int initalStone, PitStyle ps, JFrame parent) {
	// Set size
	this.setSize(700, 300);
	this.setTitle("Mancala");
	setResizable(false);
	this.parent = parent;
	// Set location
	int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this
		.getWidth()) / 2;
	int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this
		.getHeight()) / 2;
	this.setLocation(w, h);

	// Set layout
	getContentPane().setLayout(new BorderLayout());
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	mancala = new Mancala(1, initalStone);
	mancala.setListener(this);// sets the listener for this boradview as the class mancala
	JPanel north = new JPanel();
	this.undo = new JButton("UNDO");
	this.undo.addActionListener(this);
	north.add(undo);
	curPlayer = new JLabel("Current Player: " + this.mancala.getPlayer());
	north.add(curPlayer);
	this.add(north, BorderLayout.NORTH);

	// Initialize pits
	int[] pits = mancala.getPits();
	this.pitsView = new PitView[14];
	for (int i = 0; i < 14; i++) {
	    this.pitsView[i] = new PitView(pits[i], ps);
	    if (i != 6 && i != 13) {
                // add actionlistener to barodview
		this.pitsView[i].addActionListener(this);
	    }
	}
	this.add(this.pitsView[13], BorderLayout.WEST);
	this.add(this.pitsView[6], BorderLayout.EAST);
	JPanel center = new JPanel();
	center.setLayout(new GridLayout(2, 6, 20, 50));
	for (int i = 12; i >= 7; i--) {
	    center.add(this.pitsView[i]);
	}
	for (int i = 0; i < 6; i++) {
	    center.add(this.pitsView[i]);
	}
	this.add(center, BorderLayout.CENTER);
    }

    /**
     * 
     * @param arg0
     * @return pit's index that the player clicked
     */
    private int getPressPit(ActionEvent arg0) {
	int i;
	for (i = 0; i < 14; i++) {
	    if (arg0.getSource() == this.pitsView[i]) {
		break;
	    }
	}
	return i;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	int i = getPressPit(arg0);
	if (i < 14) {
	    if (!this.mancala.move(i)) {
		JOptionPane.showMessageDialog(null, "NOT YOUR MOVE",
			"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
	    } else {// Move pit's stone and refresh
		this.mancala.refresh();
		if (this.mancala.isEnd()) {// game end
		    int player = mancala.getWinner();
		    this.mancala.refresh();

		    // print message
		    String message = "";
		    if (player == 1) {
			message = " WINNER A!";
		    } else if (player == -1) {
			message = " WINNER B!";
		    } else {
			message = "DRAW!";
		    }
		    int n = JOptionPane.showConfirmDialog(null, message
			    + "\n Play again?", "Result",
			    JOptionPane.YES_NO_OPTION);
		    if (n == 0) {
			this.parent.setVisible(true);
			this.dispose();
		    } else {
			this.dispose();
		    }
		}
	    }
	} else {// Undo Action
	    if (this.mancala.undo()) { // undo
		this.mancala.refresh();
	    } else {
		JOptionPane.showMessageDialog(null, "CAN NOT UNDO!",
			"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }

    /**
     * Implements the refresh interface
     */
    @Override
    public void refreshPits() {
	// TODO Auto-generated method stub
	int[] pits = mancala.getPits();
	for (int i = 0; i < 14; i++) {
	    this.pitsView[i].rePlaceStone(pits[i]);
	}
	this.curPlayer.setText("Current Player: " + this.mancala.getPlayer());
    }
}
