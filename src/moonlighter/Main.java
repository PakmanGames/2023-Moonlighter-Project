/*
 * Culminating Performance Task
 * ICS4U1
 * Monday, June 12th, 2023
 * Description: Main class to run game
 */
package moonlighter;

import javax.swing.*;

public class Main extends JFrame {
	
	public static Main window = new Main(); // Creating a new JFrame called window
	
	public static void main() {

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false); // Making frame not resizable
		window.setTitle("Moonlighter"); // Setting the title of the frame
		Moonlighter panel = new Moonlighter();  // Creating a new JPanel from Moonlighter class
		
		window.add(panel);
		window.pack(); // Makes the window be the preferred size set from the panel
		window.setVisible(true); // Makes the panel visible
	}

}