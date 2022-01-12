/**
 * Enum of the possible operator text
 * 
 */
public enum OperatorEnum implements Word {
	/**
	 * IS operator
	 */
	IS("src/img/Text_IS_0.gif"),
	/**
	 * HAS operator
	 */
	HAS("src/img/Text_HAS_0.gif"),
	/**
	 * AND operator
	 */
	AND("src/img/Text_AND_0.gif");

	private final String fileimg;

	// Private constructor of OperatorEnum
	private OperatorEnum(String file) {
		this.fileimg = file;
	}

	@Override
	public String getFileImg() {
		return fileimg;
	}

}
