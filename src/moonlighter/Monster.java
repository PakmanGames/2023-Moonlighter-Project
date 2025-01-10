/*
 * Culminating Performance Task
 * ICS4U1
 * Monday, June 12th, 2023
 * Description: Monster class, to create a monster object representing a monster
 */
package moonlighter;

import java.awt.*;

public class Monster extends Entity{

	private String[] directions = { "up", "down", "left", "right" }; // Array to hold directions of movement for monster
	public String type; // Keeps track of which type of monster it is
	public boolean dead; // Keeps track if the monster is dead or alive
	
	// Creating all the images for the monster sprites
	private Image slime1, slime2; // Images for the slime
	private Image golemUp1, golemUp2, golemDown1, golemDown2, golemLeft1, golemLeft2, golemRight1, golemRight2; // Images for golem
	private Image up1, up2, down1, down2, left1, left2, right1, right2, sprite1, sprite2; // These images used to animate the monster sprite
	
	/* Constructor for the monster object
	 * pre: Moonlighter game object from the Moonlighter class, String type to determine the type of monster
	 * post: A monster object is created
	 */
	public Monster(Moonlighter game, String type) {
		super(game, 1, 3, 0, 0); // Setting values for variables in the parent class
		this.type = type; // Setting what type of monster it is
		getImages(); // Getting all the images
		setStartValues(); // Setting the start values
	}

	/* Sets the starting values for certain variables and images based on the type of monster
	 * pre: none
	 * post: none
	 */
	public void setStartValues() {
		if (type.equals("slime")) { // If the monster is a slime
			up1 = slime1;
			up2 = slime2;
			down1 = slime1;
			down2 = slime2;
			left1 = slime1;
			left2 = slime2;
			right1 = slime1;
			right2 = slime2;
		}
		else if (type.equals("golem")) { // If the monster is a golem
			up1 = golemUp1;
			up2 = golemUp2;
			down1 = golemDown1;
			down2 = golemDown2;
			left1 = golemLeft1;
			left2 = golemLeft2;
			right1 = golemRight1;
			right2 = golemRight2;
		}
		dead = false;
		ranNum = (int) (Math.random() * ((directions.length - 1) - 0 + 1) + 0); // Random number for direction monster moves in
		
		// Randomizing the starting position for the monster
		int randomPosition = (int) (Math.random() * (3 - 1 + 1) + 1);
		switch (randomPosition) { // For the x coordinate
		case 1:
			spriteX = 150;
			break;
		case 2:
			spriteX = 350;
			break;
		case 3:
			spriteX = 550;
			break;
		}
		randomPosition = (int) (Math.random() * (3 - 1 + 1) + 1);
		switch (randomPosition) { // For the y coordinate
		case 1:
			spriteY = 90;
			break;
		case 2:
			spriteY = 190;
			break;
		case 3:
			spriteY = 290;
			break;
		}
	}

	/* Gets the images for different monster sprites
	 * pre: none
	 * post: none
	 */
	public void getImages() {
		// Images for the slime
		slime1 = kit.getImage("res/Baby_Fire_Slime1.png");
		slime2 = kit.getImage("res/Baby_Fire_Slime2.png");
		
		// Images for the golem
		golemUp1 = kit.getImage("res/golemUp1.png");
		golemUp2 = kit.getImage("res/golemUp2.png");
		golemDown1 = kit.getImage("res/golemDown1.png");
		golemDown2 = kit.getImage("res/golemDown2.png");
		golemLeft1 = kit.getImage("res/golemLeft1.png");
		golemLeft2 = kit.getImage("res/golemLeft2.png");
		golemRight1 = kit.getImage("res/golemRight1.png");
		golemRight2 = kit.getImage("res/golemRight2.png");
	}

	/* Draws the monster on the screen and animates it using if statements
	 * pre: Graphics2D component2D used for various graphics functions
	 * post: none
	 */
	public void draw(Graphics2D component2D) {
		Image image = null;
		if (clock >= 100) { // Every 100 Frames the monster changes the direction it moves in
			ranNum = (int) (Math.random() * ((directions.length - 1) - 0 + 1) + 0);
			clock = 0; // Resets clock counter to 0
		}

		// Changing monster coordinates based on the random direction
		if (directions[ranNum].equals("up")) { // If the monster moves up
			spriteY -= velocity;
			sprite1 = up1;
			sprite2 = up2;
		}
		if (directions[ranNum].equals("down")) { // If the monster moves down
			spriteY += velocity;
			sprite1 = down1;
			sprite2 = down2;
		}
		if (directions[ranNum].equals("right")) { // If the monster moves right
			spriteX += velocity;
			sprite1 = right1;
			sprite2 = right2;
		}
		if (directions[ranNum].equals("left")) { // If the monster moves left
			spriteX -= velocity;
			sprite1 = left1;
			sprite2 = left2;
		}
		
		// Animating the monster moving animation
		if ((clock/10)%2 == 0)
			image = sprite1;
		else if ((clock/10)%2 != 0)
			image = sprite2;

		clock++; // Increases the clock counter
		wallCheck(); // Checks if monster moves out of bounds
		
		// Draws the monster
		component2D.drawImage(image, spriteX, spriteY, 32, 32, null);
	}
}