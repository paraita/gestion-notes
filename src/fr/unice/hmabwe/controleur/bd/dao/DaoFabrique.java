/**
 * 
 */
package fr.unice.hmabwe.controleur.bd.dao;


import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion.TypePersistance;
import fr.unice.hmabwe.controleur.bd.dao.jpa.JpaDaoFabrique;

/**
 * @author Paraita Wohler
 * 
 * DaoFabrique s'utilise dans un premier temps en lui indiquant quel type de persistance
 * on compte utiliser grâce à la méthode <i>setTypeDao()</i>, puis on demande à la fabrique
 * une DaoFabrique qui serra spécialisé dans le type de persistance qu'on utilisera
 * On poura ainsi demander à la fabrique spécialisée les DAO qui nous intéressent suivant
 * le type d'entité qu'on va manipuler.
 *
 */
public abstract class DaoFabrique {
	
	/**
	 * cette méthode permet de récupérer un DAO sachant gérer les
	 * entités Enseignant. Il est nécessaire d'avoir indiqué auparavant
	 * quel type de persistance on allait utiliser
	 * 
	 * @return DaoGenerique<Enseignant, Integer> un DAO qui sait gérer les entités Enseignant
	 * 
	 */
	public abstract DaoEnseignant getDaoEnseignant();
	
	/**
	 * cette méthode permet de récuperer un DAO sachant gérer les
	 * entités Etudiant. Il est nécessaire d'avoir indiqué auparavant
	 * quel type de persistance on allait utiliser
	 * 
	 * @return DaoGenerique<Etudiant, Integer> un DAO qui sait gérer les entités Etudiant
	 * 
	 */
	public abstract DaoEtudiant getDaoEtudiant();
	
	/**
	 * cette méthode permet de récuperer un DAO sachant gérer les
	 * entités Cours. Il est nécessaire d'avoir indiqué auparavant
	 * quel type de persistance on allait utiliser
	 * 
	 * @return DaoGenerique<Cours, Integer> un DAO qui sait gérer les entités Cours
	 * 
	 */
	public abstract DaoCours getDaoCours();
	
	/**
	 * cette méthode permet de récuperer un DAO sachant gérer les
	 * entités Inscription. Il est nécessaire d'avoir indiqué auparavant
	 * quel type de persistance on allait utiliser
	 * 
	 * @return DaoGenerique<Inscription, Integer> un DAO qui sait gérer les entités Inscription
	 * 
	 */
	public abstract DaoInscription getDaoInscription();
	
	/**
	 * cette méthode permet de récuperer un DAO sachant gérer les
	 * entités Filiere. Il est nécessaire d'avoir indiqué auparavant
	 * quel type de persistance on allait utiliser
	 * 
	 * @return DaoGenerique<Filiere, Integer> un DAO qui sait gérer les entités Filliere
	 * 
	 */
	public abstract DaoFiliere getDaoFiliere();
	
	/**
	 * cette méthode permet de récuperer un DAO sachant gérer les
	 * entités Coefficient. Il est nécessaire d'avoir indiqué auparavant
	 * quel type de persistance on allait utiliser
	 * 
	 * @return DaoGenerique<Coefficient, Integer> un DAO qui sait gérer les entités Coefficient
	 * 
	 */
	public abstract DaoCoefficient getDaoCoefficient();
	
	/**
	 * cette méthode permet de récupérer la connexion qu'on va utiliser avec les DAO que cette
	 * fabrique permet de créer. On pourra utiliser Connexion pour ouvrir/fermer la connexion à
	 * la base de données. On pourra aussi lancer des transactions et faire des commit/rollback
	 * vers la base de données
	 * 
	 * @return Connexion la Connexion qu'on va utiliser dans le code métier avec les DAO
	 * 
	 */
	public abstract Connexion getConnexion();
	
	/**
	 * DEPRECATED on passe par ConfigConnexion maintenant pour set le type de persistance
	 * cette méthode permet de renseigner la fabrique quel type de persistance
	 * on compte utiliser. <b>Il est nécessaire d'indiquer à la fabrique quel est le type de persistance utilisé !</b>
	 * 
	 * @param tf le type de fabrique qu'on va utiliser
	 * 
	 */
	public static void setTypeDao(TypePersistance tp) {
		//typeFabrique = tp;
	}
	
	/**
	 * cette méthode permet de récupérer la DaoFabrique spécialisé dans le type de persistance
	 * qu'on va utiliser. Il est nécessaire d'indiquer à la fabrique quel est le type de persistance
	 * utilisé avant d'apeller cette méthode sinon la méthode retourne <code>null</code>
	 * 
	 * @return DaoFabrique la fabrique spécialisé dans le type de persistance indiqué 
	 * 
	 */
	public static DaoFabrique getDaoFabrique() {
		switch(ConfigConnexion.getTypePersistance()) {
			case JDBC:
				//return new JdbcDaoFabrique();
			case JPA:
				return new JpaDaoFabrique();
			default:
				return null; // oups...
		}
	}
	
}
