package run;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import domain.objects.Game;
import domain.objects.Player;
import job.JobGame;
import job.JobPlayer;
import persistence.mapper.DataMapper;
import persistence.mapper.PlayerMapper;
import view.SreenConnexion;

public class Main {

	static Scanner sc = new Scanner(System.in);
	static JobPlayer playerJob = new JobPlayer();
	static JobGame gameJob = new JobGame();

	public static void main(String[] args) throws Exception {

		JFrame frame = new SreenConnexion();
		frame.setVisible(true);
		
		/*
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
		
		System.out.println("Vous avez " + player.getListGames().size() + " partie(s) auxquelles vous participez.");
		for (Game game : player.getListGames()) {
			System.out.println(game.getIdGame() + " - " + game.getName());
		}
		
		System.out.println("Il existe " + gameJob.getGamesWaiting().size() + " partie(s) en attente.");
		for (Game game : gameJob.getGamesWaiting()) {
			System.out.println(game.getName());
		}

		System.out.println(
				"Que voulez-vous faire ? \n "
				+ "Créer une partie ? Tapez 1 \n "
				+ "Rejoindre une autre partie existante ? Tapez 2 \n "
				+ "Lancer une de vos parties ? Tapez 3 ");
		Integer choix = sc.nextInt();
		switch(choix){
			case 1:{
				
				System.out.println("CREATION PARTIE");
				System.out.println("Entrer nom de partie");
				sc.nextLine();
				String gameName = sc.nextLine();
				System.out.println("Entrer nombre de tours");
				int nbTurns = sc.nextInt();
				System.out.println("Entrer nombre de ressources par tour");
				int nbRessourcesTurns = sc.nextInt();
				System.out.println("Entrer nombre de ressources sur les champs");
				int nbRessourcesFields = sc.nextInt();
			
				Game game = gameJob.createGame(gameName, player, nbTurns, "WAITING", nbRessourcesTurns, nbRessourcesFields);
				
				break;
			}
			case 2:{
				
				System.out.println("Il existe " + gameJob.getGamesNotOwned(player.getIdPlayer()).size() + " autres partie(s) existantes.");
				for (Game game : gameJob.getGamesNotOwned(player.getIdPlayer())) {
					System.out.println(game.getName() + " - " + "Tapez " + game.getIdGame() + " pour la rejoindre.");
				}
				
				int choixJeu = sc.nextInt();
				
				gameJob.joinGame(player.getIdPlayer(), choixJeu);
				
				System.out.println("Vous êtes inscrit ! Voici les autres joueurs de la partie : ");
				
				Game game = gameJob.findGameById(choixJeu);
				for (Player players : game.getListPlayers()) {
					System.out.println(players.getUsername());
				}
				
				
				break;
				
				
			}
			case 3:{
				
				System.out.println("Vous avez " + gameJob.getGamesOwned(player.getIdPlayer()).size() + " partie(s) dont vous êtes le créateur.");
				for (Game game : gameJob.getGamesOwned(player.getIdPlayer())) {
					System.out.println("Lancer " + game.getName() + " ? Tapez " + game.getIdGame());
				}
				
				int choixJeu = sc.nextInt();
				
				Game game = gameJob.findGameById(choixJeu);
				game.setStatus("RUNNING");
				gameJob.updateGame(game);
				
				break;
			}
			
			default:
				System.out.println("Vous avez tapé un mauvais chiffre, choississez entre 1, 2 et 3.");
				choix = sc.nextInt();
			}*/
		}

		
			

		
}
