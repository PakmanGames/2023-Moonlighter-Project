/* Andy Pak & Mustafa Merchant
 * Culminating Performance Task
 * ICS4U1
 * Monday, June 12th, 2023
 * Description: End screen class, for when the user either wins or loses the game
 */
package moonlighter;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

public class EndScreen extends JFrame implements ActionListener{
	// Making all the buttons
	private JButton startOver = new JButton("Play Again");
	private JButton mainMenu = new JButton("Main Menu");
	private JButton exit = new JButton("Exit Game");
	SoundEffects sound = new SoundEffects(); // Creating a sound effects object to play sounds
	
	/* Constructor to create end screen frame
	 * pre: String back representing the file name of the background image, String status representing the title of the window,
	 * Color buttonColour for the colour of the text, int soundIndex for the sound to be played when this window opens
	 * post: A end screen window is created
	 */
	public EndScreen(String back, String status, Color buttonColour, int soundIndex) {
		super(status); // Sets the title of the window
		setSize(720, 450); // Sets the size of the window
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		IPanel background = new IPanel(back); // Setting the background image
		background.setLayout(null); // Getting rid of the default layout

		// Setting bounds for the buttons
		startOver.setBounds(312, 270, 100, 30);
		mainMenu.setBounds(312, 310, 100, 30);
		exit.setBounds(312, 350, 100, 30);

		// Adding action listener to each of the buttons
		startOver.addActionListener(this);
		mainMenu.addActionListener(this);
		exit.addActionListener(this);

		// Changing the background colour of the buttons
		startOver.setBackground(Color.BLACK);
		mainMenu.setBackground(Color.BLACK);
		exit.setBackground(Color.BLACK);

		// Changinge the text colour of the buttons
		startOver.setForeground(buttonColour);
		mainMenu.setForeground(buttonColour);
		exit.setForeground(buttonColour);

		// Adding the components to the frame
//		background.add(startOver);
//		background.add(mainMenu);
		background.add(exit);
		add(background);
		setVisible(true);
		playSoundEffects(soundIndex); // Plays the sound at the specified URL array index
	}
	
	/* Plays the sound effect at the specified index
	 * pre: int i represents the index of the URL array
	 * post: none
	 */
	public void playSoundEffects(int i) {
		sound.setFile(i); // Sets the clip to the sound at the specified index
		sound.play(); // Plays the sound
	}
	
	/* Opens certain windows depending on the button the user presses
	 * pre: ActionEvent event when the user presses a button
	 * post: none
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == startOver) { // If user presses play again
			dispose(); // Closes this window
			Main.window.dispose(); // Closes the main game window
			MoonlighterMenu.playSoundEffects(13, false); // Plays sound effect for butotn click
			Main.main();
		}
		if (event.getSource() == mainMenu) { // If user presses main menu
			dispose(); // Closes this window
			MoonlighterMenu.playSoundEffects(13, false); // Plays sound effect for butotn click
			MoonlighterMenu example = new MoonlighterMenu();
		}
		if (event.getSource() == exit) { // If user presses exit game
			MoonlighterMenu.playSoundEffects(13, false); // Plays sound effect for butotn click
			System.exit(0); // ends program
		}
	}
}