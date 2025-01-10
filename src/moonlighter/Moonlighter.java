/* Andy Pak & Mustafa Merchant
 * Culminating Performance Task
 * ICS4U1
 * Monday, June 12th, 2023
 * Description: Moonlighter class, JPanel of the main game
 */
package moonlighter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Moonlighter extends JPanel implements Runnable, KeyListener, MouseListener {

	Thread gameThread; // Thread for the game
	Toolkit kit = Toolkit.getDefaultToolkit(); // Used to get the images into the game
	Image dungeon, heart, coin, coinBag, potion; // Images for various items and things on the screen
	public int coinCount; // Keeps track of the number of coins
	private int deadMonsterCount; // Keeps track of the number of dead monsters

	Player player = new Player(this); // Creating a player object
	// Creating slime objects
	Monster slime1 = new Monster(this, "slime");
	Monster slime2 = new Monster(this, "slime");
	// Creating golem objects
	Monster golem1 = new Monster(this, "golem");
	Monster golem2 = new Monster(this, "golem");
	// Creating sound effects object
	SoundEffects sound = new SoundEffects();
	
	Monster [] monsters = {slime1, slime2, golem1, golem2}; // Object array of monsters
	boolean[] soundPlayed = {false, false, false, false}; // Array parallel to monsters array to check if death noise is played
	public int monsterIndex; // Used to keep track of the index of monster

	/* Constructor for Moonlighter object
	 * pre: none
	 * post: A moonlighter object is created
	 */
	public Moonlighter() {
		super();
		this.setName("Moonlighter");
		setPreferredSize(new Dimension(720, 450)); // Setting the size of the window
		setDoubleBuffered(true); // Makes the painting of components smoother
		
		//Adding key and mouse listeners
		addKeyListener(this);
		addMouseListener(this);
		
		setFocusable(true);
		setLayout(null); // Getting rid of the default layout

		// Getting all the images
		dungeon = kit.getImage("res/dungeon.png");
		heart = kit.getImage("res/heart.png");
		coin = kit.getImage("res/coin.png");
		coinBag = kit.getImage("res/coinBag.png");
		potion = kit.getImage("res/potion.png");
		
		coinCount = 0;
		deadMonsterCount = 0;
		gameThread = new Thread(this); // Creating a new thread
		gameThread.start(); // Starting the thread
		setVisible(true);
	}

	/* To paint all the components of the game on the screen
	 * pre: Graphics g to use various graphics functions
	 * post: none
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		// Drawing the background, heart symbol, and coin bag symbol
		g2d.drawImage(dungeon, 0, 0, 720, 450, this);
		g2d.drawImage(heart, 0, 0, 40, 40, this);
		g2d.drawImage(coinBag, 0, 410, 40, 40, this);

		// Drawing the potions based on the number of potions the player has
		for (int i = 1; i <= player.potionCount; i++)
			g2d.drawImage(potion, 0, i * 40, 40, 40, this);
		
		// Drawing the coins based on the number of coins the player has
		for (int i = 1; i <= coinCount; i++)
			g2d.drawImage(coin, i * 40, 410, 40, 40, this);

		checkHealth(monsters, g2d); // Checks how many monsters are alive and draws them
		player.draw(g2d); // Draws the player

		if (intersects(player.spriteX + 16, player.spriteY + 16, monsters, g2d)) { // If the player collides with the monster
			if (player.invincibilityFrames == 0 && player.healthBars > 0) { // If the player can take damage and has health greater than 0
				randomSoundEffect(2, 3); // Plays a random take damage noise
				player.healthBars--; // Reducing the health
				player.invincibilityFrames = 60; // Setting invincibility frames to 60 so player cannot take damage for another 60 frames
			}
		}

		if (player.invincibilityFrames > 0) // If player's invincibility frames are greater than 0
			player.invincibilityFrames--; // Reduce the number of invinvibility frames
		
		if (player.attackClock > 0) { // If the player has attacked, attack clock is greater than 0
			if (player.attackType.equals("click")) { // If the player attack type is click
				if (intersects(player.attackBoxX, player.attackBoxY, monsters, g2d)) // Checks if the player's attack hit a monster
					monsterTakeDamage(); // Reduces the monster's health
			}
			else if (player.attackType.equals("space")) { // If the player attack type is space
				if (intersects(player.spriteX + 16, player.spriteY + 16, monsters, g2d)) // Checks if the player's attack hit a monster
					monsterTakeDamage(); // Reduces the monster's health
			}
		}
		
		if (player.attackClock > 0) // If player is on attack cooldown
			player.attackClock--; // Reduces attack clock by 1
		
		g2d.dispose(); // Disposing the 2D graphics each frame to save computer memory
	}

	/* Method that must be implemented when using KeyListener
	 * pre: KeyEvent event represents the key that the user presses
	 * post: none
	 * 
	 */
	public void keyTyped(KeyEvent event) {}

	/* Updates the status of the player object when a key is pressed
	 * pre: KeyEvent event represents the key that the user presses
	 * post: none
	 */
	public void keyPressed(KeyEvent event) {
		player.update(event); // Updates the status of player
	}

	/* Method that must be implemented when using KeyListner
	 * pre: KeyEvent event represents the key that the user presses
	 * post: none
	 */
	public void keyReleased(KeyEvent event) {}
	
	/* Updates the status of the player object when a mouse button is clicked
	 * pre: MoustEvent event represents the mouse button that the user presses
	 * post: none
	 */
	public void mouseClicked(MouseEvent event) {
		player.update(event); // Updates the status of player
	}

	/* Method that must be implemented when using MouseListener
	 * pre: MoustEvent event represents the mouse button that the user presses
	 * post: none 
	 */
	public void mousePressed(MouseEvent event) {}

	/* Method that must be implemented when using MouseListener
	 * pre: MoustEvent event represents the mouse button that the user presses
	 * post: none 
	 */
	public void mouseReleased(MouseEvent event) {}

	/* Method that must be implemented when using MouseListener
	 * pre: MoustEvent event represents the mouse button that the user presses
	 * post: none 
	 */
	public void mouseEntered(MouseEvent event) {}

	/* Method that must be implemented when using MouseListener
	 * pre: MoustEvent event represents the mouse button that the user presses
	 * post: none 
	 */
	public void mouseExited(MouseEvent event) {}

	/* Executes code inside when thread is running
	 * pre: none
	 * post: none
	 */
	public void run() {
		Thread thisThread = Thread.currentThread(); // Creates a new thread equal to the current thread
		while (gameThread == thisThread) { // If the two threads are equal to each other
			repaint(); // Calling the paint component method
			try {
				Thread.sleep((long) (1000.0 / 60.0)); // Causes this while loop to run at 60 frames per second
			} catch (InterruptedException e) {
			}
		}
	}
	
	/* Plays the sound effect at the specified index
	 * pre: int i represents the index of the URL array
	 * post: none
	 */
	public void playSoundEffects(int i) {
		sound.setFile(i); // Sets the clip to the sound at the specified index
		sound.play(); // Plays the sound
	}
	
	/* Randomly plays one of two sound effects
	 * pre: int option1 represents the index of the first option for sound effect,
	 * int option2 represents the index of the second option for sound effect
	 * post: none
	 */
	public void randomSoundEffect(int option1, int option2) {
		int randomNumber = (int)(Math.random()*(2 - 1 + 1) + 1);
		switch(randomNumber) { // Switch case to choose between two random sounds
		case 1:
			playSoundEffects(option1);
			break;
		case 2:
			playSoundEffects(option2);
			break;
		}
	}
	
	/* Checks the health status of each monster in the monster array
	 * pre: Monster[] arr an array of monster objects, Graphics2D g2d used to draw the monster
	 * post: none
	 */
	public void checkHealth(Monster [] arr, Graphics2D g2d) {
		for (int i = 0; i < arr.length; i++) { // For loop goes through the monster object array
			if (arr[i].healthBars > 0) // If monster has more than 0 health
				arr[i].draw(g2d); // Monster is drawn
			if (arr[i].invincibilityFrames > 0) // If monster has invincibility frames
				arr[i].invincibilityFrames--; // Reduces the number of invincibility frames
			
			if (arr[i].healthBars <= 0 && soundPlayed[i] == false) { // If monster is dead and a death sound hasn't played
				arr[i].dead = true; // Set dead boolean from monster class to be true
				if (arr[i].type.equals("golem")) // If the monster type is golem
					randomSoundEffect(8, 9); // Play random death noise
				soundPlayed[i] = true;
				deadMonsterCount++; // Increase the monster death count
				coinCount++; // Increase the player's coin coin
				arr[i].velocity = 0; // Set velocity to 0 so monster no longer moves
				// Setting the coordinates of the monster to off the screen so player doesn't collide with it
				arr[i].spriteX = 400;
				arr[i].spriteY = 800;
			}
		}
		if (deadMonsterCount == 4) { // If all the monsters have been killed
			EndScreen end = new EndScreen("res/youWin.jpg", "You Win!", Color.WHITE, 14);
			Main.window.dispose(); // Closes the main game window
		}
	}
	
	/* Checks if two boxes are intersecting each other
	 * pre: int pX represents the x coordinate of the rectangle, int pY represents the y coordinate of the rectangle,
	 * Monster[] arr an array of monster objects, Graphics2D g2d used to draw the hitboxes for testing
	 * post: boolean returns true if they intersect and false if they don't
	 */
	public boolean intersects(int pX, int pY, Monster[] arr, Graphics2D g2d) {
		Rectangle playerHitBox = new Rectangle(pX, pY, 32, 32); // Creating the player box
		Rectangle monsterHitBox; // Creating the monster box
		for (int i = 0; i < arr.length; i++) { // Goes through the monster object array to check for each monster
			monsterHitBox = new Rectangle(arr[i].spriteX, arr[i].spriteY, 32, 32);
//			g2d.draw(playerHitBox); // For testing
//			g2d.draw(monsterHitBox); // For testing
			if (playerHitBox.intersects(monsterHitBox)) { // If player box collides with monster hit box
				monsterIndex = i; // Setting the index to the monster that collides with player
				return true;
			}
		}
		return false;
	}
	
	/* Makes the monster take damage
	 * pre: none
	 * post: none
	 */
	public void monsterTakeDamage() {
		if (monsters[monsterIndex].invincibilityFrames == 0 && monsters[monsterIndex].healthBars > 0) { // If monster can take damage
			monsters[monsterIndex].healthBars--; // Reduces health bar
			monsters[monsterIndex].invincibilityFrames = 60; // Putting a 60 frame cooldown before monster can take damage again
		}
	}

	public static void main() {
		Moonlighter panel = new Moonlighter();
	}

}
