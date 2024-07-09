package fr.diginamic.hello.services;

import java.util.ArrayList;
import java.util.List;

//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import fr.diginamic.hello.departements.Departement;
import fr.diginamic.hello.departements.DepartementDAO;
import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
//import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

/**
 * Classe pour proposer les services d'acces à la base de données
 * chemin d'acces de la BDD: C://xampp/mysql/data/recensement
 */
@Service
@Transactional
public class DepartementServices {
	
	/**
	 * 
	 */
	
	@PersistenceContext
	EntityManager em;
	//@PersistenceContext
	//EntityTransaction transaction;
	DepartementDAO DDAO;
	
	
	//
	/**
	 * Constructeur utilisé pour accéder aux methodes de classe
	 */
	public DepartementServices() {
		System.out.println("cons de DepartementServices!");
	}
	
	
	
	/**
	 * Methode d'insertion d'un departement dans la base de données
	 */
	@Transactional
	public List<Departement> ajoutDepartement(String numero, String nom) {
		///transaction.begin();
		Departement dep = new Departement(numero, nom);
		/*
		if(em.isOpen()) {
			System.out.println(" em depServices: "+em.isOpen());
			em.persist(dep);
		}
		*/
		em.persist(dep);
		System.out.println(" numero: "+numero+"nom: "+nom);
		return DDAO.insertDepartement(dep);
	}
	
	/**
	 * Methode d'affichage des villes de la base de données
	 */
	@Transactional
	public List<Departement> afficherDepartement() {
		
		List<Departement> depListe = new ArrayList<>();		
		return DDAO.extractDepartement();
		
	
	}
	
	/**
	 * Methode d'ajout d'un departement en fin de la base de données
	 * 
	 */
	@Transactional
	public void ajouterDepartement(String numero, String nom) {
		Departement departement = new Departement();
		departement.setNumero(numero);
		departement.setNom(nom);
		System.out.println("ajouterdepartement VS numero: "+numero+" nom: "+nom);
	}
	/**
	 * Methode d'affichage d'une ville de la base de données
	 * Recherche par Nom
	 */
	@Transactional
	public Departement afficherDepartementNom(String nom) {
		TypedQuery<Departement> query = em.createQuery("Select d FROM Departement dep WHERE dep.nom = :nom",Departement.class);
		query.setParameter("nom",nom);
		return query.getSingleResult();
	}
	
	/**
	 * Methode d'affichage d'une ville de la base de données
	 * Recherche par Numero
	 */
	@Transactional
	public Departement afficherDepartementNumero(String numero) {
		TypedQuery<Departement> query = em.createQuery("Select d FROM Departement dep WHERE dep.numero = :numero",Departement.class);
		query.setParameter("numero",numero);
		return query.getSingleResult();
	}
	/**
	 * Methode d'affichage d'une ville de la base de données
	 * Recherche par Id
	 */
	@Transactional
	public Departement afficherDepartementId(int id) {
		TypedQuery<Departement> query = em.createQuery("Select d FROM Departement dep WHERE dep.id = :id",Departement.class);
		query.setParameter("id",id);
		return query.getSingleResult();
	}
	
	
	
	/**
	 * Methode d'insertion d'une ville de la base de données
	 * Recherche par Id, puis suppréssion des données
	 */
	@Transactional
	public List<Departement> supprimerDepartement(int id) {
		Query query2 = null;
		 TypedQuery<Departement> query = em.createQuery("SELECT dep FROM Departement dep WHERE dep.id = :id",Departement.class); 
		 query.setParameter("id",id);
		 Departement depSup = query.getSingleResult();
		 if(depSup!=null) {
			query2 = em.createQuery("DELETE FROM Departement dep WHERE dep.id= :id");
			query2.setParameter("id",id);
			query2.executeUpdate();
		 }
		 return query.getResultList();
	}
	
	public List<Departement> modifierDepartement(int id, String numero, String nom) {
		// TODO Auto-generated method stub
		Query query2;
		 TypedQuery<Departement> query = em.createQuery("SELECT dep FROM Departement dep WHERE dep.id = :id",Departement.class); 
		 query.setParameter("id",id);
		 Departement depModif = query.getSingleResult();
		if(depModif!=null) {
			query2 = em.createQuery("UPDATE Departement dep SET dep.numero= :newnom  WHERE dep.nom= :oldnumero");
			query2.setParameter("newnumero",numero);
			query2.setParameter("oldnumero",depModif.getNumero());
			query2.executeUpdate();
			query2 = em.createQuery("UPDATE Villes vil SET dep.nom= :newnom  WHERE dep.nom= :oldnom");
			query2.setParameter("newnom",nom);
			query2.setParameter("oldnom",depModif.getNom());
			query2.executeUpdate();
		}
		return query.getResultList();
	}


}
