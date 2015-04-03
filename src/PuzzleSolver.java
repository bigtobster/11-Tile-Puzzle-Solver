import java.util.List;

/**
 * Calculates and writes solutions to a list of puzzles
 * Created by Toby Leheup on 28/02/14.
 *
 * @author Toby Leheup - TL258
 * @version 28/02/2014
 */
@SuppressWarnings("PublicMethodNotExposedInInterface")
public class PuzzleSolver
{
	/**
	 * List of puzzles to solveWithIterativeDeepening
	 */
	private final List<Puzzle> puzzles;

	/**
	 * Instantiates PuzzleSolver
	 *
	 * @param puzzles List of Puzzles to solveWithIterativeDeepening
	 */
	public PuzzleSolver(final List<Puzzle> puzzles)
	{
		this.puzzles = puzzles;
	}

	/**
	 * Method which uses Iterative Deepening to optimally solveWithIterativeDeepening all puzzles in PuzzleSolver
	 * .puzzles
	 * Solution history is stored in each puzzle
	 * Note that method will never cease if the puzzles are not solvable!
	 */
	@SuppressWarnings("FeatureEnvy")
	public void solveAllIterativeDeepening()
	{
		for (final Puzzle puzzle : this.puzzles)
		{
			System.out.println("Solving " + puzzle.getFilename() + "...");
			puzzle.solveWithIterativeDeepening();
			System.out.println("Solved puzzle " + puzzle.getFilename() + '!');
		}
	}

	/**
	 * Method which uses A* Searching to optimally solveWithIterativeDeepening all puzzles in PuzzleSolver.puzzles
	 * Solution history is stored in each puzzle
	 * Note that method will never cease if the puzzles are not solvable!
	 */
	@SuppressWarnings({"FeatureEnvy", "UnusedDeclaration"})
	public void solveAllUniformCost()
	{
		for (final Puzzle puzzle : this.puzzles)
		{
			System.out.println("Solving " + puzzle.getFilename() + "...");
			puzzle.solveWithUniformCostSearch();
			System.out.println("Solved puzzle " + puzzle.getFilename() + '!');
		}
	}

	/**
	 * Method which uses A* Searching to optimally solveWithIterativeDeepening all puzzles in PuzzleSolver.puzzles
	 * Solution history is stored in each puzzle
	 * Note that method will never cease if the puzzles are not solvable!
	 */
	@SuppressWarnings({"FeatureEnvy", "UnusedDeclaration"})
	public void solveAllAStar()
	{
		for (final Puzzle puzzle : this.puzzles)
		{
			System.out.println("Solving " + puzzle.getFilename() + "...");
			puzzle.solveWithAStarSearch();
			System.out.println("Solved puzzle " + puzzle.getFilename() + '!');
		}
	}

	/**
	 * Getter for puzzles list
	 * This method does not guarantee that the list of puzzles returned will be solved
	 *
	 * @return List of puzzles
	 */
	public List<Puzzle> getPuzzles()
	{
		return this.puzzles;
	}
}
