/**
 * Enum of the possible noun text
 * 
 */
public enum NounTextEnum implements Word {
	/**
	 * BABA noun
	 * 
	 */
	BABA("src/img/Text_BABA_0.gif"),
	/**
	 * LAVA noun
	 */
	LAVA("src/img/Text_LAVA_0.gif"),
	/**
	 * COG noun
	 */
	COG("src/img/Text_COG_0.gif"),
	/**
	 * FLAG noun
	 */
	FLAG("src/img/Text_FLAG_0.gif"),
	/**
	 * SKULL noun
	 */
	SKULL("src/img/Text_SKULL_0.gif"),
	/**
	 * WALL noun
	 */
	WALL("src/img/Text_WALL_0.gif"),
	/**
	 * WATER noun
	 */
	WATER("src/img/Text_WATER_0.gif"),
	/**
	 * ROCK noun
	 */
	ROCK("src/img/Text_ROCK_0.gif"),
	/**
	 * TEXT noun
	 */
	TEXT("src/img/Text_TEXT_0.gif"),
	/**
	 * TILE noun
	 */
	TILE("src/img/Text_TILE_0.gif"),
	/**
	 * GRASS noun
	 */
	GRASS("src/img/Text_GRASS_0.gif"),
	/**
	 * FLOWER noun
	 */
	FLOWER("src/img/Text_FLOWER_0.gif"),
	/**
	 * BRICK noun
	 */
	BRICK("src/img/Text_BRICK_0.gif");

	private final String filetext;

	// Private constructor of NounTextEnum
	private NounTextEnum(String filetext) {
		this.filetext = filetext;
	}

	@Override
	public String getFileImg() {
		return filetext;
	}

}
