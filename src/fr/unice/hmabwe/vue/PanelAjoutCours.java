package fr.unice.hmabwe.vue;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jxl.biff.drawing.ComboBox;

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
 * Panel pour l'ajout d'un Cours 
 *
 */

public class PanelAjoutCours extends JPanel{
	
	private JLabel labelNom;
	private JLabel labelEnseignant, labelFiliere, labelCoeff, labelCours;
	
	public JButton bAjoutCours;
	
	private FenetreAjoutCours fac;
	
	public JTextField txtNom, txtCoeff;
	public JComboBox tabEnseignant;
	public JComboBox tabFiliere, tabCours;
	private DefaultComboBoxModel comboModel;
	private DefaultComboBoxModel comboModelFili, comboModelCours;
	
	private JPanel panel;
	private JPanel panelNom, panelFiliere, panelCoeff, panelAjoutCours;
	private JPanel panelEnseignant;
	
	private DaoFabrique df;
	private DaoEnseignant de;
	private DaoCours dc;
	private DaoFiliere dfili;
	
	private Collection<Enseignant> listEns = new ArrayList<Enseignant>();
	
	/**Construcuteur pour le panel permettant de créer un cours
	 * @param df Daofabrique
	 */
	public PanelAjoutCours(DaoFabrique df) {
		dfili = df.getDaoFiliere();
		comboModelFili = new DefaultComboBoxModel();
		
		try {
			Collection<Filiere> listFili = dfili.findAll();
			for(Filiere f : listFili){
				comboModelFili.addElement(f);
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		labelNom = new JLabel("Nom du cours:");
		txtNom = new JTextField();
		txtNom.setMaximumSize(new Dimension(150, 30));
		txtNom.setColumns(25);
		panelNom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelNom.add(labelNom);
		panelNom.add(txtNom);
		
		labelFiliere = new JLabel("Filiere :");
		tabFiliere = new JComboBox(comboModelFili);
		panelFiliere = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelFiliere.add(labelFiliere);
		panelFiliere.add(tabFiliere);
		
		labelCoeff = new JLabel("Coefficient du cours :");
		txtCoeff = new JTextField("");
		txtCoeff.setMaximumSize(new Dimension(150, 30));
		txtCoeff.setColumns(10);
		panelCoeff = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelCoeff.add(labelCoeff);
		panelCoeff.add(txtCoeff);
		
		
		
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(panelNom);
		panel.add(panelFiliere);
		panel.add(panelCoeff);
		
	}
	
	
	
	/**COnstructeur pour le panel d'ajout d'un nouveau cours associé à un enseignant
	 * @param df Daofabrique
	 * @param ens L'enseignant responsable
	 * @param fac la fenetre principal
	 */
	public PanelAjoutCours(DaoFabrique df, Enseignant ens, FenetreAjoutCours fac){
		this.fac = fac;
		dfili = df.getDaoFiliere();
		comboModelFili = new DefaultComboBoxModel();
		
		try {
			Collection<Filiere> listFili = dfili.findAll();
			for(Filiere f : listFili){
				comboModelFili.addElement(f);
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		labelNom = new JLabel("Nom du cours:");
		txtNom = new JTextField();
		txtNom.setMaximumSize(new Dimension(150, 30));
		txtNom.setColumns(25);
		panelNom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelNom.add(labelNom);
		panelNom.add(txtNom);
		
		labelFiliere = new JLabel("Filiere :");
		tabFiliere = new JComboBox(comboModelFili);
		panelFiliere = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelFiliere.add(labelFiliere);
		panelFiliere.add(tabFiliere);
		
		labelCoeff = new JLabel("Coefficient du cours :");
		txtCoeff = new JTextField("");
		txtCoeff.setMaximumSize(new Dimension(150, 30));
		txtCoeff.setColumns(10);
		panelCoeff = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelCoeff.add(labelCoeff);
		panelCoeff.add(txtCoeff);
		
		panelAjoutCours = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bAjoutCours = new JButton("Ajouter Cours existant");
		panelAjoutCours.add(bAjoutCours);
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(panelNom);
		panel.add(panelFiliere);
		panel.add(panelCoeff);
		//panel.add(panelAjoutCours);
		
	}
	
	public PanelAjoutCours(DaoFabrique df, Enseignant ens){
		de = df.getDaoEnseignant();
		List<Enseignant> e = de.getEnseignantsByName(ens.getNom());
		dc = df.getDaoCours();
		comboModelCours = new DefaultComboBoxModel();
		try {
			Collection<Cours> listCours = dc.findAll();
			for(Cours c : listCours){
				comboModelCours.addElement(c);
			}
			
		} catch (DaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		tabCours = new JComboBox(comboModelCours);
		labelCours = new JLabel("Cours :");
		panelAjoutCours = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		panelAjoutCours.add(labelCours);
		panelAjoutCours.add(tabCours);
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(panelAjoutCours);
	}
	
	
	
	
	
	
	public JPanel getPanelPrincipal(){
		return this.panel;
	}
	
	public String getNom(){
		return this.txtNom.getText();
	}
	
	public Enseignant getEnseignantChoisi(){
		return (Enseignant) this.tabEnseignant.getSelectedItem();
		//TODO A faire
	}
	
	public JComboBox getComboEnseignant(){
		return this.tabEnseignant;
	}
	
	public Filiere getSelectedFiliere(){
		return (Filiere)this.tabFiliere.getSelectedItem();
	}
	
	public String getCoeff(){
		return this.txtCoeff.getText();
	}
	
	public Cours getSelectedCours(){
		return (Cours)this.tabCours.getSelectedItem();
	}
	

}
