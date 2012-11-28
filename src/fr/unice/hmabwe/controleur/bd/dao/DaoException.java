package fr.unice.hmabwe.controleur.bd.dao;

/**
 * 
 * @author Paraita Wohler
 * 
 * DaoException est l'encapsulation des erreurs pouvant arriver
 * dans le code métier, il permet de faire abstraction en cachant le type
 * de persistance utilisé. Il est néamoins possible de <i>voir</i> le problème
 * interne car les DaoException sont la plupart du temps chaînées à l'Exception interne.
 * Autrement, il est possible de récupérer le code d'erreur de la DaoException en utilisant
 * la méthode getCode():
 * 
 * -1 s'il y a une erreur chaînée
 * 1 pour une connexion pas ouverte
 * 2 pour une connexion déjà ouverte
 * 3 si pas de transaction en cours
 * 4 si l'EntityManager est inexistant ou fermé
 * ...
 * 
 */
public class DaoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	  private int codeErreur;

	  public DaoException() {
	  }

	  /**
	   * Exception avec un message explicatif si erreur interne
	   * 
	   * @param message le message à rendre
	   * 
	   */
	  public DaoException(String message) {
	    this(message, 0);
	  }

	  /**
	   * Crée une nouvelle exception avec un message et une cause donnée.
	   * 
	   * @param message le message qui explique le problème.
	   * @param cause une exception qui est la cause du problème. Le type de cette
	   * exception doit être cachée à l'utilisateur du DAO et ne pas apparaître
	   * dans l'interface de la classe DaoException. Cette cause peut être
	   * connue de l'utilisateur par l'appel de la méthode getCause() héritée
	   * de Exception.
	   * 
	   * @param codeErreur code erreur du support de persistance.
	   * 
	   */
	  public DaoException(String message, Throwable cause, int codeErreur) {
	    super(message, cause);
	    this.codeErreur = codeErreur;
	  }
	  
	  /**
	   * Exception chaînée à une exception interne liée à la persistance
	   * accompagnée d'un message explicatif
	   * 
	   * @param message le message qui doit accompagner l'exception chaînée
	   * @param cause l'exception chaînée liée à la persistance
	   * 
	   */
	  public DaoException(String message, Throwable cause) {
	    this(message, cause, -1);
	  }

	  /**
	   * Exception chaînée à une exception interne liée à la persistance
	   * Le message <i>Erreur liée aux DAO</i> accompagne cette exception
	   * 
	   * @param cause l'exception chaînée liée à la persistance
	   * 
	   */
	  public DaoException(Throwable cause) {
	    this("Erreur liée aux DAO", cause, -1);
	  }
	  
	  /**
	   * Exception interne au DAO constitué d'un message explicatif
	   * et d'un code pour comprendre l'erreur
	   * 
	   * @param message le message explicatif lié à cette exception
	   * @param codeErreur le code permettant de savoir quel type d'erreur
	   * 	    on vient de rencontrer 
	   * 
	   */
	  public DaoException(String message, int codeErreur) {
	    this(message, null, codeErreur);
	  }
	  
	  /**
	   * pour récuperer le code d'erreur de l'exception
	   * 
	   * @return le numéro de l'erreur
	   */
	  public int getCode() {
	    return codeErreur;
	  }
}
