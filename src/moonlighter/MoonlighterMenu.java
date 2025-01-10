/*
 * Culminating Performance Task
 * ICS4U1
 * Monday, June 12th, 2023
 * Description: Moonlighter menu class, starting menu when user runs program
 */
package moonlighter;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

class MoonlighterMenu extends JFrame implements ActionListener {
	// Creating all the buttons
	private JButton start = new JButton("Start Game");
	private JButton control = new JButton("Controls");
	private JButton exit = new JButton("Exit Game");
	private JButton credits = new JButton("Credits");
	
	private Color button = new Color(1, 146, 91); // Creating a colour
	public static SoundEffects sound = new SoundEffects(); // Creating a sound effects object to play sounds

	/* Constructor for the Moonlighter menu frame
	 * pre: none
	 * post: A moonlighter menu window is created
	 */
	public MoonlighterMenu() {
		super("Moonlighter"); // Sets the title of window
		setSize(720, 450); // Sets size of window
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		IPanel background = new IPanel("res/MoonlighterLDEditT.jpg"); // Background image
		background.setLayout(null); // Getting rid of default layout
		
		// Setting bounds for all the buttons
		start.setBounds(312, 250, 100, 30);
		control.setBounds(312, 290, 100, 30);
		credits.setBounds(312, 330, 100, 30);
		exit.setBounds(312, 370, 100, 30);
		
		// Adding action listener for all the buttons
		start.addActionListener(this);
		control.addActionListener(this);
		exit.addActionListener(this);
		credits.addActionListener(this);
		
		// Changing colour of each button
		start.setBackground(button);
		control.setBackground(button);
		exit.setBackground(button);
		credits.setBackground(button);
		
		// Changing the text colour of each button
		start.setForeground(Color.WHITE);
		control.setForeground(Color.WHITE);
		exit.setForeground(Color.WHITE);
		credits.setForeground(Color.WHITE);
		
		// Adding components to the panel
		background.add(control);
		background.add(exit);
		background.add(start);
		background.add(credits);
		
		add(background);
		setVisible(true);
	}
	
	/* Plays the sound effect at the specified index
	 * pre: int i represents the index of the URL array, boolean loop to determine if the sound is looped
	 * post: none
	 */
	public static void playSoundEffects(int i, boolean loop) {
		sound.setFile(i); // Sets the clip to the sound at the specified index
		if (loop == true)
			sound.loop(); // Loops the sound
		else if (loop == false)
			sound.play(); // Plays the sound
	}
	
	/* Opens certain windows depending on the button the user presses
	 * pre: ActionEvent event when the user presses a button
	 * post: none
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == start) { // If user presses start game button
			dispose(); // Closes this window
			sound.stop(); // Stops the looping music
			Main.main(); // Runs the main method in Main Class
		}
		if (event.getSource() == control) { // If user presses controls button
			dispose(); // Closes this window
			sound.stop(); // Stops the looping music
			playSoundEffects(13, false); // Plays sound effect for button click
			ControlMenu window = new ControlMenu();
		}
		if (event.getSource() == credits) { // If user presses credits button
			dispose(); // Closes this window
			sound.stop(); // Stops the looping music
			playSoundEffects(13, false); // Plays sound effect for button click
			CreditsMenu window = new CreditsMenu("res/credits.png");
		}
		if (event.getSource() == exit) { // If user presses exit button
			playSoundEffects(13, false); // Plays sound effect for button click
			System.exit(0); //ends program
		}
	}
	
	public static void main(String[] args) {
		MoonlighterMenu example = new MoonlighterMenu();
		playSoundEffects(11, true); // Loops background music
	}

}