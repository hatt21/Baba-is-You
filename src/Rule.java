import java.util.List;
import java.util.Objects;

/**
 * Rule defines a sentence ( noun + operator + (property or noun) ). This class
 * is characterized by the following information :
 * <ul>
 * <li>A Word, for the noun</li>
 * <li>A Word, for the operator</li>
 * <li>A Word, for the property or the other noun</li>
 * </ul>
 * Rule can return the correct BabaElement associated to the rule
 */
public class Rule {
	private final Word noun;
	private final Word op;
	private final Word propOrNoun;

	/**
	 * Create a new Rule with the following arguments.
	 * 
	 * @param noun the noun of the rule
	 * @param op   the operator of the rule
	 * @param prop the property of the rule (or the other noun of the rule)
	 */
	public Rule(Word noun, OperatorEnum op, Word prop) {
		this.noun = noun;
		this.op = op;
		this.propOrNoun = prop;
	}

	/**
	 * Returns the noun of the rule.
	 * 
	 * @return the noun of the rule
	 */
	public Word getNoun() {
		return noun;
	}

	/**
	 * Returns the property or the other noun of the rule.
	 * 
	 * @return the property or the other noun of the rule.
	 */
	public Word getProp() {

		return propOrNoun;
	}

	/**
	 * Returns true if the property of the rule is YOU.
	 * 
	 * @return true if the property of the rule is YOU.
	 */
	public boolean isYouRule() {
		if (propOrNoun.equals(PropertyEnum.YOU)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a string representation of the rule.
	 * 
	 * @return a string representation of the rule.
	 */
	@Override
	public String toString() {
		return noun.toString() + " " + op.toString() + " " + propOrNoun.toString();
	}

	/**
	 * Returns the BabaElement associated to the noun of the rule.
	 * 
	 * @param elements the list of all the BabaElement of the game
	 * @return the BabaElement associated to the noun of the rule.
	 */
	public BabaElement getEntityFromNoun(List<BabaElement> elements) {
		Objects.requireNonNull(elements);
		return getEntityFrom(elements, (NounTextEnum) noun);
	}

	/**
	 * Returns the BabaElement associated to the attribute propOrNoun of the rule.
	 * 
	 * @param elements the list of all the BabaElement of the game
	 * @return the BabaElement associated to the attribute propOrNoun of the rule.
	 */
	public BabaElement getEntityFromNounText(List<BabaElement> elements) {
		Objects.requireNonNull(elements);
		return getEntityFrom(elements, (NounTextEnum) propOrNoun);
	}

	// Search the NounImgEnum associated to the NounTextEnum.
	// Returns the BabaElement associated to the NounImgEnum.
	private BabaElement getEntityFrom(List<BabaElement> elements, NounTextEnum word) {
		for (var elem : elements) {
			if (elem.getElement().equals(NounImgEnum.valueOf(word.name()))) {
				return elem;
			}
		}
		return new BabaElement(NounImgEnum.valueOf(word.name()));

	}
}
