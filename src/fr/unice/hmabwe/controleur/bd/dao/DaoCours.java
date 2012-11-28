/**
 * 
 */
package fr.unice.hmabwe.controleur.bd.dao;

import java.util.Collection;
import java.util.List;

import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Etudiant;

/**
 * @author Engilberge swan
 *
 * DaoEtudiant est une interface non parametrée qui sera implementé 
 * par les daoetudiant 
 *
 */


public interface DaoCours extends DaoGenerique<Cours, Integer> {
	
	/**
	 * renvoie la moyenne de tout les etudiants
	 * qui ont suivit un cours donnée à une année donnée
	 * 
	 * @param nomCours le nom du cours dont on veut la moyenne
	 * @param annee l'année a laquelle on cherche la moyenne
	 * @return la moyenne du cours l'année donnée
	 */
	
	public double getMoyenne(String nomCours, int annee); 
	
	/**
	 * renvoie tout les etudiant inscrit à ce cours
	 * toutes années confondue
	 */
	
	public Collection<Etudiant> getEtudiantsInstcrits(Cours cour);
	
	/**
	 * renvoie un cours dont on à donné le nom
	 * @param nom le nom du cours qu'on cherche
	 * 
	 */
	public List<Cours> getCoursByName(String nom);

}
