package fr.unice.hmabwe.controleur.es.mail;

import java.util.Collection;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import fr.unice.hmabwe.controleur.bd.Connexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion;
import fr.unice.hmabwe.controleur.bd.config.ConfigConnexion.TypePersistance;
import fr.unice.hmabwe.controleur.bd.dao.DaoCours;
import fr.unice.hmabwe.controleur.bd.dao.DaoEtudiant;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.controleur.bd.dao.jpa.JpaDaoEtudiant;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Enseignant;
import fr.unice.hmabwe.modele.Etudiant;
import fr.unice.hmabwe.modele.Filiere;
import fr.unice.hmabwe.modele.Inscription;

/**
 * @author iliasse Hassala
 * 
 * 
 */
public class MailSSL {

	private String login;
	private String password;
	private String host;
	private int port;

	private String tagNom;
	private String tagPrenom;
	private String tagNote;
	private String tagCours;
	private String tagMoyenne;
	private String tagPrenomEnseignant;
	private String tagNomEnseignant;
	private String tagMailEnseignant;

	/**
	 * @param login
	 *            l'identifiant sur le serveur smtp choisit
	 * @param password
	 *            le password sur le serveur smtp
	 * @param host
	 *            adresse du serveur smtp
	 * @param port
	 *            port d'envoi, ici le 465 pour l'envoi en SSL
	 */
	public MailSSL(String login, String password, String host, int port) {
		this.login = login;
		this.password = password;
		this.host = host;
		this.port = port;
	}

	/**
	 * Cette méthode initialise les tags
	 * 
	 * @param tagNom
	 *            valeur du tag (nom à mettre à la place du tag)
	 * @param tagPrenom
	 *            prenom à mettre à la place du tag
	 * @param tagNote
	 *            note à mettre à la place du tag
	 * @param tagCours
	 *            cours à mettre à la place du tag
	 * @param tagMoyenne
	 *            moyenne à mettre à la place du tag
	 * @param tagPrenomEnseignant
	 *            PrenomEnseignant à mettre à la place du tag
	 * @param tagNomEnseignant
	 *            NomEnseignant à mettre à la place du tag
	 * @param tagMailEnseignant
	 *            MailEnseignant à mettre à la place du tag
	 */
	public void setTags(String tagNom, String tagPrenom, String tagNote,
			String tagCours, String tagMoyenne, String tagPrenomEnseignant,
			String tagNomEnseignant, String tagMailEnseignant) {

		this.tagPrenom = tagPrenom;
		this.tagCours = tagCours;
		this.tagMailEnseignant = tagMailEnseignant;
		this.tagMoyenne = tagMoyenne;
		this.tagNom = tagNom;
		this.tagNomEnseignant = tagNomEnseignant;
		this.tagPrenomEnseignant = tagPrenomEnseignant;
		this.tagNote = tagNote;
	}

	/**
	 * @param text
	 *            on emplace les tags présent dans le texte par leur valeur
	 * @return retourne le texte à envoyer par mail pré-formaté
	 */
	public String replaceBalises(String text) {
		text = text.replaceAll("#\\{nom\\}", tagNom);
		text = text.replaceAll("#\\{prenom\\}", tagPrenom);
		text = text.replaceAll("#\\{note\\}", tagNote);
		text = text.replaceAll("#\\{moyenne\\}", tagMoyenne);
		text = text.replaceAll("#\\{cours\\}", tagCours);
		text = text.replaceAll("#\\{prenom_enseignant\\}", tagPrenomEnseignant);
		text = text.replaceAll("#\\{nom_enseignant\\}", tagNomEnseignant);
		text = text.replaceAll("#\\{email_enseignant\\}", tagMailEnseignant);
		return text;

	}

	/**
	 * @param from
	 *            ce qu'on souhaite faire apparaitre dans le champ from
	 * @param to
	 *            l'adresse du destinataire
	 * @param subject
	 *            le sujet du mail
	 * @param text
	 *            le texte à envoyer
	 * @param cours cours concerné
	 * @param annee année concernée
	 * 
	 */
	public void SendMail(String from, Collection<Etudiant> to, String subject,
			String text, Cours cours, Integer annee) {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(login, password);
					}
				});

		ConfigConnexion.setTypePersistance(TypePersistance.JPA);
		DaoFabrique df = DaoFabrique.getDaoFabrique();
		Connexion conn = df.getConnexion();
		DaoEtudiant etu = df.getDaoEtudiant();
		// JpaDaoEtudiant etu = new JpaDaoEtudiant();
		DaoCours c = df.getDaoCours();

		try {

			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			for (Etudiant e : to) {
				conn.beginTransaction();
				String text1 = text;
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(e.getMail()));

				message.setSubject(subject);

				setTags(e.getNom(), e.getPrenom(), Double.toString(etu
						.inscriptionEtu(e.getNumEtu(), cours.getNom(), annee)
						.getMoyenne()), cours.getNom(), Double.toString(c.getMoyenne(cours.getNom(), annee)),
						cours.getEnseignant().getPrenom(), cours
								.getEnseignant().getNom(), cours
								.getEnseignant().getMail());

				// passage de la moyenne en dur dans un premier temps, a terme
				// la méthode du dessus sera appelé.
				/*
				 * setTags(e.getNom(),
				 * e.getPrenom(),"12",cours.getNom(),"moyenne du cours",
				 * cours.getEnseignant().getPrenom(), cours
								.getEnseignant().getNom(), cours
								.getEnseignant().getMail());
				 */

				text1 = replaceBalises(text1);
				message.setText(text1);

				Transport.send(message);
				System.out.println("Done");

				conn.commitTransaction();
			}
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param from
	 *            ce qu'on souhaite faire apparaitre dans le champ from
	 * @param to
	 *            l'adresse du destinataire
	 * @param subject
	 *            le sujet du mail
	 * @param text
	 *            le texte à envoyer
	 * @param filiere filiere concernée
	 * @param annee année concernée
	 */
	public void SendMail(String from, Collection<Etudiant> to, String subject,
			String text, Filiere filiere, Integer annee) {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(login, password);
					}
				});

		ConfigConnexion.setTypePersistance(TypePersistance.JPA);
		DaoFabrique df = DaoFabrique.getDaoFabrique();
		Connexion conn = df.getConnexion();
		DaoFiliere fil = df.getDaoFiliere();
		DaoEtudiant etu = df.getDaoEtudiant();
		// JpaDaoEtudiant etu = new JpaDaoEtudiant();

		try {

			conn.beginTransaction();
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			for (Etudiant e : to) {
				String text1 = text;
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(e.getMail()));

				message.setSubject(subject);

				setTags(e.getNom(), e.getPrenom(), Double.toString(etu.getMoyenne(e.getNumEtu(), annee)), filiere.getNom(), Double.toString(fil.getMoyenne(filiere, annee)),
						filiere.getResponsable().getPrenom(), filiere.getResponsable().getNom(), filiere.getResponsable().getMail());

				// passage de la moyenne en dur dans un premier temps, a terme
				// la méthode du dessus sera appelé.
				/*
				 * setTags(e.getNom(),
				 * e.getPrenom(),"12",cours.getNom(),"moyenne du cours",
				 * cours.getEnseignant().getPrenom(), cours
								.getEnseignant().getNom(), cours
								.getEnseignant().getMail());
				 */

				text1 = replaceBalises(text1);
				message.setText(text1);

				Transport.send(message);
				System.out.println("Done");

				conn.commitTransaction();
			}
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}


}