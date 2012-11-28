package fr.unice.hmabwe.vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.dao.DaoCours;
import fr.unice.hmabwe.controleur.bd.dao.DaoEtudiant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoInscription;
import fr.unice.hmabwe.controleur.es.tableur.ExportExcel;
import fr.unice.hmabwe.controleur.es.tableur.ImportExcel;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Etudiant;

public class FenetreImportExcel extends JFrame {

	private DaoFabrique df;
	private Connexion conn;
	private DaoEtudiant daoEtudiant;
	private DaoCours daoCours;
	private DaoInscription daoInscription;
	
	private BoutonListener l = new BoutonListener();
	private JFileChooser fc = new JFileChooser();
	
	private String url = "";
	
	private JButton parcourir = new JButton("Parcourir");
	private JLabel file = new JLabel();
	private JButton valider = new JButton("Importer");
	
	private JTextField numEtu = new JTextField(1);
	private JTextField nom = new JTextField(1);
	private JTextField prenom = new JTextField(1);
	private JTextField mail = new JTextField(1);
	private JTextField origine = new JTextField(1);
	private JTextField moyenne = new JTextField(1);
	private JTextField filiere = new JTextField(1);
	private JTextField annee = new JTextField(1);
	private JTextField cours = new JTextField(1);
	
	public FenetreImportExcel(DaoFabrique df) {
		this.df = df;
		
		this.conn = df.getConnexion();
		this.daoCours = df.getDaoCours();
		this.daoEtudiant = df.getDaoEtudiant();
		this.daoInscription = df.getDaoInscription();
		
		this.setLocationRelativeTo(null);
		this.setSize(500,250);
		this.setTitle("Import depuis Excel");
		this.setLayout(new BorderLayout());
		
			
		parcourir.addActionListener(l);
		
		JPanel fileChooser = new JPanel();
		JPanel content = new JPanel();
		JPanel south = new JPanel();
		
		fileChooser.add(parcourir);
		fileChooser.add(file);
		
		content.add(new JLabel("Colonne numéro étudiant :"));
		content.add(numEtu);
		content.add(new JLabel("Colonne nom :"));
		content.add(nom);
		content.add(new JLabel("Colonne prénom :"));
		content.add(prenom);
		content.add(new JLabel("Colonne e-mail :"));
		content.add(mail);
		content.add(new JLabel("Colonne origine :"));
		content.add(origine);
		content.add(new JLabel("Colonne moyenne :"));
		content.add(moyenne);
		content.add(new JLabel("Colonne filière :"));
		content.add(filiere);
		content.add(new JLabel("Colonne année :"));
		content.add(annee);
		content.add(new JLabel("Colonne cours :"));
		content.add(cours);
		
		
		south.add(valider,BorderLayout.SOUTH);
		valider.addActionListener(l);
		valider.setEnabled(false);
		
		this.add(fileChooser,BorderLayout.NORTH);
		this.add(content,BorderLayout.CENTER);
		this.add(south,BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	private class BoutonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(valider)) {
				ImportExcel ie = new ImportExcel(url, numEtu.getText(), nom.getText(), prenom.getText(), mail.getText(), origine.getText(), moyenne.getText(), filiere.getText(), annee.getText(), cours.getText());
				
				HashMap<Etudiant, Double> ed = ie.lectureListEtudiants();
				
				
				
				FenetreImportExcel.this.dispose();
			}
			if(e.getSource().equals(parcourir)) {
				int returnVal = fc.showOpenDialog(FenetreImportExcel.this);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                url = fc.getSelectedFile().toString();
	                file.setText(url);
	                valider.setEnabled(true);
	            }
			}
		}
		
	}
	
}