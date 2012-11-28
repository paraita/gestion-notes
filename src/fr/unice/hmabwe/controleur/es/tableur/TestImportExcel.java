package fr.unice.hmabwe.controleur.es.tableur;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import fr.unice.hmabwe.modele.Etudiant;

public class TestImportExcel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//sans BD
		//ExcelImport ei = new ExcelImport("/home/amazigh/workspace/EnvoiDeNotes/moyenneEtu.xls", "a", "b", "c", "d", "e", "f","g", "h", "i");
		
		//avec Bd
		ImportExcel ie = new ImportExcel("/Users/milkyboi/Downloads/moyenneEtu.xls", "a", "b", "c", "d", "e", "f","g", "h", "i");
		
		HashMap<Etudiant, Double> ed = ie.lectureListEtudiants();
		System.out.println("Taille de la liste d'etudiants : " + ed.size());
		
		for (Entry<Etudiant, Double> entry : ed.entrySet())
		{
		    System.out.println(entry.getKey().getNom()+ " " + entry.getKey().getPrenom() + "/" + entry.getValue());
		}




	}

}
