import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import fr.umlv.zen5.KeyboardKey;

/**
 * LevelManager manages the level of the game. This class is characterized by
 * the following information :
 * <ul>
 * <li>The number of lines of the board</li>
 * <li>The number of columns of the board</li>
 * <li>A list of all the entity</li>
 * <li>A RuleManager of the board</li>
 * </ul>
 */
public class LevelManager {
	private final int lines;
	private final int cols;
	private List<BabaEntity> entities;
	private final RuleManager rulemanager;

	/**
	 * Create a new LevelManager.
	 * 
	 * @param lines the number of lines of the board
	 * @param cols  the number of columns of the board
	 */
	public LevelManager(int lines, int cols) {
		this.lines = lines;
		this.cols = cols;
		entities = new ArrayList<>();
		rulemanager = new RuleManager();
	}

	/**
	 * Add a coordinate to an Entity
	 * 
	 * @param coord  the coordinate to add
	 * @param entity the entity where we add the coordinate
	 */
	public void add(Coordinate coord, BabaEntity entity) {
		Objects.requireNonNull(entity);
		Objects.requireNonNull(coord);
		this.addEntity(entity);
		for (var elem : entities) {
			if (elem.equals(entity)) {
				elem.addCoord(coord);
			}
		}
	}

	/**
	 * Add an Entity to the level
	 * 
	 * @param entity the entity to add
	 */
	public void addEntity(BabaEntity entity) {
		Objects.requireNonNull(entity);
		if (!entities.contains(entity)) {
			entities.add(entity);
		}
	}

	/**
	 * Add a list of coordinate to an entity
	 * 
	 * @param coords the list of coordinate to add
	 * @param entity the entity where we add the coordinate
	 */
	public void add(List<Coordinate> coords, BabaEntity entity) {
		Objects.requireNonNull(coords);
		for (var coord : coords) {
			add(coord, entity);
		}
	}

	/**
	 * Add a coordinate to a list of entities
	 * 
	 * @param coord    the coordinate to add
	 * @param entities the list of entities where we add the coordinate
	 */
	public void add(Coordinate coord, List<BabaEntity> entities) {
		Objects.requireNonNull(entities);
		for (var entity : entities) {
			add(coord, entity);
		}
	}

	/**
	 * Remove a coordinate to an entity
	 * 
	 * @param coord  the coordinate to remove
	 * @param entity the entity where we remove the coordinate
	 */
	public void remove(Coordinate coord, BabaEntity entity) {
		Objects.requireNonNull(entity);
		Objects.requireNonNull(coord);
		if (entities.contains(entity)) {
			entity.removeCoord(coord);
		}
	}

	/**
	 * Remove a coordinate to a list of Entities
	 * 
	 * @param coord    the coordinate to remove
	 * @param entities the list of entities where we remove the coordinate
	 */
	public void remove(Coordinate coord, List<BabaEntity> entities) {
		Objects.requireNonNull(entities);
		for (var entity : entities) {
			remove(coord, entity);
		}
	}

	/**
	 * Returns the list of Entity of the level.
	 * 
	 * @return the list of Entity of the level.
	 */
	public List<BabaEntity> getData() {
		return entities;
	}

	/**
	 * Move all the YOU's Entities of the level
	 * 
	 * @param keyboard the moving direction
	 * @return true if a YOU's entity is on a Win element. False otherwise
	 */
	public boolean move(KeyboardKey keyboard) {
		Objects.requireNonNull(keyboard);
		var youentitys = ElemWithThisRule(PropertyEnum.YOU);
		for (var entity : youentitys) { // For all YOU's element
			var lstcoordsofyou = List.copyOf(entity.getCoords());
			var mapcoordsofyou = lstcoordsofyou.stream() // Map of the coord and the number of times the coordinate
															// appears
					.collect(Collectors.groupingBy(coord -> coord, Collectors.counting()));
			for (var coord : mapcoordsofyou.keySet()) {
				keyboardToMove(keyboard, coord, entity, mapcoordsofyou.get(coord));
			}
		}
		// Detect and Update all the rule
		updateAnddetectRule();

		// After everything has been moved
		checkSink();
		checkMeltAndHot();
		checkDefeat();
		return checkIfWin();
	}

	// Return true if a YOU's entity is on a Win element. False otherwise
	private boolean checkIfWin() {
		var lstcoordswin = ElemWithThisRule(PropertyEnum.WIN).stream().flatMap(entity -> entity.getCoords().stream())
				.collect(Collectors.toList()); // All the WIN coordinate
		for (var elem : entities) {
			if (elem.checkWin(lstcoordswin)) { // If the elem is YOU and on a coordinate in lstcoordswin
				return true;
			}
		}
		return false;
	}

	private void checkDefeat() {
		var lstcoordsdefeat = ElemWithThisRule(PropertyEnum.DEFEAT).stream()
				.flatMap(entity -> entity.getCoords().stream()).collect(Collectors.toList());// All the DEFEAT
																								// coordinate
		for (var elem : entities) {
			elem.checkDefeat(lstcoordsdefeat); // If the elem is YOU and on a coordinate in lstcoordsdefeat
		}
	}

	private void checkSink() {
		var lstsinkelems = ElemWithThisRule(PropertyEnum.SINK); // All SINK element
		for (var elem : lstsinkelems) {
			var copycoords = List.copyOf(elem.getCoords());
			for (var coord : copycoords) {
				var lstentityincoord = getEntityFromCoord(coord); // All element on this coordinate
				lstentityincoord.remove(elem); // Removing the sink element
				if (!lstentityincoord.isEmpty()) { // if true : there is an element on this coordinate
					remove(coord, lstentityincoord.get(0)); // We remove the element
					remove(coord, elem); // We remove the SINK Element
					continue;
				}
			}
		}
	}

	private void checkMeltAndHot() {
		var lstcoordshot = ElemWithThisRule(PropertyEnum.HOT).stream().flatMap(entity -> entity.getCoords().stream())
				.collect(Collectors.toList());// All the HOT coordinate
		for (var elem : entities) {
			elem.checkAndDeleteMeltandHot(lstcoordshot); // If the element is MELT and on a coordinate in lstcoordshot
		}
	}

	/**
	 * Transforms a keyboard to a moving direction
	 * 
	 * @param keyboard the keyboard pressed
	 * @param coord    the coord of the YOU entity
	 * @param entity   the YOU entity
	 * @param nbtomove number of the same YOU entity on this coordinate
	 */
	private void keyboardToMove(KeyboardKey keyboard, Coordinate coord, BabaEntity entity, long nbtomove) {
		Objects.requireNonNull(entity);
		var listentities = new ArrayList<BabaEntity>();
		for (var i = 0; i < nbtomove; i++) { // Creating the list of entities to move
			listentities.add(entity);
		}
		switch (keyboard) {
		case UP:
			moveEntity(new Coordinate(coord.i(), coord.j() - 1), coord, listentities);
			break;
		case DOWN:
			moveEntity(new Coordinate(coord.i(), coord.j() + 1), coord, listentities);
			break;
		case LEFT:
			moveEntity(new Coordinate(coord.i() - 1, coord.j()), coord, listentities);
			break;
		case RIGHT:
			moveEntity(new Coordinate(coord.i() + 1, coord.j()), coord, listentities);
			break;
		default:
			break;
		}

	}

	// Recursive method to check if we can move elements in the currentcoord
	// We have to take care of STOP elements and PUSH elements
	// We introduce a GHOSTLY property, it canceled the property STOP of an element
	private boolean moveEntity(Coordinate newcoord, Coordinate currentcoord, List<BabaEntity> elems) {
		var i = newcoord.i();
		var j = newcoord.j();
		if ((i >= 0 && i <= cols) && (j >= 0 && j <= lines)) { // In the limits of the board

			if (!entities.stream().flatMap(entity -> entity.getCoords().stream())
					.anyMatch(coord -> coord.equals(newcoord))) { // if there is nothing on the new Coord
				add(newcoord, elems);
				remove(currentcoord, elems);
				return true;
			} else {

				// Map of PUSH elems in the new coord and how many there are
				var lstpushelems = ElemWithThisRule(PropertyEnum.PUSH).stream().filter(
						entity -> entity.isOnThisCoord(newcoord) && !entity.getRuleOfElem().contains(PropertyEnum.YOU))
						.collect(Collectors.toMap(elem -> elem, elem -> elem.nbOfElementInThisCoord(newcoord)));

				// List of stop (and not ghostly) elems in the new coord
				var lststopelems = ElemWithThisRule(PropertyEnum.STOP).stream()
						.filter(entity -> (entity.isOnThisCoord(newcoord)
								&& !entity.getRuleOfElem().contains(PropertyEnum.GHOSTLY)))
						.collect(Collectors.toList());

				lststopelems.removeAll(lstpushelems.keySet()); // If stop elems are pushable

				// If there are PUSH elem and not STOP elem
				if ((!lstpushelems.isEmpty() && lststopelems.isEmpty()) || (!lstpushelems.isEmpty()
						&& elems.stream().allMatch(entity -> entity.getRuleOfElem().contains(PropertyEnum.GHOSTLY)))) {

					var listentities = new ArrayList<BabaEntity>();
					for (var elem : lstpushelems.keySet()) {
						for (var x = 0; x < lstpushelems.get(elem); x++) { // Creating the list of entities to move
							listentities.add(elem);
						}
					}

					if (moveEntity(new Coordinate(i + (i - currentcoord.i()), j + (j - currentcoord.j())), newcoord,
							listentities)) { // If we can move elements in the current coord

						add(newcoord, elems);
						remove(currentcoord, elems);
						return true;
					}
				}
				// if there is no STOP element or there are but there are GHOSTLY
				else if ((lststopelems.isEmpty()) || (!lststopelems.isEmpty()
						&& elems.stream().allMatch(entity -> entity.getRuleOfElem().contains(PropertyEnum.GHOSTLY)))) {
					add(newcoord, elems);
					remove(currentcoord, elems);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns the number of columns of the level.
	 * 
	 * @return the number of columns of the level.
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Returns the number of lines of the level.
	 * 
	 * @return the number of lines of the level.
	 */
	public int getLines() {
		return lines;
	}

	/**
	 * Detect and update rule of the board.
	 */
	public void updateAnddetectRule() {
		rulemanager.updateAnddetectRule(getAllText(), getAllElement());
	}

	/**
	 * Returns all the Text of the level.
	 * 
	 * @return all the Text of the level.
	 */
	public List<Text> getAllText() {
		return entities.stream().filter(entity -> entity instanceof Text).map(text -> (Text) text)
				.collect(Collectors.toList());
	}

	/**
	 * Returns all the BabaElement of the level.
	 * 
	 * @return all the BabaElement of the level.
	 */
	public List<BabaElement> getAllElement() {
		return entities.stream().filter(entity -> entity instanceof BabaElement).map(elem -> (BabaElement) elem)
				.collect(Collectors.toList());
	}

	/**
	 * Returns all entities on the coordinate.
	 * 
	 * @param coordinate the coordinate to check
	 * @return all the entity on the coordinate
	 */
	public List<BabaEntity> getEntityFromCoord(Coordinate coordinate) {
		Objects.requireNonNull(coordinate);
		var lstentity = new ArrayList<BabaEntity>();
		for (var entity : entities) {
			var lstcoords = entity.getCoords();
			for (var coord : lstcoords) {
				if (coord.equals(coordinate)) {
					lstentity.add(entity);
				}
			}
		}
		return lstentity;
	}

	/**
	 * Returns all the BabaEntity with the following property.
	 * 
	 * @param rule the property to check
	 * @return all the BabaEntity with the following property.
	 */
	public List<BabaEntity> ElemWithThisRule(PropertyEnum rule) {
		return entities.stream().filter(entity -> entity.getRuleOfElem().contains(rule)).collect(Collectors.toList());
	}

}