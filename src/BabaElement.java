import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BabaElement defines element ( rock, baba, wall...). This class is
 * characterized by the following information :
 * <ul>
 * <li>The type of the Element defined with a NounImgEnum</li>
 * <li>A list of rule, defined as a list of PropertyEnum</li>
 * <li>A list of coordinates, defined as a list of Coordinate</li>
 * </ul>
 * BabaElement manages for an element, where is he (his coordinates), his rules
 * and the comparison between another BabaElement
 */
public class BabaElement implements BabaEntity {
	private final NounImgEnum element;
	private final List<PropertyEnum> ruleOfThisElem;
	private final List<Coordinate> coords;

	/**
	 * Create a new List of PropertyEnum and a new List of Coordinate
	 * 
	 * @param type the argument type is a NounImgEnum
	 */
	public BabaElement(NounImgEnum type) {
		this.element = type;
		ruleOfThisElem = new ArrayList<>();
		coords = new ArrayList<>();
	}

	@Override
	public String getFileImg() {
		return element.getFileImg();
	}

	@Override
	public List<PropertyEnum> getRuleOfElem() {
		return ruleOfThisElem;
	}

	/**
	 * Add a rule for a BabaElement
	 * 
	 * @param prop prop is a PropertyEnum
	 */
	public void addRule(PropertyEnum prop) {
		Objects.requireNonNull(prop);
		if (!ruleOfThisElem.contains(prop)) {
			ruleOfThisElem.add(prop);
		}
	}

	/**
	 * Remove a rule for a BabaElement
	 * 
	 * @param prop prop is a PropertyEnum
	 */
	public void removeRule(PropertyEnum prop) {
		Objects.requireNonNull(prop);
		ruleOfThisElem.remove(prop);
	}

	@Override
	public String toString() {
		return element.toString();
	}

	/**
	 * Returns the NounImgEnum type of the BabaElement
	 * 
	 * @return a NounImgEnum of the element
	 */
	public NounImgEnum getElement() {
		return element;
	}

	@Override
	public void addCoord(Coordinate coord) {
		Objects.requireNonNull(coord);
		coords.add(coord);
	}

	@Override
	public void removeCoord(Coordinate coord) {
		Objects.requireNonNull(coord);
		coords.remove(coord);
	}

	@Override
	public List<Coordinate> getCoords() {
		return coords;
	}

	@Override
	public int compareTo(BabaEntity o) {
		if (o instanceof Text) {
			return -10;
		} else if (ruleOfThisElem.contains(PropertyEnum.YOU)) {
			return 10;
		} else if (o.getRuleOfElem().contains(PropertyEnum.YOU)) {
			return -10;
		}
		var elem = (BabaElement) o;

		return element.getOpacity() - elem.element.getOpacity();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof BabaElement) {
			BabaElement babaentity = (BabaElement) o;
			return (babaentity.element).equals(element);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return element.hashCode();
	}
}
