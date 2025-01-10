/*
 * Culminating Performance Task
 * ICS4U1
 * Monday, June 12th, 2023
 * Description: Player class, to create a player object representing the player
 */
package moonlighter;

import java.awt.*;
import java.awt.event.*;

public class Player extends Entity{
	
	public int attackBoxX, attackBoxY; // x and y coordinates of the attack box when player attacks
	public int attackClock; // Used to create a cooldown per attack so player cannot spam attacks to do a lot of damage
	public String attackType; // Used to keep track of what type of attack the player chooses, space or click
	public int potionCount; // Keeps count of the number of potions player has
	private boolean gameOver; // Keeps track if player is dead or alive
	private String direction, originalDirection; // Keeps the current direction of player and the previous direction
	
	// Creating all the images for player sprites
	private Image up1, up2, down1, down2, left1, left2, right1, right2; // Moving sprites
	private Image upDodge1, upDodge2, upDodge3, upDodge4; // Upward dash
	private Image downDodge1, downDodge2, downDodge3, downDodge4; // Downward dash
	private Image leftDodge1, leftDodge2, leftDodge3, leftDodge4; // Left dash
	private Image rightDodge1, rightDodge2, rightDodge3, rightDodge4; // Right dash
	private Image upAttack1,upAttack2, downAttack1, downAttack2, leftAttack1, leftAttack2, rightAttack1, rightAttack2; // Attack sprites
	private Image death1, death2; // Death sprites
	private Image dodge1, dodge2, dodge3, dodge4, attack1, attack2; // These images used to animate the player sprite

	/* Constructor for the player object
	 * pre: Moonlighter game object from the Moonlighter class
	 * post: A player object is created
	 */
	public Player(Moonlighter game) {
		super(game, 10, 10, 0, 0); // Setting values for variables in the parent class
		setStartValues(); // Setting start values
		getImages(); // Getting all the images
	}

	/* Sets the starting values for certain variables
	 * pre: none
	 * post: none
	 */
	public void setStartValues() {
		spriteX = 400;
		spriteY = 192;
		potionCount = 5;
		direction = "down";
		attackClock = 0;
		attackType = "click";
		gameOver = false;
	}

	/* Gets the images for different animations and actions
	 * pre: none
	 * post: none
	 */
	public void getImages() {
		// Images for moving animations
		up1 = kit.getImage("res/up1.png");
		up2 = kit.getImage("res/up2.png");
		down1 = kit.getImage("res/down1.png");
		down2 = kit.getImage("res/down2.png");
		left1 = kit.getImage("res/left1.png");
		left2 = kit.getImage("res/left2.png");
		right1 = kit.getImage("res/right1.png");
		right2 = kit.getImage("res/right2.png");

		// Images for the dash attack
		leftDodge1 = kit.getImage("res/leftDodge1.png");
		leftDodge2 = kit.getImage("res/leftDodge2.png");
		leftDodge3 = kit.getImage("res/leftDodge3.png");
		leftDodge4 = kit.getImage("res/leftDodge4.png");
		rightDodge1 = kit.getImage("res/rightDodge1.png");
		rightDodge2 = kit.getImage("res/rightDodge2.png");
		rightDodge3 = kit.getImage("res/rightDodge3.png");
		rightDodge4 = kit.getImage("res/rightDodge4.png");
		upDodge1 = kit.getImage("res/upDodge1.png");
		upDodge2 = kit.getImage("res/upDodge2.png");
		upDodge3 = kit.getImage("res/upDodge3.png");
		upDodge4 = kit.getImage("res/upDodge4.png");
		downDodge1 = kit.getImage("res/downDodge1.png");
		downDodge2 = kit.getImage("res/downDodge2.png");
		downDodge3 = kit.getImage("res/downDodge3.png");
		downDodge4 = kit.getImage("res/downDodge4.png");

		// Images for click attack
		upAttack1 = kit.getImage("res/upAttack1.png");
		upAttack2 = kit.getImage("res/upAttack2.png");
		downAttack1 = kit.getImage("res/downAttack1.png");
		downAttack2 = kit.getImage("res/downAttack2.png");
		leftAttack1 = kit.getImage("res/leftAttack1.png");
		leftAttack2 = kit.getImage("res/leftAttack2.png");
		rightAttack1 = kit.getImage("res/rightAttack1.png");
		rightAttack2 = kit.getImage("res/rightAttack2.png");
		
		// Images for death animation
		death1 = kit.getImage("res/death1.png");
		death2 = kit.getImage("res/death2.png");
	}

	/* Updates certain values of the player based on the key that is pressed
	 * pre: KeyEvent event represents the key that the user presses
	 * post: none
	 */
	public void update(KeyEvent event) {
		if (gameOver == false) { // Make sure player can only move if they are not dead
			if (event.getKeyCode() == KeyEvent.VK_W) { // If player presses W to move up
				direction = "up";
				spriteY -= velocity;
				wallCheck();
			} 
			else if (event.getKeyCode() == KeyEvent.VK_A) { // If player presses A to move left
				direction = "left";
				spriteX -= velocity;
				wallCheck();
			} 
			else if (event.getKeyCode() == KeyEvent.VK_S) { // If player presses S to move down
				direction = "down";
				spriteY += velocity;
				wallCheck();
			}  
			else if (event.getKeyCode() == KeyEvent.VK_D) { // If player presses D to move right
				direction = "right";
				spriteX += velocity;
				wallCheck();
			} 
			else if (event.getKeyCode() == KeyEvent.VK_SPACE) { // If player presses space to dash
				originalDirection = direction; // Stores the direction before dashing
				if (direction.equals("left")) // If player dashes left
					direction = "leftDodge";
				else if (direction.equals("right")) // If player dashes right
					direction = "rightDodge";
				else if (direction.equals("up")) // If player dashes up
					direction = "upDodge";
				else if (direction.equals("down")) // If player dashes down
					direction = "downDodge";
				attackClock = 40; // 40 Frames before player can deal damage again
				attackType = "space"; // Sets the attack type to space
				game.playSoundEffects(4); // Plays the dash sound effect
			}

			if (event.getKeyCode() == KeyEvent.VK_Q && potionCount > 0) { // If player presses Q to use potion
				if (healthBars + 2 >= 10) // If player has more than or equal 10 health after using the potion
					healthBars = 10; // Sets health to 10
				else if (healthBars + 2 <= 10) // If player has less than or equal 10 health after using the potion
					healthBars += 2; // Adds two health
				potionCount--; // Reduces the potion count by 1
				game.randomSoundEffect(6, 7); // Plays a random potion drinking noise
			}
			
			if (event.getKeyCode() == KeyEvent.VK_B && game.coinCount > 0) { // If player presses B to buy potion and has coins
				game.coinCount--; // Reduces the coin coin
				potionCount++; // Increases the potion count
				game.playSoundEffects(10); // Plays sound effect for buying
			}
		}
	}

	/* Updates attack values of the player based on the direction they are facing when they press the mouse
	 * pre: MouseEvent event representing the left click button when the user presses it
	 * post: none
	 */
	public void update(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON1) { // If the button the user presses is left click
			originalDirection = direction; // Stores the direction before attacking
			if (direction.equals("up")) { // If the player is facing upwards
				direction = "upAttack";
				attackBoxX = spriteX + 16;
				attackBoxY = spriteY - 16;
			}
			else if (direction.equals("down")) { // If the player is facing downwards
				direction = "downAttack";
				attackBoxX = spriteX + 16;
				attackBoxY = spriteY + 48;
			}
			else if (direction.equals("left")) { // If the player is facing left
				direction = "leftAttack";
				attackBoxX = spriteX - 16;
				attackBoxY = spriteY + 16;
			}
			else if (direction.equals("right")) { // If the player is facing right
				direction = "rightAttack";
				attackBoxX = spriteX + 48;
				attackBoxY = spriteY + 16;
			}
			attackClock = 40; // 40 Frames before player can deal damage again
			attackType = "click"; // Sets the attack type to click
			game.randomSoundEffect(0, 1); // Playes a random attacking sound effect
		}
	}

	/* Checks if player is within the borders of the map and keeps it in the boundaries, and overrides the parent class wallCheck method
	 * pre: none
	 * post: none
	 */
	public void wallCheck() {
		if (spriteY + 16 < 40) // If player moves too far up
			spriteY = 40 - 16;
		if (spriteX + 16 < 50) // If player moves too far left
			spriteX = 50 - 16;
		if (spriteY + 16 > 360) // If player moves too far down
			spriteY = 360 - 16;
		if (spriteX + 16 > 640) // If player moves too far right
			spriteX = 640 - 16;
	}

	/* Draws the player on the screen and animates it using if statements
	 * pre: Graphics2D component2D used for various Graphics functions
	 * post: none
	 */
	public void draw(Graphics2D component2D) {
		Image image = null;
		clock++; // Increases clock counter
		if (clock > 10 && !direction.equals("leftDodge") && !direction.equals("rightDodge")
				&& !direction.equals("upDodge") && !direction.equals("downDodge") && !direction.equals("upAttack")
				&& !direction.equals("downAttack") && !direction.equals("leftAttack") && !direction.equals("rightAttack")
				&& !direction.equals("dead")) { // If player is standing still or moving normally and clock is greater than 10
			velocity = 10; // Sets velocity
			if (direction.equals("up")) // If player is moving upwards
				image = up2;
			else if (direction.equals("down")) // If player is moving downwards
				image = down2;
			else if (direction.equals("left")) // If player is moving left
				image = left2;
			else if (direction.equals("right")) // If player is moving right
				image = right2;
			if (clock > 20) // If clock is greater than 20
				clock = 0; // Resets clock back to 0
		}

		else if (clock < 10 && !direction.equals("leftDodge") && !direction.equals("rightDodge")
				&& !direction.equals("upDodge") && !direction.equals("downDodge") && !direction.equals("upAttack")
				&& !direction.equals("downAttack") && !direction.equals("leftAttack") && !direction.equals("rightAttack")
				&& !direction.equals("dead")) { // If player is standing still or moving normally and clock is less than 10
			velocity = 10; // Sets the velocity
			if (direction.equals("up")) // If player is moving upwards
				image = up1;
			if (direction.equals("down")) // If player is moving downwards
				image = down1;
			if (direction.equals("left")) // If player is moving left
				image = left1;
			if (direction.equals("right")) // If player is moving right
				image = right1;
		}

		else if (direction.equals("leftDodge") || direction.equals("rightDodge") || direction.equals("upDodge")
				|| direction.equals("downDodge")) { // If player is dashing
			if (direction.equals("leftDodge")) { // If player dashes left
				dodge1 = leftDodge1;
				dodge2 = leftDodge2;
				dodge3 = leftDodge3;
				dodge4 = leftDodge4;
				velocity = -2;
			} 
			else if (direction.equals("rightDodge")) { // If player dashes right
				dodge1 = rightDodge1;
				dodge2 = rightDodge2;
				dodge3 = rightDodge3;
				dodge4 = rightDodge4;
				velocity = 2;
			} 
			else if (direction.equals("upDodge")) { // If player dashes upwards
				dodge1 = upDodge1;
				dodge2 = upDodge2;
				dodge3 = upDodge3;
				dodge4 = upDodge4;
				velocity = -2;
			} 
			else if (direction.equals("downDodge")) { // If player dashes downwards
				dodge1 = downDodge1;
				dodge2 = downDodge2;
				dodge3 = downDodge3;
				dodge4 = downDodge4;
				velocity = 2;
			}

			// To animate the dash based on the clock counter
			if (clock >= 0 && clock < 10) // First 10 frames
				image = dodge1;
			else if (clock >= 10 && clock < 20) // Next 10 frames
				image = dodge2;
			else if (clock >= 20 && clock < 30) // Next 10 frames
				image = dodge3;
			else if (clock >= 30 && clock < 40) // Last 10 frames
				image = dodge4;

			if (direction.equals("leftDodge") || direction.equals("rightDodge")) // Changing the player's x coordinate during dash
				spriteX += velocity;
			else if (direction.equals("upDodge") || direction.equals("downDodge")) // Changing the player's y coordinate during dash
				spriteY += velocity;

			if (clock == 40) { // When clock reaches 40, the dash animation ends, values return to before the dash
				velocity = 10;
				clock = 0;
				direction = originalDirection; // Sets the direction back to before the dash
			}
			
			invincibilityFrames = 40; // Sets invincibility frames to 40 so player cannot take damage during the dash
			wallCheck(); // Checks if the player moves out of bounds during the dash
		}
		
		else if (direction.equals("upAttack") || direction.equals("downAttack") || direction.equals("leftAttack") 
				|| direction.equals("rightAttack")) { // If player attacks
			if (direction.equals("upAttack")) { // If player is facing up and attacks
				attack1 = upAttack1;
				attack2 = upAttack2;
			}
			else if (direction.equals("downAttack")) { // If player is facing down and attacks
				attack1 = downAttack1;
				attack2 = downAttack2;
			}
			else if (direction.equals("leftAttack")) { // If player is facing left and attacks
				attack1 = leftAttack1;
				attack2 = leftAttack2;
			}
			else if (direction.equals("rightAttack")) { // If player is facing right and attacks
				attack1 = rightAttack1;
				attack2 = rightAttack2;
			}
			
			// To animate the attack based on the clock counter
			if (clock >= 0 && clock < 20) // First 20 frames of attack
				image = attack1;
			else if (clock >= 20 && clock < 40) // Last 20 frames of attack
				image = attack2;
			
			if (clock == 40) { // When clock reaches 40, the attack animation ends
				clock = 0;
				direction = originalDirection; // Sets the direction back to before the attack
			}
		}

		// Health Bar
		component2D.setColor(Color.WHITE);
		component2D.fillRect(40, 10, 110, 20); // White background of health bar
		if (healthBars > 0) { // Only drawing the health bar if the health is greater than 0
			component2D.setColor(Color.RED);
			component2D.fillRect(45, 15, (int) Math.round(10 * healthBars), 10); // Drawing health bar based on remaining health
		}

		// If player is dead
		if (healthBars <= 0 && gameOver == false) { // When health bars reach 0 or less
			gameOver = true; // Sets gameOver to true
			direction = "dead";
			clock = 0;
			game.playSoundEffects(5); // Plays the death sound effect
		}
		if (direction.equals("dead")) { // Animates the dead player
			if (clock >= 0 && clock < 40) // First 40 frames of death animation
				image = death2;
			else if (clock >= 40 && clock < 80) // Last 40 frames of death animation
				image = death1;
			if (clock == 80) // When clock reaches 80, the death animation ends
				clock = 0;
			attackClock += 2;
			if (attackClock == 400) { // Death animation plays until 400 frames have passed
				EndScreen end = new EndScreen("res/died.png", "You Lose!", Color.RED, 12);
				Main.window.dispose();
			}
		}
		
		// Draws the player
		component2D.drawImage(image, spriteX, spriteY, 64, 64, null);
	}
}