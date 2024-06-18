package lotterie;

public class CommandeAchatC1 implements CommandeAchat {
	
	//on a besoin du gestionnaire de billet ou juste le serveur
	//et le joueur emettant la commande
	private GestionBillet gestionnaire;
	private Joueur joueur;
	 
	public CommandeAchatC1(GestionBillet g, Joueur j ) {
		gestionnaire=g;
		joueur=j;
	}
	
	public GestionBillet getGestionnaire() {
		return gestionnaire;
	}
	
	public Joueur getJoueur() {
		return joueur;
	}
	
	public void execute() {
		if(gestionnaire==null || joueur==null) {
			throw new NullPointerException("Impossible de d'executer la commande");
		}
		synchronized(gestionnaire) {
			gestionnaire.vendreBilletC1(joueur);
		}
	}

}
