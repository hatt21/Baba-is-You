import java.util.List;
import java.util.Objects;

/**
 * BabaEntity defines either a BabaElement or a Text.
 */
public interface BabaEntity extends Comparable<BabaEntity> {
	/**
	 * Returns the filename of an entity
	 * 
	 * @return the filename of an entity
	 */
	String getFileImg();

	/**
	 * Returns the list of properties of the element
	 * 
	 * @return the list of properties of the element
	 */
	List<PropertyEnum> getRuleOfElem();

	/**
	 * Returns a string representation of the entity. The representation contains
	 * the name of the entity.
	 * 
	 * @return a string representation of the entity
	 */

	String toString();

	/**
	 * Add a coordinate for an entity
	 * 
	 * @param coord the coordinate to add
	 */
	void addCoord(Coordinate coord);

	/**
	 * Remove a coordinate for an entity
	 * 
	 * @param coord the coordinate to remove
	 */
	void removeCoord(Coordinate coord);

	/**
	 * Returns the list of coordinate
	 * 
	 * @return list of coordinate, where the entity is in the level
	 */
	List<Coordinate> getCoords();

	/**
	 * Compares the opacity of two BabaEntity
	 * 
	 * @param o the BabaEntity to compare
	 * @return a positive number if the Entity's opacity is superior than the
	 *         BabaEntity o's opacity. Return a negative number otherwise.
	 */
	@Override
	int compareTo(BabaEntity o);

	/**
	 * Indicates whether some other object is "equal to" this one. The objects are
	 * equal if the other object is of the same class and if it's the same element.
	 * 
	 * @param o the object with which to compare
	 * @return true if this object is the same as the o argument; false otherwise.
	 */

	@Override
	boolean equals(Object o);

	/**
	 * Returns a hash code value for this object.
	 * 
	 * @return a hash code value for this object
	 */
	@Override
	int hashCode();

	/**
	 * Returns true if the entity is on this coordinate
	 * 
	 * @param newcoord the coordinate to check
	 * @return true if the entity is on this coordinate
	 */
	default boolean isOnThisCoord(Coordinate newcoord) {
		return getCoords().contains(newcoord);
	}

	/**
	 * Returns how many time the entity is on the coordinate
	 * 
	 * @param coord the coordinate to check
	 * @return how many time the entity is on the coordinate
	 */
	default long nbOfElementInThisCoord(Coordinate coord) {
		return getCoords().stream().filter(coordinate -> coordinate.equals(coord)).count();
	}

	/**
	 * Check and apply the rule <b>melt</b> and <b>hot</b>.
	 * <p>
	 * Check if the entity is <b>MELT</b>. If yes, remove the coordinate of the
	 * entity if the entity is on the list of coordinate : <b>lstcoordshot</b>
	 * 
	 * @param lstcoordshot list of coordinate where there is an entity with the
	 *                     property hot
	 */
	default void checkAndDeleteMeltandHot(List<Coordinate> lstcoordshot) {
		if (!lstcoordshot.isEmpty() && getRuleOfElem().contains(PropertyEnum.MELT)) {
			var coordsiterator = getCoords().iterator();
			while (coordsiterator.hasNext()) {
				var coord = coordsiterator.next();
				if (lstcoordshot.contains(coord)) {
					coordsiterator.remove();
				}
			}
		}

	}

	/**
	 * Check and apply the rule <b>defeat</b>.
	 * <p>
	 * Check if the entity is <b>YOU</b>. If yes, remove the coordinate of the
	 * entity if the entity is on the list of coordinate : <b>lstcoordsdefeat</b>
	 * 
	 * @param lstcoordsdefeat list of coordinate where there is an entity with the
	 *                        property defeat
	 */
	default void checkDefeat(List<Coordinate> lstcoordsdefeat) {
		Objects.requireNonNull(lstcoordsdefeat);
		if (!lstcoordsdefeat.isEmpty() && getRuleOfElem().contains(PropertyEnum.YOU)) {
			var coordsiterator = getCoords().iterator();
			while (coordsiterator.hasNext()) {
				var coord = coordsiterator.next();
				if (lstcoordsdefeat.contains(coord)) {
					coordsiterator.remove();
				}
			}
		}
	}

	/**
	 * Check and apply the rule <b>win</b>.
	 * <p>
	 * Check if the entity is <b>YOU</b>. If yes, returns true if the entity is on
	 * the list of coordinate : <b>lstcoordswin</b>
	 * 
	 * @param lstcoordswin list of coordinate where there is an entity with the
	 *                     property win
	 * @return true, if the entity is <b>YOU</b> and if the entity is on the list of
	 *         coordinate : <b>lstcoordswin</b>
	 */
	default boolean checkWin(List<Coordinate> lstcoordswin) {
		Objects.requireNonNull(lstcoordswin);
		if (!lstcoordswin.isEmpty() && getRuleOfElem().contains(PropertyEnum.YOU)) {
			for (var coord : getCoords()) {
				if (lstcoordswin.contains(coord)) {
					return true;
				}
			}
		}
		return false;
	}

}
