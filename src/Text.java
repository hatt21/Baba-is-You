import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Text defines a text element (noun, operator or property). This class is
 * characterized by the following information :
 * <ul>
 * <li>A word, which is a interface to define if it's a noun, operator or
 * property</li>
 * <li>A list of rule, defined as a list of PropertyEnum</li>
 * <li>A list of coordinates, defined as a list of Coordinate</li>
 * </ul>
 * Text is <b>push</b> by default.
 */
public class Text implements BabaEntity {
	private final Word word;
	private final List<PropertyEnum> ruleOfThisElem;
	private final List<Coordinate> coords;

	/**
	 * Create a new List of PropertyEnum and a new List of Coordinate.
	 * 
	 * @param word the argument word is either a noun or an operator or a property.
	 */
	public Text(Word word) {
		this.word = word;
		ruleOfThisElem = new ArrayList<>();
		ruleOfThisElem.add(PropertyEnum.PUSH);
		coords = new ArrayList<>();
	}

	@Override
	public String getFileImg() {
		return word.getFileImg();
	}

	@Override
	public List<PropertyEnum> getRuleOfElem() {
		return ruleOfThisElem;
	}

	@Override
	public String toString() {
		return word.toString();
	}

	@Override
	public void addCoord(Coordinate coord) {
		Objects.requireNonNull(coord);
		if (!coords.contains(coord)) {
			coords.add(coord);
		}
	}

	@Override
	public void removeCoord(Coordinate coord) {
		Objects.requireNonNull(coord);
		if (coords.contains(coord)) {
			coords.remove(coord);
		}
	}

	@Override
	public List<Coordinate> getCoords() {
		return coords;
	}

	/**
	 * Returns the word of the Text
	 * 
	 * @return the word of the Text
	 */
	public Word getWord() {
		return word;
	}

	@Override
	public int compareTo(BabaEntity o) {
		return 1;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Text) {
			Text text = (Text) o;
			return text.word.equals(word);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return word.hashCode();
	}
}
