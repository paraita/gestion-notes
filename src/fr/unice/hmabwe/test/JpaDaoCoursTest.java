package fr.unice.hmabwe.test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion.TypePersistance;
import fr.unice.hmabwe.controleur.bd.dao.DaoCours;
import fr.unice.hmabwe.controleur.bd.dao.DaoEnseignant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Enseignant;
import fr.unice.hmabwe.modele.Etudiant;

public class JpaDaoCoursTest {

	static DaoFabrique df;
	static Connexion conn;
	static DaoCours daoCours;
	static DaoEnseignant daoEnseignant;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//TestEnvironment.setup();
		
		ConfigConnexion.setTypePersistance(TypePersistance.JPA);
		df = DaoFabrique.getDaoFabrique();
		conn = df.getConnexion();
		daoCours = df.getDaoCours();
		daoEnseignant = df.getDaoEnseignant();
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
	public void testGetMoyenne() {
		double moyenne = 0.0;
		try{
			conn.beginTransaction();
			
			moyenne = daoCours.getMoyenne("POO", 2010);
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(moyenne == 15);
	}

	@Test
	public void testGetEtudiantsInstcrits() {
		Collection<Etudiant> etud = null;
		try{
			conn.beginTransaction();
			
			List<Cours> c = daoCours.getCoursByName("POO");
			if(c.size()>0){
				etud = daoCours.getEtudiantsInstcrits(c.get(0));
			}
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertNotNull(etud);
	}

	@Test
	public void testGetCoursByName() {
		List<Cours> c=null;
		try{
			conn.beginTransaction();
			
			c = daoCours.getCoursByName("POO");
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertNotNull(c);
	}

	@Test
	public void testCreate() {
		Collection<Cours> cours = null;
		Enseignant en=null;
		try{
			conn.beginTransaction();
			
			en = daoEnseignant.findById(2);
			Cours cour = new Cours("NewCour",en);
			
			daoCours.create(cour);
			
			//find all cours. there should be three cours now
			cours = daoCours.findAll();
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(cours.size() == 3);
	}

	@Test
	public void testUpdate() {
		Collection<Cours> cours = null;
		boolean isupdated = false;
		try{
			conn.beginTransaction();
			
			cours = daoCours.findAll();
			for(Cours c : cours){
				if(c.getEnseignant().getId() == 2){
					c.setNom("AnotherCour");
					daoCours.update(c);
					isupdated = true;
					break;
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
		assertTrue(isupdated);
	}

	@Test
	public void testDelete() {
		Collection<Cours> cours = null;
		boolean isdeleted = false;
		try{
			conn.beginTransaction();
			
			cours = daoCours.findAll();
			for(Cours c : cours){
				if(c.getNom().compareTo("AnotherCour") == 0){
					daoCours.delete(c);
					isdeleted = true;
					break;
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
		Cours c = null;
		try{
			conn.beginTransaction();
			
			c = daoCours.findById(3);
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertNotNull(c);
	}

	@Test
	public void testFindAll() {
		Collection<Cours> c = null;
		try{
			conn.beginTransaction();
			
			c = daoCours.findAll();
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(c.size() == 2);
	}

}
