package scouting;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.SwingWorker;

import resources.DraftClass;
import resources.TeamList;


import main.MainWindow;

public class WorkoutWorker extends SwingWorker<Object, Object> {
	
	private DraftClass draftclass;
	private enum Rating {FGD, FGI, FGJ, FT, FG3, SCR, PAS, HDL, ORB, DRB, BLK, STL, DRFL, DEF, DIS, IQ};
		
	private File directory;
	private BufferedWriter workouts;
	
	public WorkoutWorker(File f)
	{
		draftclass = new DraftClass(); // Generate ratings table for rookie class
		directory = f;
		setProgress(0);
	}
	
	@Override
	public Object doInBackground() throws IOException
	{        
		conductWorkouts();
             
		return null;
	}
	
	private void conductWorkouts() throws IOException
	{
		File files[] = directory.listFiles(); // Get all the files in the directory.
		TeamList teamList = new TeamList();
		
		for(File f: files)
		{
			String filename = f.getName();
			
			if (filename.endsWith(".txt"))
			{	
				BufferedReader br = new BufferedReader(new FileReader(f));

				String teamName;
				String playerName;
				String str; 
				int count = 0;
		
				teamName = br.readLine(); // read Team Name
				teamName = teamName.trim();
				teamName = teamName.replaceAll("\\s++", " ");
				
				teamName = teamList.getTeamName(teamName);
				
				// skip if file doesn't start with correct team name
				if (teamName == null)
				{
					MainWindow.GetInstance().updateOutput(filename + " -- ERROR: Team Name\n");
					continue; 
				}

				// Create a text file with the results for the respective team.
				workouts = new BufferedWriter(new FileWriter("results/" + teamName + ".txt"));
				workouts.append("\n" + teamName.toUpperCase() + "\n\n");		
				
				
				while ((str = br.readLine()) != null)
				{
					if(str.isEmpty())
						continue;
					
					playerName = str.trim();
					playerName = playerName.replaceAll("\\s++", " ");
					
					// Error check: Name
					if (!draftclass.checkName(playerName))
					{
						workouts.append("ERROR: Name Not Found! \n --/--\n\n");
						continue;
					}
					
					int[] rating = draftclass.getWorkout(playerName);
					appendWorkoutReport(playerName, rating); // generate workout report for this player.
					
					count++; // keep track of # of players worked out.
				}	
				br.close();
				workouts.close();
				
				MainWindow.GetInstance().updateOutput(filename + " -- " + count + "\n");
			}
		}
		
		MainWindow.GetInstance().updateOutput("\nWORKOUT -- DONE\n");
	}
	
	public void appendWorkoutReport(String name, int[] rating) throws IOException
	{		
		
		workouts.append(name + "\n");
		
		int i = 0;
		
		for (Rating category : Rating.values())
		{
			workouts.append(category + ": " + rating[i] + "/" + rating[i+1] + "\n");
			i+=2;
		}
		workouts.append("\n");
	}
}

