package experiments;

import interfaces.Experiment;
import interfaces.Algorithm;
import interfaces.Problem;
import algorithms.*;
import benchmarks.BaseFunctions.*;

public class Test extends Experiment
{
	public Test(int probDim)
	{
		super(probDim, 5000, "TEST"); // Instantiate an instance of an experiment
			
		setNrRuns(5); // Set the number of runs or number of times each optimiser is executed, for every experiment (default is 100 iterations)
		
		Algorithm algorithm; // A generic optimiser
	    Problem problem; // A generic problem

	    algorithm = new jDES(); // Create a Self-Adaptive Differential Evolution Short Distance Exploration (jDES) optimiser instance
		algorithm.setParameter("p0", 10.0 * probDim); // Initialise the first parameter for the optimiser instance (population size)
		//algorithm.setParameter("p1", 0.5); // Initialise the second parameter for the optimiser instance (scaling factor - F)
		//algorithm.setParameter("p2", 0.9); // Initialise the third parameter for the optimiser instance (crossover rate - CR)
		algorithm.setParameter("p3", 0.1); // Initialise the fourth parameter for the optimiser instance (scale factor lower bound - Fl)
		algorithm.setParameter("p4", 1.0); // Initialise the fifth parameter for the optimiser instance (scale factor upper bound - Fu)
		algorithm.setParameter("p5", 1.0); // Initialise the sixth parameter for the optimiser instance (tau one)
		algorithm.setParameter("p6", 1.0); // Initialise the seventh parameter for the optimiser instance (tau two)
		algorithm.setParameter("p7", 0.4); // Initialise the eighth parameter for the optimiser instance (alpha)
		algorithm.setParameter("p8", 10.0); // Initialise the ninth parameter for the optimiser instance (procedural iterations)
		add(algorithm); // Add the optimiser to the containing object (list) of algorithms
	    
		algorithm = new ISPO(); // Create a Intelligent Single Particle Optimisation (ISPO) optimiser instance
		algorithm.setParameter("p0", 1.0); // Initialise the first parameter for the optimiser instance
		algorithm.setParameter("p1", 10.0); // Initialise the second parameter for the optimiser instance
		algorithm.setParameter("p2", 2.0); // Initialise the third parameter for the optimiser instance
		algorithm.setParameter("p3", 4.0); // Initialise the fourth parameter for the optimiser instance
		algorithm.setParameter("p4", 1e-5); // Initialise the fifth parameter for the optimiser instance
		algorithm.setParameter("p5", 30.0); // Initialise the sixth parameter for the optimiser instance
		add(algorithm); // Add the optimiser to the containing object (list) of algorithms

		// N.B. this algorithm makes use of "generateRandomSolution" that has still to be implemented
		algorithm = new CMAES(); // Create a Covariance Matrix Adaptation Evolutionary Strategy (CMAES) optimiser instance
		add(algorithm); // Add the optimiser to the containing object (list) of algorithms
		
		algorithm = new S(); // Create a Short Distance Exploration (S) optimiser instance
		algorithm.setParameter("p0", 0.4); // Initialise the first parameter for the optimiser instance (S)
		add(algorithm); // Add the optimiser to the containing object (list) of algorithms
		
		//algorithm = new SResetDelta(); // Create a Short Distance Exploration (S) optimiser instance
		//algorithm.setParameter("p0", 0.4); // Initialise the first parameter for the optimiser instance (alpha)
		//algorithm.setParameter("p1", 150.0); // Initialise the second parameter for the optimiser instance (procedural iterations)
		//add(algorithm); // Add the optimiser to the containing object (list) of algorithms
		
		// Manual intervention example
        //double[] bounds = {-100, 100};
		//p = new Ackley(problemDimension, bounds);
		
		// Given problems to benchmark the optimisers
		problem = new Ackley(probDim); // Create an Ackley problem instance
		add(problem); // Add the problem to the containing object (list) of problems
		problem = new Alpine(probDim); // Create an Alpine problem instance
		add(problem); // Add the problem to the containing object (list) of problems
		problem = new Rosenbrock(probDim); // Create a Rosenbrock problem instance
		add(problem); // Add the problem to the containing object (list) of problems
		
		// Implemented problems to benchmark the optimisers
		problem = new Sphere(probDim); // Create a sphere (De Jong) problem instance
		add(problem); // Add the problem to the containing object (list) of problems
		problem = new Schwefel(probDim); // Create a Schwefel problem instance
		add(problem); // Add the problem to the containing object (list) of problems
		problem = new Rastrigin(probDim); // Create a Rastrigin problem instance
		add(problem); // Add the problem to the containing object (list) of problems
		problem = new Michalewicz(probDim); // Create a Michalewicz problem instance
		add(problem); // Add the problem to the containing object (list) of problems
	}
}
