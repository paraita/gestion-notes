package fr.unice.hmabwe.vue;


import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * 
 * @author Bastien Auda
 *
 */
public class FenetreAPropos extends JFrame {

	public FenetreAPropos() {

		String apropos = "<html>" +
				"<body align=\"center\">" +
		"<h1>hmabwe-envoiedenotes</h1>" +
		"<p><a href=\"http://code.google.com/p/hmabwe-envoiedenotes/\">http://code.google.com/p/hmabwe-envoiedenotes/</a>" +
		"<p>Projet réalisé dans le cadre du cours de persistance d'objets en M1 informatique.</p>" +
		"<ul style='list-style-type : none'>" +
		"<li>iliasse Hassala</li>" +
		"<li>Mehdi M'rah</li>" +
		"<li>Bastien Auda</li>" +
		"<li>Anthony Biga</li>" +
		"<li>Paraita Wohler</li>" +
		"<li>Swan Engilberge</li>" +
		"</ul></p>" +
		"<p>Icônes par <a href=\"http://p.yusukekamiyamane.com/\">Yusuke Kamiyamane</a>.<br />" +
		"Tous droits réservés.<br />" +
		"Utilisation autorisée sous <a href=\"http://creativecommons.org/licenses/by/3.0/deed.fr\">licence Creative Commons Paternité 3.0</a>.</p>" +
		"</body>" +
		"</html>";

		JEditorPane text = new JEditorPane("text/html",apropos);

		text.setEditable(false);
		text.setOpaque(false);
		text.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent arg0) {
				if (HyperlinkEvent.EventType.ACTIVATED.equals(arg0.getEventType())) {  
					try {
						java.awt.Desktop.getDesktop().browse(java.net.URI.create(arg0.getURL().toString()));
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(FenetreAPropos.this,
								"Impossible de lancer votre navigateur web.",
								"Erreur",
								JOptionPane.ERROR_MESSAGE);
					}
				}			
			}
		});

		this.setTitle("À propos");
		this.setSize(460,300);
		this.setResizable(false);
		this.add(text);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
