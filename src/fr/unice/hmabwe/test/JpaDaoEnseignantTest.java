package fr.unice.hmabwe.test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion.TypePersistance;
import fr.unice.hmabwe.controleur.bd.dao.DaoEnseignant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.modele.Enseignant;

public class JpaDaoEnseignantTest {

	static DaoFabrique df;
	static Connexion conn;
	static DaoEnseignant daoEnseignant;
	static Collection<Enseignant> ens;
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//TestEnvironment.setup();
		//set up testing Environment with two Enseignants
		ConfigConnexion.setTypePersistance(TypePersistance.JPA);
		df = DaoFabrique.getDaoFabrique();
		conn = df.getConnexion();
		daoEnseignant = df.getDaoEnseignant();

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
	public void testGetEnseignantsByNameString() {
		Collection<Enseignant> listens=null;
		try{
			conn.beginTransaction();
			
			listens = daoEnseignant.getEnseignantsByName("Grin");
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(listens.size() == 1);
	}

	@Ignore
	public void testGetEnseignantsByNameStringString() {
		Collection<Enseignant> listens=null;
		try{
			conn.beginTransaction();
			
			listens = daoEnseignant.getEnseignantsByName("Grin", "Richard");
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(listens.size() == 1);
	}

	@Test
	public void testCreate() {
		Collection<Enseignant> listens=null;
		Enseignant en = new Enseignant("Nom","Prenom","email@unice.fr");
		try{
			conn.beginTransaction();
			
			//create a new Enseignant
			daoEnseignant.create(en);
			
			//Find that enseignant
			listens = daoEnseignant.getEnseignantsByName("Nom");
				
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		
		assertTrue(listens.size() == 1);
	}

	@Test
	public void testUpdate() {
		Collection<Enseignant> listens = null;
		Collection<Enseignant> listens2 = null;
		
		try{
			conn.beginTransaction();
			
			//Find that enseignant
			listens = daoEnseignant.getEnseignantsByName("Nom");
			
			//update
			for(Enseignant enseignant : listens){
				enseignant.setNom("NewNom");
				daoEnseignant.update(enseignant);
			}
			
			//Find again
			listens2 = daoEnseignant.getEnseignantsByName("NewNom");
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		
		//System.out.println("listens2 = " + listens.size());
		assertTrue(listens2.size() == 1);
		
	}

	@Test
	public void testDelete() {
		Collection<Enseignant> listens=null;
		try{
			conn.beginTransaction();
			
			//find "NewNom"
			listens = daoEnseignant.getEnseignantsByName("NewNom");
			//Delete
			for(Enseignant en : listens){
				daoEnseignant.delete(en);
			}
			
			//find "NewNom"
			listens = daoEnseignant.getEnseignantsByName("NewNom");
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(listens.size()==0);
	}

	@Test
	public void testFindById() {
		Enseignant en = null;
		try{
			conn.beginTransaction();
			Collection<Enseignant> listens = daoEnseignant.getEnseignantsByName("Grin");
			int id=0;
			for(Enseignant grin : listens){
				id = grin.getId();
			}
			
			en = daoEnseignant.findById(id);
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertNotNull(en);
	}

	@Test
	public void testFindAll() {
		Collection<Enseignant> listens = null;
		try{
			conn.beginTransaction();
			
			listens = daoEnseignant.findAll();
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(listens.size() == 2);
	}

}
