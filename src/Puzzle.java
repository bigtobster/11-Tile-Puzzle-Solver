import java.util.List;
import java.util.PriorityQueue;

/**
 * Puzzle is a class which represents a Puzzle
 * It contains its current configuration as well as its destination configuration
 * It also contains its optimal route from start to destination - IFF it has been solved
 * Created by Toby Leheup on 28/02/14.
 *
 * @author Toby Leheup - TL258
 * @version 28/02/2014
 */
public class Puzzle
{
	/**
	 * FULL_CONFIG_LENGTH is the number of tiles represented in a single string in puzzle
	 */
	private static final int FULL_CONFIG_LENGTH = 25;
	/**
	 * destinationConfig is a configuration of the target
	 */
	@SuppressWarnings("InstanceVariableOfConcreteClass")
	private final PuzzleConfiguration       destinationConfig;
	/**
	 * startConfig is a configuration of th FULL_e current state
	 */
	@SuppressWarnings("InstanceVariableOfConcreteClass")
	private final PuzzleConfiguration       startConfig;
	/**
	 * filename that solution will be exported to
	 */
	private final CharSequence              filename;
	/**
	 * Optimal route from start to destination
	 */
	@SuppressWarnings("InstanceVariableMayNotBeInitialized")
	private       List<PuzzleConfiguration> route;

	/**
	 * Constructor for puzzle
	 * Initialises destination and current configurations
	 *
	 * @param configuration The configuration representing the whole puzzle including start and destination
	 *                      separated by a '2'
	 */
	public Puzzle(final CharSequence configuration)
	{
		this.startConfig = new PuzzleConfiguration(Puzzle.getStartingConfiguration(configuration));
		this.destinationConfig = new PuzzleConfiguration(Puzzle.getDestinationConfiguration(configuration));
		this.filename = configuration + ".txt";
	}

	/**
	 * Converts string representation of state into the destinationConfiguration
	 *
	 * @param configuration String representation of puzzle challenge in "start2destination" format
	 *
	 * @return CharSequence representation of destination configuration
	 */
	private static CharSequence getDestinationConfiguration(final CharSequence configuration)
	{
		try
		{
			if ((configuration.length() != FULL_CONFIG_LENGTH))
			{
				throw new IllegalArgumentException("Invalid Puzzle");
			}
			return configuration.subSequence(((FULL_CONFIG_LENGTH / 2) + 1), FULL_CONFIG_LENGTH);
		}
		catch (final IllegalArgumentException exception)
		{
			System.err.println("Status 4: Attempted to parse invalid configuration");
			System.err.println("Quitting Application...");
			System.exit(4);
			return null;
		}
	}

	/**
	 * Converts string representation of state into the startingConfiguration
	 *
	 * @param configuration String representation of puzzle challenge in "start2destination" format
	 *
	 * @return CharSequence representation of starting configuration
	 */
	private static CharSequence getStartingConfiguration(final CharSequence configuration)
	{
		try
		{
			if ((configuration.length() != FULL_CONFIG_LENGTH))
			{
				throw new IllegalArgumentException("Invalid Puzzle");
			}
			return configuration.subSequence(0, ((FULL_CONFIG_LENGTH / 2)));
		}
		catch (final IllegalArgumentException exception)
		{
			System.err.println("Status 4: Attempted to parse invalid configuration");
			System.err.println("Quitting Application...");
			System.exit(4);
			return null;
		}
	}

	@SuppressWarnings({"MethodReturnOfConcreteClass", "PublicMethodNotExposedInInterface"})
	public PuzzleConfiguration getDestinationConfig()
	{
		return this.destinationConfig;
	}

	/**
	 * Getter for the route
	 *
	 * @return The solved route or null
	 */
	@SuppressWarnings("PublicMethodNotExposedInInterface")
	public List<PuzzleConfiguration> getRoute()
	{
		return this.route;
	}

	@SuppressWarnings("PublicMethodNotExposedInInterface")
	public CharSequence getFilename()
	{
		return this.filename;
	}

	/**
	 * Converts current state into a string representation
	 *
	 * @return String representation of current state
	 */
	public String toString()
	{
		return this.startConfig + "2" + this.destinationConfig;
	}

	/**
	 * Solves puzzle using Iterative Deepening
	 * If no solution possible, algorithm will not terminate
	 */
	@SuppressWarnings("PublicMethodNotExposedInInterface")
	public void solveWithIterativeDeepening()
	{
		int depth = 1;
		while (true)
		{
			this.route = this.depthFirstSearch(this.startConfig, this.destinationConfig, depth);
			//noinspection VariableNotUsedInsideIf
			if (this.route != null)
			{
				return;
			}
			depth++;
		}
	}

	/**
	 * Terminating Depth-Limited-First search
	 * Alternative to A* Search and Uniform Cost Search
	 *
	 * @param start       Starting node
	 * @param destination Destination node
	 * @param depth       Degrees of depth algorithm will go from start to finding target node or halting
	 *
	 * @return List containing nodes from Start to Destination or null
	 */
	private List<PuzzleConfiguration> depthFirstSearch(final PuzzleConfiguration start,
	                                                   final PuzzleConfiguration destination,
	                                                   final int depth)
	{
		if (depth == 0)
		{
			return null;
		}
		else if (start.equals(destination))
		{
			final RankedRoute route = new RankedRoute();
			if (!route.contains(destination))
			{
				route.add(destination);
				//De ja vu test here
			}
			return route;
		}
		else
		{
			final List<PuzzleConfiguration> configNeighbours = start.generateNextConfigs();
			for (final PuzzleConfiguration config : configNeighbours)
			{
				final List<PuzzleConfiguration> route = this.depthFirstSearch(config, destination, depth - 1);
				if (route != null)
				{
					route.add(0, start);
					return route;
				}
			}
			return null;
		}
	}

	/**
	 * Accessor method to solveWithIterativeDeepening this puzzle using Uniform cost search
	 */
	@SuppressWarnings("PublicMethodNotExposedInInterface")
	public void solveWithUniformCostSearch()
	{
		this.uniformCostSearch(this.startConfig, this.destinationConfig);
	}

	/**
	 * Accessor method to solveWithIterativeDeepening this puzzle using A* Search
	 */
	@SuppressWarnings("PublicMethodNotExposedInInterface")
	public void solveWithAStarSearch()
	{
		this.aStarSearch(this.startConfig, this.destinationConfig);
	}

	/**
	 * Non-Terminating UniformCostSearch to find Optimal PuzzleConfigurations on a route between start and destination
	 * Alternative to A* Search and Iterative Deepening
	 *
	 * @param start       Starting node
	 * @param destination Destination node
	 */
	private void uniformCostSearch(final PuzzleConfiguration start, final PuzzleConfiguration destination)
	{
		//PriorityQueue can record which node has the best ranking
		final PriorityQueue<RankedRoute> nodeLeague = new PriorityQueue<RankedRoute>();
		nodeLeague.add(new RankedRoute(start));
		while (true)
		{
			if (nodeLeague.isEmpty())
			{
				// No solutions exist
				System.err.println("Status 8: Unable to finish search.");
				System.err.println("Quitting Application...");
				System.exit(8);
			}
			final RankedRoute rankedRoute = nodeLeague.poll();
			// Get the best looking route
			final PuzzleConfiguration last = rankedRoute.getLast();
			if (last.equals(destination))
			{
				//Solved
				this.route = rankedRoute;
				return;
			}
			for (final PuzzleConfiguration config : last.generateNextConfigs())
			{
				if (!rankedRoute.contains(config)) // deja vu
				{
					final RankedRoute tempRoute = new RankedRoute(rankedRoute);
					tempRoute.addLast(config);
					nodeLeague.add(tempRoute);
				}
			}
		}
	}

	/**
	 * Non-Terminating A* Search to find Optimal PuzzleConfigurations on a route between start and destination
	 * Alternative to Uniform Cost Search and Iterative Deepening
	 *
	 * @param start       Starting node
	 * @param destination Destination node
	 */
	@SuppressWarnings("FeatureEnvy")
	private void aStarSearch(final PuzzleConfiguration start, final PuzzleConfiguration destination)
	{
		//PriorityQueue can record which node has the best ranking
		final PriorityQueue<RankedRoute> nodeLeague = new PriorityQueue<RankedRoute>();
		final RankedRoute starter = new RankedRoute(start);
		starter.addToRank(start.getMinDistanceTo(destination));
		nodeLeague.add(starter);
		while (true)
		{
			if (nodeLeague.isEmpty())
			{
				// No solutions exist
				System.err.println("Status 8: Unable to finish search.");
				System.err.println("Quitting Application...");
				System.exit(8);
			}
			final RankedRoute rankedRoute = nodeLeague.poll();
			// Get the best looking route
			final PuzzleConfiguration last = rankedRoute.getLast();
			if (rankedRoute.getLast().equals(destination))
			{
				//Solved
				this.route = rankedRoute;
				return;
			}
			for (final PuzzleConfiguration config : last.generateNextConfigs())
			{
				if (!rankedRoute.contains(config))
				{
					final RankedRoute tempRoute = new RankedRoute(rankedRoute);
					tempRoute.addLast(config);
					tempRoute.addToRank(config.getMinDistanceTo(destination));
					nodeLeague.add(tempRoute);
				}
			}
		}
	}
}
