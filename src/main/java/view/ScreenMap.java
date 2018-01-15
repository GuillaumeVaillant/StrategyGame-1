package view;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import domain.objects.Game;


public class ScreenMap extends JFrame {
	
	public JPanel panelMap;
	public JPanel panelAction;
	private Game game;
	
	public ScreenMap(Game game)
	{
		this.game = game;
		initJframe();
		GridBagConstraints contrainte = new GridBagConstraints();
		
		this.panelMap = new PanelMap(this);
		this.panelAction = new PanelAction(this);

		add(this.panelMap);
		add(this.panelAction);
		
	}

	public void initJframe()
	{
		setSize(500,500);
	    setLocation(100,100); 
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Projet Coo Map");
		
		setLayout(new GridLayout(2,0)); 
		
		
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public void fermerAppli()
	{
		super.dispose();
	}
	
}
