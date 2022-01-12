import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * RuleManager manages all the rules of the board. This class is characterized
 * by the following information :
 * <ul>
 * <li>A list of rule called : rulemanager</li>
 * </ul>
 * RuleManager associate to an Entity a rule.
 */
public class RuleManager {
	private final List<Rule> rulemanager;

	/**
	 * Create a new RuleManager.
	 */
	public RuleManager() {
		rulemanager = new ArrayList<>();
	}

	/**
	 * Add a rule to the RuleManager.
	 * 
	 * @param rule the rule to add to the RuleManager
	 */
	public void add(Rule rule) {
		Objects.requireNonNull(rule);
		if (!rulemanager.contains(rule)) {
			rulemanager.add(rule);
		}
	}

	/**
	 * Removes a rule to the RuleManager.
	 * 
	 * @param rule the rule to remove to the RuleManager
	 */
	public void remove(Rule rule) {
		Objects.requireNonNull(rule);
		rulemanager.remove(rule);
	}

	/**
	 * Removes all the rules to the RuleManager.
	 */
	public void removeAll() {
		rulemanager.clear();
	}

	/**
	 * Detect and associate all the rule of the board to a BabaElement.
	 * 
	 * @param texts    list of all the Text in the board
	 * @param elements list of all the BabaElement in the board
	 */
	public void updateAnddetectRule(List<Text> texts, List<BabaElement> elements) {
		Objects.requireNonNull(texts);
		var copyrule = List.copyOf(rulemanager); // Copy the old rulemanager
		removeAll(); // Remove all the rules
		for (var text : texts) { // Detect all the new rules
			if (text.getWord() instanceof NounTextEnum) {
				var lstcoords = text.getCoords();
				for (var coord : lstcoords) {
					isCorrectRule((NounTextEnum) text.getWord(), null, null, 0, 1, coord, texts);
					isCorrectRule((NounTextEnum) text.getWord(), null, null, 1, 0, coord, texts);
				}
			}
		}

		compareAndApplyRules(copyrule, elements);
		updateRule(elements);
	}

	// Compare the old rulemanager and the new rulemanager.
	// Add or remove the rule only if there are differences between the old and the
	// new rulemanager
	private void compareAndApplyRules(List<Rule> copyrule, List<BabaElement> elements) {
		Objects.requireNonNull(copyrule);
		if (!copyrule.isEmpty()) {
			for (var rule : copyrule) {
				if (!rulemanager.contains(rule)) { // If a rule is missing
					if (rule.getProp() instanceof PropertyEnum) {
						rule.getEntityFromNoun(elements).removeRule((PropertyEnum) rule.getProp()); // We delete the
																									// property to the
																									// BabaElement
					}
				}
			}
			for (var rule : rulemanager) {
				if (!copyrule.contains(rule)) { // If a rule has been added
					if (rule.getProp() instanceof PropertyEnum) {
						rule.getEntityFromNoun(elements).addRule((PropertyEnum) rule.getProp()); // We add the property
																									// to the
																									// BabaElement
					}
				}
			}

		}
	}

	// Recursive method to find a Rule
	private void isCorrectRule(NounTextEnum noun, OperatorEnum op, Word propOrNoun, int i, int j, Coordinate coord,
			List<Text> texts) {
		if (noun != null && op != null && propOrNoun != null) { // If a Rule is found
			var rule = new Rule(noun, op, propOrNoun);
			rulemanager.add(rule);
		} else {
			var newcoord = new Coordinate(coord.i() + i, coord.j() + j);
			var textsOnNewCoord = texts.stream().filter(text -> text.getCoords().contains(newcoord))
					.collect(Collectors.toList()); // Texts on the next coordinate
			for (var text : textsOnNewCoord) {
				if (text.getWord() instanceof OperatorEnum && noun != null) { // If we found an Operator
					isCorrectRule(noun, (OperatorEnum) text.getWord(), null, i, j, newcoord, texts);
				} else if ((text.getWord() instanceof PropertyEnum
						|| (text.getWord() instanceof NounTextEnum) && op != null)) { // If we found a Property or a
																						// Noun
					isCorrectRule(noun, op, text.getWord(), i, j, newcoord, texts);
				}
			}
		}
	}

	/**
	 * Returns the list of all the rule of the board.
	 * 
	 * @return the list of all the rule of the board.
	 */
	public List<Rule> getRulemanager() {
		return rulemanager;
	}

	/**
	 * Apply all the rule of the board.
	 * 
	 * @param elems the list of all the BabaElement of the board
	 */
	public void updateRule(List<BabaElement> elems) {
		Objects.requireNonNull(elems);
		for (var rule : rulemanager) {
			var entity = rule.getEntityFromNoun(elems);
			if (entity != null) {
				if (rule.getProp() instanceof PropertyEnum) {
					entity.addRule((PropertyEnum) rule.getProp());
				} else if (rule.getProp() instanceof NounTextEnum) {
					var entity2 = rule.getEntityFromNounText(elems);
					var lstcoords = List.copyOf(entity.getCoords());
					for (var coord : lstcoords) {
						entity2.addCoord(coord);
						entity.removeCoord(coord);
					}
				}
			}
		}
	}

	/**
	 * Returns a string representation of the RuleManager.
	 * 
	 * @return a string representation of the RuleManager.
	 */
	@Override
	public String toString() {
		return rulemanager.toString();
	}
}
