/* Andy Pak & Mustafa Merchant
 * Culminating Performance Task
 * ICS4U1
 * Monday, June 12th, 2023
 * Description: Entity class, parent class of monster and player classes
 */

package moonlighter;
import java.awt.*;

public class Entity {
	
	Toolkit kit = Toolkit.getDefaultToolkit(); // Used to get the images into the game
	Moonlighter game;
	public int spriteX, spriteY; // x and y coordinates of the entity
	public int velocity; // Velocity of the entity
	public int invincibilityFrames; // Invincibility Frames to give entity a pause from taking damage
	public int ranNum; // Used for switch cases to perform random actions
	public int healthBars; // Represents the health of the entity
	public int clock; // This counter works in tandem with thread, increasing in value 60 times a second

	/* Constructor to create an entity object
	 * pre: Moonlighter game is an object of the Moonlighter class, int velocity represents the entity's velocity,
	 * int healthBars is the entity's health, int invincibilityFrames is how many frames before the entity can get hit again,
	 * int clock is a counter used with the game thread
	 * post: An entity object is created
	 */
	public Entity(Moonlighter game, int velocity, int healthBars, int invincibilityFrames, int clock) {
		// Setting the values of this object with what was passed through the constructor
		this.game = game;
		this.velocity = velocity;
		this.healthBars = healthBars;
		this.invincibilityFrames = invincibilityFrames;
		this.clock = clock;
	}
	
	/* Checks if entity is within the borders of the map and keeps it in the boundaries
	 * pre: none
	 * post: none
	 */
	public void wallCheck() {
		if (spriteY < 40) // If entity moves too far up
			spriteY = 40;
		if (spriteX < 50) // If entity moves too far left
			spriteX = 50;
		if (spriteY > 360) // If entity moves too far down
			spriteY = 360;
		if (spriteX > 640) // If entity moves too far right
			spriteX = 640;
	}
}