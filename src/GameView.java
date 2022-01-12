import java.awt.Graphics2D;

import fr.umlv.zen5.ApplicationContext;

/**
 * Interface for the different views of a game.
 */
public interface GameView {
	/**
	 * Draws the GUI of a game
	 * 
	 * @param graphics    the graphics is used for the game
	 * @param leveldesign the bank of ImageIcon already loaded
	 * @param data        is all the data of the game
	 */
	public void draw(Graphics2D graphics, LevelManager data, LevelDesign leveldesign);

	/**
	 * Draws the GUI of the game
	 * 
	 * @param context     the context application
	 * @param data        is all the data of the game
	 * @param view        the view of the game
	 * @param leveldesign the bank of ImageIcon already loaded
	 */
	public static void draw(ApplicationContext context, LevelManager data, BabaGameView view, LevelDesign leveldesign) {

		context.renderFrame(graphics -> {
			view.draw(graphics, data, leveldesign);

		});
	}

	/**
	 * Draws the winning view of a game
	 * 
	 * @param graphics the graphics is used for the game
	 * @param data     is all the data of the game
	 */
	void win(Graphics2D graphics, LevelManager data);

	/**
	 * Draws the winning view of the game
	 * 
	 * @param context the context application
	 * @param data    is all the data of the game
	 * @param view    the view of the game
	 */
	static void win(ApplicationContext context, LevelManager data, BabaGameView view) {

		context.renderFrame(graphics -> {
			view.win(graphics, data);

		});
	}

	/**
	 * Draws the loosing view of a game
	 * 
	 * @param graphics the graphics is used for the game
	 * @param data     is all the data of the game
	 */
	void loose(Graphics2D graphics, LevelManager data);

	/**
	 * Draws the loosing view of the game
	 * 
	 * @param context the context application
	 * @param data    is all the data of the game
	 * @param view    the view of the game
	 */
	static void loose(ApplicationContext context, LevelManager data, BabaGameView view) {

		context.renderFrame(graphics -> {
			view.loose(graphics, data);

		});
	}
}
