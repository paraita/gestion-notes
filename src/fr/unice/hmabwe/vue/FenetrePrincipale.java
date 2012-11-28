package fr.unice.hmabwe.vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.config.*;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.modele.Etudiant;

/**
 * La fenêtre principale dans laquelle nous auront les filières, les cours et les étudiants.
 * 
 * @author Bastien Auda
 * 
 *
 */
public class FenetrePrincipale extends JFrame {
	private DaoFabrique df;

	private ActionListener l = new MenuListener();

	// La barre des menus
	private JMenuBar menu = new JMenuBar();

	// Les menus
	private JMenu fichier = new JMenu("Fichier");
	private JMenu edition = new JMenu("Édition");
	private JMenu presentation = new JMenu("Présentation");
	private JMenu aide = new JMenu("Aide");

	// Les items du menu fichier
	private JMenu ajouter = new JMenu("Ajouter");
	private JMenuItem addEtudiant = new JMenuItem("un étudiant");
	private JMenuItem addEnseignant = new JMenuItem("un enseignant");
	private JMenuItem addCours = new JMenuItem("un cours");
	private JMenuItem addFiliere = new JMenuItem("une filière");

	private JMenuItem importFromXls = new JMenuItem("Importer depuis Excel", new ImageIcon(this.getClass().getResource("/resource/import.png")));
	private JMenuItem exportToXls = new JMenuItem("Exporter vers Excel", new ImageIcon(this.getClass().getResource("/resource/export.png")));

	private JMenuItem email = new JMenuItem("Envoyer les notes par e-mail", new ImageIcon(this.getClass().getResource("/resource/mail--arrow.png")));

	private JMenuItem quitter = new JMenuItem("Quitter", new ImageIcon(this.getClass().getResource("/resource/cross-button.png")));

	// Les items du menu edition
	private JMenuItem copier = new JMenuItem("Copier", new ImageIcon(this.getClass().getResource("/resource/document-copy.png")));
	private JMenuItem coller = new JMenuItem("Coller", new ImageIcon(this.getClass().getResource("/resource/clipboard-paste-document-text.png")));
	private JMenuItem selectAll = new JMenuItem("Tout sélectionner");
	private JMenuItem prefs = new JMenuItem("Préférences", new ImageIcon(this.getClass().getResource("/resource/gear.png")));

	// Les items du menu d'aide
	private JMenuItem about = new JMenuItem("À propos", new ImageIcon(this.getClass().getResource("/resource/information-frame.png")));
	private JMenuItem web = new JMenuItem("Site web du projet", new ImageIcon(this.getClass().getResource("/resource/question-frame.png")));

	// les composants de la fenêtre
	private PanneauGauchePrincipal panneauGauche;
	private PanneauListeEtudiants panneauDroite;
	private JSplitPane splitPane;



	public FenetrePrincipale(DaoFabrique df) {
		this.df = df;
		panneauGauche = new PanneauGauchePrincipal(df);
		panneauDroite  = new PanneauListeEtudiants(df);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panneauGauche, panneauDroite);

		this.setTitle("Envoi de note - hmabwe");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		addMenuBar();

		this.add(splitPane);

		this.setVisible(true);

	}

	/**
	 * Ajoute une barre des menus à la fenêtre.
	 */
	private void addMenuBar() {

		ajouter.add(addEtudiant);
		addEtudiant.addActionListener(l);
		ajouter.add(addEnseignant);
		addEnseignant.addActionListener(l);
		ajouter.add(addCours);
		addCours.addActionListener(l);
		ajouter.add(addFiliere);
		addFiliere.addActionListener(l);

		ajouter.setIcon(new ImageIcon(this.getClass().getResource("/resource/plus.png")));
		fichier.add(ajouter);
		fichier.addSeparator();
		fichier.add(importFromXls);
		importFromXls.addActionListener(l);
		fichier.add(exportToXls);
		exportToXls.addActionListener(l);
		fichier.add(email);
		email.addActionListener(l);
		fichier.addSeparator();
		fichier.add(quitter);
		quitter.addActionListener(l);

//		edition.add(copier);
//		copier.addActionListener(l);
//		edition.add(coller);
//		coller.addActionListener(l);
		edition.add(selectAll);
		selectAll.addActionListener(l);
		edition.add(prefs);
		prefs.addActionListener(l);

		aide.add(about);
		about.addActionListener(l);
		aide.add(web);
		web.addActionListener(l);

		menu.add(fichier);
		menu.add(edition);
		//menu.add(presentation);
		menu.add(aide);

		this.setJMenuBar(menu);

		this.addWindowListener(new FenetrePrincipaleListener());
	}

	/**
	 * Ferme la fenêtre en se déconnectant de la base proprement. 
	 */
	private void close() {
		try {
			df.getConnexion().fermer();
		} catch (DaoException e1) {
			e1.printStackTrace();
			System.out.println("La connexion n'a pas été fermée correctement, des données ont pu être corrompue.");
		}
		System.exit(0);
	}
	
	public void setListeEtudiant(Collection<Etudiant> etudiants) {
		panneauDroite.setListeEtudiants(etudiants);
	}
	
	public Object getSelectedItem() {
		return panneauGauche.getSelectedItem();
	}



	
	/*
	 * Le listener de la fenêtre
	 */
	private class FenetrePrincipaleListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent e) {

		}

		@Override
		public void windowClosed(WindowEvent e) {

		}

		@Override
		public void windowClosing(WindowEvent e) {

			((FenetrePrincipale) e.getSource()).close();

		}

		@Override
		public void windowDeactivated(WindowEvent e) {

		}

		@Override
		public void windowDeiconified(WindowEvent e) {

		}

		@Override
		public void windowIconified(WindowEvent e) {

		}

		@Override
		public void windowOpened(WindowEvent e) {

		}

	}

	/*
	 * Le listener de la barre de menu
	 */
	private class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Object source = e.getSource();
			// fichier
			if(source.equals(quitter)) {
				close();
			}
			if(source.equals(addCours)) {
				new FenetreAjoutCours(df);
			}
			if(source.equals(addEnseignant)) {
				new FenetreGestionEnseignants(df);
			}
			if(source.equals(addEtudiant)) {
				new FenetreAjoutEleve(df);
			}
			if(source.equals(addFiliere)) {
				new FenetreAjoutFiliere(df);
			}
			if(source.equals(email)) {
				panneauDroite.sendMail();
			}
			if(source.equals(exportToXls)) {
				new FenetreExportExcel(df);
			}
			if(source.equals(importFromXls)) {
				new FenetreImportExcel(df);
			}
			
			// edition
			if(source.equals(prefs)) {
				new FenetrePreferences();
			}
			if(source.equals(selectAll)) {
				panneauDroite.selectAllEtudiants();
			}
			
			// aide
			if(source.equals(web)) {
				String url = "http://code.google.com/p/hmabwe-envoiedenotes/";
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(FenetrePrincipale.this,
						    "Impossible de lancer votre navigateur web.",
						    "Erreur",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
			if(source.equals(about)) {
				new FenetreAPropos();
			}


		}

	}

}
