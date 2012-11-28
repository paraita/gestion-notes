package fr.unice.hmabwe.controleur.bd.config;


public abstract class ConfigConnexion {
	
	public enum TypePersistance { JDBC, JPA };
	private static TypePersistance typePersistance = null;
	
	
	/**
	 * Permet de configurer le type de persistance qu'on va utiliser
	 * 
	 */
	public static void setTypePersistance(TypePersistance tp) {
		typePersistance = tp;
	}
	
	/**
	 * Récupère le type de persistance utilisé par l'application
	 *  
	 * @return typePersistance le type de persistance utilisé
	 * 
	 */
	public static TypePersistance getTypePersistance() {
		return typePersistance;
	}
	
	public static ConfigConnexion newConfiguration() throws ConnexionException {
		if(typePersistance == null) {
			throw new ConnexionException("type de persistance non indiqué !");
		}
		else {
			switch(typePersistance) {
				case JDBC:
					//return new JdbcConfigConnexion();
				case JPA:
					return new JpaConfigConnexion();
				default:
					return null;
			}
		}
	}
	
	/**
	 * Permet de configurer la connexion qu'on va utiliser.
	 * 
	 * @param serveur l'adresse du serveur sur lequel on travaille
	 * @param port port du serveur
	 * @param username nom d'utilisateur
	 * @param mdp le mot de passe de l'utilisateur
	 * @param SID l'identificateur de la base de données
	 * @throws ConnexionException si une erreur arrive
	 */
	public abstract void setProprietes(String serveur, String port, String username, String mdp, String SID);
	
	/**
	 * Sauvegarde la nouvelle configuration
	 */
	public abstract void sauvegarder() throws ConnexionException;
}
