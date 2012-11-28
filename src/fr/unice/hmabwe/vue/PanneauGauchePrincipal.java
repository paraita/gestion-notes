package fr.unice.hmabwe.vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.persistence.annotations.Property;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.dao.DaoCours;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Filiere;

/**
 * Le JPanel contenant la liste des filières et cours.
 *
 * @author Bastien Auda
 * 
 * 
 */

public class PanneauGauchePrincipal extends JPanel {

	private DaoFabrique df;
	private Connexion conn;
	private DaoFiliere daoFiliere;
	private DaoCours daoCours;

	private ActionListener l = new EcouteurGauche();
	private ListSelectionListener pcl = new ListeListener();

	private JComboBox choix = new JComboBox();
	private JList liste = new JList();

	private JPanel boutons = new JPanel();
	private JButton stats = new JButton(new ImageIcon(this.getClass().getResource("/resource/chart.png")));
	private JButton edit = new JButton(new ImageIcon(this.getClass().getResource("/resource/pencil.png")));
	private JButton remove = new JButton(new ImageIcon(this.getClass().getResource("/resource/minus-circle.png")));
	private JButton add = new JButton(new ImageIcon(this.getClass().getResource("/resource/plus-circle.png")));

	public PanneauGauchePrincipal(DaoFabrique df) {
		this.df = df;
		conn = df.getConnexion();
		daoFiliere = df.getDaoFiliere();
		daoCours = df.getDaoCours();

		this.setLayout(new BorderLayout());

		choix.addItem("Filière");
		choix.addItem("Cours");
		choix.addActionListener(l);

		this.add(choix, BorderLayout.NORTH);
		this.add(new JScrollPane(liste), BorderLayout.CENTER);
		liste.addListSelectionListener(pcl);
		try {
			liste.setListData(daoFiliere.findAll().toArray());
		} catch (DaoException e) {
			e.printStackTrace();
		}

		boutons.add(stats);
		stats.addActionListener(l);
		boutons.add(edit);
		edit.addActionListener(l);
		boutons.add(remove);
		remove.addActionListener(l);
		boutons.add(add);
		add.addActionListener(l);

		this.add(boutons, BorderLayout.SOUTH);

	}

	/**
	 * rafraichit la liste des filières et cours
	 */
	public void refreshList() {
		try {
			if(choix.getSelectedItem().equals("Filière")) {
				liste.setListData(daoFiliere.findAll().toArray());
			} else {
				liste.setListData(daoCours.findAll().toArray());
			}
			liste.setSelectedIndex(0);
		} catch (DaoException e) {
			//TODO error message
			e.printStackTrace();
		}
	}

	public Object getSelectedItem() {
		return liste.getSelectedValue();
	}

	/*
	 * Le listener des composants du panneau
	 */

	private class EcouteurGauche implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source.equals(choix)) {
				refreshList();
			}
			if(source.equals(add)) {
				if(choix.getSelectedItem().equals("Cours")) {
					new FenetreAjoutCours(df);
				} else {
					new FenetreAjoutFiliere(df);
				}
			}
			if(source.equals(remove)) {
				String objetASupprimer;
				try {
					conn.beginTransaction();
					if(choix.getSelectedItem().equals("Cours")) {
						Cours c = (Cours) liste.getSelectedValue();
						objetASupprimer = "le cours " + c.getNom();
						daoCours.delete(c);
					} else {
						Filiere f = (Filiere) liste.getSelectedValue();
						objetASupprimer = "la filière " + f.getNom();
						daoFiliere.delete(f);
					}
					int reponse = JOptionPane.showConfirmDialog(PanneauGauchePrincipal.this.getParent(), "Êtes vous certain de vouloir supprimer " +
							objetASupprimer + " ?");
					if(reponse == JOptionPane.OK_OPTION) {
						conn.commitTransaction();
					} else {
						conn.rollbackTransaction();
					}
				} catch (Exception ex) {
					// TODO: handle exception
				}
				refreshList();
			}
			if(source.equals(edit)) {
				if(choix.getSelectedItem().equals("Cours")) {
					Cours c = (Cours) liste.getSelectedValue();
					new FenetreAjoutCours(df, c);
				} else {
					Filiere f = (Filiere) liste.getSelectedValue();
					new FenetreAjoutFiliere(df,f);
				}
			}
			if(source.equals(stats)) {
				if(choix.getSelectedItem().equals("Cours")) {
					Cours c = (Cours) liste.getSelectedValue();
					//TODO stats cours
				} else {
					Filiere f = (Filiere) liste.getSelectedValue();
					new FenetreStatistiqueFiliere(df, f);
				}
			}
		}


	}

	private class ListeListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(liste.getSelectedValue() == null) {
				return;
			}
			Collection<Etudiant> etudiants = new ArrayList<Etudiant>();
			if(choix.getSelectedItem().equals("Cours")) {
				Cours c = (Cours) liste.getSelectedValue();
				etudiants = daoCours.getEtudiantsInstcrits(c);
			} else {
				Filiere f = (Filiere) liste.getSelectedValue();
				etudiants = daoFiliere.getEtudiantsInscrits(f);
			}
			FenetrePrincipale fp = (FenetrePrincipale) SwingUtilities.getRoot(PanneauGauchePrincipal.this);
			if(fp != null) {
				fp.setListeEtudiant(etudiants);
			}
		}


	}

}