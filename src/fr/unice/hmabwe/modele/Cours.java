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
 * La classe Cours représente un cours auquel sont associés un enseignant et des étudiant.
 * Elle est modélisée par la table hmabwe_cours.
 * 
 * */

@Entity
@Table (
		name="hmabwe_cours",
		uniqueConstraints=@UniqueConstraint(name="hmabwe_nomCours_UniqueConstraint", columnNames={"nom"}))
public class Cours {
	/**Chaque cours a un identifiant unique, c'est la clé primaire(automatique) dans la table associée.*/
	@Id
	@GeneratedValue
	private int id;
	
	/**Un cours est désigné par son nom.*/
	private String nom;
	
	/**Un cours est présené par un enseignant.*/
	@ManyToOne
	private Enseignant enseignant;
	
	/**Un cours peut avoir une ou plusieurs inscriptions*/
	@OneToMany(mappedBy="cours", cascade=CascadeType.ALL)
	private Collection<Inscription> listeInscriptions = new ArrayList<Inscription>();
	
	/**Un cours peut avoir plusieurs filières ayant chacune un coefficient.*/
	@OneToMany(mappedBy="cours", cascade=CascadeType.ALL)
	private Collection<Coefficient> listeCoeffFilieres= new ArrayList<Coefficient>();
	
	/**Constructeur par défaut*/
	public Cours(){
		
	}
	
	/**Constructeur associant un nom à un cours
	 * @param n nom du cours à créer*/
	public Cours(String n){
		nom=n;
	}
	
	/**Constructeur associant un nom et un enseignant à un cours
	 * @param n nom de la filière à créer
	 * @param e enseignant du cours à créer*/
	public Cours(String n, Enseignant e){
		nom=n;
		enseignant=e;
		//enseignant.addCours(this);
	}
	
	/**Retourne l'id du cours.
	 * @return identifiant du cours*/
	public int getId(){
		return id;
	}

	//Accesseurs pour l'attribut nom

	/**Retourne le nom du cours.
	 * @return nom de la filière*/
	public String getNom(){
		return nom;
	}

	/**Modifie le nom du cours si celui passé en paramètre est différent de null(retourne vrai si
	 * la modification s'est bien passée, faux sinon).
	 * @param n nouveau nom à attribuer au cours
	 * @return modification effectuée ou non*/
	public boolean setNom(String n){
		boolean res = false;
		
		if(n != null && !n.equalsIgnoreCase("")){
			nom=n;
			res=true;
		}
		
		return res;
	}
	
	//Accesseurs pour l'attribut enseignant

	/**Retourne l'enseignant qui présente le cours.
	 * @return enseignant qui présente le cours*/
	public Enseignant getEnseignant(){
		return enseignant;
	}

	/**Modifie l'enseignant qui présente le cours si celui passé en paramètre est différent de null(retourne vrai si
	 * la modification s'est bien passée, faux sinon).
	 * @param e nouvel enseignant qui présente le cours
	 * @return modification effectuée ou non*/
	public boolean setEnseignant(Enseignant e){
		boolean res = false;
		
		enseignant=e;
		
		if(e != null){
			e.addCours(this);
			res=true;
		}
		
		return res;
	}
	
//Accesseurs pour l'attribut listeInscription
	
	/**Retourne toutes les inscriptions au cours.
	 * @return inscriptions au cours*/
	public Collection<Inscription> getInscriptions(){
		return listeInscriptions;
	}
	
	/**Associe une nouvelle inscription à un cours si celle passée en paramètre est différente de null(retourne vrai si
	 * la modification s'est bien passée, faux sinon).
	 * @param i nouvelle inscription à associer au cours
	 * @return modification effectuée ou non*/
	public boolean addInscription(Inscription i){
		boolean res = false;
		
		if(i != null){
			listeInscriptions.add(i);
			res=true;
		}
		
		return res;
	}
	
	/**Retire l'inscription passée en paramètre de la liste des inscriptions au cours
	 * (retourne vrai si la modification s'est bien passée, faux sinon).
	 * @param i inscription à retirer au cours
	 * @return modification effectuée ou non*/
	public boolean removeInscriptions(Inscription i){
		boolean res = false;
		
		if(i!=null){
			res=listeInscriptions.remove(i);
		}
		return res;
	}
	
	/**Vide la liste des inscriptions au cours.*/
	public void removeAllInscriptions(){
		listeInscriptions.clear();
	}
	
//Accesseurs pour l'attribut listeCoeffFillieres
	
	/**Associe une nouvelle filière(avec un coefficient) à un cours si cellle passée en paramètre est 
	 * différente de null(retourne vrai si la modification s'est bien passée, faux sinon).
	 * @param f nouvelle filière à associer au cours
	 * @param coeff coefficient du cours à associer à la filière
	 * @return modification effectuée ou non*/
	public boolean addFiliere(Filiere f, Integer coeff){
		boolean res = false;
		
		if(f != null){
			listeCoeffFilieres.add(new Coefficient(this, f, coeff));
			res=true;
		}
		
		return res;
	}
	
	/**Retire la filière passée en paramètre de la liste des filières associées à un cours
	 * (retourne vrai si la modification s'est bien passée, faux sinon).
	 * @param f filière à retirer du cours
	 * @return modification effectuée ou non*/
	public boolean removeFiliere(Filiere f){
		boolean res=false;
		
		if(f!=null){
			for(Coefficient coeff: listeCoeffFilieres){
				if(coeff.getFiliere()==f){
					listeCoeffFilieres.remove(coeff);
					break;
				}
			}
		}
		return res;
	}
	
	/**Vide la liste des filières associées à un cours.*/
	public void removeAllFilieres(){
		listeCoeffFilieres.clear();
	}

	/**Description d'un cours
	 * @return description du cours*/
	public String toString(){
		return "Cours : " + nom;
	}

}
