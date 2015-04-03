import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Toby Leheup on 28/02/14.
 * Test class for Puzzle
 * Checks correct instantiation and the inherent parsing functionality
 *
 * @author Toby Leheup - TL258
 * @version 28/02/2014
 */
@SuppressWarnings({"PublicMethodNotExposedInInterface",
                   "SpellCheckingInspection",
                   "FeatureEnvy",
                   "InstanceVariableOfConcreteClass"})
public class PuzzleTest
{
	/**
	 * Number of puzzles to process
	 */
	private final static int                 NO_OF_PUZZLES  = 16;
	/**
	 * Collection of puzzles
	 */
	private final        Map<Puzzle, String> puzzles        = new HashMap<Puzzle, String>(NO_OF_PUZZLES);
	/**
	 * Path to expected output of solutions
	 */
	private final static String PATH_TO_OUTPUT = "./res/output/";
	/**
	 * Example of a puzzle
	 */
	private final        Puzzle puzzle1        = new Puzzle("dbbdad_bacdd2bbdddacb_dda");
	/**
	 * Example of a puzzle
	 */
	private final        Puzzle puzzle2        = new Puzzle("ddba_cbbddda2acddbbbddd_a");
	/**
	 * Example of a puzzle
	 */
	private final        Puzzle puzzle3        = new Puzzle("bdbddcaadbd_2bbcddda_dbda");
	/**
	 * Example of a puzzle
	 */
	private final        Puzzle puzzle4        = new Puzzle("acdbb_ddddba2cdbadbdbd_da");

	/**
	 * Adds puzzles to the puzzle key map store
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		this.puzzles.put(new Puzzle("dbbdad_bacdd2bbdddacb_dda"), "dbbdad_bacdd2bbdddacb_dda");
		this.puzzles.put(new Puzzle("ddba_cbbddda2acddbbbddd_a"), "ddba_cbbddda2acddbbbddd_a");
		this.puzzles.put(new Puzzle("bdbddcaadbd_2bbcddda_dbda"), "bdbddcaadbd_2bbcddda_dbda");
		this.puzzles.put(new Puzzle("acdbb_ddddba2cdbadbdbd_da"), "acdbb_ddddba2cdbadbdbd_da");
		this.puzzles.put(new Puzzle("badca_ddbbdd2dba_bacdddbd"), "badca_ddbbdd2dba_bacdddbd");
		this.puzzles.put(new Puzzle("baddbbdda_cd2bbdba_addddc"), "baddbbdda_cd2bbdba_addddc");
		this.puzzles.put(new Puzzle("dbaddb_dcdab2dbadadbb_ddc"), "dbaddb_dcdab2dbadadbb_ddc");
		this.puzzles.put(new Puzzle("badadbcb_ddd2bbadddca_ddb"), "badadbcb_ddd2bbadddca_ddb");
		this.puzzles.put(new Puzzle("d_bdbdbdacda2b_dddbbaacdd"), "d_bdbdbdacda2b_dddbbaacdd");
		this.puzzles.put(new Puzzle("dbdba_ddbacd2a_bddbddbacd"), "dbdba_ddbacd2a_bddbddbacd");
		this.puzzles.put(new Puzzle("ba_dbdadbcdd2dbaabdbddc_d"), "ba_dbdadbcdd2dbaabdbddc_d");
		this.puzzles.put(new Puzzle("dba_bbdddcad2bdaadbd_bdcd"), "dba_bbdddcad2bdaadbd_bdcd");
		this.puzzles.put(new Puzzle("ddcadbbdadb_2ddbdc_adabdb"), "ddcadbbdadb_2ddbdc_adabdb");
		this.puzzles.put(new Puzzle("daddbdcdbba_2dda_bdcbddab"), "daddbdcdbba_2dda_bdcbddab");
		this.puzzles.put(new Puzzle("b_bdbadcdadd2dbbddabcd_ad"), "b_bdbadcdadd2dbbddabcd_ad");
		this.puzzles.put(new Puzzle("db_dbaddadcb2bbdddadcad_b"), "db_dbaddadcb2bbdddadcad_b");
	}

	/**
	 * Tests that toString produces the string it is expected to produce
	 * @throws Exception
	 */
	@Test
	public void testToString() throws Exception
	{
		for (final Entry<Puzzle, String> entry : this.puzzles.entrySet())
		{
			final Puzzle key = entry.getKey();
			final String value = entry.getValue();
			Assert.assertEquals(key.toString(), value);
		}
	}

	/**
	 * Tests that solveWithIterativeDeepening does get from start state to finish state
	 * Also tests that solution is the shortest possible solution (data derived from known, provided solution)
	 * @throws Exception
	 */
	@Test
	public void testSolve() throws Exception
	{
		@SuppressWarnings("MagicNumber")
		final long TEST_ROUTE_LENGTH = 15L;
		final Puzzle puzzle = new Puzzle("dbaabdc_bddd2adbdb_abdcdd");
		puzzle.solveWithIterativeDeepening();
		final PuzzleConfiguration finalConfig = puzzle.getRoute().get(puzzle.getRoute().size() - 1);
		Assert.assertEquals(finalConfig, puzzle.getDestinationConfig());
		//noinspection UnnecessaryExplicitNumericCast
		Assert.assertEquals((long) puzzle.getRoute().size(), TEST_ROUTE_LENGTH);
	}

	/**
	 * Tests that solveWithIterativeDeepening does get from start state to finish state using Uniform Cost Search
	 * Also tests that solution is the shortest possible solution (data derived from known, provided solution)
	 * @throws Exception
	 */
	@Test
	public void testSolveWithUniformCost() throws Exception
	{
		@SuppressWarnings("MagicNumber")
		final long TEST_ROUTE_LENGTH = 15L;
		final Puzzle puzzle = new Puzzle("dbaabdc_bddd2adbdb_abdcdd");
		puzzle.solveWithUniformCostSearch();
		final PuzzleConfiguration finalConfig = puzzle.getRoute().get(puzzle.getRoute().size() - 1);
		Assert.assertEquals(finalConfig, puzzle.getDestinationConfig());
		//noinspection UnnecessaryExplicitNumericCast
		Assert.assertEquals((long) puzzle.getRoute().size(), TEST_ROUTE_LENGTH);
	}

	/**
	 * Tests that solveWithIterativeDeepening does get from start state to finish state using A* Search
	 * Also tests that solution is the shortest possible solution (data derived from known, provided solution)
	 * @throws Exception
	 */
	@Test
	public void testSolveWithAStarSearch() throws Exception
	{
		@SuppressWarnings("MagicNumber")
		final long TEST_ROUTE_LENGTH = 15L;
		final Puzzle puzzle = new Puzzle("dbaabdc_bddd2adbdb_abdcdd");
		puzzle.solveWithAStarSearch();
		final PuzzleConfiguration finalConfig = puzzle.getRoute().get(puzzle.getRoute().size() - 1);
		Assert.assertEquals(finalConfig, puzzle.getDestinationConfig());
		//noinspection UnnecessaryExplicitNumericCast
		Assert.assertEquals((long) puzzle.getRoute().size(), TEST_ROUTE_LENGTH);
	}

	/**
	 * Tests that getFilename produces the expected result
	 * @throws Exception
	 */
	@Test
	public void testGetFilename() throws Exception
	{
		Assert.assertEquals(this.puzzle1.getFilename(), "dbbdad_bacdd2bbdddacb_dda.txt");
		Assert.assertEquals(this.puzzle2.getFilename(), "ddba_cbbddda2acddbbbddd_a.txt");
		Assert.assertEquals(this.puzzle3.getFilename(), "bdbddcaadbd_2bbcddda_dbda.txt");
		Assert.assertEquals(this.puzzle4.getFilename(), "acdbb_ddddba2cdbadbdbd_da.txt");
	}

	/**
	 * Tests that export produces the correctly labelled files
	 * Tests that the files that export produces contain the write amount of content
	 * @throws Exception
	 */
	@SuppressWarnings("OverlyLongMethod")
	@Test
	public void testExport() throws Exception
	{
		final LinkedList<Puzzle> puzzles = new LinkedList<Puzzle>();
		final PuzzleSolver solver;
		puzzles.add(this.puzzle1);
		puzzles.add(this.puzzle2);
		puzzles.add(this.puzzle3);
		puzzles.add(this.puzzle4);
		solver = new PuzzleSolver(puzzles);
		solver.solveAllIterativeDeepening();
		Application.export(solver.getPuzzles());

		BufferedReader reader;
		reader = new BufferedReader(new FileReader(PATH_TO_OUTPUT + this.puzzle1.getFilename()));
		try
		{
			for (int i = 0;i < 4;i++)
			{
				final String line = reader.readLine();
				Assert.assertNotNull(line);
				if ((line.isEmpty()))
				{
					assert false;
				}
			}
			final String line = reader.readLine();
			Assert.assertNull(line);
		}
		finally
		{
			reader.close();
		}
		reader = new BufferedReader(new FileReader(PATH_TO_OUTPUT + this.puzzle2.getFilename()));
		try
		{
			for (int i = 0;i < 4;i++)
			{
				final String line = reader.readLine();
				Assert.assertNotNull(line);
				if ((line.isEmpty()))
				{
					assert false;
				}
			}
			final String line = reader.readLine();
			Assert.assertNull(line);
		}
		finally
		{
			reader.close();
		}
		reader = new BufferedReader(new FileReader(PATH_TO_OUTPUT + this.puzzle3.getFilename()));
		try
		{
			for (int i = 0;i < 4;i++)
			{
				final String line = reader.readLine();
				Assert.assertNotNull(line);
				if ((line.isEmpty()))
				{
					assert false;
				}
			}
			final String line = reader.readLine();
			Assert.assertNull(line);
		}
		finally
		{
			reader.close();
		}
		reader = new BufferedReader(new FileReader(PATH_TO_OUTPUT + this.puzzle4.getFilename()));
		try
		{
			for (int i = 0;i < 4;i++)
			{
				final String line = reader.readLine();
				Assert.assertNotNull(line);
				if ((line.isEmpty()))
				{
					assert false;
				}
			}
			final String line = reader.readLine();
			Assert.assertNull(line);
		}
		finally
		{
			reader.close();
		}
	}
}
