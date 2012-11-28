/**
 * 
 */
package fr.unice.hmabwe.controleur.bd.dao.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import fr.unice.hmabwe.controleur.bd.dao.DaoEtudiant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.modele.Coefficient;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Filiere;
import fr.unice.hmabwe.modele.Inscription;

/**
 * @author Paraita Wohler
 *
 * JpaDaoEtudiant permet de faire abstraction de la couche persistance JPA
 * tout en rendant ce DAO suffisament générique.
 * Cette classe fourni les méthodes spécifiques au code métier de l'application
 *
 */

public class JpaDaoEtudiant extends JpaDaoGenerique<Etudiant, Integer>
implements DaoEtudiant{

	private final String RequeteEtaitInscrit = "select e from Inscription i " +
	"join i.etudiant e " +
	"join i.cours c " +
	"where i.annee = :annee and e.numEtu = :numEtu and c.nom = :nomCours";
	
	private final String RequeteInscrit = "select i from Inscription i " +
	"join i.etudiant e " +
	"join i.cours c " +
	"where i.annee = :annee and e.numEtu = :numEtu and c.nom = :nomCours";


	private final String requetGetEtudiant = "select e from Etudiant e where e.nom = :nomEtudiant";
	private final String requetGetEtudiant2 = "select e from Etudiant e where e.nom = :nomEtudiant and e.prenom = :prenomEtudiant";

	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoEtudiant#etaitInscrit()
	 */
	public boolean etaitInscrit(String numEtu, String nomCours, int annee) {
		EntityManager em = getEntityManager();
		Query q = em.createQuery(RequeteEtaitInscrit);
		q.setParameter("numEtu", numEtu);
		q.setParameter("nomCours", nomCours);
		q.setParameter("annee", annee);
		List<Etudiant> res = (List<Etudiant>)q.getResultList();
		if (res.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}

	public Inscription inscriptionEtu(String numEtu, String nomCours, int annee) {
		EntityManager em = getEntityManager();
		Query q = em.createQuery(RequeteInscrit);
		q.setParameter("numEtu", numEtu);
		q.setParameter("nomCours", nomCours);
		q.setParameter("annee", annee);
		List<Inscription> res = (List<Inscription>)q.getResultList();
		if (res.isEmpty()) {
			return null;
		}
		else {
			return res.get(0);
		}
	}


	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoEtudiant#findByNumeroEtudiant()
	 */
	public Etudiant findByNumeroEtudiant(String numEtu) {
		EntityManager em = getEntityManager();
		Query q = em.createQuery("select e from Etudiant e where e.numEtu = :numEtu");
		q.setParameter("numEtu", numEtu);
		List<Etudiant> res = (List<Etudiant>)q.getResultList();
		if (res.isEmpty()) {
			return null;
		}
		else {
			return res.get(0);
		}
	}

	
	public List<Etudiant> getEtudiantByName(String nom){
		EntityManager em = getEntityManager();
		Query q = em.createQuery(requetGetEtudiant);
		q.setParameter("nomEtudiant", nom);
		return (List<Etudiant>)q.getResultList();
	}
	
	
	public List<Etudiant> getEtudiantByName(String nom, String prenom){
		EntityManager em = getEntityManager();
		Query q = em.createQuery(requetGetEtudiant2);
		q.setParameter("nomEtudiant", nom);
		q.setParameter("prenomEtudiant", prenom);
		return (List<Etudiant>)q.getResultList();
	}


	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoEtudiant#getMoyenne()
	 */
	
	public double getMoyenne(String numEtu) {
		HashMap<Integer, Integer> coeffs = new HashMap<Integer, Integer>();
		EntityManager em = getEntityManager();
		Etudiant e = findByNumeroEtudiant(numEtu);
		Filiere f = e.getFiliere();
		Query q = em.createQuery("select c from Coefficient c join c.filiere f where f.id = " + f.getId());
		Collection<Inscription> l_inscr = e.getInscriptions();
		double somme_notes = 0;
		int somme_coef = 0;
		List<Coefficient> l_coeffs = (List<Coefficient>)q.getResultList();
		for (Coefficient coefficient : l_coeffs) {
			coeffs.put(coefficient.getCours().getId(), coefficient.getCoefficient());
		}
		for (Inscription inscription : l_inscr) {
			somme_notes += inscription.getMoyenne() * coeffs.get(inscription.getCours().getId());
			somme_coef += coeffs.get(inscription.getCours().getId());
		}
		return somme_notes / somme_coef;
	}
	
	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoEtudiant#getMoyenne()
	 */

	public double getMoyenne(String numEtu, int annee) throws DaoException {
		HashMap<Integer, Integer> coeffs = new HashMap<Integer, Integer>();
		EntityManager em = getEntityManager();
		Etudiant e = findByNumeroEtudiant(numEtu);
		Filiere f = e.getFiliere();
		Query q = em.createQuery("select c from Coefficient c join c.filiere f where f.id = " + f.getId());
		Collection<Inscription> l_inscr_test = e.getInscriptions();
		Collection<Inscription> l_inscr = new ArrayList<Inscription>();
		for(Inscription inscr : l_inscr_test){
			if(inscr.getAnnee()==annee){
				l_inscr.add(inscr);
			}
			if(l_inscr.isEmpty())
				throw new DaoException("aucun etudiant avec le numéro " + numEtu + " en " + annee);
		}
		double somme_notes = 0;
		int somme_coef = 0;
		List<Coefficient> l_coeffs = (List<Coefficient>)q.getResultList();
		for (Coefficient coefficient : l_coeffs) {
			coeffs.put(coefficient.getCours().getId(), coefficient.getCoefficient());
		}
		for (Inscription inscription : l_inscr) {
			somme_notes += inscription.getMoyenne() * coeffs.get(inscription.getCours().getId());
			somme_coef += coeffs.get(inscription.getCours().getId());
		}
		return somme_notes / somme_coef;
	}


}
