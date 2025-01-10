/*
 * Culminating Performance Task
 * ICS4U1
 * Monday, June 12th, 2023
 * Description: Credits class, to show the credits of the game
 */
package moonlighter;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

public class CreditsMenu extends JFrame implements ActionListener{

	private JButton backButton = new JButton("Back"); // Creating the back button
	
	/* Constructor to create a credits menu frame
	 * pre: String back represents the name of the file to be used for the background
	 * post: A credits menu window is created
	 */
	public CreditsMenu(String back) {
		super("Moonlighter by Andy Pak & Mustafa Merchant"); // Setting the name of the window
		setSize(720, 450); // Setting the size of the window
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		IPanel background = new IPanel(back); // Setting a background image
		background.setLayout(null); // Getting rid of the default layout
		backButton.setBounds(630, 10, 65, 25); // Setting bounds for the back button
		
		// Changinge the background colour of the button and the text colour
		backButton.setBackground(Color.BLACK);
		backButton.setForeground(Color.WHITE);
		
		backButton.addActionListener(this); // Adding action listener to button
		background.add(backButton); // Adding the button to the background
		add(background);
		MoonlighterMenu.playSoundEffects(11, true); // Loops the background music
		setVisible(true);
	}
	
	/* Opens certain windows depending on the button the user presses
	 * pre: ActionEvent event when the user presses a button
	 * post: none
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == backButton) { // If user presses back button
			dispose(); // Closes this window
			MoonlighterMenu.sound.stop(); // Stops the looping music
			MoonlighterMenu.playSoundEffects(13, false); // Plays sound effect for button click
			MoonlighterMenu example = new MoonlighterMenu();
			MoonlighterMenu.playSoundEffects(11, true); // Loops the background music
		}
	}
}