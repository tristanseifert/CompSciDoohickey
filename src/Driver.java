import javax.swing.*;

public class Driver {
	public static void main(String[] args) {
		// pretty
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		// create the frontend
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FrontEnd w = new FrontEnd();
				w.setVisible(true);
			}
		});
	}

}
