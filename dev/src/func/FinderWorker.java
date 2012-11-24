package func;

import java.io.IOException;
import javax.swing.SwingWorker;

import draftClass.DraftClass;
import main.MainWindow;

public class FinderWorker extends SwingWorker<Object, Object> {
	
	private enum Rating {FGD, FGI, FGJ, FT, FG3, SCR,
		PAS, HDL, ORB, DRB, BLK, STL, DRFL, DEF, DIS, IQ};
	private String name;
	
	public FinderWorker(String str)
	{
		setProgress(0);
		name = str;
	}

	@Override
	public Object doInBackground() throws IOException
	{        
		findPlayer(name);
        
        //set the status text in the main window back to idle
		MainWindow.GetInstance().SetStatusIdle();
        
		return null;
	}
	
	public void findPlayer(String name) throws IOException 
	{
		DraftClass prospects = new DraftClass();
		
		// Error check: Name
		if (!prospects.checkName(name))
			MainWindow.GetInstance().updateOutput(name + "\nERROR: Name Not Found!\n");
		else {
			MainWindow.GetInstance().updateOutput(name + "\n");
			
			int[] rating = prospects.getPlayerRatings(name);
			int i = 0;
			
			for (Rating category : Rating.values())
			{
				if (category == Rating.FGD || category == Rating.FGI || category == Rating.FGJ ||
					category == Rating.FT || category == Rating.FG3)
				{
					MainWindow.GetInstance().updateOutput(category + ": " + rating[i] + "\n");
				}
				else
				{
					MainWindow.GetInstance().updateOutput(category + ": " + rating[i] + "/" + rating[i+1] + "\n");
					i++;
				}
				i++;
			}	
		}	
	}
}