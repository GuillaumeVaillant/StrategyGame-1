package run;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import domain.objects.Game;
import domain.objects.Player;
import job.JobGame;
import job.JobPlayer;
import persistence.mapper.DataMapper;
import persistence.mapper.PlayerMapper;

public class Main {

	static Scanner sc = new Scanner(System.in);
	static JobPlayer playerJob = new JobPlayer();
	static JobGame gameJob = new JobGame();

	public static void main(String[] args) throws Exception {

		System.out.println("Bienvenue dans StrategyGame ! \n Quel joueur êtes vous ?");

		
		Iterator<Player> ite = playerJob.findExistingPlayers().listIterator();
		List<Integer> idsPlayers = new ArrayList<>();
		for (int i = 0; i < playerJob.findExistingPlayers().size(); i++) {
			Player playerExisting = ite.next();
			System.out.println(playerExisting.getUsername() + " ? - Taper " + playerExisting.getIdPlayer());
			idsPlayers.add(playerExisting.getIdPlayer());
		}

		Integer idPlayer = sc.nextInt();
		boolean found = false;
		while (found == false) {
			if (idsPlayers.contains(idPlayer)) {
				found = true;
			} else {
				System.out.println("Le joueur n'existe pas. Veuillez saisir le bon identifiant.");
				idPlayer = sc.nextInt();
			}
		}

		Player player = playerJob.findPlayer(idPlayer);
		System.out.println("Bonjour " + player.getUsername());

		System.out.println(
				"Que voulez-vous faire ? \n Créer une partie ? Tapez 1 \n Rejoindre une autre partie existante ? Tapez 2 \n "
				+ "Rejoindre une de vos parties ? Tapez 3 ");
		Integer choix = sc.nextInt();
		found = false;
		while (found == false) {
			if (choix == 1) {
				
			
				Game game = gameJob.createGame("Partie2", player.getIdPlayer(), 1, "WAITING", 10, 10);
				System.out.println(game.getIdGame());
				found = true;
			} else if (choix == 2) {
				
				
				
				gameJob.joinGameOtherPlayers();
				found = true;
			} else if (choix == 3) {

				System.out.println("Vous avez " + player.getListGames().size() + " partie(s) auxquelles vous participez.");
				for (Game game : player.getListGames()) {
					System.out.println(game.getIdGame() + " - " + game.getName());
				}
				
				gameJob.joinYourGames();
				found = true;
			} else {
				System.out.println("Vous avez tapé un mauvais chiffre, choississez entre 1, 2 et 3.");
				choix = sc.nextInt();
			}
		}

		

		
	}
}
