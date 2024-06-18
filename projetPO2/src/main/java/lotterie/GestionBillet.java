package lotterie;

import java.util.*;
import java.io.*;

public class GestionBillet  {//cette classe permet au serveur de gerer les billets(toutes les operations relatives aux billets)
	private int k;//nombre de nombre d'un billet
	private int n;//l'intervalle des nombres
	String nomfichier;
	ObjectOutputStream oos;//pour garder les billets en memoire
		
	public GestionBillet(String nomfichier,int n,int k) {
		this.nomfichier=nomfichier;
		this.k=k;
		this.n=n;
	}
	
	public String getNomfichier() {
		return nomfichier;
	}
	
	public synchronized void vendreBilletC1(Joueur j) {
		Billet billet=new Billet(j);
		genererNumero(billet);
		enregistrerBillet(billet);
		billet.getJoueur().getListeDeBillet().add(billet);
	}
	
	public synchronized void vendreBilletC2(Joueur j,List<Integer> l) {
		Billet billet=new Billet(j);
		billet.fixerNumero(l);
		enregistrerBillet(billet);
		billet.getJoueur().getListeDeBillet().add(billet);
	}
	
	public void genererNumero(Billet billet) {
		if (n <= 0 || k <= 0) {
	        throw new IllegalArgumentException("Les valeurs de n et k doivent être strictement positives.");
	    }
	   Random random = new Random();
	   int i=0;
       while(i<k) {
        // Générer des numéros de loterie au hasard
        int lotteryNumber = random.nextInt(n) + 1; // Exemple : Numéro entre 1 et n
        if(!billet.getNumeros().contains(lotteryNumber)) {
        	billet.getNumeros().add(lotteryNumber);
        	i++;
        }  	
       }
	}
	
	public void enregistrerBillet(Billet billet) {
		
		try {
			oos.writeObject(billet);		
		} catch (IOException e){
			System.out.println("La serialisation ->pro "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<Billet> recuperationBillet(){
		List<Billet> billets=new ArrayList<>();
		FileInputStream fis;
		Billet billet=null;
		try {
			fis = new FileInputStream(nomfichier);
			ObjectInputStream ois = new ObjectInputStream(fis);
			while(true) {
				 try {
	                    // Tentative de lecture d'un objet
	                    billet = (Billet) ois.readObject();
	                    billets.add(billet);
	                } catch (EOFException e) {
	                    // Fin du fichier atteinte
	                    break;
	                }
			}
			ois.close();
		} catch (IOException e) {
			System.out.println("La recuperation des billets "+e.getMessage());
			//e.printStackTrace();
		}catch(ClassNotFoundException nfe) {
			System.out.println("La recuperation des billets "+nfe.getMessage());
		}
		
		return billets;
	}
	
}
