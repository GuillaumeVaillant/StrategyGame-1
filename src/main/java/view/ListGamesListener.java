package view;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import domain.objects.Game;
import job.JobGame;

public class ListPartieListener implements ListSelectionListener {

	public JList<Game> list;
	public ScreenList screen;
	public JobGame jobGame;
	
	public ListPartieListener(JList<Game> list,ScreenList screen)
	{
		this.list = list;
		this.screen = screen;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		int selected = list.getSelectionModel().getMinSelectionIndex();
		
		ListSelectionModel model = (ListSelectionModel)e.getSource();
		
		if(e.getValueIsAdjusting())
		{
			this.screen.gameSelected = list.getSelectedValue();
			
			System.out.println(this.screen.gameSelected.getStatus());
			//new ScreenMap(this.screen.gameSelected);
			jobGame.joinGame(this.screen.p.getIdPlayer(), this.screen.gameSelected.getIdGame());
			
		}
			

	}

}
