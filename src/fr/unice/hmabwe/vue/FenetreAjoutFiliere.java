package fr.unice.hmabwe.vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

import fr.unice.hmabwe.controleur.bd.dao.DaoEnseignant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.modele.Enseignant;
import fr.unice.hmabwe.modele.Filiere;

/**
 * 
 * @author M'RAH Mehdi
 * 
 * Fenetre permettant d'ajouter une filiere et son prof responsable
 *
 */

public class FenetreAjoutFiliere extends FenetreCommune{
	
	public PanelAjoutFiliere panelFiliere;
	
	public Enseignant ens;
	public DaoFiliere daoFiliere;
	public DaoEnseignant daoEnseignant;
	public EcouteurFiliere l;
	public EcouteurFiliereEnseignant l2;
	public Collection<Enseignant> listEns = new ArrayList<Enseignant>();
	public boolean estNouvelleFiliere;
	
	/**Constructeur permettant l'ajout d'une nouvelle filière
	 *@param df Daofabrique
	 */
	public FenetreAjoutFiliere(DaoFabrique df) {
		super("Ajout/Edition d'une filière", 500, 300, df);
		estNouvelleFiliere = true;
		
		panelFiliere = new PanelAjoutFiliere(df);
		
		daoFiliere = df.getDaoFiliere();
		daoEnseignant = df.getDaoEnseignant();
			
		l = new EcouteurFiliere(this);
		boutonOK.addMouseListener(l);
		boutonAnnuler.addMouseListener(l);
		panelFiliere.bAjoutEnseignant.addMouseListener(l);
		
		this.setResizable(true);
		this.container.add(panelFiliere.getPanelPrincipal(), BorderLayout.NORTH);
		this.setVisible(true);
		
		
	}
	
	/**Constructeur permettant de modifier une filiere
	 *@param df Daofabrique
	 *@param f Filière à modifier
	 */
	public FenetreAjoutFiliere(DaoFabrique df, Filiere f){
		
		super("Ajout/Edition d'une filière", 500, 300, df);
		estNouvelleFiliere = false;
		
		panelFiliere = new PanelAjoutFiliere(df);
		
		daoFiliere = df.getDaoFiliere();
		daoEnseignant = df.getDaoEnseignant();
		
		panelFiliere.textNom.setText(f.getNom());
		panelFiliere.tabEnseignant.setSelectedItem(f.getResponsable());
		
		
		l = new EcouteurFiliere(this, f);		
		boutonOK.addMouseListener(l);
		boutonAnnuler.addMouseListener(l);
		panelFiliere.bAjoutEnseignant.addMouseListener(l);
		
		
		
		this.setResizable(true);
		this.container.add(panelFiliere.getPanelPrincipal(), BorderLayout.NORTH);
		this.setVisible(true);
		
	}
	
	/**Ajout d'une filière en fonction de l'enseignant selectionné
	 * @param df La DaoFabrique
	 * @param e l'enseignant responsable de la filière*/
	public FenetreAjoutFiliere(DaoFabrique df, Enseignant e, FenetreGestionEnseignants fge){
		super("Ajout d'une filière pour l'enseignant " + e.getPrenom() + " " + e.getNom(), 350, 250, df);
		this.ens = e;
		daoFiliere = df.getDaoFiliere();
		
		panelFiliere = new PanelAjoutFiliere(df);
		l2 = new EcouteurFiliereEnseignant(this, panelFiliere, fge);
		
		this.boutonOK.addMouseListener(l2);
		this.setResizable(true);
		this.container.add(panelFiliere.getPanelPrincipal(), BorderLayout.NORTH);
		this.setVisible(true);
		
	}
	
	
	/** Classe qui gère les écouteurs de boutons */
	private class EcouteurFiliereEnseignant implements MouseListener{
		FenetreAjoutFiliere fac;
		PanelAjoutFiliere pac;
		FenetreGestionEnseignants fge;
		/**Constructeur principal pour l'ecoute des boutons
		 * @param fac La fenetre Principal
		 * @param pac Le panel principal
		 */
		public EcouteurFiliereEnseignant(FenetreAjoutFiliere fac, PanelAjoutFiliere pac, FenetreGestionEnseignants fge){
			this.fac = fac;
			this.pac = pac;
			this.fge = fge;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Object boutonSelected = arg0.getSource();
			if(boutonSelected.equals(boutonOK)){
				Filiere f = new Filiere(pac.getNom(), ens);
				daoFiliere = df.getDaoFiliere();
				
				try {
					conn.beginTransaction();
					daoFiliere.create(f);
					conn.commitTransaction();
					JOptionPane.showMessageDialog(fac,
						    "Filière ajoutée à la base");
					fac.setVisible(false);
					fge.setVisible(false);
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class EcouteurFiliere implements MouseListener{
		public FenetreAjoutFiliere faf;
		public Filiere filiereModif;
		
		public EcouteurFiliere(FenetreAjoutFiliere faf){
			this.faf = faf;
		}
		
		public EcouteurFiliere(FenetreAjoutFiliere faf, Filiere filiereModif){
			this.faf = faf;
			this.filiereModif = filiereModif;
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
			
			Object boutSelected = arg0.getSource();
			if(boutSelected.equals(boutonOK)){//Debut bouton OK
				if(estNouvelleFiliere){//Si nouvelle Filiere
					Filiere f = new Filiere(panelFiliere.getNom(), panelFiliere.getEnseignant());
					
					try {
						conn.beginTransaction();
						daoFiliere.create(f);
						conn.commitTransaction();
						this.faf.setVisible(false);
					} 
					catch (DaoException e) {
						try {
							conn.rollbackTransaction();
						}
						catch(DaoException ee) {
							ee.printStackTrace();
						}
						e.printStackTrace();
					}
					
				
				}//Fin nouvelle FIliere
				else{
					if(!estNouvelleFiliere){
						
						filiereModif.setNom(panelFiliere.getNom());
						try {
							conn.beginTransaction();
							daoFiliere.update(filiereModif);
							conn.commitTransaction();
							this.faf.setVisible(false);
						} 
						catch (DaoException e) {
							try {
								conn.rollbackTransaction();
							}
							catch(DaoException ee) {
								ee.printStackTrace();
							}
							e.printStackTrace();
						}
						
					}
				}
			}//Fin Bouton OK
			else{
				if(boutSelected.equals(panelFiliere.bAjoutEnseignant)){
					new FenetreGestionEnseignants(df);
					
				}
				else{
					if(boutSelected.equals(boutonAnnuler)){
						this.faf.setVisible(false);
						//TODO Ne rien faire et fermer la fenetre
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			
			
		}
		
	}
	

}
