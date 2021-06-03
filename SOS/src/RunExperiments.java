/** @file RunExperiments.java
 *  
 *  @author Fabio Caraffini
*/
import java.util.Vector; 
import interfaces.Experiment;
import static utils.RunAndStore.resultsFolder;
import experiments.*;

/** 
* This class contains the main method and has to be used for launching experiments.
*/
public class RunExperiments
{
	
	/** 
	* Main method.
	* This method has to be modified in order to launch a new experiment.
	*/
	public static void main(String[] args) throws Exception
	{	
		// Make sure that "results" folder exists
		resultsFolder();
	
		Vector<Experiment> experiments = new Vector<Experiment>(); // List of problems
			
		//experiments.add(new Test(10)); // Add an experiment scenario for the test benchmark, of problem dimensionality '10' (design variables)
		//experiments.add(new Test(50)); // Add an experiment scenario for the test benchmark, of problem dimensionality '50' (design variables)
		//experiments.add(new Test(100)); // Add an experiment scenario for the test benchmark, of problem dimensionality '100' (design variables)
		experiments.add(new CEC14(10)); // Add an experiment scenario for the CEC 2014 benchmark, of problem dimensionality '10' (design variables)
		experiments.add(new CEC14(50)); // Add an experiment scenario for the CEC 2014 benchmark, of problem dimensionality '50' (design variables)
		experiments.add(new CEC14(100)); // Add an experiment scenario for the CEC 2014 benchmark, of problem dimensionality '100' (design variables)
		//experiments.add(new CEC15(10)); // Add an experiment scenario for the CEC 2015 benchmark, of problem dimensionality '10' (design variables)
		//experiments.add(new CEC15(50)); // Add an experiment scenario for the CEC 2015 benchmark, of problem dimensionality '50' (design variables)
		//experiments.add(new CEC15(100)); // Add an experiment scenario for the CEC 2015 benchmark, of problem dimensionality '100' (design variables)
		
		for (Experiment experiment : experiments)
		{
			//experiment.setShowPValue(true);
			experiment.startExperiment();
			System.out.println();
			experiment = null;
		}
	}
}
