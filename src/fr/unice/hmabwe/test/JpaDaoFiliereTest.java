package fr.unice.hmabwe.test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion.TypePersistance;
import fr.unice.hmabwe.controleur.bd.dao.DaoEnseignant;
import fr.unice.hmabwe.controleur.bd.dao.DaoEtudiant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.modele.Enseignant;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Filiere;

public class JpaDaoFiliereTest {

	static DaoFabrique df;
	static Connexion conn;
	static DaoEnseignant daoEnseignant;
	static DaoFiliere daoFiliere;
	static DaoEtudiant daoEtudiant;
	
	static Collection<Filiere> fil;
	static Collection<Enseignant> ens;
	static Collection<Etudiant> etu;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//TestEnvironment.setup();
		//set up testing Environment with two Filieres
				ConfigConnexion.setTypePersistance(TypePersistance.JPA);
				df = DaoFabrique.getDaoFabrique();
				conn = df.getConnexion();
				daoEnseignant = df.getDaoEnseignant();
				daoFiliere = df.getDaoFiliere();
				daoEtudiant = df.getDaoEtudiant();
				//Environment is ready to test
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//close the connection when the tests finish
				try{
					conn.fermer();
				}catch(DaoException e){
					e.printStackTrace();
				}
	}

	@Test
	public void testGetEtudiantsInscrits() {
		Collection<Etudiant> etudiants=null;
		try{
			Filiere f = daoFiliere.findById(5);
			
			 etudiants = daoFiliere.getEtudiantsInscrits(f);
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(etudiants.size() == 6);
	}

	@Ignore
	public void testGetMoyenne_old() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetMoyenneFiliereInt() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetMoyenneParGroupe() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetMoyenneFiliere() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreate() {
		Filiere f = null;
		Collection<Filiere> listf=null;
		try{
			conn.beginTransaction();
			
			//select Grin
			Collection<Enseignant> grin = daoEnseignant.getEnseignantsByName("Grin");
			for(Enseignant en : grin){
				f = new Filiere("Filiere", en);
				daoFiliere.create(f);
			}
						
			listf = daoFiliere.findAll();
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(listf.size() == 3);
	}

	@Test
	public void testUpdate() {
		Collection<Filiere> f = null;
		Collection<Filiere> f2 = null;
		boolean ischanged = false;
		try{
			conn.beginTransaction();
			
			//select Filiere
			f = daoFiliere.findAll();
			for(Filiere fl : f){
				if(fl.getNom() == "Filiere"){
					fl.setNom("NewFiliere");
					daoFiliere.update(fl);
				}
			}
			
			//Select again
			f2 = daoFiliere.findAll();
			for(Filiere fl2 : f2){
				if(fl2.getNom() == "NewFiliere"){
					ischanged = true;
				}
			}
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(ischanged);
	}

	@Test
	public void testDelete() {
		Collection<Filiere> f = null;
		Collection<Filiere> f2 = null;
		boolean isdeleted = true;
		try{
			conn.beginTransaction();
			
			//select Filiere
			f = daoFiliere.findAll();
			for(Filiere fl : f){
				if(fl.getNom() == "NewFiliere"){
					daoFiliere.delete(fl);
				}
			}
			
			//Select again
			f2 = daoFiliere.findAll();
			for(Filiere fl : f2){
				if(fl.getNom() == "NewFiliere"){
					isdeleted = false;
				}
			}
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(isdeleted);
	}

	@Test
	public void testFindById() {
		Collection<Filiere> f = null;
		Filiere finfo = null;
		int id=0;
		try{
			conn.beginTransaction();
			
			f = daoFiliere.findAll();
			for(Filiere fl : f){
				if(fl.getNom().compareTo("M1 Miage") == 0){
					id = fl.getId();
				}
			}
			
			finfo = daoFiliere.findById(id);
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		System.out.println(id + " = id");
		assertNotNull(finfo);
	}

	@Test
	public void testFindAll() {
		Collection<Filiere> f = null;
		try{
			conn.beginTransaction();
			f = daoFiliere.findAll();
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(f.size() == 2);
	}

}
