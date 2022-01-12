/**
 * Word is a sealed interface and permits NounTextEnum, OperatorEnum or
 * PropertyEnum.
 */
public sealed interface Word permits NounTextEnum,OperatorEnum,PropertyEnum {
	/**
	 * Returns the filename of the element
	 * 
	 * @return the filename of the element
	 */
	String getFileImg();
}
