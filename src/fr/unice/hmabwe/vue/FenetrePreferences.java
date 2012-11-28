package fr.unice.hmabwe.vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 * Classe de la fenetre de préférences.
 * @author Bastien Auda
 *	
 */
public class FenetrePreferences extends JFrame {

	Properties props = new Properties();
	
	private JTabbedPane onglets = new JTabbedPane();
	
	private ActionListener l = new BoutonListener();
	
	private Box persistance =  Box.createVerticalBox();
	private Box mail = Box.createVerticalBox();
	
	// onglet persistance
	private JTextField pserveur = new JTextField(30);
	private JTextField pport = new JTextField(6);
	private JTextField puser = new JTextField(30);
	private JPasswordField pmdp = new JPasswordField(30);
	private JTextField psid = new JTextField(30);
	
	// onglet mail
	private JTextField mserveur = new JTextField(30);
	private JTextField mport = new JTextField(6);
	private JTextField muser = new JTextField(30);
	private JPasswordField mmdp = new JPasswordField(30);
	
	private JPanel boutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	private JButton ok = new JButton("Valider");
	private JButton annuler = new JButton("Annuler");
	
	public FenetrePreferences() {
		
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setSize(550,325);
		this.setTitle("Préférences");
		
		// initialisation de l'onglet persistance
		
		JPanel prefRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		prefRow.add(new JLabel("Serveur : "));
		prefRow.add(pserveur);
		persistance.add(prefRow);
		
		prefRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		prefRow.add(new JLabel("Port : "));
		prefRow.add(pport);
		persistance.add(prefRow);
		
		prefRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		prefRow.add(new JLabel("Nom d'utilisateur : "));
		prefRow.add(puser);
		persistance.add(prefRow);
		
		prefRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		prefRow.add(new JLabel("Mot de passe : "));
		prefRow.add(pmdp);
		persistance.add(prefRow);
		
		prefRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		prefRow.add(new JLabel("SID : "));
		prefRow.add(psid);
		persistance.add(prefRow);
		
		onglets.add("Persistance",persistance);
		
		// initialisation de l'onglet mail
		
		prefRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		prefRow.add(new JLabel("Serveur smtp : "));
		prefRow.add(mserveur);
		mail.add(prefRow);
		
		prefRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		prefRow.add(new JLabel("Port : "));
		prefRow.add(mport);
		mail.add(prefRow);
		
		prefRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		prefRow.add(new JLabel("Nom d'utilisateur : "));
		prefRow.add(muser);
		mail.add(prefRow);
		
		prefRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		prefRow.add(new JLabel("Mot de passe : "));
		prefRow.add(mmdp);
		mail.add(prefRow);
		
		onglets.add("Mail", mail);
		
		// la fenetre
		this.add(onglets,BorderLayout.CENTER);
		boutons.add(annuler);
		annuler.addActionListener(l);
		boutons.add(ok);
		ok.addActionListener(l);
		this.add(boutons, BorderLayout.SOUTH);
		
		chargerPrefs();
		
		this.setVisible(true);
	}
	
	/**
	 * Charge les données du fichier de préférences dans les champs.
	 */
	private void chargerPrefs() {
		try {
			FileInputStream file = new FileInputStream("hmabwe-properties");
			props.load(file);
			file.close();
		} catch (Exception e) {
			
		}
		pserveur.setText(props.getProperty("pserveur"));
		pport.setText(props.getProperty("pport"));
		puser.setText(props.getProperty("puser"));
		pmdp.setText(props.getProperty("pmdp"));
		psid.setText(props.getProperty("psid"));
		
		mserveur.setText(props.getProperty("mserveur"));
		mport.setText(props.getProperty("mport"));
		muser.setText(props.getProperty("muser"));
		mmdp.setText(props.getProperty("mmdp"));
	}
	
	/**
	 * Sauvegarde les préférences dans un fichier.
	 */
	private void sauverPrefs() {
		props.setProperty("pserveur", pserveur.getText());
		props.setProperty("pport", pport.getText());
		props.setProperty("puser", puser.getText());
		props.setProperty("pmdp", new String(pmdp.getPassword()));
		props.setProperty("psid",psid.getText());
		
		props.setProperty("mserveur", mserveur.getText());
		props.setProperty("mport", mport.getText());
		props.setProperty("muser", muser.getText());
		props.setProperty("mmdp", new String( mmdp.getPassword()));
		
		props.setProperty("type_persistance", "jpa");
		
		try {
			FileOutputStream file = new FileOutputStream("hmabwe-properties");
			props.store(file,"");
			file.close();
		} catch (Exception e) {
			System.out.println("Impossible d'écrire le fichier de propriétés.");
		}
	}
	
	private class BoutonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source.equals(annuler)) {
				FenetrePreferences.this.dispose();
			}
			if(source.equals(ok)) {
				sauverPrefs();
				FenetrePreferences.this.dispose();
			}
			
		}
		
	}
	
}
