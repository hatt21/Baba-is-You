import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

/**
 * Main class is created to run the application
 *
 */
public class Main {
	/**
	 * Manages the given arguments
	 * 
	 * @param args    the arguments passed with the command
	 * @param context context of the application
	 * @return a list of level filename
	 * @throws IOException throw IOException if an argument or a file is not correct
	 */
	private static ArrayList<String> argsManager(String[] args, ApplicationContext context) throws IOException {
		int i = 0;
		var lstfile = new ArrayList<String>();
		if (1 < args.length && args[i].equals("-level")) {
			var filepath = Path.of(args[i + 1]);
			if (!filepath.toFile().isFile()) {
				throw new IOException(filepath.toString() + " is not a file");
			}
			lstfile.add(args[i + 1]);
		} else if (1 < args.length && args[i].equals("-levels")) {
			var directorypath = Path.of(args[i + 1]);
			if (!directorypath.toFile().isDirectory()) {
				throw new IOException(directorypath.toString() + " is not a directory");
			}
			for (var file : directorypath.toFile().list()) {
				var str = new StringBuilder();
				str.append(args[i + 1]).append("/").append(file);
				lstfile.add(str.toString());
			}
		} else if (0 < args.length) {
			throw new IOException(args[i] + " is not a correct argument");
		} else {
			lstfile.add("src/levels/default-level.txt");
		}
		return lstfile;
	}

	/***
	 * Running the application
	 * 
	 * @param args the arguments passed with the command
	 */
	public static void main(String[] args) {
		Application.run(Color.BLACK, context -> {

			// get the size of the screen
			ScreenInfo screenInfo = context.getScreenInfo();
			int width = Math.round(screenInfo.getWidth());
			int height = Math.round(screenInfo.getHeight());

			ArrayList<String> files;
			try {
				files = argsManager(args, context);
			} catch (IOException e1) {
				System.err.println(e1.getMessage());
				context.exit(height);
				return;
			}

			for (var levelname : files) {
				var win = false;
				var leveldesign = new LevelDesign();
				// Initializes the data of the board
				LevelManager levelmanager;
				try {
					levelmanager = LevelParser.initialise(levelname, leveldesign);
				} catch (IOException e) {
					System.err.println(e.getMessage());
					break;
				}

				// Initializes the board graphics
				var view = BabaGameView.initGameGraphics(0, 0, width, height, levelmanager);
				levelmanager.updateAnddetectRule();

				for (;;) {
					GameView.draw(context, levelmanager, view, leveldesign);
					Event event = context.pollOrWaitEvent(50);
					// If we loose
					if ((levelmanager.ElemWithThisRule(PropertyEnum.YOU).stream()
							.flatMap(entity -> entity.getCoords().stream()).collect(Collectors.toList())).isEmpty()) {
						GameView.loose(context, levelmanager, view);
						for (;;) {
							Event event2 = context.pollEvent();
							if (event2 == null) { // no event
								continue;
							}
							Action action2 = event2.getAction();
							if (action2 == Action.POINTER_UP || action2 == Action.POINTER_DOWN
									|| action2 == Action.KEY_PRESSED) {
								context.exit(0);
								return;
							}
						}
					}
					if (event == null) { // no event
						continue;
					}
					Action action = event.getAction();
					if (action == Action.KEY_PRESSED) {
						var keyboard = event.getKey();
						if (keyboard == KeyboardKey.UP || keyboard == KeyboardKey.DOWN || keyboard == KeyboardKey.RIGHT
								|| keyboard == KeyboardKey.LEFT) {
							if (levelmanager.move(keyboard)) { // We move and check if we win the level
								GameView.draw(context, levelmanager, view, leveldesign);
								GameView.win(context, levelmanager, view);
								for (;;) {
									Event event2 = context.pollEvent();
									if (event2 == null) { // no event
										continue;
									}
									Action action2 = event2.getAction();
									if (action2 == Action.POINTER_UP || action2 == Action.POINTER_DOWN
											|| action2 == Action.KEY_PRESSED) {
										win = true;
										break;
									}

								}
							}
						}

					}
					if (win) {
						break;
					}
					// We leave the game if we click
					if (action == Action.POINTER_UP || action == Action.POINTER_DOWN) {
						context.exit(0);
						return;
					}

				}
			}
			context.exit(0);
			return;

		});

	}
}