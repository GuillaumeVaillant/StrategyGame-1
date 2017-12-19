package run;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.objects.Game;
import domain.objects.Player;
import job.JobPlayer;
import persistence.factories.FactoryListGame;
import persistence.factories.FactoryListPlayer;
import persistence.factories.FactoryPlayer;
import persistence.mapper.PlayerMapper;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	static JobPlayer playerJob = new JobPlayer();

	public static void main(String[] args) throws Exception {
		
		
		System.out.println("Bienvenue dans StrategyGame !");
		
		System.out.println("Nouveau ? Taper 1");
		System.out.println("D�j� inscrit ? Taper 2");
		
		int choix = sc.nextInt();
		Player player = null;
		switch(choix){
		case 1: {
			System.out.println("Entrer un nom de joueur : ");
			
			String username = sc.nextLine();
			
			player = new Player(username, "");
			playerJob.insertPlayer(player);
			
			System.out.println("Bonjour " + player.getUsername() + "!");
			System.out.println("Voici votre identifiant à garder pour vous connecter : " + player.getIdPlayer());
			break;
		}
		case 2: {
			System.out.println("Entrer votre identifiant : ");
			int idPlayer = sc.nextInt();
			
			player = new FactoryPlayer(idPlayer).create();
			System.out.println("Bonjour " + player.getUsername());
			
			
			break;
		}
		default :
			System.out.println("Erreur saisie");
		}
		

		System.out.println("Vous avez " + player.getListGames().size() + " partie(s) en cours." );
		
		
		
		/*try {
			List<Player> players = new FactoryListPlayer(1).create();
			Game game = new Game();
			game.setListPlayers(players);
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}*/
		
	}

}
