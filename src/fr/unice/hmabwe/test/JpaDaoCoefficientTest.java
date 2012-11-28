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
import fr.unice.hmabwe.controleur.bd.dao.DaoCoefficient;
import fr.unice.hmabwe.controleur.bd.dao.DaoCours;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.modele.Coefficient;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Enseignant;
import fr.unice.hmabwe.modele.Filiere;

public class JpaDaoCoefficientTest {

	static DaoFabrique df;
	static Connexion conn;
	static DaoCours daoCours;
	static DaoCoefficient daoCoefficient;
	static DaoFiliere daoFiliere;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//TestEnvironment.setup();
		
				ConfigConnexion.setTypePersistance(TypePersistance.JPA);
				df = DaoFabrique.getDaoFabrique();
				conn = df.getConnexion();
				daoCours = df.getDaoCours();
				daoCoefficient = df.getDaoCoefficient();
				daoFiliere = df.getDaoFiliere();
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
	public void testCreate() {
		Collection<Coefficient> coe = null;
		Cours cours = null;
		Filiere filiere = null;
		
		try{
			conn.beginTransaction();
			
			//find cour and filiere
			cours = daoCours.findById(3);
			filiere = daoFiliere.findById(6);
			
			Coefficient co = new Coefficient(cours, filiere, 2);
			
			daoCoefficient.create(co);
			
			//find all coefficient
			coe = daoCoefficient.findAll();
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(coe.size() == 3);
	}

	@Test
	public void testUpdate() {
		Collection<Coefficient> coe = null;
		Coefficient c2=null;
		boolean isupdated = false;
		try{
			conn.beginTransaction();
			
			//find a recently added coefficient
			coe = daoCoefficient.findAll();
			for (Coefficient c : coe){
				if(c.getCoefficient() == 3 && c.getCours().getId()==3 && c.getFiliere().getId()==6){
					c2 = new Coefficient(c.getCours(), c.getFiliere(), 2);
					daoCoefficient.update(c2);
				}
			}
			
			//find all coefficient
			coe = daoCoefficient.findAll();
			for( Coefficient c3 : coe){
				if( c3.equals(c2)){
					isupdated=true;
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
		Collection<Coefficient> coe = null;
		boolean isdeleted = false;
		
		try{
			conn.beginTransaction();
			
			//find cour and filiere
			
			
			//find all coefficient
			coe = daoCoefficient.findAll();
			for( Coefficient c: coe){
				if(c.getCoefficient() == 2 && c.getCours().getId()==3 && c.getFiliere().getId()==6){
					daoCoefficient.delete(c);
					isdeleted=true;
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
		Coefficient c = null;
		try{
			conn.beginTransaction();
			
			c = daoCoefficient.findById(7);
			
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
		Collection<Coefficient> coe = null;
		try{
			conn.beginTransaction();
			
			coe = daoCoefficient.findAll();
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(coe.size() == 2);
	}

}
