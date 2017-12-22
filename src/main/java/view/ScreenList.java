package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.objects.Game;
import domain.objects.Player;


public class ScreenList extends JFrame{
	
	public JPanel pannelPartieEnAttente = null;
	public JPanel pannelPartieEnCours = null;
	public JPanel pannelHistorique = null;
	public Game gameSelected = null; 
	public List<Game> gamesAttente = null;
	public List<Game> gamesEnCours = null;
	public List<Game> gamesHistorique = null;
	public List<Game> gamesParticipe = null;
	public Player p = null;
	
	public ScreenList(Player p, List<Game> gamesAttente, List<Game> gamesEnCours, List<Game> gamesHistorique, List<Game> gamesParticipe)
	{
		this.p = p;
		this.gamesAttente = gamesAttente;
		this.gamesEnCours = gamesEnCours;
		this.gamesHistorique = gamesHistorique;
		
		setSize(500,200);
	    setLocation(100,100); 
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Liste des parties");

		GridBagConstraints contrainte = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		JButton create = new JButton("Create game");
		JLabel labelPartieAttente = new JLabel("Partie en attente");
		JLabel labelPartieEnCours = new JLabel("Partie en cours");
		JLabel labelHistorique = new JLabel("Historique");
		
		pannelPartieEnAttente = new PannelPartieEnAttente(this);
		pannelPartieEnCours = new PannelPartieEnCours(this);
		pannelHistorique = new PannelHistorique(this);
		
		contrainte.gridx = 0; // colonne
		contrainte.gridy = 0; //ligne
		
		add(labelPartieAttente,contrainte);
		
		contrainte.gridx = 0;
		contrainte.gridy = 1;
		
		add(pannelPartieEnAttente,contrainte);
		
		contrainte.gridx = 1;
		contrainte.gridy = 0;
		
		add(labelPartieEnCours,contrainte);
		
		contrainte.gridx = 1;
		contrainte.gridy = 1;
		
		add(pannelPartieEnCours,contrainte);
		
		contrainte.gridx = 0;
		contrainte.gridy = 2;
		
		add(labelHistorique,contrainte);
		
		contrainte.gridx = 0;
		contrainte.gridy = 3;
		
		add(pannelHistorique,contrainte);
		
		contrainte.gridx = 1;
		contrainte.gridy = 3;
		
		add(create,contrainte);
		
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new ScreenCreateParty(p);
				frame.setVisible(true);
				
			}
		});
		
	}
	
	
	public void fermerAppli()
	{
		super.dispose();
	}

}
