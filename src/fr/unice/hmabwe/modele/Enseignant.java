package fr.unice.hmabwe.modele;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**@author Anthony Biga
 * 
 * La classe Enseignant représente un enseignant dans la table hmabwe_personne
 * (il n'y a pas de table hmabwe_enseignant).
 * 
 * */

@Entity
@DiscriminatorValue("enseignant")
public class Enseignant extends Personne{
	
	/**Un enseignant peut être responsable d'une ou plusieurs filières*/
	@OneToMany(mappedBy="responsable")
	private Collection<Filiere> listeFilieres= new ArrayList<Filiere>();
	
	/**Un enseignant peut présenter un ou plusieurs cours*/
	@OneToMany(mappedBy="enseignant")
	private Collection<Cours> listeCours= new ArrayList<Cours>();
	
	/**Constructeur par défaut*/
	public Enseignant(){
		
	}

	/**Constructeur associant à un enseignant un nom, un prénom et une adresse e-mail
	 * @param n nom de l'enseignant à créer
	 * @param pn prénom de l'enseignant à créer
	 * @param m adresse e-mail de l'enseignant à créer*/
	public Enseignant(String n, String pn, String m){
		super(n, pn, m);
	}
	
	//Accesseurs pour l'attribut listeFillieres
	
	/**Retourne toutes les filières dont l'enseignant est responsable.
	 * @return filières dont l'enseignant est responsable*/
	public Collection<Filiere> getFilieres(){
		return listeFilieres;
	}
	
	/**Associe une nouvelle filière à un enseignant si celle passée en paramètre est différente de null
	 * (retourne vrai si la modification s'est bien passée, faux sinon).
	 * @param f nouvelle filière à associer à l'enseignant
	 * @return modification effectuée ou non*/
	public boolean addFiliere(Filiere f){
		boolean res = false;
		
		if(f != null){
			listeFilieres.add(f);
			f.setResponsable(this);
			res=true;
		}
		
		return res;
	}
	
	/**Retire la filière passée en paramètre de la liste des filières dont l'enseignant est responsable
	 * (retourne vrai si la modification s'est bien passée, faux sinon).
	 * @param f filière à retirer à l'enseignant
	 * @return modification effectuée ou non*/
	public boolean removeFiliere(Filiere f){
		boolean res = false;
		if(f!=null){
			f.setResponsable(null);
			res=listeFilieres.remove(f);
		}
		return res;
	}
	
	/**Vide la liste des filières dont l'enseignant est responsable.*/
	public void removeAllFilieres(){
		for(Filiere f : listeFilieres){
			f.setResponsable(null);
		}
		listeFilieres.clear();
	}
	
	//Accesseurs pour l'attribut listeCours
	
	/**Retourne touts les cours présentés par l'enseignant.
	 * @return cours présentés par l'enseignant*/
	public Collection<Cours> getCours(){
		return listeCours;
	}
	
	/**Associe un nouveau cours à un enseignant si celui passé en paramètre est différent de null
	 * (retourne vrai si la modification s'est bien passée, faux sinon).
	 * @param c nouveau cours à associer à l'enseignant
	 * @return modification effectuée ou non*/
	public boolean addCours(Cours c){
		boolean res = false;
		
		if(c != null){
			listeCours.add(c);
			c.setEnseignant(this);
			res=true;
		}
		
		return res;
	}
	
	/**Retire le cours passé en paramètre de la liste des cours présentés par l'enseignant
	 * (retourne vrai si la modification s'est bien passée, faux sinon).
	 * @param c cours à retirer à l'enseignant
	 * @return modification effectuée ou non*/
	public boolean removeCours(Cours c){
		boolean res = false;
		if(c!=null){
			c.setEnseignant(null);
			res=listeCours.remove(c);
		}
		return res;
	}
	
	/**Vide la liste des cours présentés par l'enseignant.*/
	public void removeAllCours(){
		for(Cours c : listeCours){
			c.setEnseignant(null);
		}
		listeCours.clear();
	}
	
	/**Description d'un enseignant
	 * @return description de l'étudiant*/
	public String toString(){
		return "" + this.prenom + " " + this.nom;
		
	}
}
