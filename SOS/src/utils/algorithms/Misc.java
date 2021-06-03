/** @file Misc.java
 * MISCELLANEOUS
 *  @author Fabio Caraffini
*/
package utils.algorithms;

import static utils.MatLab.max;
import static utils.MatLab.min;

import utils.random.RandUtils;

/**
 * This class contains useful miscellaneous methods.
*/	
public class Misc
{

	/**
	 * Saturation on bounds of the search space.
	 * 
	 * @param x solution to be saturated.
	 * @param bounds search space boundaries.
	 * @return x_tor corrected solution.
	 */
	public static double[] saturate(double[] x, double[][] bounds)
	{
		int n = x.length;
		double[] x_sat = new double[n];
		for (int i = 0; i < n; i++)
			x_sat[i] = min(max(x[i], bounds[i][0]), bounds[i][1]);
		return x_sat;
	}
	
	/**
	 * Clone a solution.
	 * 
	 * @param x solution to be duplicated.
	 * @return xc cloned solution.
	 */
	public static double[] clone(double[] x)
	{
		int n = x.length;
		double[] xc = new double[n];
		for (int i = 0; i < n; i++)
			xc[i] = x[i];
		return xc;
	}
	
	/**
	 * Rounds x to the nearest integer towards zero.
	 */
	public static int fix(double x)
	{
		return (int) ((x >= 0) ? Math.floor(x) : Math.ceil(x));  
	}
	
	/**
	 * Random point in bounds.
	 * 
	 * @param bounds search space boundaries (general case).
	 * @param n problem dimension.
	 * @return r randomly generated point.
	 */
	public static double[] generateRandomSolution(double[][] bounds, int n)
	{
		double[] r = new double[n]; // Initialise the randomly generated points variable
			
		// For loop - loop [i] not [j] ([j] == 0 and [j] == 1 is the lower and upper bounds for the [i]th variable)
		// L + (U - L) * random number between [0, 1] e.g. -5.12 + (5.12 + -5.12) * random(0, 1)
			
		for (int i = 0; i < n; i++) // For the dimensionality of the problem, do the following
			r[i] = bounds[i][0] + (bounds[i][1] - bounds[i][0]) * RandUtils.random(); // Generate and calculate a point in between the specific problem search space (generic), randomly, for each variable in the problem domain
			
		return r; // Return the randomly generated points
	}
	
	/**
	 * Random point in bounds.
	 * 
	 * @param bounds search space boundaries (hyper-parallel piped).
	 * @param n problem dimension.
	 * @return r randomly generated point.
	 */
	public static double[] generateRandomSolution(double[] bounds, int n)
	{
		double[] r = new double[n]; // Initialise the randomly generated points variable
		
		// For loop - loop [i] ([0] and [1] is the lower and upper bounds for the [i]th variable)
		// L + (U - L) * random number between [0, 1] e.g. -5.12 + (5.12 + -5.12) * random(0, 1)
		
		for (int i = 0; i < n; i++) // For the dimensionality of the problem, do the following
			r[i] = bounds[0] + (bounds[1] - bounds[0]) * RandUtils.random(); // Generate and calculate a point in between the common problem search space (same), randomly, for each variable in the problem domain
		
		return r; // Return the randomly generated points
	}
	
	/**
	 * Toroidal correction within the search space
	 * 
	 * @param x solution to be corrected.
	 * @param bounds search space boundaries (hyper-parallel piped).
	 * @return x_tor corrected solution.
	 */
	public static double[] toro(double[] x, double[] bounds)
	{
		int n = x.length; // Store the length (problem dimensionality) of the solution passed
		double[] x_tor = new double[n]; // Initialise the toroidal corrected solution variable
				
		for (int i = 0; i < n; i++) // For the dimensionality of the problem, do the following
		{
			x_tor[i] = (x[i] - bounds[0]) / (bounds[1] - bounds[0]); // Normalise the currently iterated point to exist relative to the common boundaries of the problem search space (efficient and for comparability purposes)
					
			if (x_tor[i] > 1) // If the normalised point exceeds the upper bound of the search space, do the following
			{
				x_tor[i] = x_tor[i] - fix(x_tor[i]); // Correct (round) the currently iterated point to exist within the boundaries of the decision space
			}
			else if (x_tor[i] < 0) // Else if the normalised point exceeds the lower bound of the search space, do the following
			{
				x_tor[i] = 1 - Math.abs(x_tor[i] - fix(x_tor[i])); // Correct (make positive then round) the currently iterated point to exist within the boundaries of the decision space
			}
					
			x_tor[i] = x_tor[i] * (bounds[1] - bounds[0]) + bounds[0]; // Rescale the currently iterated point to exist relative to its original form (value)
		}
				
		return x_tor; // Return the toroidal corrected solution
	}

	/**
	 * Toroidal correction within search space
	 * 
	 * @param x solution to be corrected.
	 * @param bounds search space boundaries (general case).
	 * @return x_tor corrected solution.
	 */
	public static double[] toro(double[] x, double[][] bounds)
	{
		int n = x.length; // Store the length (problem dimensionality) of the solution passed
		double[] x_tor = new double[n]; // Initialise the toroidal corrected solution variable
		
		for (int i = 0; i < n; i++) // For the dimensionality of the problem, do the following
		{
			x_tor[i] = (x[i] - bounds[i][0]) / (bounds[i][1] - bounds[i][0]); // Normalise the currently iterated point to exist relative to the generically decided bounds of the problem search space (efficient and for comparability purposes)
			
			if (x_tor[i] > 1) // If the normalised point exceeds the upper bound of the search space, do the following
			{
				x_tor[i] = x_tor[i] - fix(x_tor[i]); // Correct (round) the currently iterated point to exist within the boundaries of the decision space
			}
			else if (x_tor[i] < 0) // Else if the normalised point exceeds the lower bound of the search space, do the following
			{
				x_tor[i] = 1 - Math.abs(x_tor[i] - fix(x_tor[i])); // Correct (make positive then round) the currently iterated point to exist within the boundaries of the decision space
			}
			
			x_tor[i] = x_tor[i] * (bounds[i][1] - bounds[i][0]) + bounds[i][0]; // Rescale the currently iterated point to exist relative to its original form (value)
		}
		
		return x_tor; // Return the toroidal corrected solution
	}

}