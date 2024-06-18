package lotterie;

import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class Serveur implements Observable {
	//on a le gestionnaire des billets
	//un ensembles des toutes les commandes d'achat des joueurs
	//un pool de thread pour executer les commandes d'achat
	private LinkedBlockingQueue<CommandeAchat> commandes;
	private ExecutorService serveurThreads;
	private GestionBillet gestionnaire;
	private List<Observateur> joueursObservateurs;
	private int n,k,t;  //est ce necessaire?
	
	public Serveur(GestionBillet gestion,int n,int k,int t) {
		this.n=n;
		this.k=k;
		this.t=t;
		gestionnaire=gestion;
		commandes=new LinkedBlockingQueue<CommandeAchat>();
		serveurThreads=Executors.newFixedThreadPool(10);
		joueursObservateurs=new ArrayList<>();
	}
	
	public void commencerLotterie() {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(gestionnaire.nomfichier);
			gestionnaire.oos = new ObjectOutputStream(fos);
		} catch (IOException e){
			System.out.println("La serialisation ->pro "+e.getMessage());
			e.printStackTrace();
		}
		if (gestionnaire == null) {
	        throw new NullPointerException("Le gestionnaire ne peut pas être nul.");
	    }
		//tant que le temps de lotterie reste
		long duree=calculerBilletMin();
		System.out.println("duree:"+duree);
		long duree1 = 55*1000 - 5000; // Durée en millisecondes on enleve 5 secondes du temps du joueur intelligent
		long debutTemps = System.currentTimeMillis();
        
        while (System.currentTimeMillis() - debutTemps < duree1) {
        	venteBillet();
        }
	}
	
	public void arreterLoterie() {
		//que peut on faire avant l'arret de la lotterie
		if (serveurThreads != null) {
	        serveurThreads.shutdownNow();
	    }
		try {
			gestionnaire.oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("La loterie est fermee!!!");
	}
	
	public void venteBillet() {
		//while (!commandes.isEmpty()) {
		CommandeAchat commande = commandes.poll();
        if (commande != null) {
            serveurThreads.execute(commande::execute);
        }
	   //}
	}
	
	public long dureeLoterie() {
		//la logique par rapport a un joueur intelligent cad (n,k et t) 
		//L'INTELLIGENCE D'UN JOUEUR EST D'ACHETER LE NOMBRE MINIMAL DE BILLET 
		//POUR AVOIR AU MOINS UN BILLET GAGNANT: C(n,t)=n!/t!⋅(n−t)!
		long nombreMin=calculerBilletMin();
		//on suppose que l'achat d'un billet de categorie II prend 40 s
		// sinon on peut simuler pour avoir le temps exact 
		return 40*nombreMin;
	}
	
	public List<Integer> TirageNumerosGagnant(List<Billet> billets) throws ClassNotFoundException {
		List<Billet> billetsGagnant=new ArrayList<>();
		List<Integer> numerosTires=new ArrayList<>();
		
		//on peut utiliser le tirage ALEATOIRE PONDERE comme strategie de tirage des numeros gagnants
		Random random = new Random();
		int i=0;
		while(true) {
		       while(i<k) {
		        // Générer des numéros de loterie au hasard
		        int lotteryNumber = random.nextInt(n) + 1; // Exemple : Numéro entre 1 et n
		        if(!numerosTires.contains(lotteryNumber)) {
		        	numerosTires.add(lotteryNumber);
		        	i++;
		        }  	
		       }
		       billetsGagnant=billetGagnants(numerosTires,billets);
		       if(billetsGagnant.size()!=0) {
		    	   break;
		       }
		}
		
        return numerosTires;
	}
	
	public List<Billet> billetGagnants(List<Integer> gagnants, List<Billet> billets){
		if(gagnants==null ) {
			throw new NullPointerException("les numeros n'existent pas");
		}
		int count=0;
		List<Billet> billetsGagnant=new ArrayList<>();
		for(Billet element : billets) {
			count=element.nombreNumeroGagnant(gagnants);
			if(count>=t)
				billetsGagnant.add(element);
		}
		return billetsGagnant;
	}
	
	public void afficherBilletGagnants(List<Billet> l,List<Integer> g) {
		if(l==null || g==null) {
			throw new NullPointerException("Pas de billets gagnants pour cette loterie");
		}
		int count=0 ;
		int i=0;
		System.out.println("LES BILLETS GAGNANTS SONT:");
		for(Billet element : l) {
			count=element.nombreNumeroGagnant(g);
			System.out.println("******"+ ++i +"******");
			System.out.println("Billet "+ element.getNumeroSerie());
			System.out.println(element.getJoueur().getNom()+" avec "+count+" numeros gagnants");
		}
	}
	
	@Override
	public void ajouterObservateur(Observateur observateur) {
		if(observateur==null)
			throw new NullPointerException("Le joueur est null");
		 joueursObservateurs.add(observateur);
	}

	@Override
	public void supprimerObservateur(Observateur observateur) {
		if(joueursObservateurs.size()==0) {
			throw new NullPointerException("la liste des observateurs est vide");
		}
		joueursObservateurs.remove(observateur);
	}

	@Override
	public void notifierObservateurs(List<Integer> numerosGagnants) {
		if(numerosGagnants==null) {
			throw new NullPointerException("Pas de billets gagnants pour cette loterie");
		}
		 // Cette méthode est appelée pour diffuser les numéros gagnants
		   diffuserNumerosGagnants(numerosGagnants);
	}
	
	public void diffuserNumerosGagnants(List<Integer> numerosGagnants) {
		   for (Observateur observateur : joueursObservateurs) {
		      observateur.recevoirNumerosGagnants(numerosGagnants);
		   }
	}
	
	public void setN(int n)
	{
		this.n=n;
	}
	public int getN() {
		return n;
	}
	
	public void setK(int n)
	{
		this.k=n;
	}
	public int getK() {
		return k;
	}
	
	public void setT(int n)
	{
		this.t=n;
	}
	public int getT() {
		return t;
	}
	
	public GestionBillet getGestionnaire() {
		return gestionnaire;
	}
	
	public  LinkedBlockingQueue<CommandeAchat> getCommandes(){
		return commandes;
	}
		
	//rien a voir juste pour avoir la duree de la loterie 	
	public long factorial(int n) {
        if (n == 0 || n == 1) {
        	return 1;
        } 
        return n*factorial(n - 1);
    }
	
    public long calculateCombination() {
    	int d=n-t;
	 	long quot=factorial(t)*factorial(d);
	 	
	 	return factorial(n)/quot;
    }

    public long calculerBilletMin() {
        return calculateCombination();
    }

}
