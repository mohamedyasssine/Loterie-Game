package lotterie;

import java.io.Serializable;
import java.util.*;

public class Billet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private String id;
	private List<Integer> numeros;
	private transient Joueur joueur;
	
	public Billet(Joueur j) {
		numeros=new ArrayList<Integer>();
		joueur=j;
		id=genererNumeroSerie();
	}
	
	private String genererNumeroSerie() {
		String fullUUID = UUID.randomUUID().toString();
	    return fullUUID.substring(0, 20).toUpperCase();
    }
	
	public int nombreNumeroGagnant(List<Integer> gagnant) {
        int count = 0;
        try {
            for (Integer number : gagnant) {
                if (numeros.contains(number)) {
                    count++;
                }
            }
            return count;
        }catch(NullPointerException e)
        {
        	 System.err.println("Erreur : Liste gagnante nulle.");
        }
		return count;
    }
 
	public String getNumeroSerie() {
		return id;
	}
	
	public void setNumeroSerie(String ns) {
		id=ns;
	}
	
	public void fixerNumero(List<Integer> l) {
		numeros=l;
	}
	
	public List<Integer> getNumeros(){
		return numeros;
	}
	
	public Joueur getJoueur() {
		return joueur;
	}
	
}
