package run;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import domain.objects.Game;
import domain.objects.Player;
import job.JobPlayer;
import persistence.factories.FactoryListGame;
import persistence.factories.FactoryListPlayer;
import persistence.factories.FactoryPlayer;
import persistence.mapper.DataEnumType;
import persistence.mapper.DataMapper;
import persistence.mapper.PlayerMapper;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	static JobPlayer playerJob = new JobPlayer();
	static PlayerMapper playerMapper;
	static DataMapper dataMapper;

	public static void main(String[] args) throws Exception {
		
		
		System.out.println("Bienvenue dans StrategyGame !");
		
		System.out.println("Quel joueur êtes vous ?");
		
		Iterator<Player> ite = playerJob.findExistingPlayers().listIterator();
		for(int i = 0; i < playerJob.findExistingPlayers().size(); i++){
			Player playerExisting = ite.next();
			System.out.println(playerExisting.getUsername() + " ? - Taper " + playerExisting.getIdPlayer());
			
			
		}
		
			int idPlayer = sc.nextInt();
			Player player = playerJob.findPlayer(idPlayer);
			System.out.println("Bonjour " + player.getUsername());
			System.out.println("Vous avez " + player.getListGames().size() + " partie(s) en cours." );
			
			for(Game game : player.getListGames()){
					System.out.println(game.getIdGame() + " - " + game.getName());
				}
			
	}

}
