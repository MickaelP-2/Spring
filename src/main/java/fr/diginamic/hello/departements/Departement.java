package fr.diginamic.hello.departements;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fr.diginamic.hello.villes.Villes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Departement")
public class Departement {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="Numero", length=10,nullable=false)
	private String numero;//cas Corse 2A,2B, sinon conversion en type Integer
	@Column(name="Nom", length=50, nullable=false)
	private String nom;
	@OneToMany
	public List<Villes> villeList = new ArrayList<Villes>();//Liste des villes du departement
	//
	public Departement() {
		
	}
	//
	public Departement(String numero,String nom, Villes ville) {
		//id genere par mysql
		super();
		this.numero = numero;
		this.nom = nom;		
	}
	//
	public Departement(String numero,String nom) {
		//id genere par mysql
		this.numero = numero;
		this.nom = nom;
	}
	//
	public Departement(int id, String numero,String nom, List<Villes> ville) {
		//id genere par mysql
		super();
		this.id = id;
		this.numero = numero;
		this.nom = nom;	
		this.villeList = ville;
	}
	//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public List<Villes> getVilleList() {
		return villeList;
	}
	public void setVilleList(List<Villes> villeList) {
		this.villeList = villeList;
	}
	//
	@Override
	public String toString() {
		return "Departement [id=" + id + ", numero=" + numero + ", nom=" + nom + ", villeList="
				+ villeList + "]";
	}
	//
	
	
	public static Comparator<Departement> comparateurNom = new Comparator<Departement>(){
		@Override
		public int compare(Departement d1, Departement d2) {
		// TODO Auto-generated method stub
		return  d1.getNom().compareTo(d2.getNom());
	}
	};
	
}
