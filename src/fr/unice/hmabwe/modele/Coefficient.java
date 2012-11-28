package fr.unice.hmabwe.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**@author Anthony Biga
 * 
 * La classe Coefficient représente l'association d'un cours à une filière avec un certain coefficient.
 * Elle est modélisée par la table hmabwe_coefficient.
 * 
 * */

@Entity
@Table (name="hmabwe_coefficient")
public class Coefficient {
	
	/**Chaque instance de la classe Coefficient a un identifiant unique, c'est la clé primaire(automatique)
	 * dans la table associée.*/
	@Id
	@GeneratedValue
	private int id;
	
	/**Un coefficient est associé à un cours*/
	@ManyToOne
	private Cours cours;
	
	public void setCours(Cours cours) {
		this.cours = cours;
	}

	/**Un coefficient est associé à une filière*/
	@ManyToOne
	private Filiere filiere;
	
	public void setFiliere(Filiere filiere) {
		this.filiere = filiere;
	}

	/**Coefficient pour un cours dans une filière*/
	private Integer coefficient;
	
	public void setCoefficient(Integer coefficient) {
		this.coefficient = coefficient;
	}

	/**Constructeur par défaut*/
	public Coefficient(){
	}
	
	/**Constructeur associant au coefficient un cours et une fillère*/
	public Coefficient(Cours c, Filiere f, Integer coeff){
		cours=c;
		filiere=f;
		coefficient=coeff;
		//penser à ajouter l'instance crée dans les listes associées de Filliere et Cours juste après
		//l'appel à ce constructeur !
	}
	
	/**Retourne le cours associé à une instance de la classe Coefficient
	 * @return id de l'instance de Coefficient*/
	public int getId() {
		return id;
	}

	/**Retourne le cours associé à une instance de la classe Coefficient
	 * @return cours de l'instance de Coefficient*/
	public Cours getCours() {
		return cours;
	}

	/**Retourne la filière associée à une instance de la classe Coefficient
	 * @return filière de l'instance de Coefficient*/
	public Filiere getFiliere() {
		return filiere;
	}
	
	/**Retourne le cours associé à une instance de la classe Coefficient
	 * @return coefficient de l'instance de Coefficient*/
	public Integer getCoefficient() {
		return coefficient;
	}
	
	/**Description d'un coefficient
	 * @return description du coefficient*/
	public String toString(){
		String toString;
		toString ="Id : ";
		toString+=id+"\n";
		toString+="Cours : ";
		toString+=cours.getNom()+"\n";
		toString+="Filiere : ";
		toString+=filiere.getNom()+"\n";
		toString+="Coefficient : ";
		toString+=coefficient+"\n";
		
		return toString;
	}

}
