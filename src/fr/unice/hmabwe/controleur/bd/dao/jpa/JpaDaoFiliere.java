package fr.unice.hmabwe.controleur.bd.dao.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;

import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.modele.Coefficient;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Filiere;
import fr.unice.hmabwe.modele.Inscription;

/**
 * 
 * @author Paraita Wohler
 *
 * JpaDaoFiliere permet de faire abstraction de la couche persistance JPA
 * tout en rendant ce DAO suffisament générique.
 * Cette classe fourni les méthodes spécifiques au code métier de l'application
 *
 */
public class JpaDaoFiliere extends JpaDaoGenerique<Filiere, Integer>
		implements DaoFiliere {
	
	


	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoFiliere#getEtudiantsInscrit()
	 */
	public Collection<Etudiant> getEtudiantsInscrits(Filiere filiere){
		return filiere.getListEtudiants();
	}
	
	
	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoFiliere#getMoyenne()
	 */
	public double getMoyenne(Filiere filiere, int annee) {
		Collection<Etudiant> listeEtudiant = getEtudiantsInscrits(filiere);
		double somme = 0;
		for (Etudiant etudiant : listeEtudiant) {
			double moyenne_totale_etu = 0;
			HashMap<Integer, Integer> coeffs = new HashMap<Integer, Integer>();
			EntityManager em = getEntityManager();
			Query q = em.createQuery("select c from Coefficient c where c.filiere = :id");
			q.setParameter("id", filiere);
			Collection<Inscription> l_inscr_test = etudiant.getInscriptions();
			Collection<Inscription> l_inscr = new ArrayList<Inscription>();
			for(Inscription inscr : l_inscr_test){
				if(inscr.getAnnee()==annee){
					l_inscr.add(inscr);
				}
			}
			double somme_notes = 0;
			double somme_coef = 1;
			List<Coefficient> l_coeffs = (List<Coefficient>)q.getResultList();
			for (Coefficient coefficient : l_coeffs) {
				coeffs.put(coefficient.getCours().getId(), coefficient.getCoefficient());
			}
			for (Inscription inscription : l_inscr) {
				somme_notes += inscription.getMoyenne() * coeffs.get(inscription.getCours().getId());
				somme_coef += coeffs.get(inscription.getCours().getId());
			}
			moyenne_totale_etu = (somme_notes / somme_coef);
			somme += moyenne_totale_etu;
		}
		return somme / listeEtudiant.size();
		
	}


	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoFiliere#getMoyenneParGroupe()
	 */
	public HashMap<String, Double> getMoyenneParGroupe(Filiere filiere) {
		
		double somme_notes = 0;
		int nb_etu_dans_groupe = 0;
		JpaDaoEtudiant daoEtudiant = new JpaDaoEtudiant();
		daoEtudiant.setEntityManager(getEntityManager());
		Collection<Etudiant> etudiants = filiere.getListEtudiants();
		HashMap<String, Double> resultat = new HashMap<String, Double>();
		EntityManager em = getEntityManager();
		/* je récupère tout les groupes en distinct */
		Query q = em.createQuery("select distinct e.groupe" +
				" from Etudiant e join e.filiere f" +
				" where f.id = " + filiere.getId() + " group by e.groupe");
		List<Object> nom_groupes = (List<Object>)q.getResultList();
		/* calcul de la moyenne de chaque groupe */
		for (Object groupe : nom_groupes) {
			for (Etudiant etudiant : etudiants) {
				if (etudiant.getGroupe().compareTo((String)groupe) == 0) {
					somme_notes += daoEtudiant.getMoyenne(etudiant.getNumEtu());
					nb_etu_dans_groupe++;
				}
			}
			/* je rempli la hashmap en fonction */
			resultat.put((String)groupe, somme_notes / nb_etu_dans_groupe);
			somme_notes = 0;
			nb_etu_dans_groupe = 0;
		}
		return resultat;
	}
	

}
