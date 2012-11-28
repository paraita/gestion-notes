package fr.unice.hmabwe.vue;

import java.awt.FlowLayout;

import javax.swing.*;

import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Inscription;

/**
 * 
 * @author M'RAH Mehdi
 * 
 * Une ligne de moyenne d'etudiant c-a-d moyenne, cours, note a la suite
 *
 */

public class ObjetLigneMoyenneEtudiant extends JPanel{
	
	public JLabel moyenne, cours, note, annee;
	
	public JPanel panelMoyenne;
	
	/**COnstructeur créant un ligne de moyenne avec le nom du cours,  l'année et la note
	 * @param insc l'inscription concerné
	 */
	public ObjetLigneMoyenneEtudiant(Inscription insc){
		
		panelMoyenne = new JPanel(new FlowLayout(FlowLayout.LEFT));
		moyenne = new JLabel("Moyenne en ");
		this.cours = new JLabel();
		this.cours.setText(insc.getCours().getNom() + "( " + String.valueOf(insc.getAnnee()) + " )" );
		this.note = new JLabel();
		this.note.setText(": " + String.valueOf(insc.getMoyenne()));
		this.annee = new JLabel(String.valueOf(insc.getAnnee()));
		panelMoyenne.add(moyenne);
		panelMoyenne.add(this.cours);
		panelMoyenne.add(this.note);
		
	}
	
	
}
