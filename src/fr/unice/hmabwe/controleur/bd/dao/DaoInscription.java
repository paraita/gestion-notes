package fr.unice.hmabwe.controleur.bd.dao;

import java.util.HashMap;

import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Inscription;

/**
 * 
 * @author Engilberge Swan
 */

public interface DaoInscription extends DaoGenerique<Inscription, Integer> {
	
	/**
	 * cette methode renvoie la liste des étudiants inscrit à un cour et une année donnée
	 * 
	 * @param nomCours le nom du cours où on veut la liste des inscrit
	 * @param annee l'année a laquelle on veut les inscrit
	 * 
	 */
	
	public HashMap<Etudiant, String> listeInscrit(String nomCours, int annee);

}
