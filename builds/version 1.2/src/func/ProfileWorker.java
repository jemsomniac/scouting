package func;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import resources.DraftClass;
import resources.Height;

import main.MainWindow;

public class ProfileWorker extends SwingWorker<Object, Object>{
	
	public ProfileWorker()
	{
		setProgress(0);
	}
	
	@Override
	public Object doInBackground() throws IOException
	{        
		generateProfiles();
                
		return null;
	}
		
	public void generateProfiles() throws IOException
	{
		DraftClass prospects = new DraftClass();
		ArrayList<String> names = prospects.getProspectNames();
		
		String PG = "[color=#FF0000][size=150][b]POINT GUARDS[/b][/size][/color]\n\n";
		String SG = "[color=#FF0000][size=150][b]SHOOTING GUARDS[/b][/size][/color]\n\n";
		String SF = "[color=#FF0000][size=150][b]SMALL FORWARDS[/b][/size][/color]\n\n";
		String PF = "[color=#FF0000][size=150][b]POWER FORWARDS[/b][/size][/color]\n\n";
		String C = "[color=#FF0000][size=150][b]CENTERS[/b][/size][/color]\n\n";
		
		for(int i=0; i < names.size(); i++)
		{
			int pos = prospects.getPosition(names.get(i));		
			int age = prospects.getAge(names.get(i));
			int weight = prospects.getWeight(names.get(i));
		
			Height h = new Height();
			String height = h.getDisplayHeight(prospects.getHeight(names.get(i)));
			String college = prospects.getCollege(names.get(i));
			
			String str = "[size=150][b]" + names.get(i) + "[/b][/size]\n\n" + 
			"[img]http://basketball.realgm.com/images/nba/4.2/profiles/photos/2006/player_photo.jpg[/img]\n\n" +
			"[b]Age:[/b] " + age + "\n[b]Height:[/b] " + height + "\n[b]Weight:[/b] " + weight + "\n[b]College:[/b] " + college + "\n\n\n";
			
			switch(pos)
			{
				case 1: 
					PG = PG.concat(str);
					break;
				case 2: 
					SG = SG.concat(str);
					break;
				case 3: 
					SF = SF.concat(str);
					break;
				case 4: 
					PF = PF.concat(str);
					break;
				case 5: 
					C = C.concat(str);
					break;
				default:
					break;
			}			
		}	
		
		MainWindow.GetInstance().updateOutput(PG + SG + SF + PF + C);
	}
}
