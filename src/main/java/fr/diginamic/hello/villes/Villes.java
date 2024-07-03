package fr.diginamic.hello.villes;


import java.util.List;

public class Villes {
	//
	private String nom;
	private int nbHabitants;
	//
	public Villes() {
		
	}
	public Villes(String nom, int habitant) {
		this.nom = nom;
		this.nbHabitants = habitant;
		
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getNbHabitants() {
		return nbHabitants;
	}
	public void setNbHabitants(int nbHabitants) {
		this.nbHabitants = nbHabitants;
	}
	//////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return "Villes [nom=" + nom + ", nbHabitants=" + nbHabitants + "]";
	}
	

}
