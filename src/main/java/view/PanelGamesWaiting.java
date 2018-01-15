package view;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import domain.objects.Game;
import persistence.UnitOfWork;

public class PanelGamesWaiting extends JPanel {
	
	public PanelGamesWaiting(ScreenList screenList)
	{
		DefaultListModel<Game> model = new DefaultListModel<Game>();
		JList<Game> list = new JList<Game>();
		
		for (Game partie : screenList.gamesAttente) {
			partie.add(UnitOfWork.getInstance()); 
			model.addElement(partie);
		}
		
		list.setModel(model);
		
		ListSelectionModel m = list.getSelectionModel();
		
		m.addListSelectionListener(new ListGamesListener(list,screenList));
		
		add(list);
	}
}

