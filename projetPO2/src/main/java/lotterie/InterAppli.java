package lotterie;

import java.awt.*;
import java.util.Vector ;
import javax.swing.* ;
import javax.swing.border.EmptyBorder ;

public class InterAppli extends JFrame {

	public InterAppli() {
		super();
		
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelGauche=new JPanel(new FlowLayout());
		JPanel panelBas=new JPanel(new BorderLayout(0,10));
		JPanel panelCentre=new JPanel(new GridLayout(3,1,0,5));
		
		panel.add(panelCentre,BorderLayout.CENTER);
		
		panel.add(panelBas,BorderLayout.SOUTH);
		
		JButton bouton1=new JButton("Commencer");
		JButton bouton2=new JButton("AcheterC1");;
		JButton bouton3=new JButton("AcheterC2");
		panelGauche.add(bouton1);
		panelGauche.add(bouton2);
		panelGauche.add(bouton3);
		panel.add(panelGauche,BorderLayout.WEST);
			
		JLabel label = new JLabel("Exemple de bouton");
		panel.add(label, BorderLayout.NORTH);
		
		this.setContentPane(panel);
		this.pack();
	}

	public static void main(String[] args) {
		
		InterAppli applicat = new InterAppli();
		applicat.setSize(800,600);
		applicat.setVisible(true);
	}
}
