/**
 * 
 */
package fr.unice.hmabwe.controleur.bd.dao.jpa;

import fr.unice.hmabwe.controleur.bd.dao.DaoCoefficient;
import fr.unice.hmabwe.modele.Coefficient;

/**
 * @author Paraita Wohler
 *
 * JpaDaoCoefficient permet de faire abstraction de la couche persistance JPA
 * tout en rendant ce DAO suffisament générique.
 * Cette classe fourni les méthodes spécifiques au code métier de l'application
 *
 */
public class JpaDaoCoefficient extends JpaDaoGenerique<Coefficient, Integer>
		implements DaoCoefficient {

	/**
	 * le code métier va ici
	 * 
	 */
}
