package view;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import domain.objects.Game;
import persistence.UnitOfWork;


public class PanelGamesRunning extends JPanel {

	public PanelGamesRunning(ScreenList screenList) {
		DefaultListModel<Game> model = new DefaultListModel<Game>();
		JList<Game> list = new JList<Game>();
		
		for (Game partie : screenList.gamesEnCours) {
			partie.add(UnitOfWork.getInstance()); // ajout observateur ?
			model.addElement(partie);
		}
		
		list.setModel(model);
		
		ListSelectionModel m = list.getSelectionModel();
		
		m.addListSelectionListener(new ListGamesListener(list,screenList));
		add(list);
	}

}
