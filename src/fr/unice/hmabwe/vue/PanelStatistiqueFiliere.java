package fr.unice.hmabwe.vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.controleur.bd.dao.DaoFiliere;
import fr.unice.hmabwe.modele.Filiere;

/**
 * 
 * @author M'RAH Mehdi
 * 
 * Panel pour les statistiques d'une filière 
 *
 */

public class PanelStatistiqueFiliere extends JPanel{
	
	private JPanel panel, panelGeneral, panelSpecD, panelmoyenneG, panelmoyenneGr1, panelmoyenneGr2, panelmoyenneG2, panelmoyenneGr12, panelmoyenneGr22, panelEcartType, panelTotMoyenne, panelGroupe, panelNote;
	
	private JLabel moyenneG, moyenneGr1, moyenneGr2, ecartType,moyenneG2, moyenneGr12, moyenneGr22, notemoyenneG, notemoyenneGr1, notemoyenneGr2, noteEcartType, notemoyenneG2, notemoyenneGr12, notemoyenneGr22;
	
	private JList listCours;
	
	private JSplitPane panelSpec;
	
	private JScrollPane scrollList;
	
	public PanelStatistiqueFiliere(Filiere f, DaoFiliere daofiliere){
		//TODO Dans le constructeur ajouter la connexion a la base.
		//TODO Remplacer les moyennes dans le constructeur, par les moyennes recus de la database.
		HashMap<String, Double> hash = daofiliere.getMoyenneParGroupe(f);
		
		 panelTotMoyenne = new JPanel(new FlowLayout(FlowLayout.CENTER));
		 
		 
		 panelGroupe = new JPanel();
		 panelGroupe.setLayout(new BoxLayout(panelGroupe, BoxLayout.Y_AXIS));
		 panelNote = new JPanel();
		 panelNote.setLayout(new BoxLayout(panelNote, BoxLayout.Y_AXIS));

		for (Iterator<String> i = hash.keySet().iterator() ; i.hasNext() ; ){
			JPanel petitPanelGroupe  = new JPanel(new FlowLayout(FlowLayout.LEFT));
		    JLabel k = new JLabel("Groupe : ");
		    JLabel u = new JLabel(i.next());
		    petitPanelGroupe.add(k);
		    petitPanelGroupe.add(u);
		    panelGroupe.add(petitPanelGroupe);
		    System.out.println("#############<<<<<<<<<#############");
		}
		

		for (Iterator<Double> i = hash.values().iterator() ; i.hasNext() ;){
			JPanel petitPanelNote  = new JPanel(new FlowLayout(FlowLayout.LEFT));
		    JLabel a = new JLabel("Moyenne : ");
		    JLabel b = new JLabel(String.valueOf(i.next()));
		    petitPanelNote.add(a);
		    petitPanelNote.add(b);
		    panelNote.add(petitPanelNote);
		    System.out.println("#############<<<<<<<<<#############");
		}
		
		panelTotMoyenne.add(panelGroupe);
		panelTotMoyenne.add(panelNote);
		
		moyenneG = new JLabel("Moyenne General :	");
		moyenneGr1 = new JLabel("Moyenne Groupe 1 :	");
		moyenneGr2 = new JLabel("Moyenne Groupe 2 :	");
		ecartType = new JLabel("Ecart-type :		");
		moyenneG2 = new JLabel("Moyenne General 	:");
		moyenneGr12 = new JLabel("Moyenne Groupe 1 	:");
		moyenneGr22 = new JLabel("Moyenne Groupe 2 	:");
		
		//TODO A remplacer par la fonction de note qui va arriver ( j'espere )
		this.notemoyenneG = new JLabel();
		this.notemoyenneG.setText(String.valueOf(daofiliere.getMoyenne(f, 2010)));
		this.notemoyenneGr1 = new JLabel();
		this.notemoyenneGr1.setText(null);
		this.notemoyenneGr2 = new JLabel();
		this.notemoyenneGr2.setText(null);
		this.noteEcartType = new JLabel();
		this.noteEcartType.setText(null);
		this.notemoyenneG2 = new JLabel();
		this.notemoyenneG2.setText(null);
		this.notemoyenneGr12 = new JLabel();
		this.notemoyenneGr12.setText(null);
		this.notemoyenneGr22 = new JLabel();
		this.notemoyenneGr22.setText(null);
		
		panelmoyenneG = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelmoyenneG.add(moyenneG);
		panelmoyenneG.add(this.notemoyenneG);
		
		panelmoyenneGr1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelmoyenneGr1.add(moyenneGr1);
		panelmoyenneGr1.add(this.notemoyenneGr1);
		
		panelmoyenneGr2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelmoyenneGr2.add(moyenneGr2);
		panelmoyenneGr2.add(this.notemoyenneGr2);
		
		panelmoyenneG2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelmoyenneG2.add(moyenneG2);
		panelmoyenneG2.add(this.notemoyenneG2);
		
		panelmoyenneGr12 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelmoyenneGr12.add(moyenneGr12);
		panelmoyenneGr12.add(this.notemoyenneGr12);
		
		panelmoyenneGr22 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelmoyenneGr22.add(moyenneGr22);
		panelmoyenneGr22.add(this.notemoyenneGr22);
		
		panelEcartType = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelEcartType.add(ecartType);
		panelEcartType.add(this.noteEcartType);
		
		panelGeneral = new JPanel(new GridLayout(2,2));
		panelGeneral.add(panelmoyenneG);
		panelGeneral.add(panelmoyenneGr1);
		panelGeneral.add(panelmoyenneGr2);
		panelGeneral.add(panelEcartType);
		
		listCours = new JList();
		listCours.setFixedCellWidth(100);
		scrollList = new JScrollPane(listCours);
		
		
		panelSpecD = new JPanel();
		panelSpecD.setLayout(new BoxLayout(panelSpecD, BoxLayout.Y_AXIS));
		panelSpecD.add(panelmoyenneG2);
		panelSpecD.add(panelmoyenneGr12);
		panelSpecD.add(panelmoyenneGr22);
		
		panelSpec = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollList, panelSpecD);
		panelSpec.setBorder(BorderFactory.createTitledBorder("Cours"));
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(panelTotMoyenne, BorderLayout.CENTER);
		//panel.add(panelSpec, BorderLayout.CENTER);
	}
	
	public JPanel getPanelPrincipal(){
		return this.panel;
	}
	
	public void setMoyenneG(int note){
		this.moyenneG.setText(String.valueOf(note));
	}
	
	public void setMoyenneGr1(int note){
		this.moyenneGr1.setText(String.valueOf(note));
	}
	
	public void setMoyenneGr2(int note){
		this.moyenneGr2.setText(String.valueOf(note));
	}
	
	public void setMoyenneG2(int note){
		this.moyenneG2.setText(String.valueOf(note));
	}
	
	public void setMoyenneG12(int note){
		this.moyenneGr12.setText(String.valueOf(note));
	}
	
	public void setMoyenneGr22(int note){
		this.moyenneGr22.setText(String.valueOf(note));
	}
}
