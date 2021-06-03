package experiments;

import interfaces.Experiment;
import interfaces.Algorithm;
import benchmarks.CEC2014;
import algorithms.*;

public class CEC14 extends Experiment
{
	public CEC14(int probDim) throws Exception
	{
		//super(probDim, "cec2015allDim"); // Instantiate an instance of an experiment
		super(probDim, 5000, "testCEC14"); // Instantiate an instance of an experiment
		
		setNrRuns(30); // Set the number of runs or number of times each optimiser is executed, for every experiment (default is 100 iterations)

		Algorithm algorithm; // A generic optimiser
	    //Problem problem; // A generic problem
		
		// N.B. this algorithm makes use of "generateRandomSolution" that has still to be implemented
//		algorithm = new CMAES(); // Create a Covariance Matrix Adaptation Evolutionary Strategy (CMAES) optimiser instance
//		add(algorithm); // Add the optimiser to the containing object (list) of algorithms
//
//		algorithm = new ISPO(); // Create a Intelligent Single Particle Optimisation (ISPO) optimiser instance
//		algorithm.setParameter("p0", 1.0); // Initialise the first parameter for the optimiser instance
//		algorithm.setParameter("p1", 10.0); // Initialise the second parameter for the optimiser instance
//		algorithm.setParameter("p2", 2.0); // Initialise the third parameter for the optimiser instance
//		algorithm.setParameter("p3", 4.0); // Initialise the fourth parameter for the optimiser instance
//		algorithm.setParameter("p4", 1e-5); // Initialise the fifth parameter for the optimiser instance
//		algorithm.setParameter("p5", 30.0); // Initialise the sixth parameter for the optimiser instance
//		add(algorithm); // Add the optimiser to the containing object (list) of algorithms
	
		algorithm = new jDES(); // Create a Self-Adaptive Differential Evolution Short Distance Exploration (jDES) optimiser instance
		algorithm.setParameter("p0", 10.0 * probDim); // Initialise the first parameter for the optimiser instance (population size)
		//algorithm.setParameter("p1", 0.5); // Initialise the second parameter for the optimiser instance (scaling factor - F)
		//algorithm.setParameter("p2", 0.8); // Initialise the third parameter for the optimiser instance (crossover rate - CR)
		algorithm.setParameter("p3", 0.1); // Initialise the fourth parameter for the optimiser instance (scale factor lower bound - Fl)
		algorithm.setParameter("p4", 1.0); // Initialise the fifth parameter for the optimiser instance (scale factor upper bound - Fu)
		algorithm.setParameter("p5", 1.0); // Initialise the sixth parameter for the optimiser instance (tau one)
		algorithm.setParameter("p6", 1.0); // Initialise the seventh parameter for the optimiser instance (tau two)
		algorithm.setParameter("p7", 0.2); // Initialise the eighth parameter for the optimiser instance (alpha)
		algorithm.setParameter("p8", 100.0); // Initialise the ninth parameter for the optimiser instance (procedural iterations)
		add(algorithm); // Add the optimiser to the containing object (list) of algorithms
		
		algorithm = new S(); // Create a Short Distance Exploration (S) optimiser instance
		algorithm.setParameter("p0", 0.4); // Initialise the first parameter for the optimiser instance (alpha)
		add(algorithm); // Add the optimiser to the containing object (list) of algorithms
		
		algorithm = new DE(); // Create a Differential Evolution (DE) optimiser instance
		algorithm.setParameter("p0", 10.0 * probDim); // Initialise the first parameter for the optimiser instance (population size)
		algorithm.setParameter("p1", 0.5); // Initialise the second parameter for the optimiser instance (scaling factor - F)
		algorithm.setParameter("p2", 0.8); // Initialise the third parameter for the optimiser instance (crossover rate - CR)
		add(algorithm); // Add the optimiser to the containing object (list) of algorithms
		
		for(int i = 1; i <= 30; i++) // For all the problems contained within the benchmark, do the following
			add(new CEC2014(probDim, i)); // Create a problem instance (current index) and add it to the containing object (list) of problems
	}
}