package complete;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * 
 * Implement a GUI that allows users to search through and sort a list of their
 * contacts.
 * 
 */
public class EmailSearcher extends JFrame {

	private ArrayList<String> allEmails;
	private ArrayList<String> displayEmails;
	private JList<String> displayList;
	private DefaultListModel<String> displayListModel;
	private JTextField searchBar;
	private JPanel buttonPanel;
	private JButton sortButton;
	private JButton addButton;
	private JButton removeButton;

	public static void main(String[] args) {
		EmailSearcher window = new EmailSearcher();
		window.setVisible(true);
	}

	public EmailSearcher() {
		displayListModel = new DefaultListModel<String>();
		displayList = new JList<String>(displayListModel);
		allEmails = new ArrayList<String>();
		searchBar = new JTextField();
		buttonPanel = new JPanel();
		sortButton = new JButton();
		addButton = new JButton();
		removeButton = new JButton();

		setProperties();
		setUpModel(); // TODO finish
		registerListeners(); // TODO finish
		addComponents();
	}

	private void addComponents() {
		this.add(displayList, BorderLayout.CENTER);
		this.add(searchBar, BorderLayout.NORTH);
		buttonPanel.add(sortButton);
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	private void setProperties() {
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setTitle("Email Searcher");

		buttonPanel.setSize(400, 50);
		buttonPanel.setLayout(new FlowLayout());

		sortButton.setSize(200, 50);
		sortButton.setText("Sort list alphabetically");

		addButton.setSize(100, 50);
		addButton.setText("Add a contact");

		removeButton.setSize(100, 50);
		removeButton.setText("Remove selected contact");
	}

	private void setUpModel() {
		// Create new ArrayList object for allEmails
		allEmails = new ArrayList<String>();

		// Add all the emails we will keep track of
		allEmails.add("Peter Parker <spiderman@dailybugle.com>");
		allEmails.add("Lester Mccann <lester@cs.arizona.edu>");
		allEmails.add("Steve Rogers <captainamerica@shield.gov>");
		allEmails.add("Saumya Debray <debray@cs.arizona.edu>");
		allEmails.add("Patrick Homer <homer@cs.arizona.edu>");
		allEmails.add("Tony Stark <ironman@starkindustries.com>");
		allEmails.add("Beichuan Zhang <bz@cs.arizona.edu>");
		allEmails.add("Rick Schlichting <rick@cs.arizona.edu>");
		allEmails.add("Pete Downey <pete@cs.arizona.edu>");
		allEmails.add("John Kececioglu <kece@cs.arizona.edu>");
		allEmails.add("Natasha Romanoff <natalie@starkindustries.com>");
		allEmails.add("Clark Kent <superman@dailyplanet.com>");
		allEmails.add("Rick Snodgrass <rts@cs.arizona.edu>");
		allEmails.add("Logan <wolverine@xmen.edu>");
		allEmails.add("Hal Jordan <greenlantern@justiceleague.com>");
		allEmails.add("Rick Mercer <mercer@cs.arizona.edu>");
		allEmails.add("Zachary Montoya <zacharymontoya@email.arizona.edu");
		allEmails.add("Dylan Clavell <dclavell@email.arizona.edu");
		allEmails.add("Gabe Kishi <gtkishi@email.arizona.edu");
		allEmails.add("Eric Cascketta <uofkuv@email.arizona.edu");

		// TODO Create the list for currently displayed emails
		displayEmails = new ArrayList<String>(allEmails);

		// TODO Update the model's data
		updateListModel();

	}

	private void updateListModel() {
		displayListModel.clear();
		for (String email : displayEmails) {
			displayListModel.addElement(email);
		}
	}

	private void registerListeners() {
		// TODO add listeners

		// Create listener for the searchBar
		searchBar.getDocument().addDocumentListener(new SearchBarListener());

		// Create listener for the JList
		displayList.addListSelectionListener(new EmailListListener());

		// Create listener for the sorting JButton
		sortButton.addActionListener(new AlphaSortListener());
		
		// Create listener for the Add/Remove Buttons
		ActionListener al = new AddRemoveListener();
		addButton.addActionListener(al);
		removeButton.addActionListener(al);
	}

	private class SearchBarListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			// Not necessary
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			updateSearch();
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			updateSearch();
		}

		private void updateSearch() {
			String search = searchBar.getText().toLowerCase();
			displayEmails.clear();
			for (String contact : allEmails) {
				if (contact.toLowerCase().indexOf(search) != -1) {
					displayEmails.add(contact);
				}
			}
			updateListModel();
		}
	}

	private class AlphaSortListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Collections.sort(displayEmails);
			updateListModel();
		}

	}

	private class EmailListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (displayList.getValueIsAdjusting()
					&& allEmails.contains(displayList.getSelectedValue())) {
				JOptionPane.showMessageDialog(null,
						displayList.getSelectedValue());
			}
		}

	}

	private class AddRemoveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Note: you can use the same listener for two buttons!
			
			if (arg0.getSource() == addButton) {
				
				String email = JOptionPane.showInputDialog("Please enter the email you wish to add in the form: First Last <emailaddress@domain>");
				allEmails.add(email);
				displayEmails.add(email);
				updateListModel();
				
			} else if (arg0.getSource() == removeButton) {
				
				if(displayList.isSelectionEmpty()){
					JOptionPane.showMessageDialog(null, "No email selected!");
				}
				else{
					String email = displayList.getSelectedValue();
					allEmails.remove(email);
					displayEmails.remove(email);
					updateListModel();
				}
				
			}
		}

	}
}
