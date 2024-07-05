package fr.diginamic.hello.villes;


import java.util.Comparator;
//import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Classe Villes: utilisé pour le développement et utilisation de base de données.
 */
@Entity
@Table(name="Villes")
public class Villes {
	//
	/**
	 * attribut identifiant id de type int auto-incrementé
	 */
	//***************@Min(value=1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/**
	 * attribut nom de type String/varChar
	 * le nom de la ville
	 */
	@NotNull
	@Size(min = 2,max = 50)
	@Column(name="Nom", length=50, nullable=false)
	private String nom;
	
	/**
	 * attribut nbHabitants de type integer
	 * Le nombre d'habitants de la ville
	 */
	@Min(value=1)
	@Column(name="Habitants", length=50, nullable=false)
	private int nbHabitants;
	//
	/**
	 * Constructeur sans arguments utilisé par ibernate
	 */
	public Villes() {
		
	}
	
	/**
	 * Constructeur avec arguments utilisé pour le développement
	 * @param nom
	 * @param habitant
	 */
	public Villes(int id,String nom, int habitant) {
		super();
		this.id = id;
		this.nom = nom;
		this.nbHabitants = habitant;
		
	}
	/**
	 * Constructeur avec arguments utilisé pour la base de données
	 * @param nom
	 * @param habitant
	 */
	public Villes(String nom, int habitant) {
		//super();
		this.nom = nom;
		this.nbHabitants = habitant;
		
	}
	/**
	 * Accesseur de l'attribut id
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * mutateur de l'attribut id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * accesseur de l'attribut nom
	 * @return nom
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * mutateur de l'attribut nom
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * accesseur de l'attribut nbHabitants
	 * @return nbHabitants
	 */
	public int getNbHabitants() {
		return nbHabitants;
	}
	
	/**
	 * mutateur de l'attribut nbHabitants
	 * @param nbHabitants
	 */
	public void setNbHabitants(int nbHabitants) {
		this.nbHabitants = nbHabitants;
	}
	//////////////////////////////////////////////////////////////////////////
	/**
	 * Methode toString pour l'affichage d'un objet de type Ville
	 */
	@Override
	public String toString() {
		return "Villes [id=" + id + ", nom=" + nom + ", nbHabitants=" + nbHabitants + "]";
	}
	
	/**
	 * Methode de comparaison de villes pour un classement par population
	 */
	public static Comparator<Villes> comparateurHabitants = new Comparator<Villes>(){
		@Override
		public int compare(Villes v1, Villes v2) {
		// TODO Auto-generated method stub
		return  (int)(v2.getNbHabitants() - v1.getNbHabitants());
	}
	};
	
	/**
	 * Methode  de comparaison de villes pour un classement par ordre alphabetique
	 */
	public static Comparator<Villes> comparateurNom = new Comparator<Villes>(){
		@Override
		public int compare(Villes v1, Villes v2) {
		// TODO Auto-generated method stub
		return  v1.getNom().compareTo(v2.getNom());
	}
	};
	

}
