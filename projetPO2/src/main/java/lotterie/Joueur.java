package lotterie;

import java.util.*;

public class Joueur extends Thread implements Observateur{

    private String nom;
    private List<Billet> listeDeBillet;
    private Serveur serveur;
    int nombreC1, nombreC2; 
    
    public Joueur(String no, Serveur s, int c1, int c2) {
        nom=no;
        serveur=s;
        listeDeBillet=new ArrayList<Billet>();
        nombreC1=c1;
        nombreC2=c2;
    }

    public List<Billet> getListeDeBillet(){
        return listeDeBillet;
    }

    public String getNom() {
        return nom;
    }

    public lotterie.Serveur getServeur() {
        return serveur;
    }

    public void acheterBilletC1() {
    	if (serveur == null || serveur.getGestionnaire() == null) {
            throw new NullPointerException("Le serveur ou son gestionnaire ne peut pas être nul.");
        }
        CommandeAchatC1 command1 = new CommandeAchatC1(serveur.getGestionnaire(), this);
        synchronized (serveur) {
            serveur.getCommandes().add(command1);
        }
    }
    		
    public void acheterBilletC2(List<Integer> l) {
    	if (serveur == null || serveur.getGestionnaire() == null) {
            throw new NullPointerException("Le serveur ou son gestionnaire ne peut pas être nul.");
        }
    	//on verifie les numeros d'abord donnes par le joueur
        for(int i : l)
        {
            if(i<= 1 && i>= serveur.getN() )
            {
            	throw new IllegalArgumentException("Le nombre est hors des limites valides (1, " + serveur.getN() + "].");
            }
        }
        CommandeAchatC2 command2 =new CommandeAchatC2(serveur.getGestionnaire(),this,l);
        synchronized (serveur)
        {
            serveur.getCommandes().add(command2);
        }
    }
    

	@Override
	public void recevoirNumerosGagnants(List<Integer> numerosGagnants) {
		 // Vérifiez si les numéros gagnants correspondent aux numéros des billets du joueur
		List<Billet> bilgag=new ArrayList<>();
	    for (Billet billet : listeDeBillet) {
	        int count = billet.nombreNumeroGagnant(numerosGagnants);
	        if (count >= serveur.getT()) {
	        	bilgag.add(billet);
	        }
	    }
	    if(bilgag.size()!=0) { 
	    	System.out.println("----------->"+nom.toUpperCase()+"<-------------");
	    	for(Billet billet : bilgag) {
	    		 System.out.println("NS:" + billet.getNumeroSerie()+" numeros:"+billet.getNumeros());
	    	}
	    }
	}
	
    public void run() {
    	Random rom=new Random(); 
    	int r,j;
    	
    	long att=(nombreC1!=0)?nombreC2*1000:nombreC1*1000;
    	long debut=System.currentTimeMillis();
    	while(System.currentTimeMillis()-debut<att);
    	
    	try {
    		for(int i=0; i<nombreC2; i++) {//les numeros d'un billet de categorie II sont generer par le joueur
    			List<Integer> billetNum=new ArrayList<>();
    			j=0;
    			while(j<serveur.getK()) {
    				r=rom.nextInt(serveur.getN()) + 1;
    				if(!billetNum.contains(r)) {
    					billetNum.add(r);
    					j++;
    				}
    			}
    			acheterBilletC2(billetNum);
    		}
    		for  (int i=0;i<nombreC1;i++)
    		{
    		    acheterBilletC1();
    		}
    		System.out.println("Le joueur:"+nom+"------- a rejoins la lotterie");
    	}catch(Exception rie) {
    		rie.printStackTrace();
    	}
   }

}
