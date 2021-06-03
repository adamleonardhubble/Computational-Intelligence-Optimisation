package algorithms;

import static utils.algorithms.Misc.generateRandomSolution;

import utils.random.RandUtils;
import interfaces.Algorithm;
import interfaces.Problem;
import utils.RunAndStore.FTrend;
import static utils.algorithms.Misc.toro;

/**
 * Short Distance Exploration
 */
public class S extends Algorithm // This class implements the algorithm. Every algorithm will have to contain its specific implementation within the method "execute". The latter will contain a main loop performing the iterations, and will have to return the fitness trend (including the final best) solution. Look at this examples before implementing your first algorithm.
{
	@Override
	public FTrend execute(Problem problem, int maxEvaluations) throws Exception
	{
		// We always need an object of the kind FTrend (for storing the fitness trend), and variables for storing the dimensionality value and the bounds of the problem as showed below
		FTrend FT = new FTrend(); // Create a fitness trend instance
		int problemDimension = problem.getDimension(); // Store the dimensionality of the problem domain
		double[][] bounds = problem.getBounds(); // Store the bounds of each variable in the problem domain
		
		double[] xBest = new double[problemDimension]; // Initialise the best known solution variable
		double fBest = Double.NaN; // Initialise the fitness value, i.e. "f(x)", of the best solution known
		int k = 0; // Initialise the incremental counter variable
		
		double alpha = getParameter("p0"); // Initialise the alpha cut value of the length of the problems decision space
		
		// Initial solution
		if (initialSolution != null) // If the initial solution has been assigned, do the following
		{
			xBest = initialSolution; // Set the best known solution to the already computed initial solution (guess)
			fBest = initialFitness; // Set the best known solution fitness value to the already computed initial fitness value 
		}
		else // Else if the initial solution has not been assigned, do the following
		{
			xBest = generateRandomSolution(bounds, problemDimension); // Generate the initial solution (guess)
			fBest = problem.f(xBest); // Calculate the fitness value of the best known solution
			k++; // Increment the counter to near the computational budget
			FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
			//FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
		}

		double fShort = fBest; // Initialise the fitness value, i.e. "f(x)", of the trial solution
		
		double[] xShort = xBest; // Initialise the trial solution variable
		double[] exploratoryRadius = new double[problemDimension]; // Initialise the exploratory radius variable for the Short Distance Exploration (S) algorithm
		
		for (int i = 0; i < problemDimension; i++) // For the dimensionality of the problem, do the following
		{
			exploratoryRadius[i] = alpha * (bounds[i][1] - bounds[i][0]); // Calculate and the exploratory radius for each variable in the problem domain
		}
		
		// Main loop
		while (k < maxEvaluations) // While the computational budget has not been met, do the following
		{
			boolean improved = false; // Initialise the improved variable (reinitialise every iteration)
			
			for (int i = 0; i < problemDimension && k < maxEvaluations; i++) // For the dimensionality of the problem and whilst within the computational budget, do the following
			{
				int fitnessTrendPopulated = 0; // Initialise the fitness trend populated variable
				
				xShort[i] = xBest[i] - exploratoryRadius[i]; // Perturb the currently iterated variable in the problem domain, negatively, along the corresponding axis (exclusively) 
				xShort = toro(xShort, bounds); // Correct the trial solution that may exist out of the bounds of the search space (problem domain)
				
				fShort = problem.f(xShort); // Calculate the new fitness value of the trial solution
				k++; // Increment the counter to near the computational budget
				
				if (fShort <= fBest) // If the trial solution is an improvement or equivalent to the best known solution, do the following
				{
					xBest[i] = xShort[i]; // Update the best known solution to the current trial solution (one variable perturbed at each iteration)
					
					fBest = fShort; // Store the fitness value of the improved trial solution
					//FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
					
					if (k % 100 == 0 && fitnessTrendPopulated != k) // If the current iteration is divisible by '100' and has no remainder whilst the fitness trend has not been populated for the current iteration, do the following
					{
						FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
						fitnessTrendPopulated = k; // The fitness trend has been populated for the current iteration
					}
					
					improved = true; // The trial solution is an improvement or equivalent to the best known solution
				}
				else if (k < maxEvaluations) // Else if the trial solution is not an improvement to the best solution found and its within the computational budget, do the following
				{
					if (k % 100 == 0 && fitnessTrendPopulated != k) // If the current iteration is divisible by '100' and has no remainder whilst the fitness trend has not been populated for the current iteration, do the following
					{
						FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
						fitnessTrendPopulated = k; // The fitness trend has been populated for the current iteration
					}
					
					xShort[i] = xBest[i] + (exploratoryRadius[i] / 2); // Perturb the currently iterated variable in the problem domain, positively (half-step), along the corresponding axis (exclusively) 
					xShort = toro(xShort, bounds); // Correct the trial solution that may exist out of the bounds of the search space (problem domain)
					
					fShort = problem.f(xShort); // Calculate the new fitness value of the trial solution
					k++; // Increment the counter to near the computational budget
					
					if (fShort <= fBest) // If the trial solution is an improvement or equivalent to the best known solution, do the following
					{
						xBest[i] = xShort[i]; // Update the best known solution to the current trial solution (one variable perturbed at each iteration)
						
						fBest = fShort; // Store the fitness value of the improved trial solution
						//FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
						
						if (k % 100 == 0 && fitnessTrendPopulated != k) // If the current iteration is divisible by '100' and has no remainder whilst the fitness trend has not been populated for the current iteration, do the following
						{
							FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
							fitnessTrendPopulated = k; // The fitness trend has been populated for the current iteration
						}
						
						improved = true; // The trial solution is an improvement or equivalent to the best known solution
					}
					else // Else if the trial solution is not an improvement to the best solution found, do the following
					{
						xShort[i] = xBest[i]; // Return to the original point after the perturbations, as an improvement could not be recognised
					}
				}
				
				if (k % 100 == 0 && fitnessTrendPopulated != k) // If the current iteration is divisible by '100' and has no remainder whilst the fitness trend has not been populated for the current iteration, do the following
				{
					FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
					fitnessTrendPopulated = k; // The fitness trend has been populated for the current iteration
				}
			}
			
			if (improved == false) // If the current best solution has never improved from the initial best solution, do the following
			{
				for (int i = 0; i < exploratoryRadius.length; i++) // For the dimensionality of the problem, do the following
				{
					exploratoryRadius[i] = exploratoryRadius[i] / 2; // Store the exploratory radius for each variable in the problem domain to itself, halved (closer search from initial solution in case the initial guess is the optimum)
				}
			}
		}
		
		finalBest = xBest; // Store the final iteration of the best known solution
		FT.add(k, fBest); // Add the final iteration of the best known solution to the fitness trend (saved to a .txt file)

		return FT; // Return the fitness trend
	}
}