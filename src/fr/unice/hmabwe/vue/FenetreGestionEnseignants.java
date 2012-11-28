package fr.unice.hmabwe.vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import fr.unice.hmabwe.controleur.bd.dao.DaoCours;
import fr.unice.hmabwe.controleur.bd.dao.DaoEnseignant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Enseignant;
import fr.unice.hmabwe.modele.Filiere;

/**
 * 
 * @author M'RAH Mehdi
 * 
 * Fenetre permettant de gérer les enseignants
 *
 */

public class FenetreGestionEnseignants extends FenetreCommune{
	
	public PanelGestionEnseignant panelEnseignant;
	private DefaultListModel modelListEns, modelListFili, modelListCours;
	public DaoEnseignant daoEnseignant;
	public DaoFiliere daofiliere;
	public DaoCours daocours;
	
	public boolean estNouvelleFenetre;
	
	public Collection<Filiere> listFil = new ArrayList<Filiere>();
	public Collection<Cours> listC = new ArrayList<Cours>();
	
	public EcouteurEnseignant l;
	/**Constructeur vide*/
	public FenetreGestionEnseignants(){
		
	}
	
	/**Constructeur pour un nouvel enseignant
	 * @param df DaoFabrique
	 */
	public FenetreGestionEnseignants(DaoFabrique df) {
		super("Gestion des enseignants", 500, 500, df);
		estNouvelleFenetre = true;
		// Pas instancié ça pose un probleme !!!!!!!!!
		daoEnseignant = df.getDaoEnseignant();
		daocours = df.getDaoCours();
		daofiliere = df.getDaoFiliere();
		panelEnseignant = new PanelGestionEnseignant(df);
		modelListCours = new DefaultListModel();
		modelListEns = new DefaultListModel();
		modelListFili = new DefaultListModel();
		
		l = new EcouteurEnseignant(this);
		
		boutonOK.addMouseListener(l);
		boutonAnnuler.addMouseListener(l);
		panelEnseignant.bAjout1.addMouseListener(l);
		panelEnseignant.bAjout2.addMouseListener(l);
		panelEnseignant.bModif1.addMouseListener(l);
		panelEnseignant.bModif2.addMouseListener(l);
		panelEnseignant.bModifEns.addMouseListener(l);
		panelEnseignant.bMoins.addMouseListener(l);
		panelEnseignant.bPlus.addMouseListener(l);
		panelEnseignant.bSuppress1.addMouseListener(l);
		panelEnseignant.bSuppress2.addMouseListener(l);
		
		try {
			listFil = daofiliere.findAll();
			listC = daocours.findAll();
			//panelEnseignant.listCours.setModel((ListModel) listC);
			//panelEnseignant.listFiliere.setModel((ListModel) listFil);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.setResizable(false);
		this.container.add(panelEnseignant.getPanelPrincipal(), BorderLayout.CENTER);
		this.setVisible(true);
		
		
		
		
		// TODO Auto-generated constructor stub
	}
	
	/**Constructeur pour la modification d'un enseignant
	 * @param df Daofabrique
	 * @param e L'enseignant
	 */
	public FenetreGestionEnseignants(DaoFabrique df, Enseignant e) {
		super("Gestion des enseignants", 500, 500, df);
		estNouvelleFenetre = false;
		// Pas instancié ça pose un probleme !!!!!!!!!
		daoEnseignant = df.getDaoEnseignant();
		daocours = df.getDaoCours();
		daofiliere = df.getDaoFiliere();
		panelEnseignant = new PanelGestionEnseignant(df);
		l = new EcouteurEnseignant(this);
		
		//Pre remplissage des zones de texte
		panelEnseignant.txtNom.setText(e.getNom());
		panelEnseignant.txtPrenom.setText(e.getPrenom());
		panelEnseignant.txtEmail.setText(e.getMail());
		
		// Ajout des ecouteurs
		boutonOK.addMouseListener(l);
		boutonAnnuler.addMouseListener(l);
		panelEnseignant.bAjout1.addMouseListener(l);
		panelEnseignant.bAjout2.addMouseListener(l);
		panelEnseignant.bModif1.addMouseListener(l);
		panelEnseignant.bModif2.addMouseListener(l);
		panelEnseignant.bMoins.addMouseListener(l);
		panelEnseignant.bModifEns.addMouseListener(l);
		panelEnseignant.bPlus.addMouseListener(l);
		panelEnseignant.bSuppress1.addMouseListener(l);
		panelEnseignant.bSuppress2.addMouseListener(l);
		
		
		try {
			listFil = daofiliere.findAll();
			listC = daocours.findAll();
			for(Filiere f : listFil){
				modelListFili.addElement(f);
			}
			for(Cours c : listC){
				modelListCours.addElement(c);
			}
			panelEnseignant.listCours.setModel(modelListCours);
			panelEnseignant.listFiliere.setModel(modelListFili);
		} catch (DaoException ef) {
			// TODO Auto-generated catch block
			ef.printStackTrace();
		}
		
		
		this.setResizable(false);
		this.container.add(panelEnseignant.getPanelPrincipal(), BorderLayout.CENTER);
		this.setVisible(true);
		
		
		
		
		// TODO Auto-generated constructor stub
	}

	private class EcouteurEnseignant implements MouseListener{
		public FenetreGestionEnseignants fge;
		
		public EcouteurEnseignant(FenetreGestionEnseignants fge){
			this.fge = fge;
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Object boutSelected = arg0.getSource();
			if(boutSelected.equals(panelEnseignant.bAjout1)){//Si on veut ajouter une nouvelle filière
				//new FenetreAjoutFiliere(df);
				new FenetreAjoutFiliere(df, panelEnseignant.getEnseignantSelect(), fge);
			}
			else{
				if(boutSelected.equals(panelEnseignant.bAjout2)){//Si on veut ajouter un nouveau cours
					//new FenetreAjoutCours(df);
					new FenetreAjoutCours(df, panelEnseignant.getEnseignantSelect(), fge);
				}
				else{
					if(boutSelected.equals(boutonOK)){//Si on clique sur le boutonOK
						if(estNouvelleFenetre){//Si c'est une nouvelle inscription
							Enseignant e = new Enseignant(panelEnseignant.getNom(), panelEnseignant.getPrenom(), panelEnseignant.getEmail());
							
							//e.addFiliere(panelEnseignant.getFiliereSelect());
							//e.addCours(panelEnseignant.getCoursSelect());
							
							
							try {
								conn.beginTransaction();
								daoEnseignant.create(e);
								conn.commitTransaction();
								JOptionPane.showMessageDialog(fge,
									    "Enseignant correctement ajouté à la base");
								fge.setVisible(false);
							}
							catch (DaoException e1) {
								try {
									conn.rollbackTransaction();
								} catch (DaoException e2) {
									
									e2.printStackTrace();
								}
								e1.printStackTrace();
							}
							
						}//Fin si c'est une nouvelle Inscription
						else{
							if(!estNouvelleFenetre){//Mis a jour
								Enseignant e = new Enseignant(panelEnseignant.getNom(), panelEnseignant.getPrenom(), panelEnseignant.getEmail());
								
								//e.addFiliere(panelEnseignant.getFiliereSelect());
								//e.addCours(panelEnseignant.getCoursSelect());
								
								
								try {
									conn.beginTransaction();
									daoEnseignant.update(e);
									conn.commitTransaction();
									
								}
								catch (DaoException e1) {
									try {
										conn.rollbackTransaction();
									} catch (DaoException e2) {
										
										e2.printStackTrace();
									}
									e1.printStackTrace();
								}
								
							}//Fin si c'est une mis a jour
						}
						
					}//Fin bouton OK
					else{
						if(boutSelected.equals(boutonAnnuler)){
							//TODO Fermer la fenetre
							fge.setVisible(false);
						}
						
						else{
							if(boutSelected.equals(panelEnseignant.bMoins)){
								//TODO methode de suppression...
							}
							else{
								if(boutSelected.equals(panelEnseignant.bModif1)){
									new FenetreAjoutFiliere(df, panelEnseignant.getFiliereSelect());
									//new FenetreStatistiqueFiliere(df, panelEnseignant.getFiliereSelect());
								}
								else{
									if(boutSelected.equals(panelEnseignant.bModif2)){
										//new FenetreAjoutCours(df, panelEnseignant.getCoursSelect());
										new FenetreAjoutCours(df, panelEnseignant.getEnseignantSelect(), panelEnseignant.getCoursSelect(), fge);
									}
									else{
										if(boutSelected.equals(panelEnseignant.bModifEns)){
											new FenetreGestionEnseignants(df, panelEnseignant.getEnseignantSelect());
										}
									}
								}
							}
						}
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
