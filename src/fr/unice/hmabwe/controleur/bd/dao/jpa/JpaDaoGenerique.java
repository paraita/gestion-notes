/**
 * 
 */
package fr.unice.hmabwe.controleur.bd.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoGenerique;

/**
 * @author Paraita Wohler
 *
 * permet de factoriser le code nécessaire à un DAO dans le cadre d'une
 * persistance en utilisant JPA
 *
 */
public class JpaDaoGenerique<T, ID extends Serializable> implements DaoGenerique<T, ID> {

	private Class<T> classeEntite;
	private EntityManager em;
	private final String query_findAll_Persons = "select p from Personne as p where type(p) = :entite";
	
	public JpaDaoGenerique() {
		classeEntite = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoGenerique#create()
	 */
	public void create(T objet) throws DaoException {
		try {
			if(em != null && em.isOpen()) {
				em.persist(objet);
			}
			else
				throw new DaoException("probleme interne au DAO", 5);
		}
		catch(EntityExistsException eee) {
			// chainer l'exception avec une nouvelle DaoException
			throw new DaoException("l'entité existe déjà dans la base de données",
					eee);
		}
		/* objet n'est pas une entité */
		catch(IllegalArgumentException iae) {
			throw new DaoException("n'est pas une entité ou est une entité détachée", iae);
		}
		catch(TransactionRequiredException tre) {
			throw new DaoException("transaction nécessaire", tre, 4);
		}
	}

	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoGenerique#update()
	 */
	public void update(T objet) throws DaoException {
		try {
			if(em != null && em.isOpen()) {
				em.merge(objet);
			}
			else
				throw new DaoException("probleme interne au DAO", 5);
		}
		catch(IllegalArgumentException iae) {
			throw new DaoException("n'est pas une entité ou est une entité détachée", iae);
		}
		catch(TransactionRequiredException tre) {
			throw new DaoException("transaction nécessaire", tre, 4);
		}
	}
	
	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoGenerique#delete()
	 */
	public void delete(T objet) throws DaoException {
		try {
			if(em != null && em.isOpen()) {
				em.remove(objet);
			}
			else
				throw new DaoException("probleme interne au DAO", 5);
		}
		catch(IllegalArgumentException iae) {
			throw new DaoException("n'est pas une entité ou est une entité détachée", iae);
		}
		catch(TransactionRequiredException tre) {
			throw new DaoException("transaction nécessaire", tre, 4);
		}
	}

	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoGenerique#findById()
	 */
	public T findById(ID id) throws DaoException {
		try {
			if(em != null && em.isOpen())
				return em.find(classeEntite, id);
			else
				throw new DaoException("probleme interne au DAO", 5);
		}
		catch(IllegalArgumentException iae) {
			throw new DaoException("n'est pas une entité ou est une entité détachée", iae);
		}
	}

	/**
	 * @see fr.unice.hmabwe.controleur.dao.DaoGenerique#findAll()
	 */
	public Collection<T> findAll() throws DaoException {
		String requete = null;
		try {
			if(classeEntite.getSuperclass().getSimpleName().compareTo("Personne") == 0) {
				if(em != null && em.isOpen()) {
					Query q = null;
					requete = query_findAll_Persons;
					q = em.createQuery(requete);
					q.setParameter("entite", Class.forName(classeEntite.getName()));
					return (List<T>) q.getResultList();
				}
				else {
					throw new DaoException("erreur interne  au DAO", 5);
				}
			}
			else {
				if(em != null && em.isOpen()) {
					Query q = null;
					//todo vraiment moche mais peut pas faire autrement :/
					requete = "select e from " + classeEntite.getSimpleName() + " e";
					q = em.createQuery(requete);
					return (List<T>) q.getResultList();
				}
				else {
					throw new DaoException("erreur interne  au DAO", 5);
				}
			}
		}
		catch(IllegalArgumentException iae) {
			throw new DaoException("requete invalide (" +
									requete +
									") ou nom de classe invalide (" +
									classeEntite +
									")", iae);
		}
		catch (ClassNotFoundException e) {
			throw new DaoException("classe non trouvée !", e);
		}
		
	}
	
	/**
	 * cette méthode permet d'attribuer un EntityManager à ce DAO
	 * 
	 * @param em l'EntityManager à attribuer à ce DAO
	 * 
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	/**
	 * cette méthode permet de récupérer l'EntityManager qui a été
	 * attribué à ce DAO
	 * 
	 * @return EntityManager l'EntityManager attribué à ce DAO
	 * 
	 */
	protected EntityManager getEntityManager() {
		if(em != null && em.isOpen()) {
			return em;
		}
		else {
			return null;
		}
	}
}
