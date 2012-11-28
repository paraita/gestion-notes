package fr.unice.hmabwe.vue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.dao.DaoEtudiant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Filiere;
import fr.unice.hmabwe.modele.Inscription;

/**
 * 
 * @author Bastien Auda
 *
 */
public class EtudiantTableModel extends AbstractTableModel {

	private DaoFabrique df;
	private DaoEtudiant daoEtudiant;
	private Connexion conn;
	
	private ArrayList<Etudiant> etudiants;
	
	private Boolean tableauModifie = false;
	

	public EtudiantTableModel(Collection<Etudiant> etudiants, DaoFabrique df) {
		this.etudiants = new ArrayList<Etudiant>(etudiants);
		this.df = df;
		this.daoEtudiant = df.getDaoEtudiant();
		this.conn = df.getConnexion();
	}

	public EtudiantTableModel(DaoFabrique df) {
		this(new ArrayList<Etudiant>(),df);
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return etudiants.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Etudiant e = etudiants.get(row);
		switch (col) {
		case 0:
			return e.getNumEtu();
		case 1:
			return e.getNom();
		case 2:
			return e.getPrenom();
		case 3: 
			return e.getMail();
		case 4:
			return e.getFiliere();
		case 5:
			return e.getGroupe();
		case 6:
			return e.getOrigine();

		default:
			return null;
		}
	}

	public String getColumnName(int col) {
		switch (col) {
		case 0:
			return "Numéro";
		case 1:
			return "Nom";
		case 2:
			return "Prénom";
		case 3: 
			return "e-mail";
		case 4:
			return "Filière";
		case 5:
			return "Groupe";
		case 6:
			return "Origine";
		default:
			return null;
		}
	}

	public Class getColumnClass(int col){
		if(col == 4) {
			return JComboBox.class;
		} else {
			return String.class;
		}
	}

	public boolean isCellEditable(int row, int column) {
		return true;
	}
	
	public void setValueAt(Object valeur, int row, int column) {
		Etudiant e = etudiants.get(row);
		switch (column) {
		case 0:
			e.setNumEtu((String) valeur);
			break;
		case 1:
			e.setNom((String) valeur);
			break;
		case 2:
			e.setPrenom((String) valeur);
			break;
		case 3:
			e.setMail((String) valeur);
			break;
		case 4:
			e.setFiliere((Filiere) valeur);
			break;
		case 5:
			e.setGroupe((String) valeur);
			break;
		case 6:
			e.setOrigine((String) valeur);
			break;
		default:
			break;
		}
		try {
			conn.beginTransaction();
			daoEtudiant.update(e);
			conn.commitTransaction();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		fireTableCellUpdated(row, column);
	}
	
	/**
	 * 
	 * @return Vrai si le tableau à été modifié.
	 */
	public Boolean modifie() {
		return tableauModifie;
	}
	
	public void resetModif() {
		tableauModifie = false;
	}

	public void setEtudiants(Collection<Etudiant> etudiants) {
		this.etudiants = new ArrayList<Etudiant>(etudiants);
		this.fireTableDataChanged();
	}
	
	public void addEtudiants(Collection<Etudiant> etudiants) throws DaoException {
		this.etudiants.addAll(etudiants);
		conn.beginTransaction();
		for(Etudiant e : etudiants) {
			daoEtudiant.create(e);
		}
		conn.commitTransaction();
		fireTableDataChanged();
	}
	
	public void addEtudiants(Etudiant e) throws DaoException {
		Collection<Etudiant> etudiants = new ArrayList<Etudiant>();
		etudiants.add(e);
		addEtudiants(etudiants);
	}
	
	/**
	 * Ajoute une ligne étudiant inscrit dans la filière ou le cours affiché.
	 * @param coursFiliere
	 * @throws DaoException 
	 */
	public void addEtudiants(Object coursFiliere) throws DaoException {
		Etudiant e = new Etudiant();
		e.setNumEtu(Integer.toString(new Random().nextInt(9000)+1000));
		e.setGroupe("1");
		if(coursFiliere.getClass() == Cours.class) {
			e.addInscription(new Inscription(e,(Cours) coursFiliere, Calendar.getInstance().get(Calendar.YEAR)));
		} else {
			e.setFiliere((Filiere) coursFiliere);
		}
		addEtudiants(e);
	}
	
	/**
	 * Supprime tout les étudiants sélectionnés.
	 */
	public void deleteRows(int[] selectedRows) {
		for(int i : selectedRows) {
			etudiants.remove(i);
		}
		fireTableDataChanged();
	}
	
	public Etudiant getEtudiant(int i) {
		return etudiants.get(i);
	}
	
	public Collection<Etudiant> getEtudiants(int[] rows) {
		Collection<Etudiant> res = new ArrayList<Etudiant>();
		for(int i : rows) {
			res.add(getEtudiant(i));
		}
		return res;
	}

}