package fr.unice.hmabwe.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**@author Anthony Biga
 * 
 * La classe Inscription représente l'inscription d'un étudiant à un cours.
 * Elle est modélisée par la table hmabwe_inscription.
 * 
 * */

@Entity
@Table (name="hmabwe_inscription")
public class Inscription {
	
	/**Chaque inscription a un identifiant unique généré automatiquement.*/
	@Id
	@GeneratedValue
	private int id;
	
	/**Une inscription concerne un étudiant.*/
	@ManyToOne
	private Etudiant etudiant;
	
	/**Une inscription concerne un cours.*/
	@ManyToOne
	private Cours cours;
	
	/**Une inscription a lieu une année donnée.*/
	private Integer annee;
	
	/**Dans le cadre de l'application EnvoieDe Notes, un étudiant n'a qu'une seule note par cours, 
	 * sa moyenne*/
	private Double moyenne;
	
	/**Constructeur par défaut*/
	public Inscription(){
		
	}
	
	/**Constructeur associant un étudiant, un cours et une année à une inscription
	 * @param e étudiant de l'inscription à créer
	 * @param c cours de l'inscription à créer
	 * @param a année de l'inscription à créer*/
	public Inscription(Etudiant e, Cours c, Integer a){
		etudiant=e;
		setCours(c);
		//cours.addInscription(this);
		annee=a;
	}
	
	/**Constructeur associant un étudiant, un cours, une année et une moyenne à une inscription
	 * @param e étudiant de l'inscription à créer
	 * @param c cours de l'inscription à créer
	 * @param a année de l'inscription à créer
	 * @param m moyenne associée au cours de l'inscription à créer*/
	public Inscription(Etudiant e, Cours c, Integer a, Double m){
		this(e, c, a);
		moyenne=m;
	}
	
	/**Retourne l'id de l'inscription.
	 * @return identifiant associé à l'inscription*/
	public int getId(){
		return id;
	}
	
	/**Retourne l'étudiant associé à l'inscription.
	 * @return étudiant associé à l'inscription*/
	public Etudiant getEtudiant(){
		return etudiant;
	}
	
	/**Retourne l'année associé à l'inscription.
	 * @return année associé à l'inscription*/
	public Integer getAnnee(){
		return annee;
	}
	
	
	//Accesseurs pour l'attribut moyenne

	/**Retourne la moyenne de l'étudiant associé à l'inscription.
	 * @return moyenne de l'étudiant associé à l'inscription*/
	public Double getMoyenne(){
		return moyenne;
	}	

	/**Modifie la moyenne de l'étudiant associé à l'inscription.
	 * @param n nouvelle moyenne à attribuer à l'étudiant
	 */
	public void setMoyenne(Double m){
		moyenne=m;
	}
	
	/**Retourne le cours associé à l'inscription.
	 * @return cours associé à l'inscription*/
	public Cours getCours() {
		return cours;
	}

	/**Modifie le cours associé à l'inscription.
	 * @param cours nouveau cours associé à l'inscription
	 */
	public void setCours(Cours cours) {
		this.cours = cours;
	}

	/**Description d'une inscription
	 * @return description de l'inscription*/
	public String toString(){
		return "Inscription numero : " + this.id + "cour: " + this.cours + " année: " + this.annee + " etudiant " + this.etudiant.getNom() + " moyenne " + this.moyenne;
	}
}
