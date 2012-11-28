package fr.unice.hmabwe.vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.dao.DaoCours;
import fr.unice.hmabwe.controleur.bd.dao.DaoEtudiant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoInscription;
import fr.unice.hmabwe.controleur.es.tableur.ExportExcel;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Etudiant;

public class FenetreExportExcel extends JFrame {

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
	private JButton valider = new JButton("Exporter");
	
	private JComboBox choixCours;
	private JSpinner annee;
	
	public FenetreExportExcel(DaoFabrique df) {
		this.df = df;
		
		this.conn = df.getConnexion();
		this.daoCours = df.getDaoCours();
		this.daoEtudiant = df.getDaoEtudiant();
		this.daoInscription = df.getDaoInscription();
		
		this.setLocationRelativeTo(null);
		this.setSize(500,150);
		this.setTitle("Export vers Excel");
		this.setLayout(new BorderLayout());
		
		try {
			choixCours = new JComboBox(daoCours.findAll().toArray());
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		annee = new JSpinner();
		annee.setValue(Calendar.getInstance().get(Calendar.YEAR));
		annee.setEditor(new JSpinner.NumberEditor(annee, "#"));
		
		parcourir.addActionListener(l);
		
		JPanel fileChooser = new JPanel();
		JPanel content = new JPanel();
		JPanel south = new JPanel();
		
		fileChooser.add(parcourir);
		fileChooser.add(file);
		
		content.add(new JLabel("Cours :"));
		content.add(choixCours);
		content.add(new JLabel("Année :"));
		content.add(annee);
		
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
				ExportExcel eex = new ExportExcel();
				try{
					conn.beginTransaction();
					Cours cours = (Cours)choixCours.getSelectedItem();
					HashMap<Etudiant, String> ed = daoInscription.listeInscrit(cours.getNom(),(Integer) annee.getValue());
			
					eex.createXls(ed, cours, (Integer)annee.getValue(), url);
					
					conn.commitTransaction();
					FenetreExportExcel.this.dispose();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if(e.getSource().equals(parcourir)) {
				int returnVal = fc.showSaveDialog(FenetreExportExcel.this);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                url = fc.getSelectedFile().toString();
	                file.setText(url);
	                valider.setEnabled(true);
	            }
			}
		}
		
	}
	
}
