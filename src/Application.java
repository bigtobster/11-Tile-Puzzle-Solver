import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Initialises application
 * Created by Toby Leheup on 28/02/14.
 *
 * @author Toby Leheup - TL258
 * @version 28/02/2014
 */

public class Application
{
	/**
	 * Path to the list of puzzles to be created
	 */
	private static final String PATH_TO_PUZZLE_LIST = "./res/my_tiles";

	/**
	 * Path where the solutions will be exported to
	 */
	private static final String PATH_TO_OUTPUT = "./res/output/";

	/**
	 * Nominated Main method for whole application
	 * Gets puzzles
	 * Parses Puzzles
	 * Solves Puzzles
	 * Exports results
	 *
	 * @param args Command line arguments
	 */
	public static void main(final String[] args)
	{
		System.out.println("Eleven Tile Puzzle Solver Started");
		final List<Puzzle> puzzles = new ArrayList<Puzzle>(Application.initialisePuzzles());
		System.out.println("Solving process initiated");
		final PuzzleSolver puzzleSolver = new PuzzleSolver(puzzles);
		//puzzleSolver.solveAllIterativeDeepening();
		//puzzleSolver.solveAllUniformCost();
		puzzleSolver.solveAllAStar();
		System.out.println("All puzzles solved");
		System.out.println("Exporting solutions to " + PATH_TO_OUTPUT + "...");
		Application.export(puzzleSolver.getPuzzles());
		System.out.println("Puzzle solutions exported");
		System.out.println("Success!");
		System.out.println("Quitting Application...");
	}

	/**
	 * Export takes a list of solved puzzles and writes each solution history to a text file
	 *
	 * @param puzzles The list of puzzles to export
	 */
	public static void export(final Iterable<Puzzle> puzzles)
	{
		//For each puzzle, get a route
		//For each step in the route, get a configuration
		//For each configuration, get top row
		//For each row, append to string
		//Repeat for all rows
		final File outputDir = new File(Application.PATH_TO_OUTPUT);
		//noinspection ConstantConditions
		for (final File file : outputDir.listFiles())
		{
			//noinspection ResultOfMethodCallIgnored
			file.delete();
		}
		for (final Puzzle puzzle : puzzles)
		{
			final String[] textRows = new String[4];
			final Iterable<PuzzleConfiguration> route = puzzle.getRoute();
			for (int i = 0;i < textRows.length;i++)
			{
				//String builder could be any length
				//noinspection StringBufferWithoutInitialCapacity
				final StringBuilder stringBuilder = new StringBuilder();
				for (final PuzzleConfiguration configuration : route)
				{
					stringBuilder.append(new String(configuration.getRow(i)));
					stringBuilder.append(' ');
				}
				textRows[i] = stringBuilder.toString();
			}
			Application.writeSolution(textRows, puzzle);
		}
	}


	/**
	 * Writes a set of puzzle configurations to a text file
	 * Writes in all top rows \n bottom rows \n ... format
	 *
	 * @param rows   An ordered set of rows of the route configurations
	 * @param puzzle The puzzle being written
	 */
	private static void writeSolution(final String[] rows, final Puzzle puzzle)
	{
		final String filename = Application.PATH_TO_OUTPUT + puzzle.getFilename();
		try
		{
			//noinspection ResultOfMethodCallIgnored
			new File(filename).createNewFile();
			final PrintWriter writer = new PrintWriter(filename, "UTF-8");
			try
			{
				for (final String row : rows)
				{
					writer.println(row);
				}
			}
			finally
			{
				writer.close();
			}
		}
		catch (final FileNotFoundException exception)
		{
			System.err.println("Status 5: File not found");
			System.err.println("Quitting Application...");
			System.exit(5);
		}
		catch (final UnsupportedEncodingException exception)
		{
			System.err.println("Status 6: Encoding not supported");
			System.err.println("Quitting Application...");
			System.exit(6);
		}
		catch (final IOException exception)
		{
			System.err.println("Status 7: Error writing file " + filename);
			System.err.println("Quitting Application...");
			System.exit(7);
		}
	}


	/**
	 * Reads a text file and parses it into a Puzzle
	 *
	 * @return Collection of puzzles
	 */
	public static Collection<Puzzle> initialisePuzzles()
	{
		BufferedReader reader = null;
		//noinspection MismatchedQueryAndUpdateOfCollection
		final Collection<Puzzle> puzzles = new ArrayList<Puzzle>();
		try
		{
			//noinspection IOResourceOpenedButNotSafelyClosed
			reader = new BufferedReader(new FileReader(PATH_TO_PUZZLE_LIST));
			@SuppressWarnings("StringBufferWithoutInitialCapacity")
			String line = reader.readLine();
			while (line != null)
			{
				line = line.replace(".txt", "");
				puzzles.add(new Puzzle(line));
				line = reader.readLine();
			}
		}
		catch (final FileNotFoundException exception)
		{
			System.err.println("Status 1: Puzzles not found.");
			System.err.println("Quitting Application...");
			System.exit(1);
		}
		catch (final IOException exception)
		{
			System.err.println("Status 2: Error whilst reading puzzle file");
			System.err.println("Quitting Application...");
			System.exit(2);
		}
		finally
		{
			try
			{
				if (reader != null)
				{
					reader.close();
				}
				System.out.println("Puzzles Initialised");
			}
			catch (final IOException exception)
			{
				System.err.println("Status 3: Error whilst closing file reader");
				System.err.println("Quitting Application...");
				System.exit(3);
			}

		}
		return puzzles;
	}
}
