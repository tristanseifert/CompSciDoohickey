import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

public class FrontEnd extends JFrame {
	private static final long serialVersionUID = 2133338847613583748L;

	// das tree
	private JTree tree;
	
	/// This model holds the various nodes of the tree.
	private DefaultTreeModel treeModel;
	
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
		
		// create a toolbar
		JToolBar toolbar = new JToolBar();

		JButton addButton = new JButton("Add Message…");
        toolbar.add(addButton);
                
		JButton removeButton = new JButton("Remove Message");
        toolbar.add(removeButton);
        
        add(toolbar, BorderLayout.NORTH);
        
        // add button handler
        JFrame frame = this;
        
        addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// query for a username
				String user = JOptionPane.showInputDialog(frame, 
						"Please enter a username", "Add Message", 
						JOptionPane.QUESTION_MESSAGE);
				if(user != null) {
					String message = JOptionPane.showInputDialog(frame, 
							"Please enter a message.", "Add Message", 
							JOptionPane.QUESTION_MESSAGE);
					
					if(message != null) {
						back.addMessage(user, message);
						
						// update tree
						createTreeModel();
					} else {
						JOptionPane.showMessageDialog(frame, 
								"Please enter a message.", "Add Message", 
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Please enter a username.", "Add Message", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
        
        // remove handler
        removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        
        // tree
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Messages");
		treeModel = new DefaultTreeModel(root);
		
		// Create the JTree.
		tree = new JTree(treeModel);		
		add(new JScrollPane(tree));
		
		// Convert our list of people into a bunch of nodes, and expand it.
		createTreeModel();
	}
	
	/**
	 * This is used to expand the tree.
	 */
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
	    for(int i=startingIndex; i < rowCount; ++i){
	        tree.expandRow(i);
	    }

	    if(tree.getRowCount() != rowCount){
	        expandAllNodes(tree, rowCount, tree.getRowCount());
	    }
	}

	/**
	 * Crates a tree node for each person, and then calls on another routine to
	 * create sub-nodes for each job.
	 */
	private void createTreeModel() {
		// remove all 
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
		root.removeAllChildren();
		
		// iterate over all the users
		for(String user : this.back.getUsers()) {
			// Create a node for this person, and fill it in with their jobs.
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(user);
			createMessageNodes(node, user);
			
			// Insert the newly created node at the bottom of the list.
			root.insert(node, root.getChildCount());
		}
		
		// update tree
		treeModel.reload();
		
		// Expand tree
		expandAllNodes(tree, 0, tree.getRowCount());
	}
	
	/**
	 * Creates nodes for all the messages of the user.
	 */
	private void createMessageNodes(DefaultMutableTreeNode root, String user) {
		// get messages
		ArrayList<String> messages = (ArrayList<String>) this.back.getMessages(user);

		for(String message : messages) {
			// Crate a node for this job.
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(message);
			
			// Insert it at the bottom of the root node.
			root.insert(node, root.getChildCount());
		}
	}
}
