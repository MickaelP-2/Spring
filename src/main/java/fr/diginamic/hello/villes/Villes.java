package fr.diginamic.hello.villes;


import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



public class Villes {
	//
	@Min(value=1)
	private int id;
	
	@NotNull
	@Size(min = 2,max = 50)
	private String nom;
	@Min(value=1)
	private int nbHabitants;
	//
	/**
	 * 
	 */
	public Villes() {
		
	}
	/**
	 * 
	 * @param nom
	 * @param habitant
	 */
	public Villes(int id, String nom, int habitant) {
		super();
		this.id = id;
		this.nom = nom;
		this.nbHabitants = habitant;
		
	}
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "Villes [id=" + id + ", nom=" + nom + ", nbHabitants=" + nbHabitants + "]";
	}
	
	
	

}
