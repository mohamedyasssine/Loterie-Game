package lotterie;

import java.util.*;

public class CommandeAchatC2 implements CommandeAchat {

	//on a besoin du gestionnaire de billet ou juste le serveur
	// le joueur emettant la commande
	//et la liste des nombre du billet 
	private GestionBillet gestionnaire;
	private Joueur joueur;
	private List<Integer> nombres;
	
	public CommandeAchatC2(GestionBillet g, Joueur j,List<Integer> nomb) {
		gestionnaire=g; 
		joueur=j;
		nombres=nomb;
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
			gestionnaire.vendreBilletC2(joueur,nombres);
		}
	}

}
