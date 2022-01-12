/**
 * Enum of the possible property text
 * 
 */
public enum PropertyEnum implements Word {
	/**
	 * DEFEAT property
	 */
	DEFEAT("src/img/Text_DEFEAT_0.gif"),
	/**
	 * HOT property
	 */
	HOT("src/img/Text_HOT_0.gif"),
	/**
	 * MELT property
	 */
	MELT("src/img/Text_MELT_0.gif"),
	/**
	 * PUSH property
	 */
	PUSH("src/img/Text_PUSH_0.gif"),
	/**
	 * SINK property
	 */
	SINK("src/img/Text_SINK_0.gif"),
	/**
	 * STOP property
	 */
	STOP("src/img/Text_STOP_0.gif"),
	/**
	 * WIN property
	 */
	WIN("src/img/Text_WIN_0.gif"),
	/**
	 * YOU property
	 */
	YOU("src/img/Text_YOU_0.gif"),
	/**
	 * GHOSTLY property
	 */
	GHOSTLY("src/img/Text_GHOSTLY_0.gif");

	private final String fileimg;

	// Private constructor of PropertyEnum
	private PropertyEnum(String file) {
		this.fileimg = file;
	}

	@Override
	public String getFileImg() {
		return fileimg;
	}

}
