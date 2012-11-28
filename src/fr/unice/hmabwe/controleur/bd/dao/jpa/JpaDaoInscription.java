package fr.unice.hmabwe.controleur.bd.dao.jpa;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import fr.unice.hmabwe.controleur.bd.dao.DaoInscription;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Inscription;

/**
 * 
 * @author Paraita Wohler
 *
 * JpaDaoInscription permet de faire abstraction de la couche persistance JPA
 * tout en rendant ce DAO suffisament générique.
 * Cette classe fourni les méthodes spécifiques au code métier de l'application
 *
 */
public class JpaDaoInscription extends JpaDaoGenerique<Inscription, Integer>
		implements DaoInscription {
	private final String RequeteListeInscrit = "select e, i.moyenne from Inscription i " +
	"join i.etudiant e " +
	"join i.cours c " +
	"where i.annee = :annee and c.nom = :nomCours";


	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoEtudiant#listeInscrit()
	 */
	public HashMap<Etudiant, String> listeInscrit(String nomCours, int annee){
		HashMap<Etudiant, String> map = new HashMap<Etudiant, String>();
		EntityManager em = getEntityManager();
		Query q = em.createQuery(RequeteListeInscrit);
		q.setParameter("nomCours", nomCours);
		q.setParameter("annee", annee);
		List<Object[]> res = (List<Object[]>)q.getResultList();
		for (Object[] o : res) {
			map.put(((Etudiant)o[0]), ((Double)o[1]).toString());
		}
		return map;
	}
}
