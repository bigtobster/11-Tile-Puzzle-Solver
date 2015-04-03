import sun.plugin.dom.exception.InvalidStateException;

import java.util.List;

/**
 * PuzzleConfiguration is a class which represents a single state of a Puzzle
 * It is initialised by using strings which are parsed and validated appropriately
 * It can generate the possible configurations that it can go to
 * Created by Toby Leheup on 28/02/14.
 *
 * @author Toby Leheup - TL258
 * @version 28/02/2014
 */
@SuppressWarnings("PublicMethodNotExposedInInterface")
public class PuzzleConfiguration
{
	/**
	 * CONFIG_LENGTH is the number of tiles represented in a single string in puzzle
	 */
	private static final int CONFIG_LENGTH = 12;
	/**
	 * configuration is a 2 dimensional char array representation of the grid of the puzzle
	 */
	private final char[][] configuration;

	/**
	 * Constructor for PuzzleConfiguration
	 * Initialises configuration
	 *
	 * @param configuration The configuration representing the whole puzzle including start and destination
	 *                      separated by a '2'
	 */
	public PuzzleConfiguration(final CharSequence configuration)
	{
		this.configuration = PuzzleConfiguration.parseConfig(configuration);
	}

	/**
	 * Parses a charSequence representing a full state into the configuration of the starting state and configuration
	 * of the destination state
	 *
	 * @param configuration The whole state of the puzzle
	 *
	 * @return An array containing 2 configurations
	 */
	public static char[][] parseConfig(final CharSequence configuration)
	{
		try
		{
			if ((configuration.length() != CONFIG_LENGTH) || !isValidConfig(configuration))
			{
				throw new IllegalArgumentException("Invalid Puzzle");
			}
			//CONFIGURATION IS VALID
			final char[][] puzzleConfig = new char[4][3];
			int jumper = 0;
			for (int i = 0;i < 4;i++)
			{
				for (int t = 0;t < 3;t++)
				{
					puzzleConfig[i][t] = configuration.charAt(i + t + jumper);
				}
				jumper += 2;
			}
			return puzzleConfig;
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
	 * Ensures that the given configuration is a valid one
	 *
	 * @param config The configuration to be tested
	 *
	 * @return boolean of whether configuration is valid or not
	 */
	private static boolean isValidConfig(final CharSequence config)
	{
		for (int i = 0;i < config.length();i++)
		{
			final char c = config.charAt(i);
			if (!((c == 'a') || (c == 'b') || (c == 'c') || (c == 'd') || (c == '_') || (c == '2')))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * getter for the configuration
	 *
	 * @return The current configuration
	 */
	@SuppressWarnings("PublicMethodNotExposedInInterface")
	public char[][] getConfiguration()
	{
		return this.configuration;
	}

	/**
	 * String representation of the current state
	 *
	 * @return string representation of the current state
	 */
	@Override
	public String toString()
	{
		final StringBuilder stringBuilder = new StringBuilder(4);
		for (final char[] chars : this.configuration)
		{
			stringBuilder.append(chars);
		}
		return stringBuilder.toString();
	}

	/**
	 * Converts a configuration into a string representation
	 *
	 * @param config A valid puzzle configuration
	 *
	 * @return A string representation of a configuration
	 */
	private static String toString(final char[][] config)
	{
		final StringBuilder stringBuilder = new StringBuilder(4);
		for (final char[] chars : config)
		{
			stringBuilder.append(chars);
		}
		return stringBuilder.toString();
	}

	/**
	 * Generates the next possible configurations from this configuration
	 * A configuration can move left, right, up or down only.
	 * Any move can only be performed if there is a valid cell in the place that _ wants to move to
	 *
	 * @return A list of configurations that can be moved to from base
	 */
	@SuppressWarnings("MethodWithMoreThanThreeNegations")
	public List<PuzzleConfiguration> generateNextConfigs()
	{
		final RankedRoute neighbours = new RankedRoute();
		final PuzzleConfiguration up = this.generateMoveUp();
		final PuzzleConfiguration right = this.generateMoveRight();
		final PuzzleConfiguration down = this.generateMoveDown();
		final PuzzleConfiguration left = this.generateMoveLeft();
		if (up != null)
		{
			neighbours.add(up);
		}
		if (right != null)
		{
			neighbours.add(right);
		}
		if (down != null)
		{
			neighbours.add(down);
		}
		if (left != null)
		{
			neighbours.add(left);
		}
		return neighbours;
	}

	/**
	 * Generates a new configuration where the '_' character in the current configuration has swapped with the
	 * character above it.
	 * If this is not possible, the '_' character remains where it is
	 *
	 * @return Either the new configuration or null if '_' cannot move
	 */
	@SuppressWarnings("MethodReturnOfConcreteClass")
	public PuzzleConfiguration generateMoveUp()
	{
		//Check if '_' is on top row
		//true -> return null
		//false -> swap '_' and char above it
		for (int i = 0;i < 3;i++)
		{
			if (this.configuration[0][i] == '_')
			{
				return null;
			}
		}
		//Possible to move up
		for (int i = 1;i < 4;i++)
		{
			for (int t = 0;t < 3;t++)
			{
				if (this.configuration[i][t] == '_')
				{
					final char[][] newConfig = PuzzleConfiguration.copyConfiguration(this.configuration);
					final char temp = newConfig[i][t];
					newConfig[i][t] = newConfig[i - 1][t];
					newConfig[i - 1][t] = temp;
					return new PuzzleConfiguration(PuzzleConfiguration.toString(newConfig));
				}
			}
		}
		//Move up
		//In theory, not possible to get this far...
		throw new InvalidStateException("Invalid configuration: Missing '_");
	}

	/**
	 * Generates a new configuration where the '_' character in the current configuration has swapped with the
	 * character to the right of it.
	 * If this is not possible, the '_' character remains where it is
	 *
	 * @return Either the new configuration or null if '_' cannot move
	 */
	@SuppressWarnings("MethodReturnOfConcreteClass")
	public PuzzleConfiguration generateMoveRight()
	{
		//Check if '_' is on right column
		//true -> return null
		//false -> swap '_' and char to left of it
		for (int i = 0;i < 4;i++)
		{
			if (this.configuration[i][2] == '_')
			{
				return null;
			}
		}
		//Possible to move right
		for (int i = 0;i < 4;i++)
		{
			for (int t = 0;t < 2;t++)
			{
				if (this.configuration[i][t] == '_')
				{
					@SuppressWarnings("CallToSimpleGetterFromWithinClass")
					final char[][] newConfig = PuzzleConfiguration.copyConfiguration(this.configuration);
					final char temp = newConfig[i][t];
					newConfig[i][t] = newConfig[i][t + 1];
					newConfig[i][t + 1] = temp;
					return new PuzzleConfiguration(PuzzleConfiguration.toString(newConfig));
				}
			}
		}
		//Move right
		//In theory, not possible to get this far...
		throw new InvalidStateException("Invalid configuration: Missing '_");
	}

	/**
	 * Generates a new configuration where the '_' character in the current configuration has swapped with the
	 * character below it.
	 * If this is not possible, the '_' character remains where it is
	 *
	 * @return Either the new configuration or null if '_' cannot move
	 */
	@SuppressWarnings("MethodReturnOfConcreteClass")
	public PuzzleConfiguration generateMoveDown()
	{
		//Check if '_' is on bottom row
		//true -> return null
		//false -> swap '_' and char below it
		for (int i = 0;i < 3;i++)
		{
			if (this.configuration[3][i] == '_')
			{
				return null;
			}
		}
		//Possible to move down
		for (int i = 0;i < 3;i++)
		{
			for (int t = 0;t < 3;t++)
			{
				if (this.configuration[i][t] == '_')
				{
					final char[][] newConfig = PuzzleConfiguration.copyConfiguration(this.configuration);
					final char temp = newConfig[i][t];
					newConfig[i][t] = newConfig[i + 1][t];
					newConfig[i + 1][t] = temp;
					return new PuzzleConfiguration(PuzzleConfiguration.toString(newConfig));
				}
			}
		}
		//Move down
		//In theory, not possible to get this far...
		throw new InvalidStateException("Invalid configuration: Missing '_");
	}

	/**
	 * Generates a new configuration where the '_' character in the current configuration has swapped with the
	 * character to the left of it.
	 * If this is not possible, the '_' character remains where it is
	 *
	 * @return Either the new configuration or null if '_' cannot move
	 */
	@SuppressWarnings("MethodReturnOfConcreteClass")
	public PuzzleConfiguration generateMoveLeft()
	{
		//Check if '_' is on left column
		//true -> return null
		//false -> swap '_' and char to the right of it
		for (int i = 0;i < 4;i++)
		{
			if (this.configuration[i][0] == '_')
			{
				return null;
			}
		}
		//Possible to move left
		for (int i = 0;i < 4;i++)
		{
			for (int t = 1;t < 3;t++)
			{
				if (this.configuration[i][t] == '_')
				{
					final char[][] newConfig = PuzzleConfiguration.copyConfiguration(this.configuration);
					final char temp = newConfig[i][t];
					newConfig[i][t] = newConfig[i][t - 1];
					newConfig[i][t - 1] = temp;
					return new PuzzleConfiguration(PuzzleConfiguration.toString(newConfig));
				}
			}
		}
		//Move left
		//In theory, not possible to get this far...
		throw new InvalidStateException("Invalid configuration: Missing '_");
	}

	/**
	 * Creates a deep copy of a configuration
	 *
	 * @param configuration The configuration to be deep copied
	 *
	 * @return The deep copy of the given configuration
	 */
	private static char[][] copyConfiguration(final char[][] configuration)
	{
		final char[][] copy = new char[4][3];
		for (int i = 0;i < 4;i++)
		{
			System.arraycopy(configuration[i], 0, copy[i], 0, 3);
		}
		return copy;
	}

	/**
	 * Tests 2 given Puzzles for current configuration equality only (this is sufficient for all purposes)
	 * Override of Object.equals
	 *
	 * @param puzzleConfiguration The Puzzle to be compared to this
	 *
	 * @return boolean of equality test
	 */
	@SuppressWarnings({"MethodMayBeStatic", "EqualsWhichDoesntCheckParameterClass"})
	@Override
	public boolean equals(final Object puzzleConfiguration)
	{
		@SuppressWarnings("CallToSimpleGetterFromWithinClass")
		final char[][] otherConfig = ((PuzzleConfiguration) puzzleConfiguration).getConfiguration();
		for (int i = 0;i < 4;i++)
		{
			for (int t = 0;t < 3;t++)
			{
				if (this.configuration[i][t] != otherConfig[i][t])
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Gets a single row of 3 characters
	 *
	 * @param i The index of the row
	 *
	 * @return The row of characters
	 */
	public char[] getRow(final int i)
	{
		return this.configuration[i];
	}

	/**
	 * Gets the shortest possible distance between the '_' of this configuration being in the position of the '_' in
	 * target configuration
	 * This is very useful for finding the absolute minimum number of moves that any given configuration will require
	 * to be converted into a target configuration
	 *
	 * @param target The configuration with the sought after '_'
	 *
	 * @return The number of moves required to take this '_' to target '_'
	 */
	public int getMinDistanceTo(final PuzzleConfiguration target)
	{
		int myX = 0;
		int myY = 0;
		int targetX = 0;
		int targetY = 0;
		for (int i = 0;i < 4;i++)
		{
			for (int t = 0;t < 3;t++)
			{
				if (this.configuration[i][t] == '_')
				{
					myX = i;
					myY = t;
				}
				//noinspection CallToSimpleGetterFromWithinClass
				if (target.getConfiguration()[i][t] == '_')
				{
					targetX = i;
					targetY = t;
				}
			}
		}
		return Math.abs((myX + myY) - (targetX + targetY));
	}
}
