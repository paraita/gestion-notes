package fr.unice.hmabwe.vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.unice.hmabwe.controleur.bd.dao.DaoCours;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.controleur.es.mail.MailSSL;
import fr.unice.hmabwe.modele.Cours;
import fr.unice.hmabwe.modele.Enseignant;
import fr.unice.hmabwe.modele.Etudiant;

/**
 * Cette fenêtre permet d'envoyer les note par email aux étudiants.
 * @author Bastien Auda
 *
 */
public class FenetreMail extends JFrame {

	private DaoFabrique df;
	private DaoFiliere daoFiliere;
	private DaoCours daoCours;

	private Collection<Etudiant> etudiants;

	private ActionListener l = new BoutonListener();

	private JComboBox choixNote;
	private JSpinner annee = new JSpinner();

	private JTextField sujet = new JTextField(20);
	private JTextArea textMail = new JTextArea();

	private JButton aide = new JButton(new ImageIcon(this.getClass().getResource("/resource/question-frame.png")));
	private JButton annuler = new JButton("Annuler");
	private JButton valider = new JButton("Envoyer");

	private String file = "hmabwe-properties";
	private String smtp;
	private String user;
	private String port;
	private String mdp;

	public FenetreMail(DaoFabrique df, Collection<Etudiant> etudiants, Object filiereCours) {
		this.df = df;
		daoFiliere = df.getDaoFiliere();
		daoCours = df.getDaoCours();

		this.etudiants = etudiants;

		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setSize(800,400);
		this.setTitle("Envoi de notes par email");

		Collection<Object> choixFiliereCours = new ArrayList<Object>();
		try {
			//	choixFiliereCours.addAll(daoFiliere.findAll());
			choixFiliereCours.addAll(daoCours.findAll());	
		} catch (DaoException e) {
			e.printStackTrace();
		}

		choixNote = new JComboBox(choixFiliereCours.toArray());
		if(filiereCours.getClass() == Cours.class) {
			choixNote.setSelectedItem(filiereCours);
		}

		JPanel top = new JPanel();

		top.add(new JLabel("Cours :"));
		top.add(choixNote);
		top.add(new JLabel("Année :"));
		annee.setValue(Calendar.getInstance().get(Calendar.YEAR));
		annee.setEditor(new JSpinner.NumberEditor(annee, "#"));
		top.add(annee);
		top.add(new JLabel("Sujet :"));
		top.add(sujet);
		sujet.setText("Votre note");
		top.add(aide);		
		aide.addActionListener(l);
		aide.setBorderPainted(false);
		this.add(top,BorderLayout.NORTH);

		textMail.setBorder(BorderFactory.createTitledBorder("Texte personnalisé du mail :"));
		textMail.setText("Bonjour #{prenom} #{nom},\nvotre note au cours de #{cours} est de #{note}.\n\n Cordialement,\n #{prenom_enseignant} #{prenom_enseignant} #{nom_enseignant}\n #{email_enseignant}");
		this.add(textMail,BorderLayout.CENTER);

		JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		south.add(annuler);
		annuler.addActionListener(l);
		south.add(valider);
		valider.addActionListener(l);

		this.add(south, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	private void loadProperties(String filename) {
		Properties props = new Properties();
		try {
			FileInputStream file = new FileInputStream(filename);
			props.load(file);
			file.close();
		} catch (Exception e) {

		}

		smtp = props.getProperty("mserveur");
		port = props.getProperty("mport");
		user  = props.getProperty("muser");
		mdp = props.getProperty("mmdp");
	}

	private class BoutonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();

			if(source.equals(annuler)) {
				FenetreMail.this.dispose();
			}
			if(source.equals(aide)) {
				String tagDispo = "<html><p>Les emplacements spéciaux utilisables sont :" +
				"<ul>" +
				"<li>#{nom} : Le nom de l'étudiant</li>" +
				"<li>#{prenom} : Le prénom de l'étudiant</li>" +
				"<li>#{note} : La note de l'étudiant pour le cours/ la filière selectionné(e)</li>" +
				"<li>#{moyenne} : La moyenne de la classe pour le cours/ la filière selectionné(e)</li>" +
				"<li>#{cours} : Le noms du cours/de la filière sélectionné(e)</li>" +
				"<li>#{prenom_enseignant}, #{nom_enseignant} et #{email_enseignant} : Les coordonnées de l'enseignant responsable du cours/de la filière selectionné(e)</li>" +
				"</ul>" +
				"</p></html>";
				JLabel textAide = new JLabel(tagDispo);
				JFrame aide = new JFrame();
				aide.add(textAide);
				aide.setTitle("Aide redaction de mail");
				aide.setLocationRelativeTo(null);
				aide.setSize(400,300);
				aide.setVisible(true);
			}
			if(source.equals(valider)) {
				try {
					loadProperties(file);
					
					MailSSL mail = new MailSSL(user, mdp,
							smtp, Integer.parseInt(port));

					if(choixNote.getSelectedItem().getClass() == Cours.class) {
						mail.SendMail(((Cours)choixNote.getSelectedItem()).getEnseignant().getMail(), etudiants, sujet.getText(),
								textMail.getText(), (Cours) choixNote.getSelectedItem(), (Integer)annee.getValue());
						FenetreMail.this.dispose();
					} else {
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(FenetreMail.this,"Envoi de mail impossible, veuillez vérifier vos paramètres.", "Erreur de connexion" , JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}

		}



	}

}
