package fr.unice.hmabwe.controleur.bd.dao;

import java.util.Collection;
import java.util.HashMap;

import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Filiere;

/**
 * 
 * @author Engilberge Swan
 */

public interface DaoFiliere extends DaoGenerique<Filiere, Integer> {
	
	/**
	 * cette methode renvoie tout les etudiants inscrient à cette fillière
	 * 
	 */
	public Collection<Etudiant> getEtudiantsInscrits(Filiere filiere);
	
	/**
	 * cette methode renvoie la moyenne d'une filière donnée une année donnée
	 * 
	 * @param nomFiliere le nom de la filière dont on veut la moyenne
	 * @param annee année en question
	 * @return la moyenne de la filière donnée
	 */
	public double getMoyenne(Filiere filiere, int annee);
	
	/**
	 * cette méthode retourne une hashmap faisant la relation entre un groupe
	 * et sa moyenne.
	 * 
	 * @param filiere la filière dont on cherche la moyenne par groupe
	 * @return une hashmap contenant la moyenne de chaque groupe mappée par son indentificateur
	 */
	public HashMap<String, Double> getMoyenneParGroupe(Filiere filiere);
	

}
