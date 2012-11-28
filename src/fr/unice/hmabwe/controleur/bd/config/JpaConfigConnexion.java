package fr.unice.hmabwe.controleur.bd.config;

import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class JpaConfigConnexion extends ConfigConnexion {

	private String serveur;
	private String port;
	private String username;
	private String mdp;
	private String SID;
	
	public void setProprietes(String serveur, String port, String username,
			String mdp, String SID) {
		
		this.serveur = serveur;
		this.port = port;
		this.username = username;
		this.mdp = mdp;
		this.SID = SID;
		
	}
	
	@Override
	public void sauvegarder() throws ConnexionException {
		if(serveur == null ||
			port == null ||
			username == null ||
			mdp == null ||
			SID == null) {
			throw new ConnexionException("erreur: il faut d'abord configurer la connexion avant de l'enregistrer");
		}
		Preferences prefs = Preferences.userRoot().node("/hmabwe_config");
		prefs.put("hostname", serveur);
		prefs.put("port", port);
		prefs.put("sid", SID);
		prefs.put("username", username);
		prefs.put("mdp", mdp);
		
		
	}
		/*
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = null;
		Document doc = null;
		TransformerFactory tfactory = null;
		Transformer transformer = null;
		Result resultat = null;
		try {
			parser = factory.newDocumentBuilder();
			doc = parser.parse(Class.class.getResourceAsStream("/META-INF/persistence.xml"));
			NodeList list = doc.getElementsByTagName("property");
			for (int i = 0; i < list.getLength(); i++) {
				NamedNodeMap m = list.item(i).getAttributes();
				Node n = m.getNamedItem("name");
				if (n != null) {
					Node value = null;
					if (n.getNodeValue().compareTo("javax.persistence.jdbc.url") == 0) {
						value = m.getNamedItem("value");
						value.setNodeValue("jdbc:oracle:thin:@" + serveur + ":" + port + ":" + SID);
					}
					if (n.getNodeValue().compareTo("javax.persistence.jdbc.user") == 0) {
						value = m.getNamedItem("value");
						value.setNodeValue(username);
					}
					if (n.getNodeValue().compareTo("javax.persistence.jdbc.password") == 0) {
						value = m.getNamedItem("value");
						value.setNodeValue(mdp);
					}
					//TODO modifier le eclipselink.ddl-generation
				}
			}
			tfactory = TransformerFactory.newInstance();
	        transformer = tfactory.newTransformer();
	        Source source = new DOMSource(doc);
	        resultat = new StreamResult(Class.class.getResource("/META-INF/persistence.xml").toString());
	        transformer.transform(source, resultat);
		}

		catch (ParserConfigurationException e1) {
			throw new ConnexionException("persistence.xml non trouvé", e1);
		}
		catch (SAXException e2) {
			throw new ConnexionException("erreur de parsage du fichier persistence.xml", e2);
		}
		catch (IOException e3) {
			throw new ConnexionException("erreur à l'ouverture du fichier de configuration persistence.xml", e3);
		}
		catch (TransformerConfigurationException e4) {
			throw new ConnexionException("erreur à l'ouverture du fichier de configuration persistence.xml", e4);
		}
		catch (TransformerException e5) {
			throw new ConnexionException("erreur à l'ouverture du fichier de configuration persistence.xml", e5);
		}
	}
	*/

}
