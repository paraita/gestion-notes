/**
 * 
 */
package fr.unice.hmabwe.controleur.bd.dao;

import java.util.List;

import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Enseignant;

/**
 * @author Engilbegre Swan
 *
 */
public interface DaoEnseignant extends DaoGenerique<Enseignant, Integer> {
	
	/**
	 * renvoie une liste d'enseignants dont on à donné le nom
	 * @param nom le nom du cours qu'on cherche
	 * 
	 */
	public List<Enseignant> getEnseignantsByName(String nom);
	public List<Enseignant> getEnseignantsByName(String nom, String prenom);

}
