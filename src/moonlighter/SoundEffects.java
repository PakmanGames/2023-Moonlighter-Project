/*
 * Culminating Performance Task
 * ICS4U1
 * Monday, June 12th, 2023
 * Description: Sound Effects class, used for playing and looping sounds in the game
 */
package moonlighter;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffects {

	private Clip clip; // Clip object that is used to play sounds
	private URL soundURL[] = new URL[16]; // An array of URLs to hold the different sounds

	/* Constructor for the sound effects object
	 * pre: none
	 * post: A sound effects object is created
	 */
	public SoundEffects() {
		// Adding all the different sounds into the URL array
		soundURL[0] = getClass().getResource("/sound/attacking1.wav");
		soundURL[1] = getClass().getResource("/sound/attacking2.wav");
		soundURL[2] = getClass().getResource("/sound/damaged1.wav");
		soundURL[3] = getClass().getResource("/sound/damaged2.wav");
		soundURL[4] = getClass().getResource("/sound/dash.wav");
		soundURL[5] = getClass().getResource("/sound/dying.wav");
		soundURL[6] = getClass().getResource("/sound/potion1.wav");
		soundURL[7] = getClass().getResource("/sound/potion2.wav");
		soundURL[8] = getClass().getResource("/sound/golemDeath1.wav");
		soundURL[9] = getClass().getResource("/sound/golemDeath2.wav");
		soundURL[10] = getClass().getResource("/sound/purchase.wav");
		soundURL[11] = getClass().getResource("/sound/moonlighter.wav");
		soundURL[12] = getClass().getResource("/sound/death.wav");
		soundURL[13] = getClass().getResource("/sound/click.wav");
		soundURL[14] = getClass().getResource("/sound/victory.wav");
	}

	/* Sets the clip to the sound file that needs to be played
	 * pre: int i presenting the index of the URL array to play a certain sound
	 * post: none
	 */
	public void setFile(int i) {
		try {
			// Gets the audio input stream for the specific element in URL array
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip(); // Creates a new clip audio
			clip.open(ais); // Opens the audio stream
		} catch (Exception e) {
		}
	}

	/* Plays the clip audio
	 * pre: none
	 * post: none
	 */
	public void play() {
		clip.start();
	}

	/* Loops the clip audio
	 * pre: none
	 * post: none
	 */
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/* Stops the clip audio
	 * pre: none
	 * post: none
	 */
	public void stop() {
		clip.stop();
	}
}