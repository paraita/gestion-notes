package fr.unice.hmabwe.modele;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**@author Anthony Biga
 * 
 * La classe filiere représente une filière à laquelle sont associés des enseignants(dont un responsable)
 * et des étudiant. Elle est modélisée par la table hmabwe_filliere.
 * 
 * */

@Entity
@Table (
		name="hmabwe_filiere",
		uniqueConstraints=@UniqueConstraint(name="hmabwe_nomFiliere_UniqueConstraint", columnNames={"nom"}))
public class Filiere {
	
	/**Chaque filière a un identifiant unique, c'est la clé primaire(automatique) dans la table associée.*/
	@Id
	@GeneratedValue
	private int id;
	
	/**Une filière est désignée par son nom.*/
	private String nom;
	
	/**Une filière a un responsable.*/
	@ManyToOne
	private Enseignant responsable;
	
	/**Une filière peut contenir plusieurs étudiants.*/
	@OneToMany(mappedBy="filiere")
	private Collection<Etudiant> listeEtudiants= new ArrayList<Etudiant>();
	
	/**Une filière peut avoir plusieurs cours ayant chacun un coefficient.*/
	@OneToMany(mappedBy="filiere", cascade=CascadeType.ALL)
	private Collection<Coefficient> listeCoeffCours= new ArrayList<Coefficient>();
	
	/**Constructeur par défaut*/
	public Filiere(){
		listeEtudiants = new ArrayList<Etudiant>();
	}
	
	/**Constructeur associant un nom à une filière
	 * @param n nom de la filière à créer*/
	public Filiere(String n){
		nom=n;
	}
	
	/**Constructeur associant un nom et un responsable à une filière
	 * @param n nom de la filière à créer
	 * @param e responsable de la filière à créer*/
	public Filiere(String n, Enseignant e){
		nom=n;
		responsable=e;
		//responsable.addfiliere(this);
	}
	
	/**Retourne l'id de la filière.
	 * @return identifiant de la filière*/
	public int getId(){
		return id;
	}

	//Accesseurs pour l'attribut nom

	/**Retourne le nom de la filière.
	 * @return nom de la filière*/
	public String getNom(){
		return nom;
	}

	/**Modifie le nom de la filière si celui passé en paramètre est différent de null(retourne vrai si
	 * la modification s'est bien passée, faux sinon).
	 * @param n nouveau nom à attribuer à la filière
	 * @return modification effectuée ou non*/
	public boolean setNom(String n){
		boolean res = false;
		
		if(n != null && !n.equalsIgnoreCase("")){
			nom=n;
			res=true;
		}
		
		return res;
	}
	
	//Accesseurs pour l'attribut listeEtudiants
	
	/**Associe un nouvel étudiant à une filière si celui passé en paramètre est différent de null
	 * (retourne vrai si la modification s'est bien passée, faux sinon).
	 * @param e nouvel étudiant à associer à la filli�re
	 * @return modification effectuée ou non*/
	public boolean addEtudiant(Etudiant e){
		boolean res = false;
		
		if(e != null){
			listeEtudiants.add(e);
			res=true;
		}
		
		return res;
	}
	
	/**Retire l'étudiant passé en paramétre de la liste des étudiant associés à une filière
	 * (retourne vrai si la modification s'est bien passée, faux sinon).
	 * @param e étudiant à retirer de la filière
	 * @return modification effectuée ou non*/
	public boolean removeEtudiant(Etudiant e){
		boolean res = false;
		if(e!=null){
			res=listeEtudiants.remove(e);
		}
		return res;
	}
	
	/**Vide la liste des étudiants associés à une filière.*/
	public void removeAllEtudiants(){
		listeEtudiants.clear();
	}
	
//Accesseurs pour l'attribut listeCours
	
	/**Associe un nouveau cours(avec un coefficient) à une filière si celui passé en paramètre est 
	 * différent de null(retourne vrai si la modification s'est bien passée, faux sinon).
	 * @param c nouveau cours à associer à la filière
	 * @param coeff coefficient du cours à associer à la filière
	 * @return modification effectuée ou non*/
	public boolean addCours(Cours c, Integer coeff){
		boolean res = false;
		
		if(c != null){
			listeCoeffCours.add(new Coefficient(c, this, coeff));
			res=true;
		}
		
		return res;
	}
	
	/**Retire le cours passé en paramètre de la liste des cours associés à une filière
	 * (retourne vrai si la modification s'est bien passée, faux sinon).
	 * @param c cours à retirer de la filière
	 * @return modification effectuée ou non*/
	public boolean removeCours(Cours c){
		boolean res=false;
		
		if(c!=null){
			for(Coefficient coeff: listeCoeffCours){
				if(coeff.getCours()==c){
					listeCoeffCours.remove(coeff);
					break;
				}
			}
		}
		return res;
	}
	
	/**Vide la liste des cours associés à une filière.*/
	public void removeAllCours(){
		listeCoeffCours.clear();
	}
	
	//Accesseurs pour l'attribut responsable

	/**Retourne le responsable de la filière.
	 * @return responsable de la filière*/
	public Enseignant getResponsable(){
		return responsable;
	}

	/**Modifie le responsable de la filière si celui passé en paramètre est différent de null(retourne vrai si
	 * la modification s'est bien passée, faux sinon).
	 * @param num nouveau numéro à attribuer à l'étudiant
	 * @return modification effectuée ou non*/
	public boolean setResponsable(Enseignant e){
		boolean res = false;
		responsable=e;
		if(e!= null){
			e.removeFiliere(this);
			e.addFiliere(this);
			res=true;
		}
		
		return res;
	}
	
	/**
	 * Retourne la liste des étudiants de la filière
	 * @return la liste des étudiants de la filière
	 */
	public Collection<Etudiant> getListEtudiants(){
		return listeEtudiants;
	}
	
	/**Description d'une filiere
	 * @return description de la filiere*/
	public String toString(){
		return "Filière : " + nom;
	}

}

