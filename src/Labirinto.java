import maze.cli.CommandLine;
import maze.gui.Gui;


public class Labirinto {
	
	public static void main(String[] args) {
		if (args.length == 0)
			Gui.main(null);
		else if (args[0].equals("cli"))
			CommandLine.main(null);
	}

}
