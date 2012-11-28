package fr.unice.hmabwe.test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;

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
import fr.unice.hmabwe.controleur.bd.dao.DaoInscription;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Inscription;

public class JpaDaoInscriptionTest {

	static DaoFabrique df;
	static Connexion conn;
	static DaoCours daoCours;
	static DaoInscription daoInscription;
	static DaoEtudiant daoEtudiant;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//TestEnvironment.setup();
		
				ConfigConnexion.setTypePersistance(TypePersistance.JPA);
				df = DaoFabrique.getDaoFabrique();
				conn = df.getConnexion();
				daoCours = df.getDaoCours();
				daoInscription = df.getDaoInscription();
				daoEtudiant = df.getDaoEtudiant();
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
	public void testListeInscrit() {
		HashMap<Etudiant, String> list_inscrit = null;
		try{
			conn.beginTransaction();
			list_inscrit = daoInscription.listeInscrit("POO", 2010);
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertNotNull(list_inscrit);
	}

	@Test
	public void testCreate() {
		Collection<Inscription> listins=null;
		try{
			conn.beginTransaction();
			
			Etudiant etu = daoEtudiant.findById(9);
			Cours cour = daoCours.findById(3);
			Inscription ins = new Inscription(etu, cour, 2011);
			
			daoInscription.create(ins);
			listins = daoInscription.findAll();
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(listins.size() == 13);
	}

	@Test
	public void testUpdate() {
		Collection<Inscription> listins=null;
		boolean isupdated = false;
		try{
			conn.beginTransaction();
			
			//Etudiant etu = daoEtudiant.findById(9);
			//Cours cour = daoCours.findById(3);
			//Inscription ins = new Inscription(etu, cour, 2010);
			listins = daoInscription.findAll();
			for(Inscription i : listins){
				if(i.getAnnee() == 2011 && i.getEtudiant().getId() == 9 && i.getCours().getId()==3){
					Cours c = daoCours.findById(4);
					i.setCours(c);
					daoInscription.update(i);
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
		Collection<Inscription> listins=null;
		boolean isdeleted = false;
		try{
			conn.beginTransaction();
			
			//Etudiant etu = daoEtudiant.findById(9);
			//Cours cour = daoCours.findById(3);
			//Inscription ins = new Inscription(etu, cour, 2010);
			listins = daoInscription.findAll();
			for(Inscription i : listins){
				if(i.getAnnee() == 2011 && i.getEtudiant().getId() == 9 && i.getCours().getId()==4){
					daoInscription.delete(i);
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
		Inscription ins = null;
		try{
			conn.beginTransaction();
			
			ins = daoInscription.findById(22);
			
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
	public void testFindAll() {
		Collection<Inscription> ins = null;
		try{
			conn.beginTransaction();
			
			ins = daoInscription.findAll();
			
			conn.commitTransaction();
		}catch(DaoException e){
			try{
				conn.rollbackTransaction();
			}catch(DaoException ex){
				ex.printStackTrace();
			}
		}
		assertTrue(ins.size()==12);
	}

}
