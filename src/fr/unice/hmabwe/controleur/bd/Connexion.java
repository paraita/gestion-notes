/**
 * 
 */
package fr.unice.hmabwe.controleur.bd;

import fr.unice.hmabwe.controleur.bd.dao.DaoException;

/**
 * @author Paraita Wohler
 * 
 * Une Connexion permet de faire abstraction du type de persistance
 * qui sera utilisé par les DAO. On peut ouvrir/fermer une connexion/transaction
 * et on peut aussi savoir si une connexion/transaction est ouverte.
 * Enfin on peut faire un commit ou un rollback à la base de données
 *
 */
public interface Connexion {
	
	/**
	 * méthode pour ouvrir une connexion avec la base de données.
	 * Les informations de connexion sont lues dans le fichier associé
	 * au type de persistance utilisé
	 * 
	 * @throws DaoException une DaoException est jetée si une erreur de
	 * 		   configuration de connexion est arrivée ou s'il y a une erreur
	 * 		   de connexion à la base de données
	 * 
	 */
	public void ouvrir() throws DaoException;
	
	/**
	 * méthode pour fermer une connexion précédement ouverte
	 * 
	 * @throws DaoException une DaoException est jetée si une erreur liée à
	 * 		   la fermeture de la connexion est arrivée
	 * 
	 */
	public void fermer() throws DaoException;
	
	/**
	 * permet de savoir si la connexion est ouverte ou fermée
	 * 
	 * @return <code>vrai</code> si la connexion est ouverte, <code>faux</code>
	 * 		   sinon
	 * 
	 */
	public boolean estOuverte();
	
	/**
	 * permet de savoir si une transaction par cette connexion est déjà
	 * en cours
	 * 
	 * @return <code>vrai</code> si une transaction est déjà en cours,
	 * 		   <code>faux</code> sinon
	 * 
	 */
	public boolean transactionEnCours();
	
	/**
	 * méthode pour débuter une transaction
	 * 
	 * @throws DaoException si une erreur liée à la connexion à la base de
	 * 		   données est arrivée
	 * 
	 */
	public void beginTransaction() throws DaoException;
	
	/**
	 * méthode qui permet de valider les changements à faire dans la
	 * base de données
	 * 
	 * @throws DaoException si une erreur liée à la connexion à la base de
	 * 		   données est arrivée
	 * 
	 */
	public void commitTransaction() throws DaoException;
	
	/**
	 * méthode qui permet de revenir en arrière et d'invalider les changements
	 * fait dans la base de données
	 * 
	 * @throws DaoException si une erreur liée au rollback est arrivée
	 */
	public void rollbackTransaction() throws DaoException;

}
