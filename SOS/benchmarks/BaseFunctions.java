/** @file BaseFunctions.java
 *  @author Fabio Caraffini
*/
package benchmarks;

import interfaces.Problem;

/**
 * Benchmark Base Functions.
 */
public class BaseFunctions
{
	/**
	 * Ackley function. 
	 * 
	 * References: 
	 * <a href="http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page295.htm" > Ref 1 </a>
	 * </br>
	 * <a href="http://www.it.lut.fi/ip/evo/functions/node14.html" > Ref 2 </a>
	 * </br>
	 * <a href="http://www.math.ntu.edu.tw/~wwang/cola_lab/test_problems/multiple_opt/multiopt_prob/Ackley/Ackley.htm" > Ref 3 </a>
	 * </br>
	 * <a href="http://tracer.lcc.uma.es/problems/ackley/ackley.html" > Ref 4 </a></br>
	*/
	public static class Ackley extends Problem
	{
		final double a = 20;
		final double b = 0.2;
		final double c = 2 * Math.PI;	
		
		/**
		* Constructor for the Ackley function defined within the specified upper and lower bounds.
		*/
		public Ackley(int dimension){ super(dimension, new double[] {-1, 1}); }
		/**
		* Constructor for the Ackley function defined within a hyper-cube.
		*/
		public Ackley(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Constructor for the Ackley function defined within particular decision space.

		*/
		public Ackley(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Ackley function.
		* 
		* @param x solution to be evaluated
		*/
		public double f(double[] x)
		{	
			final int n = x.length;
			double y = 0;
			
			if (this.getDimension() != n)
			{
				y = Double.NaN;
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!");
			}
			else
			{
				double square_sum = 0;		
				double cos_sum = 0;

				for (int i = 0; i < n; i++)	
				{
					square_sum += Math.pow(x[i],2);
					cos_sum += Math.cos(c*x[i]);
				}

				y = -a * Math.exp(-b * Math.sqrt(square_sum/n)) - Math.exp(cos_sum/n) + a + Math.exp(1);
			}
			
			return y;
		}
	}

		
	/**
	 * Alpine function.
	 * 
	 * References:
	 * <a href="http://clerc.maurice.free.fr/pso/Alpine/Alpine_Function.htm" > Ref 1 </a>
	*/
	public static class Alpine extends Problem
	{		
		/**
		* Constructor for the Alpine function defined within the specified upper and lower bounds.
		*/
		public Alpine(int dimension){ super(dimension, new double[] {-10, 10}); }
		/**
		* Constructor for the Alpine function defined within a hyper-cube.
		*/
		public Alpine(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Constructor for the Alpine function defined within particular decision space.
		*/
		public Alpine(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Alpine function.
		* 
		* @param x solution to be evaluated
		*/
		public double f(double[] x)
		{	
			final int n = x.length;
			double y = 0;
			
			if (this.getDimension() != n)
			{
				y = Double.NaN;
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!");
			}
			else
			{
				for (int i = 0; i < n; i++)
					y += Math.abs(x[i]*Math.sin(x[i]) + 0.1*x[i]);
			}
			
			return y;
		}
	}
	
	/**
	 * Rosenbrock function.
	 * 
	 * References:
	 * <a href="http://en.wikipedia.org/wiki/Rosenbrock_function" > Ref 1 </a>
	 * <a href="http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page2537.htm" > Ref 2 </a>
	 * <a href="http://mathworld.wolfram.com/RosenbrockFunction.html" > Ref 3 </a>
	*/
	public static class Rosenbrock extends Problem
	{
		/**
		* Constructor for  the  sphere function defined within the specified upper and lower bounds.
		*/
		public Rosenbrock(int dimension){ super(dimension, new double[] {-100, 100}); } 
		/**
		* Constructor the  the  sphere function defined within a hyper-cube.
		*/
		public Rosenbrock(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Constructor for the Rosenbrock function defined within particular decision space.
		*/
		public Rosenbrock(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Rosenbrock function.
		* 
		* @param x solution to be evaluated
		*/
		public double f(double[] x)
		{
			final int n = x.length;
			double y = 0;
			
			if (this.getDimension() != n)
			{
				y = Double.NaN;
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!");
			}
			else
			{
				for (int i = 0; i < n-1; i++)
				y += Math.pow((1-x[i]),2) + 100*Math.pow((x[i+1]-Math.pow(x[i],2)),2);
			}
			
			return y;
		 }
	}
	
	/**
	 * Sphere function (DE JONG).
	 * 
	 * References:
	 * <a href="http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page1113.htm" > Ref 1 </a>
	 * <a href="http://www.it.lut.fi/ip/evo/functions/node2.html" > Ref 2 </a>
	*/
	public static class Sphere extends Problem
	{
		/**
		* Constructor for the Sphere function defined within the specified upper and lower bounds.
		*/
		public Sphere(int dimension){ super(dimension, new double[] {-5.12, 5.12}); } // Initialise the bounds of the problem search space
		/**
		* Constructor for the Sphere function defined within a hyper-cube.
		*/
		public Sphere(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Constructor for the Sphere function defined within particular decision space.
		*/
		public Sphere(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Sphere function.
		* 
		* @param x solution to be evaluated
		*/
		public double f(double[] x)
		{
			final int n = x.length; // Store the length (problem dimensionality) of the solution passed
			double y = 0; // Initialise the result (fitness value) variable
		
			if (this.getDimension() != n) // If the dimensionality of the problem is not equal to the number of design variables featured in the solution passed, do the following
			{
				y = Double.NaN; // Set the result (fitness value) variable to null (no value)
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!"); // Output that the number of design variables does not match the dimensionality of the problem
			}
			else // Else if the dimensionality of the problem is equal to the number of design variables featured in the solution passed, do the following
			{
				for (int i = 0; i < n; i++) // For the dimensionality of the problem, do the following
					y += Math.pow(x[i], 2); // Add and equal (sum) the squared value of each variable iterated, for the dimensionality of the problem
			}
			
			return y; // Return the result (fitness value)
		}
	}
	
	/**
	 * Schwefel function.
	 * 
	 * References:
	 * <a href="http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page2530.htm" > Ref 1 </a>
	 * <a href="http://www.sfu.ca/~ssurjano/schwef.html" > Ref 2 </a>
	 */
	public static class Schwefel extends Problem 
	{
		/**
		* Constructor for the Schwefel function with suggested bounds.
		*/
		public Schwefel(int dimension) { super(dimension, new double[] {-500, 500}); } // Initialise the bounds of the problem search space
		/**
		* Constructor for the Schwefel function defined within a hyper-cube.
		*/
		public Schwefel(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Constructor for the Schwefel function defined within particular decision space.
		*/
		public Schwefel(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Schwefel function.
		* 
		* @param x solution to be evaluated
		*/
		public double f(double[] x)
		{
			final int n = x.length; // Store the length (problem dimensionality) of the solution passed
			double sum = 0; // Initialise the sum variable
			double y = 0; // Initialise the result (fitness value) variable
			
			if (this.getDimension() != n) // If the dimensionality of the problem is not equal to the number of design variables featured in the solution passed, do the following
			{
				y = Double.NaN; // Set the result (fitness value) variable to null (no value)
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!"); // Output that the number of design variables does not match the dimensionality of the problem
			}
			else // Else if the dimensionality of the problem is equal to the number of design variables featured in the solution passed, do the following
			{
				for (int i = 0; i < n; i++) // For the dimensionality of the problem, do the following
					sum += x[i] * Math.sin(Math.sqrt(Math.abs(x[i]))); // Add and equal (sum) the value of each variable iterated, multiplied by the sine of the root of its absolute value, for the dimensionality of the problem
				
				y = 418.9829 * n - sum; // Set the result (fitness value) to the difference between the summed value and the product of a constant and the problem dimensionality (each variable = 418.929 - sum)
			}
			
			return y; // Return the result (fitness value)
		}
	}
	
	/**
	 * Rastrigin function.
	 * 
	 * References:
	 * <a href="http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page2607.htm" > Ref 1 </a>
	 * <a href="http://en.wikipedia.org/wiki/Rastrigin_function" > Ref 2 </a>
	 * <a href="http://www.mathworks.com/help/toolbox/gads/f14773.html" > Ref 3 </a>
	 * <a href="https://www.sfu.ca/~ssurjano/rastr.html" > Ref 4 </a>
	 */
	public static class Rastrigin extends Problem
	{
		/**
		* Constructor for the Rastrigin function with suggested bounds.
		*/
		public Rastrigin(int dimension) { super(dimension, new double[] {-5.12, 5.12}); } // Initialise the bounds of the problem search space
		/**
		* Constructor for the Rastrigin function defined within a hyper-cube.
		*/
		public Rastrigin(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Constructor for the Rastrigin function defined within particular decision space.
		*/
		public Rastrigin(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Rastrigin function.
		* 
		* @param x solution to be evaluated
		*/
		public double f(double[] x)
		{
			final int n = x.length; // Store the length (problem dimensionality) of the solution passed
			double sum = 0; // Initialise the sum variable
			double y = 0; // Initialise the result (fitness value) variable
			
			if (this.getDimension() != n) // If the dimensionality of the problem is not equal to the number of design variables featured in the solution passed, do the following
			{
				y = Double.NaN; // Set the result (fitness value) variable to null (no value)
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!"); // Output that the number of design variables does not match the dimensionality of the problem
			}
			else // Else if the dimensionality of the problem is equal to the number of design variables featured in the solution passed, do the following
			{
				for (int i = 0; i < n; i++) // For the dimensionality of the problem, do the following
					sum += Math.pow(x[i], 2) - 10 * Math.cos(2 * Math.PI * x[i]); // Add and equal (sum) the product of the difference between the variable iterated and a constant, with the cosine of the product of the variable iterated and PI, doubled
				
				y = 10 * n + sum; // Set the result (fitness value) to the sum of the summed value and the product of a constant and the problem dimensionality (each variable = 10 + sum)
			}
			
			return y; // Return the result (fitness value)
		}
	}
	
	/**
	 * Michalewicz function.
	 * 
	 * References:
	 * <a href="http://www.geatbx.com/docu/fcnindex-01.html#P204_10395" > Ref 1 </a>
	 * <a href="http://www.pg.gda.pl/~mkwies/dyd/geadocu/fcnfun12.html" > Ref 2 </a>
	 * <a href="https://subscription.packtpub.com/book/big_data_and_business_intelligence/9781789612011/app02/app02lvl1sec117/the-michalewicz-function" > Ref 3 </a>
	 * <a href="http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page2376.htm" > Ref 4 </a>
	*/	
	public static class Michalewicz extends Problem
	{
		/**
		* Constructor for the Michalewicz function with suggested bounds.
		*/
		public Michalewicz(int dimension) { super(dimension, new double[] {0, Math.PI}); } // Initialise the bounds of the problem search space
		/**
		* Constructor for the Michalewicz function defined within a hyper-cube.
		*/
		public Michalewicz(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Constructor for the Michalewicz function defined within particular decision space.
		*/
		public Michalewicz(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Michalewicz function.
		* 
		* @param x solution to be evaluated
		*/
		public double f(double[] x)
		{
			final int n = x.length; // Store the length (problem dimensionality) of the solution passed
			int m = 10; // Store the steepness of the valleys and ridges (local minima) of the functions hyper-cube (recommended value)
			double sum = 0; // Initialise the sum variable
			double y = 0; // Initialise the result (fitness value) variable
			
			if (this.getDimension() != n) // If the dimensionality of the problem is not equal to the number of design variables featured in the solution passed, do the following
			{
				y = Double.NaN; // Set the result (fitness value) variable to null (no value)
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!"); // Output that the number of design variables does not match the dimensionality of the problem
			}
			else // Else if the dimensionality of the problem is equal to the number of design variables featured in the solution passed, do the following
			{
				for (int i = 0; i < n; i++) // For the dimensionality of the problem, do the following
					sum += Math.sin(x[i]) * Math.pow(Math.sin((i + 1) * Math.pow(x[i], 2) / Math.PI), 2 * m); // Add and equal (sum) the product of the sine of the variable iterated and the sine of the variable iterated multiplied by the iteration count, divided by PI, which the sum is then raised to the power of two multiplied by the steepness value configured for the functions hyper-cube valleys and ridges
				
				y = -sum; // Negate the summed total (negative result or fitness value)
			}
			
			return y; // Return the result (fitness value)
		}
	}
	
}
