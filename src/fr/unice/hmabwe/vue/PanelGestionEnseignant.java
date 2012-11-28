package fr.unice.hmabwe.vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
 * Panel pour la gestion d'un professeur 
 *
 */

public class PanelGestionEnseignant extends JPanel {
	private JSplitPane panel;
	
	private JPanel panelCours, panelFiliere, panelNom, panelPrenom ,panelEmail, panelDroite, panelIdentite, panelBouton1, panelBouton2, panelGauche, panelBoutPlusMoins;
	
	private JScrollPane panscrollEnseig, panscrollFili, panscrollCours;
	private JScrollBar essaiScroll = new JScrollBar();
	public JButton bAjout1, bAjout2, bSuppress1, bSuppress2, bModif1, bModif2,bModifEns, bPlus, bMoins;
	
	public ListEcouteur l = new ListEcouteur();
	private JLabel nom, prenom, email, filiere, cours;
	public JTextField txtNom, txtPrenom, txtEmail;
	public JList listEnseignant, listFiliere, listCours;
	
	public DefaultListModel listModelCours = new DefaultListModel();
	public DefaultListModel listModelFili = new DefaultListModel();
	public DefaultListModel listModelEnseignant;
	private DaoFabrique df;
	private DaoEnseignant de;
	private DaoCours dc;
	private DaoFiliere dfili;
	
	private Collection<Enseignant> listEnse = new ArrayList<Enseignant>();
	private Collection<Cours> listC = new ArrayList<Cours>();
	private Collection<Filiere> listF = new ArrayList<Filiere>();
	
	/**Constructeur du Panel Principal pour la fenêtre de gestion d'enseignants
	 */
	public PanelGestionEnseignant(DaoFabrique df) {
		
		this.df = df;
		// A effacer plus tard
		Object[] tabEnse = {"Patrick", "Louis", "Yves"};
		Object[] tabCours = {"Math", "Anglais", "Latin"};
		Object[] tabFili = {"L2I", "M1MASS", "Bio"};
		//
		de = df.getDaoEnseignant();
		//dc = df.getDaoCours();
		//dfili = df.getDaoFiliere();
		
		
		listModelEnseignant = new DefaultListModel();
		//listModelFili = new DefaultListModel();
		
		try {
			listEnse = de.findAll();
			//listC = dc.findAll();
			//listF = dfili.findAll();
			for(Enseignant e : listEnse){
				listModelEnseignant.addElement(e);
			}
			/*for(Cours c : listC){
				listModelCours.addElement(c);
			}
			for(Filiere f : listF){
				listModelFili.addElement(f);
			}*/
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		nom = new JLabel("Nom : ");
		prenom = new JLabel("Prenom : ");
		email = new JLabel("E-mail : ");
		
		txtNom = new JTextField();
		txtNom.setMaximumSize(new Dimension(150, 50));
		txtNom.setColumns(20);
		txtPrenom = new JTextField();
		txtPrenom.setMaximumSize(new Dimension(150, 50));
		txtPrenom.setColumns(20);
		txtEmail = new JTextField();
		txtEmail.setMaximumSize(new Dimension(150, 50));
		txtEmail.setColumns(20);
		
		panelNom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelNom.add(nom);
		panelNom.add(txtNom);
		
		panelPrenom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelPrenom.add(prenom);
		panelPrenom.add(txtPrenom);
		
		panelEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelEmail.add(email);
		panelEmail.add(txtEmail);
		
		
		panelIdentite = new JPanel();
		panelIdentite.setLayout(new BoxLayout(panelIdentite, BoxLayout.Y_AXIS));
		panelIdentite.add(panelNom);
		panelIdentite.add(panelPrenom);
		panelIdentite.add(panelEmail);
		
		listEnseignant = new JList(listModelEnseignant);
		listEnseignant.addListSelectionListener(l);
		listEnseignant.setFixedCellWidth(100);
		panscrollEnseig = new JScrollPane(listEnseignant, panscrollEnseig.VERTICAL_SCROLLBAR_AS_NEEDED, panscrollEnseig.HORIZONTAL_SCROLLBAR_NEVER );
		
		panelBoutPlusMoins = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bPlus = new JButton(new ImageIcon(this.getClass().getResource("/resource/plus-circle.png")));
		bMoins = new JButton(new ImageIcon(this.getClass().getResource("/resource/minus-circle.png")));
		bModifEns = new JButton(new ImageIcon(this.getClass().getResource("/resource/pencil.png")));
		panelBoutPlusMoins.add(bPlus);
		panelBoutPlusMoins.add(bMoins);
		panelBoutPlusMoins.add(bModifEns);
		
		panelGauche = new JPanel(new BorderLayout());
		panelGauche.add(panscrollEnseig, BorderLayout.CENTER);
		//panelGauche.add(panelBoutPlusMoins, BorderLayout.SOUTH);
		
		panelBouton1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bAjout1 = new JButton(new ImageIcon(this.getClass().getResource("/resource/plus-circle.png")));
		bSuppress1 = new JButton(new ImageIcon(this.getClass().getResource("/resource/minus-circle.png")));
		bModif1 = new JButton(new ImageIcon(this.getClass().getResource("/resource/pencil.png")));
		panelBouton1.add(bAjout1);
		//panelBouton1.add(bSuppress1);
		//panelBouton1.add(bModif1);
		
	
		listFiliere = new JList(listModelFili);
		panelFiliere = new JPanel(new BorderLayout());
		panelFiliere.setBorder(BorderFactory.createTitledBorder("Filière"));
		panscrollFili = new JScrollPane(listFiliere,panscrollCours.VERTICAL_SCROLLBAR_AS_NEEDED, panscrollCours.HORIZONTAL_SCROLLBAR_NEVER );
		panelFiliere.add(panscrollFili, BorderLayout.CENTER);
		panelFiliere.add(panelBouton1, BorderLayout.SOUTH);
		
		panelBouton2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bAjout2 = new JButton(new ImageIcon(this.getClass().getResource("/resource/plus-circle.png")));
		bSuppress2 = new JButton(new ImageIcon(this.getClass().getResource("/resource/minus-circle.png")));
		bModif2 = new JButton(new ImageIcon(this.getClass().getResource("/resource/pencil.png")));
		panelBouton2.add(bAjout2);
		//panelBouton2.add(bSuppress2);
		//panelBouton2.add(bModif2);
		
		
		listCours = new JList(listModelCours);
		panscrollCours = new JScrollPane(listCours,panscrollCours.VERTICAL_SCROLLBAR_AS_NEEDED, panscrollCours.HORIZONTAL_SCROLLBAR_NEVER );
		panelCours = new JPanel(new BorderLayout());
		panelCours.setBorder(BorderFactory.createTitledBorder("Cours"));
		
		panelCours.add(panscrollCours, BorderLayout.CENTER);
		panelCours.add(panelBouton2, BorderLayout.SOUTH);
		
		
		
		
		panelDroite = new JPanel(new GridLayout(3, 0));
		panelDroite.add(panelIdentite);
		panelDroite.add(panelFiliere);
		panelDroite.add(panelCours);
		panelDroite.setAlignmentX(50);
		
		panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelGauche, panelDroite);
		
	}
	
	public JSplitPane getPanelPrincipal(){
		return this.panel;
		
	}
	
	//private JTextField txtNom, txtPrenom, txtEmail;
	/**Methode qui retourne le nom entré
	 * @return le nom*/
	public String getNom(){
		return this.txtNom.getText();
	}
	/**Methode qui retourne le prenom entré
	 *  @return le prenom*/
	public String getPrenom(){
		return this.txtPrenom.getText();
	}
	/**Methode qui retourne l'email entré
	 *  @return l'email*/
	public String getEmail(){
		return this.txtEmail.getText();
	}
	
	//private JList listEnseignant, listFiliere, listCours;
	/**Methode qui retourne l'enseigant selectionné
	 *  @return l'enseignant*/
	public Enseignant getEnseignantSelect(){
		return (Enseignant) this.listEnseignant.getSelectedValue();
	}
	/**Methode qui retourne la filière séléctionné
	 * @return la filière*/
	public Filiere getFiliereSelect(){
		return (Filiere) this.listFiliere.getSelectedValue();
	}
	/**Methode qui retourne le cours séléctionné
	 *  @return le cours*/
	public Cours getCoursSelect(){
		return (Cours) this.listCours.getSelectedValue();
	}
	
	private class ListEcouteur implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(listEnseignant.getSelectedValue() == null) {
				return;
			}
			Enseignant ens = (Enseignant) listEnseignant.getSelectedValue();
			txtNom.setText(ens.getNom());
			txtPrenom.setText(ens.getPrenom());
			txtEmail.setText(ens.getMail());
			
			Collection<Cours> collecCours = ens.getCours();
			Collection<Filiere> collecFiliere = ens.getFilieres();
			listModelFili.removeAllElements();
			listModelCours.removeAllElements();
			for(Cours c : collecCours){
				
				listModelCours.addElement(c);
			}
			
			for(Filiere f : collecFiliere){
				
				listModelFili.addElement(f);
			}
			
			listCours.setModel(listModelCours);
			listFiliere.setModel(listModelFili);
			
			
			
			
			
		}
		
	}
	
}
