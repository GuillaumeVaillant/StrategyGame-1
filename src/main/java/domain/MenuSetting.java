package domain;

// Classe qui represente les parametres d'une partie
public class MenuSetting {

	private int id_Coo_Partie;
	private String type; // plutot montagneuse, plutot champs ...
	private int nbTurn;
	private int size;
	private int maxPlayer;
	private int ressources;
	private int nbResourcesPerTurn;
	private String namePart;
	private EnumState state;
	
	public MenuSetting(int id_Coo_Partie,String namePart,String type, int nbTurn, int size, int maxPlayer, int ressources, int nbResourcesPerTurn, EnumState state) {
		super();
		this.id_Coo_Partie = id_Coo_Partie;
		this.namePart = namePart;
		this.type = type;
		this.nbTurn = nbTurn;
		this.size = size;
		this.maxPlayer = maxPlayer;
		this.ressources = ressources;
		this.nbResourcesPerTurn = nbResourcesPerTurn;
		this.state = EnumState.EN_ATTENTE;
		
		// si pas de id_coo_partie alors il faut en crée un + créer une map
	}

	public int getId_Coo_Partie() {
		return id_Coo_Partie;
	}

	public void setId_Coo_Partie(int id_Coo_Partie) {
		this.id_Coo_Partie = id_Coo_Partie;
	}

	public EnumState getState() {
		return state;
	}

	public void setState(EnumState state) {
		this.state = state;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getNamePart() {
		return this.namePart;
	}
	
	public void setNamePart(String str) {
		this.namePart = str;
	}

	public int getNbTurn() {
		return nbTurn;
	}

	public void setNbTurn(int nbTurn) {
		this.nbTurn = nbTurn;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMaxPlayer() {
		return maxPlayer;
	}

	public void setMaxPlayer(int maxPlayer) {
		this.maxPlayer = maxPlayer;
	}

	public int getRessources() {
		return ressources;
	}

	public void setRessources(int ressources) {
		this.ressources = ressources;
	}

	public int getNbResourcesPerTurn() {
		return nbResourcesPerTurn;
	}

	public void setNbResourcesPerTurn(int nbResourcesPerTurn) {
		this.nbResourcesPerTurn = nbResourcesPerTurn;
	}
	
	
}
