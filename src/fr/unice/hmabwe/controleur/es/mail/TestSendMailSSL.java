package fr.unice.hmabwe.controleur.es.mail;

import java.util.ArrayList;
import java.util.Collection;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion.TypePersistance;
import fr.unice.hmabwe.controleur.bd.dao.DaoEtudiant;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Enseignant;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Inscription;

public class TestSendMailSSL {

	public static void main(String[] args) {
		
		MailSSL mail = new MailSSL("hi911803@etu.unice.fr", "g=0.83462",
				"smtp.unice.fr", 465);
		
	
		//Etudiant e2 = new Etudiant("20911803", "Hassala", "iliasse",
		//		"hassala.iliasse@etu.unice.fr");
		
		ConfigConnexion.setTypePersistance(TypePersistance.JPA);
		DaoFabrique df = DaoFabrique.getDaoFabrique();
		Connexion conn = df.getConnexion();
		DaoEtudiant de = df.getDaoEtudiant();
		
		Etudiant e2 = de.getEtudiantByName("Hassala").get(0);

		Enseignant enseignant = new Enseignant("Grin", "Richard",
				"grin@unice.fr");
		
		

		Collection<Etudiant> to = new ArrayList<Etudiant>();
	
		to.add(e2);
		
		System.out.println(e2.getInscriptions());


		mail.SendMail("hi911803@etu.unice.fr", to, "Subject",
				"<-- Text + Tag --> : salut #nom #prenom #moyenne", ((Inscription)(e2.getInscriptions().toArray()[0])).getCours(), 2010);

	}

}
