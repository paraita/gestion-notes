package fr.unice.hmabwe.controleur.bd.dao.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import fr.unice.hmabwe.controleur.bd.dao.DaoCours;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Inscription;

/**
 * 
 * @author Paraita Wohler
 *
 * JpaDaoCours permet de faire abstraction de la couche persistance JPA
 * tout en rendant ce DAO suffisament générique.
 * Cette classe fourni les méthodes spécifiques au code métier de l'application
 *
 */
public class JpaDaoCours extends JpaDaoGenerique<Cours, Integer> implements
		DaoCours {
	
	private final String queryGetMoyenne = "select i from Inscription i " +
			"join i.cours c where i.annee = :annee and c.nom = :cours";
	
	private final String requetGetCour = "select c from Cours c where c.nom = :cours";
	
	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoCours#getMoyenne()
	 */
	public double getMoyenne(String NomCours, int annee){
		EntityManager em = getEntityManager();
		Query q = em.createQuery(queryGetMoyenne);
		q.setParameter("annee", annee);
		q.setParameter("cours", NomCours);
		List<Inscription> l_inscr = (List<Inscription>)q.getResultList();
		double somme_moyennes = 0;
		for (Inscription inscription : l_inscr) {
			somme_moyennes += inscription.getMoyenne();
		}
		return somme_moyennes / l_inscr.size();
	}
	
	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoCours#getEtudiantsInscrits()
	 */
	public Collection<Etudiant> getEtudiantsInstcrits(Cours cour){
		Collection<Inscription> colInscrit= cour.getInscriptions();
		ArrayList<Etudiant> listEtu = new ArrayList<Etudiant>();
		for (Inscription inscrit : colInscrit) {
			listEtu.add(inscrit.getEtudiant());
		}
		
		return ((Collection<Etudiant>)listEtu);
	}
	
	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoCours#getCoursByName()
	 */
	public List<Cours> getCoursByName(String nom) {
		EntityManager em = getEntityManager();
		Query q = em.createQuery(requetGetCour);
		q.setParameter("cours", nom);
		return ((List<Cours>)q.getResultList());
	}
}
