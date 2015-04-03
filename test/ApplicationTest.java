import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Collection;
import java.util.List;

/**
 * Tests Application
 * Created by Toby Leheup on 28/02/14.
 * @author Toby Leheup - TL258
 * @version 28/02/2014
 */
@SuppressWarnings({"SpellCheckingInspection", "FeatureEnvy"})
public class ApplicationTest extends TestCase
{
	/**
	 * Index of an example puzzle
	 */
	private final static int PUZZLE_INDEX_1 = 0;
	/**
	 * Index of an example puzzle
	 */
	private final static int PUZZLE_INDEX_2 = 7;
	/**
	 * Index of an example puzzle
	 */
	private final static int PUZZLE_INDEX_3 = 15;

	/**
	 * Test that initialisePuzzles produces the set of puzzles that it is expected to produce
	 * @throws Exception
	 */
	public void testInitialisePuzzles() throws Exception
	{
		final Collection<Puzzle> puzzles = Application.initialisePuzzles();
		Assert.assertNotNull(puzzles);
		Assert.assertEquals(((List<Puzzle>) puzzles).get(PUZZLE_INDEX_1).toString(),
		                    new Puzzle("dbbdad_bacdd2bbdddacb_dda").toString());
		Assert.assertEquals(((List<Puzzle>) puzzles).get(PUZZLE_INDEX_2).toString(),
		                    new Puzzle("badadbcb_ddd2bbadddca_ddb").toString());
		Assert.assertEquals(((List<Puzzle>) puzzles).get(PUZZLE_INDEX_3).toString(),
		                    new Puzzle("db_dbaddadcb2bbdddadcad_b").toString());
	}

}
