import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 * Created by Toby Leheup on 28/02/14.
 *
 * Test class for PuzzleConfiguration
 * Checks correct instantiation and the configuration mutation functionality
 * @author Toby Leheup - TL258
 * @version 28/02/2014
 */
@SuppressWarnings({"PublicMethodNotExposedInInterface",
                   "InstanceVariableOfConcreteClass",
                   "SpellCheckingInspection",
                   "ClassWithTooManyFields", "FeatureEnvy"})
public class PuzzleConfigurationTest
{
	/**
	 * Configuration of a puzzle
	 */
	PuzzleConfiguration puzzleConfiguration1 = null;
	/**
	 * 2d char array representing configuration of puzzle
	 */
	char[][]            configuration1       = null;
	/**
	 * Charsequence representation of puzzle challenge
	 */
	CharSequence        sequence1            = null;
	/**
	 * Configuration of a puzzle
	 */
	PuzzleConfiguration puzzleConfiguration2 = null;
	/**
	 * 2d char array representing configuration of puzzle
	 */
	char[][]            configuration2       = null;
	/**
	 * Charsequence representation of puzzle challenge
	 */
	CharSequence        sequence2            = null;
	/**
	 * Configuration of a puzzle
	 */
	PuzzleConfiguration puzzleConfiguration3 = null;
	/**
	 * 2d char array representing configuration of puzzle
	 */
	char[][]            configuration3       = null;
	CharSequence        sequence3            = null;
	/**
	 * Configuration of a puzzle
	 */
	PuzzleConfiguration puzzleConfiguration4 = null;
	/**
	 * 2d char array representing configuration of puzzle
	 */
	char[][]            configuration4       = null;
	/**
	 * Charsequence representation of puzzle challenge
	 */
	CharSequence        sequence4            = null;

	/**
	 * Initialises test class fields with fixed data
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		this.sequence1 = "_dbadcbbddda";
		this.sequence2 = "bbdddacb_dda";
		this.sequence3 = "ddb_acbbddda";
		this.sequence4 = "acddbbbddd_a";

		this.puzzleConfiguration1 = new PuzzleConfiguration(this.sequence1);
		this.puzzleConfiguration2 = new PuzzleConfiguration(this.sequence2);
		this.puzzleConfiguration3 = new PuzzleConfiguration(this.sequence3);
		this.puzzleConfiguration4 = new PuzzleConfiguration(this.sequence4);

		this.configuration1 = PuzzleConfiguration.parseConfig(this.sequence1);
		this.configuration2 = PuzzleConfiguration.parseConfig(this.sequence2);
		this.configuration3 = PuzzleConfiguration.parseConfig(this.sequence3);
		this.configuration4 = PuzzleConfiguration.parseConfig(this.sequence4);
	}

	/**
	 * Tests that getConfiguration produces expected results
	 * @throws Exception
	 */
	@Test
	public void testGetConfiguration() throws Exception
	{
		Assert.assertArrayEquals(this.puzzleConfiguration1.getConfiguration(), this.configuration1);
		Assert.assertArrayEquals(this.puzzleConfiguration2.getConfiguration(), this.configuration2);
		Assert.assertArrayEquals(this.puzzleConfiguration3.getConfiguration(), this.configuration3);
		Assert.assertArrayEquals(this.puzzleConfiguration4.getConfiguration(), this.configuration4);
	}

	/**
	 * Tests that toString produces expected results
	 * @throws Exception
	 */
	@Test
	public void testToString() throws Exception
	{
		String expected = this.puzzleConfiguration1.toString();
		String actual = this.sequence1.toString();
		Assert.assertEquals(expected, actual);

		expected = this.puzzleConfiguration2.toString();
		actual = this.sequence2.toString();
		Assert.assertEquals(expected, actual);

		expected = this.puzzleConfiguration2.toString();
		actual = this.sequence2.toString();
		Assert.assertEquals(expected, actual);

		expected = this.puzzleConfiguration2.toString();
		actual = this.sequence2.toString();
		Assert.assertEquals(expected, actual);
	}

	/**
	 * Tests that generateNextConfigs produces expected results
	 * @throws Exception
	 */
	@Test
	public void testGenerateNextConfigs() throws Exception
	{
		final List<PuzzleConfiguration> puzzle1Configs = this.puzzleConfiguration1.generateNextConfigs();
		final Collection<PuzzleConfiguration> correctPuzzle1Configs = new RankedRoute();
		correctPuzzle1Configs.add(new PuzzleConfiguration("d_badcbbddda"));
		correctPuzzle1Configs.add(new PuzzleConfiguration("adb_dcbbddda"));
		assert(correctPuzzle1Configs.equals(puzzle1Configs));

		final List<PuzzleConfiguration> puzzle2Configs = this.puzzleConfiguration2.generateNextConfigs();
		final Collection<PuzzleConfiguration> correctPuzzle2Configs = new RankedRoute();
		correctPuzzle2Configs.add(new PuzzleConfiguration("bbddd_cbadda"));
		correctPuzzle2Configs.add(new PuzzleConfiguration("bbdddacbadd_"));
		correctPuzzle2Configs.add(new PuzzleConfiguration("bbdddac_bdda"));
		assert(correctPuzzle2Configs.equals(puzzle2Configs));

		final List<PuzzleConfiguration> puzzle3Configs = this.puzzleConfiguration3.generateNextConfigs();
		final Collection<PuzzleConfiguration> correctPuzzle3Configs = new RankedRoute();
		correctPuzzle3Configs.add(new PuzzleConfiguration("_dbdacbbddda"));
		correctPuzzle3Configs.add(new PuzzleConfiguration("ddba_cbbddda"));
		correctPuzzle3Configs.add(new PuzzleConfiguration("ddbbac_bddda"));
		assert(correctPuzzle3Configs.equals(puzzle3Configs));

		final List<PuzzleConfiguration> puzzle4Configs = this.puzzleConfiguration4.generateNextConfigs();
		final Collection<PuzzleConfiguration> correctPuzzle4Configs = new RankedRoute();
		correctPuzzle4Configs.add(new PuzzleConfiguration("acddbbb_ddda"));
		correctPuzzle4Configs.add(new PuzzleConfiguration("acddbbbddda_"));
		correctPuzzle4Configs.add(new PuzzleConfiguration("acddbbbdd_da"));
		assert(correctPuzzle4Configs.equals(puzzle4Configs));
	}

	/**
	 * Tests that PuzzleConfiguration.getMinDistanceTo calculates the distance between the 2 points accurately
	 * @throws Exception
	 */
	@Test
	public void testGetMinDistanceTo() throws Exception
	{
		Assert.assertEquals(this.puzzleConfiguration1.getMinDistanceTo(this.puzzleConfiguration2), 4);
		Assert.assertEquals(this.puzzleConfiguration1.getMinDistanceTo(this.puzzleConfiguration3), 1);
		Assert.assertEquals(this.puzzleConfiguration1.getMinDistanceTo(this.puzzleConfiguration4), 4);
	}
}
