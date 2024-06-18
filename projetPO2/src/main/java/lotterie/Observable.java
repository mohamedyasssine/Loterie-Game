package lotterie;

import java.util.*;

public interface Observable {
	public void ajouterObservateur(Observateur observateur);
    public void supprimerObservateur(Observateur observateur);
    public void notifierObservateurs(List<Integer> numerosGagnants);
}
