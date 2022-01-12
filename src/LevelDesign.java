import java.util.HashMap;
import java.util.Objects;

import javax.swing.ImageIcon;

/**
 * LevelDesign stock for each element its imageicon. This class is characterized
 * by the following information :
 * <ul>
 * <li>A bank of imageicon called dataImage</li>
 * </ul>
 */
public class LevelDesign {
	private final HashMap<BabaEntity, ImageIcon> dataImage;

	/**
	 * Create a new LevelDesign : a new bank of imageIcon
	 */
	public LevelDesign() {
		dataImage = new HashMap<>();
	}

	/**
	 * Add an entity and its imageicon.
	 * 
	 * @param entity the entity to add to the bank of image
	 */
	public void add(BabaEntity entity) {
		Objects.requireNonNull(entity);
		if (!dataImage.containsKey(entity)) {
			dataImage.put(entity, new ImageIcon(entity.getFileImg()));
		}
	}

	/**
	 * Returns the bank of image
	 * 
	 * @return the bank of image
	 */
	public HashMap<BabaEntity, ImageIcon> getDataImage() {
		return dataImage;
	}
}
