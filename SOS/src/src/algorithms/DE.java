package algorithms;

import static utils.algorithms.Misc.generateRandomSolution;

import utils.random.RandUtils;
import interfaces.Algorithm;
import interfaces.Problem;
import utils.RunAndStore.FTrend;
import static utils.algorithms.Misc.toro;

/**
 * Differential Evolution (original variant - rand/1/bin)
 */
public class DE extends Algorithm // This class implements the algorithm. Every algorithm will have to contain its specific implementation within the method "execute". The latter will contain a main loop performing the iterations, and will have to return the fitness trend (including the final best) solution. Look at this examples before implementing your first algorithm.
{
	@Override
	public FTrend execute(Problem problem, int maxEvaluations) throws Exception
	{
		// We always need an object of the kind FTrend (for storing the fitness trend), and variables for storing the dimensionality value and the bounds of the problem as showed below
		FTrend FT = new FTrend(); // Create a fitness trend instance
		int problemDimension = problem.getDimension(); // Store the dimensionality of the problem domain
		double[][] bounds = problem.getBounds(); // Store the bounds of each variable in the problem domain
		
		double[] best = new double[problemDimension]; // Initialise the best known solution variable
		double fBest = Double.NaN; // Initialise the fitness value, i.e. "f(x)", of the best solution known
		int k = 0; // Initialise the incremental counter variable
		
		int populationSize 	 = getParameter("p0").intValue(); // Initialise the population size 
		double scalingFactor = getParameter("p1").doubleValue(); // Initialise the scaling factor of the mutation operator
		double crossoverRate = getParameter("p2").doubleValue(); // Initialise the crossover rate of the binomial crossover operator
		
		double[][] population = new double[populationSize][problemDimension]; // Initialise the population of individuals (solutions) variable
		double[] solutionFitness = new double[populationSize]; // Initialise the fitness of a solution (individual) variable
		
		// Evaluate initial population of individuals
		for (int i = 0; i < populationSize; i++) // For the size of the population of individuals (solutions), do the following
		{
			double[] initialSolution = generateRandomSolution(bounds, problemDimension); // Generate the initial solution (guess)
			
			for (int j = 0; j < problemDimension; j++) // For the dimensionality of the problem, do the following
			{
				population[i][j] = initialSolution[j]; // Initialise the iterated solution (individual) that comprises the population, to the initial solution randomly generated 
			}
			
			solutionFitness[i] = problem.f(population[i]); // Calculate and store the fitness (value) of the iterated solution (individual)
			k++; // Increment the counter to near the computational budget		
			
			if (i == 0 || solutionFitness[i] < fBest) // If the solution generated is the first solution in the population or the fitness of the solution is better than the fitness of the best known solution, do the following
			{
				fBest = solutionFitness[i]; // Store the iterated solutions (individuals) fitness (value) as the best known solution
				//FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
				
				for (int j = 0; j < problemDimension; j++) // For the dimensionality of the problem, do the following
				{
					best[j] = population[i][j]; // Update the best solution to the points comprising the iterated solution (individual) in the population
				}
			}
			
			if (i == 0 || k % 100 == 0) // If the iterated individual in the population is first individual in the population or the current iteration is divisible by '100' and has no remainder, do the following
			{
				FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
			}
		}

		// Main loop
		while (k < maxEvaluations) // While the computational budget has not been met, do the following
		{
			double[][] nextGeneration = new double[populationSize][problemDimension]; // Reinitialise the next generation of individuals representing the population variable
			
			for (int j = 0; j < populationSize && k < maxEvaluations; j++) // For the size of the population of individuals (solutions) and whilst within the computational budget, do the following
			{
				int fitnessTrendPopulated = 0; // Initialise the fitness trend populated variable
				
				double[] mutantIndividual 	  = new double[problemDimension]; // Reinitialise the mutated individuals variable
				double[] crossoverIndividual  = new double[problemDimension]; // Reinitialise the offspring variable
				double[] populationIndividual = new double[problemDimension]; // Reinitialise the individual (solution) comprising the population variable 
				
				double currentFitness 	= Double.NaN; // Reinitialise the fitness of the current solution (individual)
				double crossoverFitness = Double.NaN; // Reinitialise the fitness of the offspring solution (individual)
				
				for (int l = 0; l < problemDimension; l++) // For the dimensionality of the problem, do the following
				{
					populationIndividual[l] = population[j][l]; // Initialise the individual (solution) in the population to the iterated individual in the population
				}
				
				currentFitness = solutionFitness[j]; // Set the current fitness (value) to the fitness of the current individual (solution)
			
				// DE/rand/1 mutation operator
				mutantIndividual = originalMutation(population, scalingFactor, problemDimension); // Function call, mutate the population of individuals to obtain a new mutated individual
				//mutantIndividual = toro(mutantIndividual, bounds); // Correct the mutated individual (solution) that may exist out of the bounds of the search space (problem domain)
				
				// Binomial crossover operator
				crossoverIndividual = binomialCrossover(populationIndividual, mutantIndividual, crossoverRate, problemDimension); // Function call, crossover the current individual and the mutated individual, binomially (recombination)
				crossoverIndividual = toro(crossoverIndividual, bounds); // Correct the offspring individual (solution) that may exist out of the bounds of the search space (problem domain)
				
				crossoverFitness = problem.f(crossoverIndividual); // Calculate and store the fitness (value) of the offspring solution (individual)
				k++; // Increment the counter to near the computational budget

				// Replace the original individual in the population
				if (crossoverFitness < currentFitness) // If the offspring individual is fitter than the original individual in the population, do the following
				{
					for (int l = 0; l < problemDimension; l++) // For the dimensionality of the problem, do the following
					{
						// Survivor selection (1-to-1 spawning)
						nextGeneration[j][l] = crossoverIndividual[l]; // Replace the original individual (solution) in the population with the offspring individual (solution)
					}
						
					solutionFitness[j] = crossoverFitness; // Update the fitness of the individual (solution) to the fitness of the offspring individual (solution)
					
					// Update the best known solution
					if (crossoverFitness < fBest) // If the offspring individual (solution) is fitter than the best known individual (solution), do the following
					{
						fBest = crossoverFitness; // Update the fitness (value) of the best known solution to the fitness (value) of the offspring individual (solution)
						//FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
						
						if (k % 100 == 0 && fitnessTrendPopulated != k) // If the current iteration is divisible by '100' and has no remainder whilst the fitness trend has not been populated for the current iteration, do the following
						{
							FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
							fitnessTrendPopulated = k; // The fitness trend has been populated for the current iteration
						}
						
						for (int l = 0; l < problemDimension; l++) // For the dimensionality of the problem, do the following
						{	
							best[l] = crossoverIndividual[l]; // Update the best known individual (solution) to the offspring individual (solution)
						}
					}
				}
				else // Else if the crossover individual is not fitter than the original individual in the population, do the following
				{
					for (int l = 0; l < problemDimension; l++) // For the dimensionality of the problem, do the following
					{
						nextGeneration[j][l] = populationIndividual[l]; // Restore the design variables (genes) of the original individual (solution) as the updated individual (solution)
					}
						
					solutionFitness[j] = currentFitness; // Restore the fitness (value) of the solution to the fitness (value) of the iterated individual (solution)
				}
				
				if (k % 100 == 0 && fitnessTrendPopulated != k) // If the current iteration is divisible by '100' and has no remainder whilst the fitness trend has not been populated for the current iteration, do the following
				{
					FT.add(k, fBest); // Add the best solution found in this iteration to the fitness trend (saved to a .txt file)
					fitnessTrendPopulated = k; // The fitness trend has been populated for the current iteration
				}
			}
			
			// Survivor selection (1-to-1 spawning)
			population = nextGeneration; // Update the current population of individuals to the next generation of fitter individuals
		}
		
		finalBest = best; // Store the final iteration of the best known solution
		FT.add(k, fBest); // Add the final iteration of the best known solution to the fitness trend (saved to a .txt file)

		return FT; // Return the fitness trend
	}
	
	/**
	* DE/rand/1 mutation operator
	* 
	* @param population pool of solutions (individuals)
	* @param scalingFactor scale factor of mutation
	* @param problemDimensionality dimensionality of the problem domain
	* @return mutantIndividuals mutated individual
	*/
	public static double[] originalMutation(double[][] population, double scalingFactor, int problemDimensionality)
	{
		int problemDimension = problemDimensionality; // Store the dimensionality of the problem domain
		int populationSize = population.length; // Store the size of the population of individuals (solutions)
	
		int[] randomPermutation = new int[populationSize]; // Initialise the random permutation variable
		
		for (int i = 0; i < populationSize; i++) // For the size of the population of individuals (solutions), do the following
		{
			randomPermutation[i] = i; // Store the increment counter at each index of the random permutation variable (array: 0, 1, 2, 3...)
		}
		
		randomPermutation = RandUtils.randomPermutation(randomPermutation); // Permute the order of the elements contained within the random permutation variable
	
		int randomPointOne 	 = randomPermutation[0]; // Store the first element of the randomly permuted sequence of elements
		int randomPointTwo   = randomPermutation[1]; // Store the second element of the randomly permuted sequence of elements
		int randomPointThree = randomPermutation[2]; // Store the third element of the randomly permuted sequence of elements
	
		double[] mutantIndividual = new double[problemDimension]; // Initialise the mutated individual (solution) variable
		
		for (int i = 0; i < problemDimension; i++) // For the dimensionality of the problem, do the following
		{
			mutantIndividual[i] = population[randomPointOne][i] + scalingFactor * (population[randomPointTwo][i] - population[randomPointThree][i]); // Linearly combine the randomly selected points in the population, to move the individual (solution) to a new promising area in the problem domain
		}
	
		return mutantIndividual; // Return the mutated individual
	}
	
	/**
	* Binomial crossover operator
	* 
	* @param populationIndividual original parent solution
	* @param mutantIndividual mutated parent solution
	* @param crossoverRate crossover rate
	* @return offspringSolution offspring solution
	*/
	public static double[] binomialCrossover(double[] populationIndividual, double[] mutantIndividual, double crossoverRate, int problemDimensionality)
	{
		int problemDimension = problemDimensionality; // Store the dimensionality of the problem domain
		int randomIndex = RandUtils.randomInteger(problemDimension - 1); // Generate a random index sampled from the range of [0, 1]
		
		double[] offspringSolution = new double[problemDimension]; // Initialise the offspring solution variable
		
		for (int i = 0; i < problemDimension; i++) // For the dimensionality of the problem, do the following
		{
			if (RandUtils.random() < crossoverRate || i == randomIndex) // If the randomly generated number (within the range of [0, 1]) is smaller than the crossover rate or the increment counter is equal to the randomly generated index, do the following
			{
				offspringSolution[i] = mutantIndividual[i]; // Set the iterated design variable of the offspring solution (individual) to the iterated design variable of the mutated individual (solution)
			}
			else // Else if the randomly generated number is not smaller than the crossover rate or the increment counter is not equal to the randomly generated index, do the following
			{
				offspringSolution[i] = populationIndividual[i]; // Set the iterated design variable of the offspring solution to the iterated design variable of the original individual (solution)
			}
		}
			
		return offspringSolution; // Return the offspring solution
	}
}