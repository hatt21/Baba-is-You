import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * LevelParser parse a correct file save to create a LevelManager
 */
public class LevelParser {

	/**
	 * Create the level.
	 * 
	 * @param filename    the filename to read
	 * @param leveldesign to load all the ImageIcon associated to the Entity
	 * @return a new LevelManager
	 * @throws IOException throw a IOException if the file is not correctly written,
	 *                     or empty
	 */
	public static LevelManager initialise(String filename, LevelDesign leveldesign) throws IOException {

		var savefilepath = Path.of(filename);
		var reader = Files.newBufferedReader(savefilepath, StandardCharsets.UTF_8);
		if (!reader.ready()) { // If the file is empty
			throw new IOException(filename + ": file empty");
		}
		String ligne;
		var firstline = reader.readLine().split(" ");
		BabaEntity entity = null;
		var level = new LevelManager(Integer.parseInt(firstline[0]), Integer.parseInt(firstline[1])); // Create the
																										// LevelManager
		while ((ligne = reader.readLine()) != null) {
			var tab = ligne.split(" ");
			switch (tab[0]) {
			case "nt": // Create a Text, and create a BabaElement associated
				entity = new Text(NounTextEnum.valueOf(tab[1]));
				BabaEntity other = new BabaElement(NounImgEnum.valueOf(tab[1]));
				leveldesign.add(other);
				leveldesign.add(entity);
				level.addEntity(other);
				break;
			case "ni": // Create a BabaElement
				entity = new BabaElement(NounImgEnum.valueOf(tab[1]));
				leveldesign.add(entity);
				break;
			case "o": // Create a Operator
				entity = new Text(OperatorEnum.valueOf(tab[1]));
				leveldesign.add(entity);
				break;
			case "p": // Create a Property
				entity = new Text(PropertyEnum.valueOf(tab[1]));
				leveldesign.add(entity);
				break;
			case "":
				break;
			default:
				if (entity == null) { // If the file is not correctly written
					throw new IOException(filename + ": file not correctly written");
				}
				level.add(new Coordinate(Integer.parseInt(tab[0]), Integer.parseInt(tab[1])), entity); // Add a
																										// coordinate to
																										// the Entity
				break;
			}
		}
		reader.close();
		return level;
	}

}
