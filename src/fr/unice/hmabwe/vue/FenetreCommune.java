package fr.unice.hmabwe.vue;

import java.awt.*;

import javax.swing.*;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;

/**
 * 
 * @author M'RAH Mehdi
 * 
 * Fenetre Fenetre mere
 *
 */

public class FenetreCommune extends JFrame{
	
	protected JPanel container, downButton;
	
	protected DaoFabrique df;
	
	protected Connexion conn;
	
    protected JButton boutonOK, boutonAnnuler ;
    
    /**Contructeur par défaut*/
    public FenetreCommune(){
    	
    }
    
    
    public FenetreCommune(String nomFenetre, int longueur, int largeur, DaoFabrique df){
        /*
         * Quelques propriétés pour les fenêtres.
         */
    	this.setTitle(nomFenetre);
        this.setSize(longueur, largeur);
        this.setLocationRelativeTo(null);
        
        this.df = df;
        
        /* je récupère la connexion qui va me permettre:
		 * - d'ouvrir/fermer une connexion
		 * - débuter/commiter une transaction
		 * - de savoir l'état de la connexion/transaction
		 */
        
		
		this.conn = df.getConnexion();
		
        container = new JPanel();
        container.setLayout(new BorderLayout());
        
        downButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
       
        boutonOK = new JButton("Ok");
        boutonAnnuler = new JButton("Annuler");        
        downButton.add(boutonOK);
        downButton.add(boutonAnnuler);
        
        
        container.add(downButton, BorderLayout.SOUTH);
        
        this.setContentPane(container);
        
        
        
    }
}
