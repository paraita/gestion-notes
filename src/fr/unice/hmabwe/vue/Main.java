package fr.unice.hmabwe.vue;

import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion.TypePersistance;
import fr.unice.hmabwe.controleur.bd.dao.DaoEtudiant;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.modele.Etudiant;

/**
 * 
 * @author M'RAH Mehdi
 * 
 * Affichage des fenetres pour avoir une vue d'ensemble
 *
 */

public class Main {
	public static void main(String[] args){
		ConfigConnexion.setTypePersistance(TypePersistance.JPA);
		DaoEtudiant de;
		/* je demande une fabrique */
		DaoFabrique df = DaoFabrique.getDaoFabrique();
		de = df.getDaoEtudiant();
		//Etudiant e = new Etudiant("mm258963", "Alban", "Michel", "allo@msn.com");
		Etudiant e = de.findByNumeroEtudiant("popop");
		Etudiant e1 = de.findByNumeroEtudiant("ba1234");
		//FenetreAjoutEleve fenetre6 = new FenetreAjoutEleve(df, e);
		//FenetreAjoutEleve fenetre = new FenetreAjoutEleve(df);
		//FenetreAjoutFiliere fenetre1 = new FenetreAjoutFiliere(df);
		//FenetreAjoutCours fenetre2 = new FenetreAjoutCours(df);
		//FenetreGestionEnseignants fenetre3 = new FenetreGestionEnseignants(df);
		//FenetreStatistiqueFiliere fenetre4 = new FenetreStatistiqueFiliere(df);
		//FenetreStatistiqueEtudiant fenetre5 = new FenetreStatistiqueEtudiant(df, e);
	}
}
