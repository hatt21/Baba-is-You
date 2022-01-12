import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;

/**
 * BabaGameView manages the display of the game. This class is characterized by
 * the following information :
 * <ul>
 * <li>xorigin of the board</li>
 * <li>yorigin of the board</li>
 * <li>lengthsize of the screen</li>
 * <li>widthsize of the screen</li>
 * <li>squaresize of an entity</li>
 * </ul>
 * BabaGameView display the board, the winning and the loosing views.
 */
public class BabaGameView implements GameView {

	private final int xorigin;
	private final int yorigin;
	private final int lengthsize;
	private final int widthsize;
	private final int squaresize;

	// Private constructor, call by the static method : initGameGraphics
	private BabaGameView(int xOrigin, int yOrigin, int lengthsize, int widthsize, int squareSize) {
		this.xorigin = xOrigin;
		this.yorigin = yOrigin;
		this.lengthsize = lengthsize;
		this.widthsize = widthsize;
		this.squaresize = squareSize;
	}

	/**
	 * 
	 * Create a BabaGameView with the given arguments.
	 * 
	 * @param xorigin the x origin of the screen
	 * @param yorigin the y origin of the screen
	 * @param length  is the length of the screen
	 * @param width   is the width of the screen
	 * @param data    is all the data of the game
	 * @return an initialized BabaGameView Calculate the size of an element
	 *         depending on the number of line or column
	 */
	public static BabaGameView initGameGraphics(int xorigin, int yorigin, int length, int width, LevelManager data) {
		int squaresize = Math.min(((length - 1) / (data.getCols() + 1)), ((width - 1) / (data.getLines() + 1)));
		xorigin = (length - squaresize * (data.getCols() + 1)) / 2;
		yorigin = (width - squaresize * (data.getLines() + 1)) / 2;
		return new BabaGameView(xorigin, yorigin, length, width, squaresize);
	}

	@Override
	public void draw(Graphics2D graphics, LevelManager level, LevelDesign leveldesign) {
		graphics.setColor(Color.darkGray);
		graphics.fill(new Rectangle2D.Float(0, 0, lengthsize, widthsize));
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(xorigin, yorigin, squaresize * (level.getCols() + 1),
				squaresize * (level.getLines() + 1)));

		var elems = level.getData();
		ArrayList<BabaEntity> lstelems = new ArrayList<>(elems);

		Collections.sort(lstelems);
		var hashimg = leveldesign.getDataImage();

		for (var elem : lstelems) {
			for (var coord : elem.getCoords()) {
				graphics.drawImage(hashimg.get(elem).getImage(), coord.i() * squaresize + xorigin,
						coord.j() * squaresize + yorigin, squaresize, squaresize, null);
			}
		}
	}

	@Override
	public void win(Graphics2D graphics, LevelManager data) {
		var img = new ImageIcon("src/img/congratulation.png");
		graphics.drawImage(img.getImage(), lengthsize / 2 - (lengthsize / 4), widthsize / 2 - (widthsize / 8),
				(lengthsize / 2), (widthsize / 4), null);

	}

	@Override
	public void loose(Graphics2D graphics, LevelManager data) {
		var img = new ImageIcon("src/img/gameover.png");
		graphics.drawImage(img.getImage(), lengthsize / 2 - (lengthsize / 4), widthsize / 2 - (widthsize / 8),
				(lengthsize / 2), (widthsize / 4), null);

	}

}