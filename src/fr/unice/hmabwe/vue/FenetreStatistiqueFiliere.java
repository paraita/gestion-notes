package fr.unice.hmabwe.vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.modele.Filiere;

/**
 * 
 * @author M'RAH Mehdi
 * 
 * Fenetre permettant d'afficher les statistiques d'une filière
 *
 */

public class FenetreStatistiqueFiliere extends JFrame{
	private PanelStatistiqueFiliere panelStatFili;
	
	private DaoFabrique df;
	
	private DaoFiliere daofiliere;
	
	private Connexion conn;
	
	/** Constructeur vide*/
	public FenetreStatistiqueFiliere(){
		
	}
	
	
	public FenetreStatistiqueFiliere(DaoFabrique df, Filiere f){
		//TODO Dans le constructeur ajouter la connexion a la base.
		//TODO Remplacer les moyennes dans le constructeur, par les moyennes recus de la database.
		this.df = df;
		
		this.conn = df.getConnexion();
		daofiliere = df.getDaoFiliere();
		panelStatFili = new PanelStatistiqueFiliere(f, daofiliere);
		
				
		this.setTitle("Statistique Flière : " + f.getNom());
        this.setSize(400, 400);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
		this.setContentPane(panelStatFili.getPanelPrincipal());
		this.setVisible(true);
		
		
		
	}
	

}
