package fr.unice.hmabwe.test;

import java.util.Collection;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion.TypePersistance;
import fr.unice.hmabwe.controleur.bd.dao.DaoCoefficient;
import fr.unice.hmabwe.controleur.bd.dao.DaoCours;
import fr.unice.hmabwe.controleur.bd.dao.DaoEnseignant;
import fr.unice.hmabwe.controleur.bd.dao.DaoEtudiant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.controleur.bd.dao.DaoInscription;
import fr.unice.hmabwe.modele.Coefficient;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Enseignant;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Filiere;
import fr.unice.hmabwe.modele.Inscription;

public class TestEnvironment {

	static DaoFabrique df;
	static Connexion conn;
	static DaoEnseignant daoEnseignant;
	static DaoEtudiant daoEtudiant;
	static DaoCours daoCours;
	static DaoCoefficient daoCoefficient;
	static DaoFiliere daoFiliere;
	static DaoInscription daoInscription;
	
	
	public static void setup(){
		//deleteAllData();
		ConfigConnexion.setTypePersistance(TypePersistance.JPA);
		df = DaoFabrique.getDaoFabrique();
		conn = df.getConnexion();
		
		daoEnseignant = df.getDaoEnseignant();
		daoEtudiant = df.getDaoEtudiant();
		daoCours = df.getDaoCours();
		daoCoefficient = df.getDaoCoefficient();
		daoFiliere = df.getDaoFiliere();
		daoInscription = df.getDaoInscription();
		
		Enseignant ens1 = new Enseignant("Grin", "Richard", "grin@unice.fr");
		Enseignant ens2 = new Enseignant("Mallet", "Frédéric", "fmallet@unice.fr");
		Cours cours1 = new Cours("POO", ens1);
		Cours cours2 = new Cours("SYSTEME", ens1);
		Filiere filiere1 = new Filiere("M1 Info", ens1);
		Filiere filiere2 = new Filiere("M1 Miage", ens2);
		Coefficient coef1 = new Coefficient(cours1, filiere1, 2);
		Coefficient coef2 = new Coefficient(cours2, filiere1, 4);
		Etudiant etu1 = new Etudiant("wp803469", "Wohler", "Paraita", "wp803469@etu.unice.fr", "L3I", filiere1, "1");
		Etudiant etu2 = new Etudiant("hi1234", "Hassala", "Iliasse", "hassala.iliasse@etu.unice.fr", "L3I", filiere1, "1");
		Etudiant etu3 = new Etudiant("ba1234", "Auda", "Bastien", "bastien26990@yahoo.fr", "L3I", filiere1, "Magenta");
		Etudiant etu4 = new Etudiant("mm1234", "M'rah", "Mehdi", "m-rah-mehdi@etu.unice.fr", "L3I", filiere1, "Magenta");
		Etudiant etu5 = new Etudiant("es1234", "Engilberge", "Swan", "engilberge-swan@etu.unice.fr", "L3I", filiere1, "2");
		Etudiant etu6 = new Etudiant("nc1234", "Nuon", "Channdarong", "nuonchanndarong@gmail.com", "???", filiere1, "2");
		Inscription inscr1 = new Inscription(etu1, cours1, 2010);
		Inscription inscr2 = new Inscription(etu1, cours2, 2010);
		Inscription inscr3 = new Inscription(etu2, cours1, 2010);
		Inscription inscr4 = new Inscription(etu2, cours2, 2010);
		Inscription inscr5 = new Inscription(etu3, cours1, 2010);
		Inscription inscr6 = new Inscription(etu3, cours2, 2010);
		Inscription inscr7 = new Inscription(etu4, cours1, 2010);
		Inscription inscr8 = new Inscription(etu4, cours2, 2010);
		Inscription inscr9 = new Inscription(etu5, cours1, 2010);
		Inscription inscr10 = new Inscription(etu5, cours2, 2010);
		Inscription inscr11 = new Inscription(etu6, cours1, 2010);
		Inscription inscr12 = new Inscription(etu6, cours2, 2010);
		inscr1.setMoyenne(15.0);
		inscr2.setMoyenne(10.0);
		inscr3.setMoyenne(15.0);
		inscr4.setMoyenne(10.0);
		inscr5.setMoyenne(15.0);
		inscr6.setMoyenne(10.0);
		inscr7.setMoyenne(15.0);
		inscr8.setMoyenne(10.0);
		inscr9.setMoyenne(15.0);
		inscr10.setMoyenne(15.0);
		inscr11.setMoyenne(15.0);
		inscr12.setMoyenne(10.0);
		/* on rend persistant ces objets */
		try {
			conn.beginTransaction();
			daoEnseignant.create(ens1);
			daoEnseignant.create(ens2);
			daoCours.create(cours1);
			daoCours.create(cours2);
			daoFiliere.create(filiere1);
			daoFiliere.create(filiere2);
			daoCoefficient.create(coef1);
			daoCoefficient.create(coef2);
			daoEtudiant.create(etu1);
			daoEtudiant.create(etu2);
			daoEtudiant.create(etu3);
			daoEtudiant.create(etu4);
			daoEtudiant.create(etu5);
			daoEtudiant.create(etu6);
			daoInscription.create(inscr1);
			daoInscription.create(inscr2);
			daoInscription.create(inscr3);
			daoInscription.create(inscr4);
			daoInscription.create(inscr5);
			daoInscription.create(inscr6);
			daoInscription.create(inscr7);
			daoInscription.create(inscr8);
			daoInscription.create(inscr9);
			daoInscription.create(inscr10);
			daoInscription.create(inscr11);
			daoInscription.create(inscr12);
			conn.commitTransaction();
		}
		catch(DaoException e) {
			try {
				conn.rollbackTransaction();
			}
			catch(DaoException ee) {
				ee.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			if(conn.estOuverte()) {
				try {
					conn.fermer();
				}
				catch(DaoException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void deleteAllData(){
		ConfigConnexion.setTypePersistance(TypePersistance.JPA);
		df = DaoFabrique.getDaoFabrique();
		conn = df.getConnexion();
		
		daoEnseignant = df.getDaoEnseignant();
		daoEtudiant = df.getDaoEtudiant();
		daoCours = df.getDaoCours();
		daoCoefficient = df.getDaoCoefficient();
		daoFiliere = df.getDaoFiliere();
		daoInscription = df.getDaoInscription();
		
		try{
			conn.beginTransaction();
			
			//delete all Enseignant
			Collection<Enseignant> ens = daoEnseignant.findAll();
			for(Enseignant en : ens){
				daoEnseignant.delete(en);
			}
			
			//delete all Etudiant
			Collection<Etudiant> etu = daoEtudiant.findAll();
			for(Etudiant et : etu){
				daoEtudiant.delete(et);
			}
			
			//delete all cours
			Collection<Cours> cou = daoCours.findAll();
			for(Cours c : cou){
				daoCours.delete(c);
			}
			
			//delete all coefficient
			Collection<Coefficient> coe = daoCoefficient.findAll();
			for(Coefficient co : coe){
				daoCoefficient.delete(co);
			}
			
			//delete Filieres
			Collection<Filiere> f = daoFiliere.findAll();
			for(Filiere fl : f){
				daoFiliere.delete(fl);
			}
			
			//delete Inscription
			Collection<Inscription> ins = daoInscription.findAll();
			for(Inscription i : ins){
				daoInscription.delete(i);
			}
			
			conn.commitTransaction();
		}catch(DaoException e) {
			try {
				conn.rollbackTransaction();
			}
			catch(DaoException ee) {
				ee.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			if(conn.estOuverte()) {
				try {
					conn.fermer();
				}
				catch(DaoException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
}
