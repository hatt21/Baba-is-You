/**
 * Enum of the possible element
 * 
 */
public enum NounImgEnum {
	/**
	 * BABA element
	 * 
	 */
	BABA("src/img/Baba_0.gif", 10),
	/**
	 * LAVA element
	 */
	LAVA("src/img/LAVA_0.gif", -2),
	/**
	 * COG element
	 */
	COG("src/img/COG_0.gif", 2),
	/**
	 * FLAG element
	 */
	FLAG("src/img/FLAG_0.gif", 5),
	/**
	 * SKULL element
	 */
	SKULL("src/img/SKULL_0.gif", 3),
	/**
	 * WALL element
	 */
	WALL("src/img/WALL_0.gif", -3),
	/**
	 * WATER element
	 */
	WATER("src/img/WATER_0.gif", -1),
	/**
	 * ROCK element
	 */
	ROCK("src/img/ROCK_0.gif", 4),
	/**
	 * TILE element
	 */
	TILE("src/img/TILE_0.gif", -8),
	/**
	 * GRASS element
	 */
	GRASS("src/img/GRASS_0.gif", -6),
	/**
	 * FLOWER element
	 */
	FLOWER("src/img/FLOWER_0.gif", -5),
	/**
	 * BRICK element
	 */
	BRICK("src/img/BRICK_0.gif", -7);

	private final String fileimg;
	private final int opacity;

	// Private constructor of NounImgEnum
	private NounImgEnum(String fileimg, int opacity) {
		this.fileimg = fileimg;
		this.opacity = opacity;
	}

	/**
	 * Returns the filename of the element
	 * 
	 * @return the filename of the element
	 */
	public String getFileImg() {
		return fileimg;
	}

	/**
	 * Returns the opacity index of the element
	 * 
	 * @return the opacity index of the element
	 */
	public int getOpacity() {
		return opacity;
	}

}
