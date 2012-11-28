package fr.unice.hmabwe.vue;



import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

import fr.unice.hmabwe.controleur.bd.dao.DaoEtudiant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.controleur.bd.dao.DaoInscription;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Filiere;
import fr.unice.hmabwe.modele.Inscription;

/**
 * 
 * @author M'RAH Mehdi
 * 
 * Fenetre permettant d'ajouter un eleve avec sa liste de cours
 *
 */
public class FenetreAjoutEleve extends FenetreCommune{
	
    private PanelAjoutEleve panelEleve;
    
    private DaoEtudiant daoetudiant;
    
    private DaoFiliere daofiliere;
    
    private DaoInscription daoinscription;
    
    public boolean nouvelEtudiant;
    
    public EcouteurEtudiant l;
    
   
    
    /**Constructeur permettant d'afficher une fenêtre d'ajout d'un nouvel etudiant
     *@param df DaoFabrique
     */
	public FenetreAjoutEleve(DaoFabrique df) {
		super("Ajouter un élève", 550, 500, df);
		//C'est un ajout d'etudiant
		this.nouvelEtudiant = true;
		
		
		panelEleve = new PanelAjoutEleve(df, this);
	    daoetudiant = df.getDaoEtudiant();
	    daoinscription = df.getDaoInscription();
        daofiliere = df.getDaoFiliere();
       /* Collection<Filiere> listFili = new ArrayList<Filiere>();
		try {
			listFili = daofiliere.findAll();
			panelEleve.combo1.addItem(listFili);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        l = new EcouteurEtudiant(this);
	    boutonOK.addMouseListener(l);
	    boutonAnnuler.addMouseListener(l);
	    
	    this.setResizable(true);
        this.container.add(panelEleve.getPanelPrincipal(), BorderLayout.CENTER);
        this.setVisible(true);
		
	}
	
	/**Constructeur permettant d'afficher une fenêtre de modification d'étudiant
	 *@param df DaoFabrique
	 *@param e Etudiant à modifier
	 */
	public FenetreAjoutEleve(DaoFabrique df, Etudiant etumodif1){
		
		super("Ajouter un élève", 550, 500, df);
		//C'est une edition d'etudiant;
		this.nouvelEtudiant = false;
		
		panelEleve = new PanelAjoutEleve(df, this);
	    daoetudiant = df.getDaoEtudiant();
        Etudiant etumodif = daoetudiant.findByNumeroEtudiant(etumodif1.getNumEtu());
	    //On remplit les champs avec les anciennes valeurs
	    panelEleve.jtf.setText(etumodif.getNom());
	    panelEleve.jtf2.setText(etumodif.getPrenom());
	    panelEleve.jtf3.setText(etumodif.getMail());
	    panelEleve.jtf4.setText((etumodif.getGroupe()));
	    panelEleve.jtf5.setText(etumodif.getOrigine());	    
	    panelEleve.jtfNumetud.setText(etumodif.getNumEtu());
	    panelEleve.combo1.setSelectedItem(etumodif.getFiliere());
	    
	    panelEleve.lignePanel.remove(0);
	    panelEleve.lignePanel.remove(0);
	    panelEleve.listeLigne.remove(0);
	    panelEleve.listeLigne.remove(0);
	    for(Inscription i : etumodif.getInscriptions()){
	    	ObjetLigneInscription ob = new ObjetLigneInscription(false, panelEleve, df, this); // Un constructeur eut été préférable
	    	ob.textAnnee.setText(String.valueOf(i.getAnnee()));
	    	ob.textNote.setText(String.valueOf(i.getMoyenne()));
	    	ob.comboCours.setSelectedItem(i.getCours());
	    	panelEleve.listeLigne.add(ob);
	    	panelEleve.lignePanel.add(ob.panelLigne);
	    	
	    }
		ObjetLigneInscription o1 = new ObjetLigneInscription(true, panelEleve, df, this);
		ObjetLigneInscription o2 = new ObjetLigneInscription(false, panelEleve, df, this);
		//panelEleve.listeLigne.add(o2);
		panelEleve.listeLigne.add(o1);
		//panelEleve.lignePanel.add(o2.panelLigne);
		panelEleve.lignePanel.add(o1.panelLigne);
		
	    l = new EcouteurEtudiant(this, etumodif);
	    boutonOK.addMouseListener(l);
	    boutonAnnuler.addMouseListener(l);
	    this.setResizable(true);
        this.container.add(panelEleve.getPanelPrincipal(), BorderLayout.CENTER);
        this.setVisible(true);
        
	}
	private class EcouteurEtudiant implements MouseListener{
		public FenetreAjoutEleve fae;
		public Etudiant etumodif;
		public EcouteurEtudiant(FenetreAjoutEleve fae){
			this.fae = fae;
		}
		
		public EcouteurEtudiant(FenetreAjoutEleve fae, Etudiant etu){
			this.fae = fae;
			this.etumodif = etu;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			Object boutSelected = e.getSource();
			
			if(boutSelected.equals(boutonOK)){ //Si on click sur le bouton OK
				
				if(nouvelEtudiant){
					Etudiant etu = new Etudiant(panelEleve.getNumEtudiant(), panelEleve.getNom(), panelEleve.getPrenom(), panelEleve.getEmail(), panelEleve.getOrigine(),panelEleve.getFiliere() , panelEleve.getGroupe());
					Inscription insc;
					Collection<Inscription> listInscr = new ArrayList<Inscription>();
					// On recupere toutes les inscriptions de l'etudiants et on les ajoute à etu
					
					for(ObjetLigneInscription objl : panelEleve.listeLigne){
						if(!objl.isNewLine){
							insc = new Inscription(etu, objl.getCoursSelected(), objl.getAnnee(), objl.getMoyenne());
							//etu.addInscription(insc);
							listInscr.add(insc);
							System.out.println("Etudiant cree");
						}
					}
					try {
						conn.beginTransaction();
						daoetudiant.create(etu);
						for(Inscription i : listInscr){
							daoinscription.create(i);
						}
						conn.commitTransaction();
						System.out.println("Ouverture de la base et commit de l'etudiant");
						fae.setVisible(false);
					} 
					catch (DaoException e1) {
						
						try {
							conn.rollbackTransaction();
						} catch (DaoException e2) {
						
							e2.printStackTrace();
						}
						e1.printStackTrace();
					}
				
				} // Fin nouvel etudiant
				
				else{
					if(!nouvelEtudiant){
						etumodif.setNom(panelEleve.getNom());
						etumodif.setPrenom(panelEleve.getPrenom());
						etumodif.setMail(panelEleve.getEmail());
						etumodif.setGroupe(panelEleve.getGroupe());
						etumodif.setNumEtu(panelEleve.getNumEtudiant());
						etumodif.setOrigine(panelEleve.getOrigine());
						etumodif.setFiliere(panelEleve.getFiliere());
						
						
						/*Collection<Inscription> listInscr = etumodif.getInscriptions();
						for(int i = 0; i < listInscr.size(); i++){
							if(!panelEleve.listeLigne.get(i).isNewLine){
								listInscr.get(i).setCours(panelEleve.listeLigne.get(i).getCoursSelected());
								listInscr.get(i).setMoyenne(panelEleve.listeLigne.get(i).getMoyenne());
								
								 //insc = new Inscription(etumodif, objl.getCoursSelected(), objl.getAnnee(), objl.getMoyenne());
								//etu.addInscription(insc);
								//listInscr.add(insc);
								//System.out.println("Etudiant cree");
							}
						}*/
						
						
						try {
							conn.beginTransaction();
							daoetudiant.update(etumodif);
							/*for(Inscription i : listInscr){
								daoinscription.update(i);
							}*/
							
							conn.commitTransaction();
							fae.setVisible(false);
						} 
						catch (DaoException e1) {
							
							try {
								conn.rollbackTransaction();
							} catch (DaoException e2) {
							
								e2.printStackTrace();
							}
							e1.printStackTrace();
						}
					}
				} // Fin mis a jour etudiant
				
			}
			else{
				if(boutSelected.equals(boutonAnnuler)){
					fae.setVisible(false);
					//Fermer la fenetre
				}
			}
		}
	

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
