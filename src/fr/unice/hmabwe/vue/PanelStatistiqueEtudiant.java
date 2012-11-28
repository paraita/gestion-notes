package fr.unice.hmabwe.vue;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.unice.hmabwe.controleur.bd.dao.DaoEtudiant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Inscription;

/**
 * 
 * @author M'RAH Mehdi
 * 
 * Panel pour les statisiques d'un etudiant 
 *
 */

public class PanelStatistiqueEtudiant extends JPanel{
	private DaoFabrique df;
	
	private JPanel panelAnnee, panelMoyenne, panelCours, panel;
	private EcouteurCombo l;
	private JLabel annee, moyenne, noteMoyenne, changeAnne;
	public Etudiant e;
	public DaoEtudiant de;

	private JComboBox comboAnnee;
	private DefaultComboBoxModel comboModel;
	
	private JScrollPane scrollCours
	;
	//Simple essai pour l'interface
	public ArrayList<ObjetLigneMoyenneEtudiant> ligneMoyenne = new ArrayList<ObjetLigneMoyenneEtudiant>();
	
	/**Constructeur principal pour le Panel Etudiant Statistique
	 * @param e l'etudiant 
	 * @param df la Daofabrique
	 */
	public PanelStatistiqueEtudiant(Etudiant e, DaoFabrique df){
		// Methode de remplissage de diverse liste.
		this.df = df;
		this.de = this.df.getDaoEtudiant();
		this.e = de.findByNumeroEtudiant(e.getNumEtu());
		
		comboModel = new DefaultComboBoxModel();
		l = new EcouteurCombo();
		Collection<Inscription> listInscription = new ArrayList<Inscription>();
		ArrayList<Integer> listannee = new ArrayList<Integer>();
		
		listInscription = e.getInscriptions();
		for(Inscription insc : listInscription){
			ObjetLigneMoyenneEtudiant o = new ObjetLigneMoyenneEtudiant(insc);
			this.ligneMoyenne.add(o); // Remplissage de la liste de moyenne
			comboModel.addElement(insc.getAnnee()); // Remplissage de la liste Annee
		}
		
		
		panelAnnee = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelMoyenne = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelCours = new JPanel();
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		annee = new JLabel("Année :");
		moyenne = new JLabel("Moyenne :");
		
		comboAnnee = new JComboBox(comboModel);
		comboAnnee.addActionListener(l);
		
		noteMoyenne = new JLabel();
		
		

		
		
		panelAnnee.add(annee);
		panelAnnee.add(comboAnnee);
		
		panelMoyenne.add(moyenne);
		panelMoyenne.add(noteMoyenne);
		
		panelCours.setLayout(new BoxLayout(panelCours, BoxLayout.Y_AXIS));
		scrollCours = new JScrollPane(panelCours);
		scrollCours.setBorder(BorderFactory.createTitledBorder("Cours"));
		
		
		for(ObjetLigneMoyenneEtudiant objl : ligneMoyenne){
			panelCours.add(objl.panelMoyenne);
		}
		
		panel.add(panelAnnee);
		panel.add(panelMoyenne);
		panel.add(scrollCours);
	}
	/**Calcule la moyenne genéral d'un étudiant en fonction de l'année*/
	public void calculMoyenne(){
		try {
			noteMoyenne.setText(String.valueOf(de.getMoyenne(e.getNumEtu(), (Integer)comboAnnee.getSelectedItem())));
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**Classe pour les écouteurs. En particulier tout ce qui touche à la comboBox
	 */
	private class EcouteurCombo implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Object selection = arg0.getSource();
			if(selection.equals(comboAnnee)){
				calculMoyenne();
			}
			
		}

	
	}
	public JPanel getPanelPrincipal(){
		return this.panel;
	}
	
	public void setMoyenne(int note){
		this.noteMoyenne.setText(String.valueOf(note));
	}
	
	public void setAnnee(int annee){
		this.changeAnne.setText(String.valueOf(annee));
	}
	
}
