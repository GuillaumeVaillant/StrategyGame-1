package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import domain.objects.Game;
import domain.objects.Player;
import job.JobGame;
import job.JobPlayer;
import persistence.mapper.GameMapper;
import persistence.mapper.PlayerMapper;

public class ControlerVerif implements ActionListener {

	private JobPlayer jobPlayer;
	private JobGame jobGame;
	private VerifPwd verif;
	private Player p;
	private List<Game> listGames;
	
	public ControlerVerif(VerifPwd verif)
	{
		this.verif = verif;
		this.p = null;
		this.jobPlayer = new JobPlayer();
		this.jobGame = new JobGame();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		check();
	}
	

	public void check()
	{
		System.out.println("Try to connect " + this.verif.getLogin() );
		
		try {
			p = jobPlayer.findPlayer(Integer.parseInt(this.verif.getLogin()));
			listGames = p.getListGames();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if(p != null) 
		{
			this.verif.error.setText("");
			this.verif.error.setForeground(Color.BLACK);
			getListGame(p);
			
		}else
		{
			this.verif.error.setText("Id inconnu");
			this.verif.error.setForeground(Color.RED);
			
		}
	}
	
	public void getListGame(Player p)
	{
		List<Game> gamesEnCours; 
		List<Game> gamesEnAttente; 
		List<Game> gamesFinish; 
		List<Game> gamesParticipe;

		gamesEnAttente = jobGame.findGameByStatus(this.p.getIdPlayer(),"WAITING");
		gamesEnCours = jobGame.findGameByStatus(this.p.getIdPlayer(),"RUNNING");
		gamesFinish = jobGame.findGameByStatus(this.p.getIdPlayer(),"FINISHED");
		gamesParticipe = p.getListGames();
		

		new ScreenList(p,gamesEnAttente,gamesEnCours,gamesFinish, gamesParticipe).setVisible(true);
	}

}

