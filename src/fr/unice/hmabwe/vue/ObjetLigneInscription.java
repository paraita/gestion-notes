package fr.unice.hmabwe.vue;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;


import fr.unice.hmabwe.controleur.bd.dao.DaoCours;
import fr.unice.hmabwe.controleur.bd.dao.DaoException;
import fr.unice.hmabwe.controleur.bd.dao.DaoFabrique;
import fr.unice.hmabwe.modele.Cours;

/**
 * 
 * @author M'RAH Mehdi
 * 
 * Une ligne d'inscription c-a-d nom du cours, annee, note
 *
 */

public class ObjetLigneInscription extends JPanel{
	private JLabel nomCours;
	private JLabel annee;
	private JLabel note;
	public JComboBox comboCours;
	public JTextField textAnnee;
	public JTextField textNote;
	public JPanel panelLigne;
	private JPanel panelBoutonV, panelBoutonN, panelField;
	private JButton boutonMoins, boutonPlus, boutonValide;
	
	private DefaultComboBoxModel comboModel;
	public Collection<Cours> listCours = new ArrayList<Cours>();
	public DaoCours dc;
	public DaoFabrique df;
	public PanelAjoutEleve f;
	public EcouteurObjetLigne l;
	public boolean isNewLine;
	public int numLigne;
	public FenetreAjoutEleve fae;
	public ObjetLigneInscription(){
		
	}
	public ObjetLigneInscription(boolean newline, PanelAjoutEleve f, DaoFabrique df, FenetreAjoutEleve fae){
		// TODO Gestion d'ajout de ligne 
		// TODO Ajout d'un bonton a la suite de la ligne pour valider ligne et ajouter une autre ligne
		this.fae = fae;
		this.df = df;
		dc = this.df.getDaoCours();
		l = new EcouteurObjetLigne(this);
		this.f = f;
		
		
		panelLigne = new JPanel();
		
		nomCours = new JLabel("Cours");
		annee = new JLabel("Année");
		note= new JLabel("Note");
		
		textAnnee = new JTextField();
		textNote = new JTextField();
		this.isNewLine = newline;
		
		comboModel = new DefaultComboBoxModel();
		try {
			listCours = dc.findAll();
			for(Cours c : listCours){
				comboModel.addElement(c);
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		comboCours = new JComboBox(comboModel);
		panelLigne.setLayout(new FlowLayout());
		
		comboCours.setMaximumSize(new Dimension(30, 30));
		textAnnee.setPreferredSize(new Dimension(50,25));
		textAnnee.setColumns(4);
		textNote.setMaximumSize(new Dimension(50, 25));
		textNote.setColumns(2);
		
		panelField = new JPanel(new FlowLayout());
		panelField.add(nomCours);
		panelField.add(comboCours);
		panelField.add(annee);
		panelField.add(textAnnee);
		panelField.add(note);
		panelField.add(textNote);
		
		boutonMoins = new JButton((new ImageIcon(this.getClass().getResource("/resource/minus-circle.png"))));
		boutonPlus =  new JButton((new ImageIcon(this.getClass().getResource("/resource/plus.png"))));
		this.boutonPlus.addMouseListener(l);
		this.boutonMoins.addMouseListener(l);
		boutonValide =  new JButton((new ImageIcon(this.getClass().getResource("/resource/tick-circle.png"))));
		
		panelBoutonV = new JPanel(new FlowLayout());
		panelBoutonV.add(boutonMoins);
		panelBoutonV.add(boutonValide);
		
		panelBoutonN = new JPanel(new FlowLayout());
		panelBoutonN.add(boutonPlus);
		
		
		
		if(!this.isNewLine){
			panelLigne.add(panelField);
			panelLigne.add(panelBoutonV);
			numLigne = f.nbLigneInscription;
		}
		else{
			panelLigne.add(panelField);
			panelLigne.add(panelBoutonN);
			numLigne = f.nbLigneInscription + 1;
			
		}
		
		
		
	}
	
	public void setIsNew(boolean nouveau){
		this.isNewLine = nouveau;
	}
	
	public Cours getCoursSelected(){
		return (Cours) this.comboCours.getSelectedItem();
	}
	
	public Integer getAnnee(){
		return Integer.parseInt(this.textAnnee.getText());
	}
	
	public Double getMoyenne(){
		return Double.parseDouble(this.textNote.getText());
	}
	
	public void supprimeLigneInscription(ObjetLigneInscription obj1){
		this.f.lignePanel.remove(obj1.panelLigne);
		this.f.listeLigne.remove(obj1);
		this.f.lignePanel.validate();
		this.f.validate();
		
	}
	public void ajoutLigneInscription(){
    	ObjetLigneInscription o1 = new ObjetLigneInscription(false, this.f, df, fae);
    	ObjetLigneInscription o = new ObjetLigneInscription(true, this.f, df, fae);
    	this.f.listeLigne.remove(this.f.listeLigne.size() - 1);
    	this.f.lignePanel.remove(this.panelLigne);
    	this.f.listeLigne.add(o1);
    	this.f.lignePanel.add(o1.panelLigne);
    	this.f.lignePanel.validate();
    	System.out.println("Ligne numero :" + o1.numLigne + " est ajouté a la liste de la fenetre " + this.f);
    	
    	this.f.listeLigne.add(o);
    	this.f.lignePanel.add(o.panelLigne);
    	this.f.lignePanel.validate();
    	System.out.println("La nouvelle ligne numero :" + o.numLigne + " est ajouté a la liste de la fenetre " + this.f);
    	this.f.panel.validate();
    	this.f.nbLigneInscription++;
    }
	 class EcouteurObjetLigne implements MouseListener{
		 public FenetreAjoutEleve fae;
		 public ObjetLigneInscription ob;
		 public EcouteurObjetLigne(ObjetLigneInscription ob){
				this.ob = ob;
		 }
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Object boutSelected = arg0.getSource();
				if(boutSelected.equals(boutonPlus)){
					ajoutLigneInscription();
					
				}
				else{
					if(boutSelected.equals(boutonMoins)){
						supprimeLigneInscription(ob);
					}
				}
				
				
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}


}
