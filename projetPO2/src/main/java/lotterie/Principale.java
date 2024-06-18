package lotterie;


//import javax.swing.*;
import java.util.*;
public class Principale { 
	 public static void main(String[] args) {
		 
		int n = 50, k = 4, t = 2;
		GestionBillet gestionnaire = new GestionBillet("billet.txt",n, k);
		Serveur serveur = new Serveur(gestionnaire, n, k, t); 
		Joueur joueur1 = new Joueur("yassine", serveur, 6, 1);
		serveur.ajouterObservateur(joueur1);
		Joueur joueur2 = new Joueur("diamanka", serveur, 2, 0);
		serveur.ajouterObservateur(joueur2);
		Joueur joueur3 = new Joueur("barry", serveur, 10, 3);
		serveur.ajouterObservateur(joueur3);
		Joueur joueur4 = new Joueur("thierno",serveur, 4, 0);
		serveur.ajouterObservateur(joueur4);
		Joueur joueur5 = new Joueur("Carl",serveur, 3, 6);
		serveur.ajouterObservateur(joueur5);
		Joueur joueur6 = new Joueur("Marc",serveur, 15, 3);
		serveur.ajouterObservateur(joueur6);
		Joueur joueurIntell = new Joueur("Smart",serveur, 0, 25);//le nombre de billet de ce joueur doit correspondre au
		//nombre total de billet pour avoir au moins un billet gagnant i.e:nCt
		
		serveur.ajouterObservateur(joueurIntell);
		try {
			System.out.println("--------------------------------------");
			joueur1.start();
			joueur2.start();
			joueur3.start();
			joueur4.start();
			joueur5.start();
			joueur6.start();
			joueurIntell.start();
			System.out.println("La loterie est ouverte!!!");
			serveur.commencerLotterie();
			serveur.arreterLoterie();
			joueur1.join();
			joueur2.join();
			joueur3.join();
			joueur4.join();
			joueur5.join();
			joueur6.join();
			joueurIntell.join();
			System.out.println("--------------------------------------");
			
			List <Integer> numerogagnant =new ArrayList<>();
			List<Billet> billets=gestionnaire.recuperationBillet();
			System.out.println("Le nombre de billet vendu est:"+billets.size());
			numerogagnant=serveur.TirageNumerosGagnant(billets);
			
			System.out.print("LES NUMEROS GAGNANTS SONT:");
			for(int i:numerogagnant) {
				System.out.print(i+"  ");
			}System.out.println();
			
			System.out.println("Diffusion des numeros gagnants \n Voici les joueurs gagnants ");
			serveur.diffuserNumerosGagnants(numerogagnant);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	 }
}
