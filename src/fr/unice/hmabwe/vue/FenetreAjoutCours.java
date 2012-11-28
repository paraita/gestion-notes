package fr.unice.hmabwe.vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

import fr.unice.hmabwe.controleur.bd.dao.DaoCoefficient;
import fr.unice.hmabwe.controleur.bd.dao.DaoCours;
import fr.unice.hmabwe.controleur.bd.dao.DaoEnseignant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.modele.Coefficient;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Enseignant;

/**
 * 
 * @author M'RAH Mehdi
 * 
 * Fenetre permettant d'ajouter un cours
 *
 */

public class FenetreAjoutCours extends FenetreCommune{
	
	public PanelAjoutCours panelCours;
	public DaoCours daocours;
	public DaoEnseignant daoenseignant;
	public DaoCoefficient daocoeff;
	public Enseignant ens;
	public Collection<Enseignant> listeEnseign = new ArrayList<Enseignant>();
	public boolean estNouveauCours;
	public EcouteurCours l;
	public EcouteurCoursEnseignant l1;
	public EcouteurPanelCours l2;
	
	
	/**Constructeur permettant d'ajouter un nouveau cours
	 *@param df Daofabrique
	 */
	public FenetreAjoutCours(DaoFabrique df) {
		super("Ajout de cours", 500, 400, df);
		estNouveauCours = true;
		daocours = df.getDaoCours();
		daocoeff = df.getDaoCoefficient();
		daoenseignant = df.getDaoEnseignant();
		panelCours = new PanelAjoutCours(df);
		l = new EcouteurCours(this);
		
		boutonOK.addMouseListener(l);
		boutonAnnuler.addMouseListener(l);
		
		this.setResizable(true);
		this.container.add(panelCours.getPanelPrincipal(), BorderLayout.NORTH);
		this.setVisible(true);
		
		
		
	}
	/**Constructeur permettant de modifier un cours
	 *@param df Daofabrique
	 *@param c Cours à modifiers
	 */
	public FenetreAjoutCours(DaoFabrique df, Cours c){
		super("Ajout/Edition de cours", 400, 250, df);
		estNouveauCours = false;
		daocours = df.getDaoCours();
		daoenseignant = df.getDaoEnseignant();
		panelCours = new PanelAjoutCours(df);
		l = new EcouteurCours(this);
		
		boutonOK.addMouseListener(l);
		boutonAnnuler.addMouseListener(l);
		
		panelCours.txtNom.setText(c.getNom());
		panelCours.tabEnseignant.setSelectedItem(c.getEnseignant());
		
				
		this.setResizable(false);
		this.container.add(panelCours.getPanelPrincipal(), BorderLayout.NORTH);
		this.setVisible(true);
		
		
		
	}
	/**Constructeur pour la fenetre d'ajout de cours associé à un enseignant
	 * @param df DaoFabrique
	 * @param ens l'enseignant responsable
	 */
	public FenetreAjoutCours(DaoFabrique df, Enseignant ens, FenetreGestionEnseignants fge){
		super("Ajout de cours pour l'enseignant" + ens.getPrenom() + " " + ens.getNom(), 500, 400, df);
		estNouveauCours = true;
		this.ens = ens;
		FenetreGestionEnseignants fg = fge;
		panelCours = new PanelAjoutCours(df, ens, this);
		l1 = new EcouteurCoursEnseignant(this, panelCours, fg);
		daocours = df.getDaoCours();
		daocoeff = df.getDaoCoefficient();
		
		
		panelCours.bAjoutCours.addMouseListener(l1);
		this.boutonOK.addMouseListener(l1);
		
		this.setResizable(true);
		this.container.add(panelCours.getPanelPrincipal(), BorderLayout.NORTH);
		this.setVisible(true);
		
		
	}
	
	public FenetreAjoutCours(DaoFabrique df, Enseignant ens, Cours c, FenetreGestionEnseignants fge){
		super("Modification de cours pour l'enseignant" + ens.getPrenom() + " " + ens.getNom(), 500, 400, df);
		estNouveauCours = false;
		this.ens = ens;
		panelCours = new PanelAjoutCours(df, ens, this);
		l1 = new EcouteurCoursEnseignant(this, panelCours, fge);
		daocours = df.getDaoCours();
		daocoeff = df.getDaoCoefficient();
		panelCours.txtNom.setText(c.getNom());
		
		
		panelCours.bAjoutCours.addMouseListener(l1);
		this.boutonOK.addMouseListener(l1);
		
		this.setResizable(true);
		this.container.add(panelCours.getPanelPrincipal(), BorderLayout.NORTH);
		this.setVisible(true);
		
		
	}
	
	/**Fenetre pour associer un cours à un enseignant selectionné
	 * @param df DaoFabrique
	 * @param ens L'enseignant selectionné
	 * @param fac La fenetre principal
	 */
	public FenetreAjoutCours(DaoFabrique df, Enseignant ens, FenetreAjoutCours fac, int i){
		super("Associer un cours à l'enseignant " + ens.getPrenom() + " " + ens.getNom(), 300, 200, df);
		
		this.ens = ens;
		panelCours = new PanelAjoutCours(df, ens);
		
		l2 = new EcouteurPanelCours(this, panelCours, ens);
		
		this.boutonOK.addMouseListener(l2);
		this.setResizable(true);
		this.container.add(panelCours.getPanelPrincipal(), BorderLayout.NORTH);
		this.setVisible(true);
	}
	
	private class EcouteurCours implements MouseListener{
		public FenetreAjoutCours fac;
		
		public EcouteurCours(FenetreAjoutCours fac){
			this.fac = fac;
		}
		

		@Override
		public void mouseClicked(MouseEvent arg0) {
			Object boutonSelected = arg0.getSource();
			if(boutonSelected.equals(boutonOK)){//Debut bouton OK
				if(estNouveauCours){//Si nouveau Cours
					Cours c = new Cours(panelCours.getNom());
					Coefficient co = new Coefficient(c, panelCours.getSelectedFiliere(), Integer.parseInt(panelCours.getCoeff()));
					try {
						conn.beginTransaction();
						daocours.create(c);
						daocoeff.create(co);
						conn.commitTransaction();
						JOptionPane.showMessageDialog(fac,
							    "Cours ajouté à la base");
						fac.setVisible(false);
						
					} catch (DaoException e) {
						
						try {
							conn.rollbackTransaction();
						} catch (DaoException e1) {
							
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
				}//Fin si nouveau Cours
				
				else{
					if(!estNouveauCours){
						Cours c = new Cours(panelCours.getNom(), panelCours.getEnseignantChoisi());
						try {
							conn.beginTransaction();
							daocours.update(c);
							conn.commitTransaction();
							fac.setVisible(false);
						} catch (DaoException e) {
							
							try {
								fac.setVisible(false);
								conn.rollbackTransaction();
							} catch (DaoException e1) {
								
								e1.printStackTrace();
							}
							e.printStackTrace();
						}
						finally{
							fac.setVisible(false);
						}
						
					}
				}
				
			}//Fin boutn OK
			else{
				if(boutonSelected.equals(boutonAnnuler)){
					//TODO Fermer la fenetre sans rien faire d'autre
					fac.setVisible(false);
					
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
	
	private class EcouteurCoursEnseignant implements MouseListener{
		
		FenetreAjoutCours fac;
		PanelAjoutCours pac;
		FenetreGestionEnseignants fge;
		public EcouteurCoursEnseignant(FenetreAjoutCours fac, PanelAjoutCours pac, FenetreGestionEnseignants fge){
			this.fac = fac;
			this.pac = pac;
			this.fge = fge;
		}
		
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Object boutonSelected = arg0.getSource();
			if(boutonSelected.equals(boutonOK)){
				Cours c = new Cours(panelCours.getNom(), ens);
				Coefficient co = new Coefficient(c, panelCours.getSelectedFiliere(), Integer.parseInt(panelCours.getCoeff()));
				
				try {
					conn.beginTransaction();
					daocours.create(c);
					daocoeff.create(co);
					conn.commitTransaction();
					JOptionPane.showMessageDialog(fac,
						    "Cours ajouté à la base");
					
					fac.setVisible(false);
					fge.setVisible(false);
				} catch (DaoException e) {
					
					try {
						conn.rollbackTransaction();
					} catch (DaoException e1) {
						
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
				
			}
			else{
				if(boutonSelected.equals(pac.bAjoutCours)){
					fac.setVisible(false);
					new FenetreAjoutCours(df, ens,  fac, 0);
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
	
	private class EcouteurPanelCours implements MouseListener{
		FenetreAjoutCours fac;
		PanelAjoutCours pac;
		Enseignant ens;
		public EcouteurPanelCours(FenetreAjoutCours fac, PanelAjoutCours pac, Enseignant ens){
			this.fac = fac;
			this.pac = pac;
			this.ens = ens;
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Object boutSelected = arg0.getSource();
			if(boutSelected.equals(boutonOK)){
				//Ici on mettra la nouvelle fenetre cours 
				fac.setVisible(false);
				ens.addCours(pac.getSelectedCours());
				try {
					conn.beginTransaction();
					daoenseignant.update(ens);
					conn.commitTransaction();
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

}
