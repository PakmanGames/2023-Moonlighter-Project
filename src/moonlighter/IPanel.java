/*
 * Culminating Performance Task
 * ICS4U1
 * Monday, June 12th, 2023
 * Description: IPanel class, for the background images of certain windows
 */
package moonlighter;

import java.awt.*;
import javax.swing.*;

public class IPanel extends JPanel {
	
	Image moonBG;
	
	/* Constructor to create a JPanel with a background image
	 * pre: String name represents the file name of the background image
	 * post: A JPanel with a background image is created
	 */
	public IPanel(String name) {
		Toolkit kit = Toolkit.getDefaultToolkit(); // Used to get the image for the background
		moonBG = kit.getImage(name); // Getting the image
		moonBG.getScaledInstance(720, 450, java.awt.Image.SCALE_SMOOTH); // Scales the image to the right size
	}

	/* To paint the background of the panel on the screen
	 * pre: Graphics comp to use various graphics functions
	 * post: none
	 */
	public void paintComponent(Graphics comp) {
		Graphics2D comp2D = (Graphics2D) comp;
		comp2D.drawImage(moonBG, 0, 0, 720, 450, this); // Draws the background image
	}
}



