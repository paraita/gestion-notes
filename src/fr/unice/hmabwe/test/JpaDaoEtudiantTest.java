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
import fr.unice.hmabwe.controleur.bd.dao.DaoEtudiant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Filiere;
import fr.unice.hmabwe.modele.Inscription;

public class JpaDaoEtudiantTest {

	static DaoFabrique df;
	static Connexion conn;
	static DaoCours daoCours;
	static DaoEtudiant daoEtudiant;
	static DaoFiliere daoFiliere;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//TestEnvironment.setup();
		
				ConfigConnexion.setTypePersistance(TypePersistance.JPA);
				df = DaoFabrique.getDaoFabrique();
				conn = df.getConnexion();
				daoCours = df.getDaoCours();
				daoEtudiant = df.getDaoEtudiant();
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
	public void testEtaitInscrit() {
		boolean isInscrit=false;
		try{
			conn.beginTransaction();
			isInscrit = daoEtudiant.etaitInscrit("nc1234", "POO", 2010);
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(isInscrit);
	}

	@Test
	public void testInscriptionEtu() {
		Inscription ins = null;
		try{
			conn.beginTransaction();
			ins = daoEtudiant.inscriptionEtu("nc1234", "POO", 2010);
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertNotNull(ins);
	}

	@Test
	public void testFindByNumeroEtudiant() {
		Etudiant etud = null;
		try{
			conn.beginTransaction();
			etud = daoEtudiant.findByNumeroEtudiant("nc1234");
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
	public void testGetEtudiantByNameString() {
		List<Etudiant> etud = null;
		try{
			conn.beginTransaction();
			etud = daoEtudiant.getEtudiantByName("Nuon");
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
	public void testGetEtudiantByNameStringString() {
		List<Etudiant> etud = null;
		try{
			conn.beginTransaction();
			etud = daoEtudiant.getEtudiantByName("Nuon", "Channdarong");
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

	@Ignore
	public void testGetMoyenneString() {
		double moyenne = 0;
		try{
			conn.beginTransaction();
			moyenne = daoEtudiant.getMoyenne("nc1234", 2010);
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
	public void testGetMoyenneStringInt() {
		double moyenne = 0;
		try{
			conn.beginTransaction();
			moyenne = daoEtudiant.getMoyenne("nc1234", 2010);
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		fail("Don't know how to calculate");
	}

	@Test
	public void testCreate() {
		Collection<Etudiant> etu = null;
		try{
			conn.beginTransaction();
			Filiere f = daoFiliere.findById(6);
			Etudiant et = new Etudiant("ab1234", "a", "b", "ab@gmail.com", "???", f, "2");
			
			daoEtudiant.create(et);
			
			etu = daoEtudiant.findAll();
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(etu.size() == 7);
	}

	@Test
	public void testUpdate() {
		Etudiant etudiant = null;
		Etudiant et = null;
		boolean isupdated = false;
		try{
			conn.beginTransaction();
			
			etudiant = daoEtudiant.findByNumeroEtudiant("ab1234");
			etudiant.setNom("NewNom");
			daoEtudiant.update(etudiant);
			
			et = daoEtudiant.findByNumeroEtudiant("ab1234");
			if(et.getNom().compareTo("NewNom") == 0){
				isupdated = true;
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
		Etudiant etudiant = null;
		Collection<Etudiant> etu = null;
		boolean isdeleted = false;
		try{
			conn.beginTransaction();
			
			etudiant = daoEtudiant.findByNumeroEtudiant("ab1234");
			
			daoEtudiant.delete(etudiant);
			
			etu = daoEtudiant.findAll();
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(etu.size() == 6);
	}

	@Test
	public void testFindById() {
		Etudiant etu=null;
		try{
			conn.beginTransaction();
			
			etu = daoEtudiant.findById(9);
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertNotNull(etu);
	}

	@Test
	public void testFindAll() {
		Collection<Etudiant> etu=null;
		try{
			conn.beginTransaction();
			
			etu = daoEtudiant.findAll();
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(etu.size() == 6);
	}

}
