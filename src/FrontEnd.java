import java.awt.*;

import javax.swing.*;


public class FrontEnd extends JFrame {
	private static final long serialVersionUID = 2133338847613583748L;

	/**
	 * Handles the data storage and parsing of messages.
	 */
	BackEnd back = new BackEnd();
	
	/**
	 * Creates the thingie.
	 */
	public FrontEnd() {
		this.setTitle("CompSci Doohickey by MEGAPUSSI");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set minimum size, and centre the window
		this.setPreferredSize(new Dimension(640, 480));
		this.setMinimumSize(this.getPreferredSize());
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.width /= 2;
		screenSize.height /= 2;

		screenSize.width -= this.getPreferredSize().width / 2;
		screenSize.height -= this.getPreferredSize().height / 2;
		
		this.setLocation(screenSize.width, screenSize.height);
	}
}
