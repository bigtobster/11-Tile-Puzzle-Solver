import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests RankedRoute
 * Created by Toby Leheup on 02/03/14.
 * @author Toby Leheup - TL258
 * @version 02/03/2014
 */
@SuppressWarnings({"InstanceVariableOfConcreteClass", "PublicMethodNotExposedInInterface", "SpellCheckingInspection"})
public class RankedRouteTest
{
	/**
	 * A sample configuration
	 */
	private PuzzleConfiguration puzzleConfiguration = null;
	/**
	 * A rankedRoute to test
	 */
	private RankedRoute rankedRoute1 = null;
	/**
	 * A rankedRoute to test
	 */
	private RankedRoute rankedRoute2 = null;

	/**
	 * Initialises Test class and above fields
	 */
	@Before
	public void setUp() throws Exception
	{
		final String sequence = "_dbadcbbddda";
		this.puzzleConfiguration = new PuzzleConfiguration(sequence);
		this.rankedRoute1 = new RankedRoute();
		this.rankedRoute2 = new RankedRoute();
	}

	/**
	 * Tests RankedRoute.getRank
	 * Ensures that it provides the answers that it is expected to provide
	 * @throws Exception
	 */
	@SuppressWarnings("FeatureEnvy")
	@Test
	public void testGetRank() throws Exception
	{
		//noinspection UnnecessaryExplicitNumericCast
		assertEquals(0L, (long) this.rankedRoute1.getRank());
		this.rankedRoute1.add(this.puzzleConfiguration);
		//noinspection UnnecessaryExplicitNumericCast
		assertEquals(1L, (long) this.rankedRoute1.getRank());
		this.rankedRoute1.addAll(this.puzzleConfiguration.generateNextConfigs());
		//noinspection UnnecessaryExplicitNumericCast,MagicNumber
		assertEquals(3L, (long) this.rankedRoute1.getRank());
	}

	/**
	 * Ensures that RankedRoute.compareTo accurately compares 2 given RankedRoutes
	 * @throws Exception
	 */
	@Test
	public void testCompareTo() throws Exception
	{
		this.rankedRoute1.add(this.puzzleConfiguration);
		this.rankedRoute2.addAll(this.puzzleConfiguration.generateNextConfigs());
		//noinspection UnnecessaryExplicitNumericCast
		assertEquals((long) this.rankedRoute1.compareTo(this.rankedRoute2), -1L);
	}
}
