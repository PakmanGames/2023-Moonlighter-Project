/* Andy Pak & Mustafa Merchant
 * Culminating Performance Task
 * ICS4U1
 * Monday, June 12th, 2023
 * Description: Control menu class, to show the user the controls of the game
 */
package moonlighter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControlMenu extends JFrame implements ActionListener {
	
	// Creating the close button
	private JButton close = new JButton("Back");
	
	// Creating all the labels for the controls
	private JLabel move = new JLabel("Move");
	private JLabel rolls = new JLabel("Dash Attack");
	private JLabel attacks = new JLabel("Attack");
	private JLabel heal = new JLabel("Heal");
	private JLabel buy = new JLabel("Buy Potion");
	
	// Labels for how to play the game
	private JLabel htp1 = new JLabel("You are in a dungeon and you have to fight your way out to escape.");
	private JLabel htp2 = new JLabel("Click to attack monsters, you take damage when you run into them.");
	private JLabel htp3 = new JLabel("Press space to dash through them without taking damage.");
	private JLabel htp4 = new JLabel("Defeated monsters drop coins you can use to buy potions to heal with.");
	private JLabel htp5 = new JLabel("Good luck adventurer!");

	// Creating all the images for the different icons for controls
	private ImageIcon keys = new ImageIcon("res/keyboard.png");
	private Image key1 = keys.getImage();
	private Image scaleKey = key1.getScaledInstance(150, 135, java.awt.Image.SCALE_SMOOTH);
	private ImageIcon keyboard = new ImageIcon(scaleKey);
	private JLabel keyslbl = new JLabel(keyboard);
	private ImageIcon roll = new ImageIcon("res/space.png");
	private Image roll1 = roll.getImage();
	private Image scaleroll = roll1.getScaledInstance(150, 135, java.awt.Image.SCALE_SMOOTH);
	private ImageIcon roller = new ImageIcon(scaleroll);
	private JLabel rolllbl = new JLabel(roller);
	private ImageIcon attack = new ImageIcon("res/attack.png");
	private Image attack1 = attack.getImage();
	private Image scaleatk = attack1.getScaledInstance(140, 100, java.awt.Image.SCALE_SMOOTH);
	private ImageIcon attacker = new ImageIcon(scaleatk);
	private JLabel atklbl = new JLabel(attacker);
	private ImageIcon q = new ImageIcon("res/q.png");
	private Image q1 = q.getImage();
	private Image scaleq = q1.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
	private ImageIcon qs = new ImageIcon(scaleq);
	private JLabel qlbl = new JLabel(qs);
	private ImageIcon b = new ImageIcon("res/b.png");
	private Image b1 = b.getImage();
	private Image scaleb = b1.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
	private ImageIcon bs = new ImageIcon(scaleb);
	private JLabel blbl = new JLabel(bs);
	
	/* Contructor for the control menu frame
	 * pre: none
	 * post: A control menu frame is created
	 */
	public ControlMenu() {
		super("Controls"); // Setting the window title
		setSize(720, 450); // Setting the size of the window
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		IPanel background = new IPanel("res/MoonlighterLDEditNT.jpg"); // Setting the background image
		background.setLayout(null); // Getting rid of the default layout
		close.setBounds(630, 10, 65, 25); // Setting location for the close button
		
		// Changing the fonts for all the how to play labels
		htp1.setFont(new Font("TimesRoman", Font.BOLD, 15));
		htp2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		htp3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		htp4.setFont(new Font("TimesRoman", Font.BOLD, 15));
		htp5.setFont(new Font("TimesRoman", Font.BOLD, 15));
		
		// Setting the location for all the how to play labels
		htp1.setBounds(110, 10, 600, 25);
		htp2.setBounds(110, 35, 600, 25);
		htp3.setBounds(110, 60, 600, 25);
		htp4.setBounds(110, 85, 600, 25);
		htp5.setBounds(110, 110, 600, 25);
		
		// Changing the text colour of all the how to play labels
		htp1.setForeground(Color.WHITE);
		htp2.setForeground(Color.WHITE);
		htp3.setForeground(Color.WHITE);
		htp4.setForeground(Color.WHITE);
		htp5.setForeground(Color.WHITE);
		
		// Label and image for the keyboard image
		move.setForeground(Color.WHITE);
		move.setFont(new Font("TimesRoman", Font.BOLD, 15));
		move.setBounds(170, 90, 150, 120);
		keyslbl.setBounds(110, 140, 150, 135);
		
		// Label and image for the space image
		rolls.setForeground(Color.WHITE);
		rolls.setFont(new Font("TimesRoman", Font.BOLD, 15));
		rolls.setBounds(420, 150, 150, 25);
		rolllbl.setBounds(380, 170, 150, 85);
		
		// Label and image for the mouse image
		attacks.setForeground(Color.WHITE);
		attacks.setFont(new Font("TimesRoman", Font.BOLD, 15));
		attacks.setBounds(470, 280, 150, 25);
		atklbl.setBounds(420, 300, 150, 105);
		
		// Label and image for Q button image
		heal.setForeground(Color.WHITE);
		heal.setFont(new Font("TimesRoman", Font.BOLD, 15));
		heal.setBounds(160, 280, 100, 25);
		qlbl.setBounds(100, 300, 150, 100);
		
		// Label and image for B button image
		buy.setForeground(Color.WHITE);
		buy.setFont(new Font("TimesRoman", Font.BOLD, 15));
		buy.setBounds(290, 280, 100, 25);
		blbl.setBounds(250, 300, 150, 100);

		// Setting the colours for the close button
		close.setBackground(Color.BLACK);
	    close.setForeground(Color.WHITE);
		close.addActionListener(this); // Adding action listener for the close button
		background.add(close);

		// Adding all the images and labels to the panel for the different control buttons
		background.add(move);
		background.add(keyslbl);
		background.add(rolls);
		background.add(rolllbl);
		background.add(attacks);
		background.add(atklbl);
		background.add(heal);
		background.add(qlbl);
		background.add(buy);
		background.add(blbl);
	
		// Adding all the how to play labels to the panel
		background.add(htp1);
		background.add(htp2);
		background.add(htp3);
		background.add(htp4);
		background.add(htp5);
		
		MoonlighterMenu.playSoundEffects(11, true); // Loops the background music
		add(background); // Adding the background to the frame
		setVisible(true);

	}

	/* Opens certain windows depending on the button the user presses
	 * pre: ActionEvent event when the user presses a button
	 * post: none
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == close) { // If the user presses the close button
			dispose(); // Closes this window
			MoonlighterMenu.sound.stop(); // Stops the looping music
			MoonlighterMenu.playSoundEffects(13, false); // Plays the click sound effect
			MoonlighterMenu example = new MoonlighterMenu(); // Opens the Moonlighter Menu window
			MoonlighterMenu.playSoundEffects(11, true); // Loops the background music
		}
	}
}