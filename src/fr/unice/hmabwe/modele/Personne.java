package fr.unice.hmabwe.modele;

/**@author Anthony Biga
 * 
 * La classe Personne représente la table hmabwe_personne. 
 * La stratégie choisie pour le mapping objet/relationnel étant une seule table par
 * arborescence d'héritage, les classes héritant de Personne ne sont pas représentées par 
 * des tables différentes dans la base de données de l'application EnvoieDeNotes.
 * 
 * */

import javax.persistence.DiscriminatorColumn;
import javax.persistence.UniqueConstraint;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
		name="hmabwe_personne", 
		uniqueConstraints=@UniqueConstraint(name="hmabwe_numEtu_UniqueConstraint", columnNames={"numEtu"})
		)
@DiscriminatorColumn(name="type")
public class Personne {
	
	/**Identifiant d'une personne (clée primaire de la table hmabwe_personne générée automatiquement)*/
	@Id
	@GeneratedValue
	protected int id;
	
	/**nom d'une personne*/
	protected String nom;
	
	/**pr�nom d'une personne*/
	protected String prenom;
	
	/**adresse e-mail d'une personne. Dans le cadre de l'application EnvoieDe Notes chaque personne
	 * doit obligatoirement avoir une adresse e-mail.*/
	protected String mail;

	/**Constructeur par d�faut*/
public Personne(){
	
}

/**Constructeur associant à une personne un nom, un prénom et une adresse e-mail
 * @param n nom de la personne à créer
 * @param pn prénom de la personne à créer
 * @param m adresse e-mail de la personne à créer*/
public Personne(String n, String pn, String m){
	nom=n;
	prenom=pn;
	mail=m;
}

/**Retourne l'id de la personne.
 * @return identifiant de la personne*/
public int getId(){
	return id;
}

//Accesseurs pour l'attribut nom

/**Retourne le nom de la personne.
 * @return nom de la personne*/
public String getNom(){
	return nom;
}

/**Modifie le nom de la personne si celui passé en paramètre est différent de null(retourne vrai si
 * la modification s'est bien passée, faux sinon).
 * @param n nouveau nom à attribuer à la personne
 * @return modification effectuée ou non*/
public boolean setNom(String n){
	boolean res = false;
	
	if(n != null && !n.equalsIgnoreCase("")){
		nom=n;
		res=true;
	}
	
	return res;
}

//Accesseurs pour l'attribut prenom

/**Retourne le prénom de la personne.
 * @return prénom de la personne*/
public String getPrenom(){
	return prenom;
}

/**Modifie le prénom de la personne si celui passé en paramètre est différent de null(retourne vrai si
 * la modification s'est bien passée, faux sinon).
 * @param pn nouveau prénom à attribuer à la personne
 * @return modification effectuée ou non*/
public boolean setPrenom(String pn){
	boolean res = false;
	
	if(pn != null && !pn.equalsIgnoreCase("")){
		prenom=pn;
		res=true;
	}
	
	return res;
}

//Accesseurs pour l'attribut mail

/**Retourne l'adresse e-mail de la personne.
 * @return adresse e-mail de la personne*/
public String getMail(){
	return mail;
}

/**Modifie l'adresse e-mail de la personne si celle passée en paramètre est différente de null
 * (retourne vrai si la modification s'est bien passée, faux sinon).
 * @param m nouvelle adresse e-mail à attribuer à la personne
 * @return modification effectuée ou non*/
public boolean setMail(String m){
	boolean res = false;
	
	if(m != null && !m.equalsIgnoreCase("")){
		mail=m;
		res=true;
	}
	
	return res;
}

/**Description d'une personne
 * @return description de la personne*/
public String toString(){
	String toString;
	toString ="Id : ";
	toString+=id+"\n";
	toString+="Nom : ";
	toString+=nom+"\n";
	toString+="Prenom : ";
	toString+=prenom+"\n";
	toString+="Mail : ";
	toString+=mail+"\n";
	
	return toString;
}

}
