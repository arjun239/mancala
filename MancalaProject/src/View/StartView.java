package View;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * This is a start View, Player should input game setting(pits stone number,
 * pits style)
 *
 */
public class StartView extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField num;
    private JButton jb1;
    private JButton jb2;

    public StartView() {
	// Set size
	this.setSize(300, 200);
	this.setTitle("GAME SETTING");
	setResizable(false);

	// Set location
	int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this
		.getWidth()) / 2;
	int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this
		.getHeight()) / 2;
	this.setLocation(w, h);

	// Set layout
	getContentPane().setLayout(new BorderLayout());
	JPanel p = new JPanel();
	JLabel tip = new JLabel("Input initial number of stones:");
	p.add(tip);
	num = new JTextField(10);
	p.add(num);
	this.add(p, BorderLayout.NORTH);

	JPanel center = new JPanel();
	JLabel sel1 = new JLabel("White Color Rectangle shape");
	JLabel sel2 = new JLabel("Red Color Circle shape");
	this.jb1 = new JButton("Select");
	this.jb2 = new JButton("Select");
	jb1.addActionListener(this);
	jb2.addActionListener(this);
	center.add(sel1);
	center.add(jb1);
	center.add(sel2);
	center.add(jb2);
	this.add(center, BorderLayout.CENTER);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * 
     * @return whether the input number is valid
     */
    private boolean inputInvalid() {
	try {
	    int i = Integer.parseInt(this.num.getText().trim());
	    if (i != 3 && i != 4) {
		return false;
	    }
	    return true;
	} catch (Exception e) {
	    return false;
	}

    }

    @Override
    public void actionPerformed(ActionEvent e) {

	if (e.getSource() == jb1) {// paint first style
	    if (this.inputInvalid()) {
		BoardView b = new BoardView(Integer.parseInt(this.num.getText()
			.trim()), new RectWhite(), this);
		b.setVisible(true);
		this.setVisible(false);
	    } else {
		JOptionPane.showMessageDialog(null, "WRONG INPUT NUMBER",
			"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
	    }
	} else {// paint second style
	    if (this.inputInvalid()) {
		BoardView b = new BoardView(Integer.parseInt(this.num.getText()
			.trim()), new CircleRed(), this);
		b.setVisible(true);
		this.setVisible(false);
	    } else {
		JOptionPane.showMessageDialog(null, "WRONG INPUT NUMBER",
			"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }

    public static void main(String[] args) {
	StartView b = new StartView();
	b.setVisible(true);
    }
}
