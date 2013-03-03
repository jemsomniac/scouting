package scouting;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.SwingWorker;

import resources.DraftClass;




import main.MainWindow;


public class InterviewWorker extends SwingWorker<Object, Object> {
	
	private DraftClass prospects;
	private enum InterviewResult {Dunk, Post, Drive, Jumper, Three, CON, GRE, LOY, PFW, PT, PER, DUR, WE, POP, IQ}; 
	private File directory;
	private BufferedWriter interviews;
	
	public InterviewWorker(File f)
	{
		prospects = new DraftClass(); // Generate ratings table for rookie class
		directory = f;
		setProgress(0);
	}
	
	@Override
	public Object doInBackground() throws IOException
	{        
		conductInterviews();
                
		return null;
	}

	private void conductInterviews() throws IOException
	{
		File files[] = directory.listFiles(); // Get all the files in the directory.
		
		for(File f: files)
		{
			String filename = f.getName();
			
			if (filename.endsWith(".txt"))
			{	
				BufferedReader br = new BufferedReader(new FileReader(f));
				
				// Start reading the interview file
				String str; 
				String teamName;
				int count = 0;
				
				teamName = br.readLine(); // read Team Name

				// Create a text file with the results for the respective team.
				interviews = new BufferedWriter(new FileWriter("results/" + teamName + ".txt"));
				interviews.append("\n" + teamName.toUpperCase() + "\n\n");
				
				while ((str = br.readLine()) != null)
				{						
					getInterviewResults(str);
					interviews.append("\n");
					
					count++; // keep track of # of players interviewed
				}
				br.close();
				interviews.close();
				MainWindow.GetInstance().updateOutput(filename + " -- " + count + "\n");
			}
		}
		MainWindow.GetInstance().updateOutput("\nINTERVIEWS -- DONE\n");
	}
	
	public void getInterviewResults(String name) throws IOException
	{	
		// Error check: Name
		if (!prospects.checkName(name))
		{
			interviews.append("ERROR: Name Not Found! \n --/--\n");
			return;
		}
		
		int[] interview = prospects.getInterview(name);
		int i=0;
		
		interviews.append(name + "\n");
		
		for (InterviewResult category : InterviewResult.values())
		{
			interviews.append(category + ": " + interview[i] + "\n");
			i++;
		}
	}
}

