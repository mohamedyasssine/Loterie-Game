# Loterie Game

## Description
Ce projet implémente une simulation de jeu de loterie en utilisant des threads en Java. 
Le jeu de loterie est composé d'un serveur qui gère les commandes d'achat de billets, 
de joueurs qui achètent des billets, et d'une gestion de billets.
.
ce projet contient un dossier Projet PO2 :les fichier src de projet 
				   Readme.md :explications pour déployer l'application


## Fonctionnalités
-**Serveur**: vends les billets à travers des commande d'achat, contrôle la durée de la loterie, et sélectionne les billets gagnants.
- **Joueur**: Achète des billets intelligemment en essayant de maximiser les chances de gagner avec un nombre minimal de billets.
- **Gestion de billets**: Génère et vend des billets de loterie avec des numéros aléatoires.
la notification des joueurs de numéro gagnant se faits par le design pattern observer-observable :le serveur implémente l'interface observable 
qui contient les méthode notifierjoueurs,ajouterObservateur et supprimerObservateur 
-à chaque fois on crée un joueur on l'ajout à la liste   JoueurObservateur de serveur 
une fois que la lotterie termine on appelle la méthode tirageNumerogagnant qui retourne une liste de numérogagnant 
et par la suite on appelle la méthode notifierJoueur en lui passant la liste des numéro gagnant .
cette méthode va appeller la méthode diffuserNumérogagnant() qui va itérer sur la liste des JoueurObservateur() et pour chaque joueur elle appelle la méthode recevoirNuméroGagnant()
-comme le joueur est l'observateur il va implémenter l'interface observer qui contient la méthode recevoir numéro gagnant ,cette méthode va récupérer la liste 
des numéro gagnant et va vérifier si le joueur a des billet contieint des t ou plus de numéro gagnant à l'aide de liste de billet dans la classe joueur.

## Comment utiliser
merci de :
1. **ouvrir le dossier Projet PO2 ** :ouvrir le dossier Projet MO avec un IDE (eclipse IDE)
dans  principale.java :merci de :
1. **Configurer le jeu** : Instanciez un gestionnaire de billets et un serveur avec les paramètres appropriés (n, k, t).
2. **Lancer les joueurs** : Créez des joueurs avec des stratégies d'achat de billets et ajoutez-les au serveur en tant qu'observateurs.
3. **Démarrer la loterie** : appeller la méthode démarrer lotterie et voir les résultat à l'aide de méthode TirageNumerosGagnant() 
4. **notifier les joueur** :à l'aide de design pattern observer-observable :appellez la méthode notifierObservateur() de serveur 
PS:vous trouverez un exemple d'utilisation dans la fichier principale.java 

## Exemple d'utilisation
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
####exécussion
La loterie est ouverte!!!
Le joueur:diamanka------- a rejoins la lotterie
Le joueur:Smart------- a rejoins la lotterie
Le joueur:thierno------- a rejoins la lotterie
duree:0
Le joueur:yassine------- a rejoins la lotterie
Le joueur:barry------- a rejoins la lotterie
Le joueur:Marc------- a rejoins la lotterie
Le joueur:Carl------- a rejoins la lotterie
La loterie est fermee!!!
--------------------------------------
Le nombre de billet vendu est:78
LES NUMEROS GAGNANTS SONT:4  27  36  41  
Diffusion des numeros gagnants 
 Voici les joueurs gagnants 
----------->SMART<-------------
NS:728E2E69-F6D5-492D-8 numeros:[27, 19, 43, 4]
NS:D9449696-C69B-4233-8 numeros:[36, 20, 4, 38]

```java

###Auteur
-Mohamed yassine aloui
-Mamadou Diamanka
Mamadou diamanka
####Licence
CC-zero