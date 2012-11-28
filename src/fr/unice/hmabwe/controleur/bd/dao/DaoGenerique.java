package fr.unice.hmabwe.controleur.bd.dao;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * @author Paraita Wohler
 * 
 * DaoGenerique est une interface parametrée qui permet de simplifier
 * la création de nouvelles interfaces de DAO tout en gardant l'ensemble
 * des classes implémentantes cohérent
 * DaoGenerique permet de s'assurer qu'un DAO peut être utilisé par le code <i>métier</i>
 *
 * @param <T> le type de l'objet métier que va gérer ce DAO
 * @param <ID> le type de l'identificateur de l'objet métier qui va permettre
 * 			   de le retrouver dans la base de données. Il faut <b>absolument</b>
 * 			   que le type de l'identificateur soit Serializable 
 */

public interface DaoGenerique<T, ID extends Serializable> {

	/**
	 * cette méthode permet de rendre persistant un objet dans
	 * la base de données
	 * 
	 * @param objet l'objet à rendre persistant dans la base de données
	 * @throws DaoException si l'élement existe déjà dans la base
	 *         de données ou une autre erreur liée à la persistance
	 */
	public void create(T objet) throws DaoException;
	
	/**
	 * cette méthode permet de mettre à jour un objet
	 * dans la base de données
	 * 
	 * @param objet l'objet à mettre à jour dans la base de données
	 * @throws DaoException si l'élement n'existe pas dans la base
	 * 		   de données ou une autre erreur liée à la persistance
	 */
	public void update(T objet) throws DaoException;
	
	/**
	 * cette méthode permet d'enlever un objet de la base de données.
	 * Il n'est dès lors plus persistant
	 * 
	 * @param objet l'objet qui ne doit plus être persistant
	 * @throws DaoException si l'élement n'existe pas dans la base
	 * 		   de données ou une autre erreur liée à la persistance
	 */
	public void delete(T objet) throws DaoException;
	
	/**
	 * cette méthode permet de récupérer un objet de la base
	 * de données, s'il existe
	 * 
	 * @param id l'identificateur de l'objet dans la base de données
	 * @return l'objet recherché
	 * @throws DaoException si l'identificateur n'existe pas dans la
	 * 		   base de données ou une erreur liée à la base de données
	 */
	public T findById(ID id) throws DaoException;
	
	/**
	 * cette méthode retourne l'ensemble de tout les objets dans la base
	 * de données que ce DAO gère
	 * 
	 * @return la Collection de tout les objets de type T 
	 * @throws DaoException s'il y a une erreur liée à la persistance
	 */
	public Collection<T> findAll() throws DaoException;
}
