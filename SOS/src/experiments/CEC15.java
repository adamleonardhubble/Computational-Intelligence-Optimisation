package experiments;

import interfaces.Experiment;
import interfaces.Problem;
import interfaces.Algorithm;
import benchmarks.CEC2015;
import algorithms.ISPO;
import algorithms.S; 



public class CEC15 extends Experiment
{
	public CEC15(int probDim) throws Exception
	{
		//super(probDim, "cec2015allDim"); // Instantiate an instance of an experiment
		super(probDim, 5000, "testCEC15"); // Instantiate an instance of an experiment
		
		setNrRuns(30); // Set the number of runs or number of times each optimiser is executed, for every experiment (default is 100 iterations)

		Algorithm algorithm; // A generic optimiser
	    //Problem problem; // A generic problem

		algorithm = new ISPO(); // Create a Intelligent Single Particle Optimisation (ISPO) optimiser instance
		algorithm.setParameter("p0", 1.0); // Initialise the first parameter for the optimiser instance
		algorithm.setParameter("p1", 10.0); // Initialise the second parameter for the optimiser instance
		algorithm.setParameter("p2", 2.0); // Initialise the third parameter for the optimiser instance
		algorithm.setParameter("p3", 4.0); // Initialise the fourth parameter for the optimiser instance
		algorithm.setParameter("p4", 1e-5); // Initialise the fifth parameter for the optimiser instance
		algorithm.setParameter("p5", 30.0); // Initialise the sixth parameter for the optimiser instance
		add(algorithm); // Add the optimiser to the containing object (list) of algorithms
	
		algorithm = new S(); // Create a Short Distance Exploration (S) optimiser instance
		algorithm.setParameter("p0", 0.4); // Initialise the first parameter for the optimiser instance
		add(algorithm); // Add the optimiser to the containing object (list) of algorithms
		
		for(int i = 1; i <= 15; i++) // For all the problems contained within the benchmark, do the following
			add(new CEC2015(probDim, i)); // Create a problem instance (current index) and add it to the containing object (list) of problems
	}
}
