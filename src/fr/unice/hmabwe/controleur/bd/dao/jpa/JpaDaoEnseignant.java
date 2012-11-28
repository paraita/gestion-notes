/**
 * 
 */
package fr.unice.hmabwe.controleur.bd.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import fr.unice.hmabwe.controleur.bd.dao.DaoEnseignant;
import fr.unice.hmabwe.modele.Enseignant;

/**
 * @author Paraita Wohler
 * 
 * JpaDaoEnseignant permet de faire abstraction de la couche persistance JPA
 * tout en rendant ce DAO suffisament générique.
 * Cette classe fourni les méthodes spécifiques au code métier de l'application
 *
 */
public class JpaDaoEnseignant extends JpaDaoGenerique<Enseignant, Integer>
implements DaoEnseignant {

	private final String requetGetEnseignant = "select e from Enseignant e " + "where e.nom = :nomEnseigant";

	private final String requetGetEnseignant2 = "select e from Enseignant e " + "where e.nom = :nomEnseigant and e.prenom = :prenomEnseignant";
	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoEnseignant#getEnseignantByName()
	 */

	public List<Enseignant> getEnseignantsByName(String nom){
		EntityManager em = getEntityManager();
		Query q = em.createQuery(requetGetEnseignant);
		q.setParameter("nomEnseigant", nom);
		return (List<Enseignant>)q.getResultList();
	}
	public List<Enseignant> getEnseignantsByName(String nom, String prenom){
		EntityManager em = getEntityManager();
		Query q = em.createQuery(requetGetEnseignant2);
		q.setParameter("nomEnseigant", nom);
		q.setParameter("prenomEnseigant", prenom);
		return (List<Enseignant>)q.getResultList();
	}
	
}
