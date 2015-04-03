import java.util.Collection;
import java.util.LinkedList;

/**
 * Provides a specific interface for a list of configurations
 * List of configurations are in a start to destination order
 * Object has a rank which is the number of jumps from start to finish
 * Low rank is "good"!
 * Created by Toby Leheup on 02/03/14.
 *
 * @author Toby Leheup - TL258
 * @version 02/03/2014
 */
@SuppressWarnings("PublicMethodNotExposedInInterface")
public class RankedRoute extends LinkedList<PuzzleConfiguration> implements Comparable<RankedRoute>
{
	private int rankBonus;

	/**
	 * Default Constructor
	 */
	public RankedRoute()
	{
		//Implicit super();
		this.rankBonus = 0;
	}

	/**
	 * Constructor that adds a single PuzzleConfiguration to the first node of the list
	 *
	 * @param configuration The starting PuzzleConfiguration
	 */
	public RankedRoute(final PuzzleConfiguration configuration)
	{
		this.add(configuration);
		this.rankBonus = 0;
	}

	/**
	 * Constructor that initialises this to a list of RankedRoutes<PuzzleConfigurations>s
	 *
	 * @param self The collect to initialise this
	 */
	public RankedRoute(final Collection<PuzzleConfiguration> self)
	{
		this.addAll(self);
		this.rankBonus = 0;
	}

	/**
	 * Gets the rank of RankeRoute (the number of jumps from Start to last)
	 *
	 * @return the number of jumps from Start to last
	 */
	public int getRank()
	{
		return this.size() + this.rankBonus;
	}

	/**
	 * Adds a modifier to the rank
	 * Useful when adding an estimated distance into considerations
	 * @param addition The value to add to Rank
	 */
	public void addToRank(final int addition)
	{
		this.rankBonus = addition;
	}

	/**
	 * Compares self with another RankedRoute and provides positive/negative response on equality
	 *
	 * @param route The RankedRoute to compare with
	 *
	 * @return +1 When self rank is higher. -1 when self rank is lower. Else 0
	 */
	@Override
	public int compareTo(final RankedRoute route)
	{
		if (this.getRank() > route.getRank())
		{
			return 1;
		}
		else if (this.getRank() < route.getRank())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}
