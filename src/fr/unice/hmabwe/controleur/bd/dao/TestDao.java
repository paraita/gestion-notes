/**
 * 
 */
package fr.unice.hmabwe.controleur.bd.dao;

import java.util.Collection;
import java.util.HashMap;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion.TypePersistance;
import fr.unice.hmabwe.controleur.bd.config.ConnexionException;
import fr.unice.hmabwe.modele.*;
import fr.unice.hmabwe.vue.FenetrePremiere;


/**
 * @author Paraita Wohler
 * 
 *
 *
 */
public class TestDao {

	//TODO changer toutes les requetes en requetes nommées
	//TODO rajouter un refresh dans les méthodes CRUD sinon le cache n'est plus en synchro avec la BDD
	
	/**
	 * @param args
	 */

	public static void remplissage() {

		/* je renseigne la classe DaoFabrique qu'on va utiliser le
		 * type de persistance JPA
		 */
		//DaoFabrique.setTypeDao(ConfigConnexion.JPA);
		//ConfigConnexion.setTypePersistance(TypePersistance.JPA);

		/* je demande une fabrique */
		DaoFabrique df = DaoFabrique.getDaoFabrique();

		/* je récupère la connexion qui va me permettre:
		 * - d'ouvrir/fermer une connexion
		 * - débuter/commiter une transaction
		 * - de savoir l'état de la connexion/transaction
		 */
		Connexion conn = df.getConnexion();

		/* je récupère quelques DAO */
		DaoEnseignant daoEnseignant = df.getDaoEnseignant();
		DaoEtudiant daoEtudiant = df.getDaoEtudiant();
		DaoCours daoCours = df.getDaoCours();
		DaoCoefficient daoCoefficient = df.getDaoCoefficient();
		DaoFiliere daoFiliere = df.getDaoFiliere();
		DaoInscription daoInscription = df.getDaoInscription();

		/* je crée quelques objets métier */
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
		System.out.println("############################### Données crées dans la BDD !");
	}

	public static void recupererDonnees() {
		
		//DaoFabrique.setTypeDao(DaoFabrique.TypeFabrique.JPA);
		//ConfigConnexion.setTypePersistance(TypePersistance.JPA);
		DaoFabrique df = DaoFabrique.getDaoFabrique();
		Connexion conn = df.getConnexion();
		DaoEtudiant daoEtudiant = df.getDaoEtudiant();
		DaoEnseignant daoEnseignant = df.getDaoEnseignant();
		DaoFiliere daoFiliere = df.getDaoFiliere();
		DaoCours daoCours = df.getDaoCours();
		DaoInscription daoInscription = df.getDaoInscription();
		Etudiant etu1 = null;

		try {
			conn.beginTransaction();
			
			
			System.out.println("Tout les étudiants de la BDD:");
			Collection<Etudiant> listeEtu = daoEtudiant.findAll();
			for (Etudiant etudiant : listeEtu) {
				System.out.println("#######################################################################");
				System.out.println("#################################### " + etudiant.getNom() + " " + etudiant.getPrenom());
				System.out.println("#################################### mail: " + etudiant.getMail());
				System.out.println("#######################################################################");
			}
			
			System.out.println("Tout les enseignants de la BDD:");
			Collection<Enseignant> listeEnseign = daoEnseignant.findAll();
			for (Enseignant enseignant : listeEnseign) {
				System.out.println("#######################################################################");
				System.out.println("#################################### " + enseignant.getNom() + " " + enseignant.getPrenom());
				System.out.println("#################################### mail: " + enseignant.getMail());
				System.out.println("#######################################################################");
			}
			
			
			System.out.println("Est ce que 'wp803469' à suivi POO en 2010 ? " + daoEtudiant.etaitInscrit("wp803469", "POO", 2010));
			String matricule = "wp803469";
			etu1 = daoEtudiant.findByNumeroEtudiant(matricule);
			if (etu1 == null) {
				System.out.println("L'étudiant de matricule " + matricule + " n'est pas dans la base de données !");
			}
			else {
				System.out.println("L'étudiant de matricule " + matricule + " s'apelle " + etu1.getPrenom() + " " + etu1.getNom());
			}
			
			System.out.println("Toutes les filieres :");
			Collection<Filiere> listFiliere = daoFiliere.findAll();
			for (Filiere filiere : listFiliere) {
				System.out.println("#######################################################################");
				System.out.println("#################################### " + filiere.getNom() + " " + filiere.getId());
				System.out.println("#######################################################################");
			}
			
			System.out.println("Tout les cours :");
			Collection<Cours> listeCours = daoCours.findAll();
			for (Cours cours : listeCours) {
				System.out.println("#######################################################################");
				System.out.println("#################################### " + cours.getNom());
				System.out.println("#######################################################################");
			}
			
			double moy_cours = daoCours.getMoyenne("POO", 2010);
			System.out.println("Moyenne du cours de POO en 2010: " + moy_cours + "/20");
			
			System.out.println(" liste des etudiant de POO en 2010 avec leur moyenne: \n" + daoInscription.listeInscrit("POO", 2010).toString());
			
			System.out.println("list de tout les etudiant de POO: \n " + daoCours.getEtudiantsInstcrits(daoCours.getCoursByName("POO").get(0)).toString());
			
			System.out.println("recherche etudiant par nom: \n " + daoEtudiant.getEtudiantByName("Wohler"));
			
			System.out.println("recherche etudiant par nom et prenom: \n " + daoEtudiant.getEtudiantByName("Engilberge", "Swan"));

			System.out.println("recherche prof par nom: \n " + daoEnseignant.getEnseignantsByName("Grin"));

			System.out.println("moyenne de la filiere info = " + daoFiliere.getMoyenne(daoFiliere.findById(5), 2010));
			
			System.out.println("moyenne de Paraita en POO =" + daoEtudiant.inscriptionEtu("wp803469", "POO", 2010).getMoyenne());
			
			HashMap<String, Double> moy_groupes = daoFiliere.getMoyenneParGroupe(daoFiliere.findById(5));
			System.out.println(moy_groupes);
			
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
	
	public static void fantomes() {
		DaoFabrique df = DaoFabrique.getDaoFabrique();
		Connexion conn = df.getConnexion();
		DaoEtudiant daoEtudiant = df.getDaoEtudiant();
		try {
			conn.beginTransaction();
			System.out.println("Tout les étudiants de la BDD:");
			Collection<Etudiant> listeEtu = daoEtudiant.findAll();
			for (Etudiant etudiant : listeEtu) {
				System.out.println("#######################################################################");
				System.out.println("#################################### " + etudiant.getNom() + " " + etudiant.getPrenom());
				System.out.println("#################################### mail: " + etudiant.getMail());
				System.out.println("#######################################################################");
			}
			System.out.print("on supprime Paraita: ");
			daoEtudiant.delete(daoEtudiant.findByNumeroEtudiant("wp803469"));
			System.out.print("ok\n");
			System.out.println("Tout les étudiants de la BDD:");
			listeEtu = daoEtudiant.findAll();
			for (Etudiant etudiant : listeEtu) {
				System.out.println("#######################################################################");
				System.out.println("#################################### " + etudiant.getNom() + " " + etudiant.getPrenom());
				System.out.println("#################################### mail: " + etudiant.getMail());
				System.out.println("#######################################################################");
			}
			conn.commitTransaction();
		}
		catch(DaoException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		
		//TODO penser à enregistrer le type de persistance quelque part
		/*
		 * le type de persistance doit etre enregistré quelque part, comme ca
		 * au lancement de l'application on aura juste a apeller setTypePersistance(prop.persitance)
		 * avec prop le fichier de configuration
		 */
		
		ConfigConnexion.setTypePersistance(TypePersistance.JPA);
		try {
			/* on demande à la fabrique de configuration une nouvelle configuration */
			ConfigConnexion configuration = ConfigConnexion.newConfiguration();
			/* on modifie cette nouvelle configuration */
			//configuration.setProprietes("euterpe.unice.fr", "1521", "M1WOHLERP", "AZERTY", "INFO");
			configuration.setProprietes("192.168.56.101", "1521", "paraita", "azerty", "xe");
			/* et on sauvegarde */
			configuration.sauvegarder();
			
			
			//remplissage();
			//recupererDonnees();
			//fantomes();
			FenetrePremiere preums = new FenetrePremiere();
		}
		catch(ConnexionException e) {
			e.printStackTrace();
		}


	}
}
