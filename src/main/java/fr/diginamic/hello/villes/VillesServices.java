package fr.diginamic.hello.villes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.diginamic.hello.controleurs.VilleControlleur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
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
public class VillesServices {
	
	/**
	 * 
	 */
	
	@PersistenceContext
	EntityManager em;
	//@PersistenceContext
	EntityTransaction transaction;
	VilleDao VDAO;
	
	//
	/**
	 * Constructeur
	 */
	public VillesServices() {
		//entityManagerFactory = Persistence.createEntityManagerFactory("");//???nom = config1
		//em = EntityManagerFactory.createEntityManager();
		//transaction = em.getTransaction();
		System.out.println("cons de VillesServices!");
	}
	
	/*
	@Transactional
	public void OuvrirConnexion() {
		//Ouverture de la transaction pour le contexte de persistence
		//transaction.begin();//ouverture de la transaction
		
		/*
		System.out.println("ouvrir connexion!!");
		if(em.isOpen()) {
			System.out.println("???isOpen: "+em.isOpen());
		}
		else System.out.println("em: "+em.toString());
		
	}
	
	public void FermerConnexion() {
		 
		//Ouverture de la transaction pour le contexte de persistence
		//*****transaction.commit();//action de la transaction
		em.close();//Pour fermer le flux entityManager
		//
		System.out.println("fermer connexion!!");
	}
	*/
	
	/**
	 * Methode d'insertion d'une ville dans la base de données
	 */
	@Transactional
	public void ajoutVille( String nom,int nbHabitants) {
		///transaction.begin();
		Villes ville = new Villes( nom, nbHabitants);
		//ville.setId(id);
		//ville.setNom(nom);
		//ville.setNbHabitants(nbHabitants);
		em.persist(ville);
		///transaction.commit();
		System.out.println("nom: "+nom+" nb: "+nbHabitants);
	}
	
	/**
	 * Methode d'affichage des villes de la base de données
	 */
	@Transactional
	public List<Villes> afficherVille() {
		
		List<Villes> liste = new ArrayList<>();
		/*
		TypedQuery query = em.createQuery("SELECT v FROM Villes",Villes.class); 
		liste = query.getResultList();
		//em.persist(liste);
		*/
		
			return VDAO.extractVille();
		
	
	}
	
	/**
	 * Methode d'ajout d'une ville en fin de la base de données
	 * 
	 */
	@Transactional
	public void ajouterVille(String nom, int nbHabitants) {
		//***transaction.begin();
		Villes ville = new Villes();
		//ville.setId(id);
		ville.setNom(nom);
		ville.setNbHabitants(nbHabitants);
		//***em.persist(ville);
		//***transaction.commit();
		System.out.println("ajouterVille VS nom: "+nom+" nb: "+nbHabitants);
	}
	/**
	 * Methode d'affichage d'une ville de la base de données
	 * Recherche par Nom
	 */
	@Transactional
	public void afficherVilleNom() {
		
	}
	
	/**
	 * Methode d'affichage d'une ville de la base de données
	 * Recherche par Id
	 */
	@Transactional
	public void afficherVilleId() {
		
	}
	
	/**
	 * Methode d'insertion d'une ville de la base de données
	 * Recherche par Id, puis modification des données
	 */
	@Transactional
	public void insererVilleId() {
		
	}
	/**
	 * Methode d'insertion d'une ville de la base de données
	 * Recherche par Nom, puis modification des données
	 */
	@Transactional
	public void insererVilleNom() {
		
	}
	
	/**
	 * Methode d'insertion d'une ville de la base de données
	 * Recherche par Id, puis suppréssion des données
	 */
	@Transactional
	public void supprimerVilleId() {
		
	}

	
	public List<Villes> insertVille(String nom, int nbHabitants) {
		// TODO Auto-generated method stub
		List<Villes> list = null;
		Villes ville = new Villes();
		ville.setNom(nom);
		ville.setNbHabitants(nbHabitants);
		System.out.println("ajouterVille VS nom: "+nom+" nb: "+nbHabitants);
		return list;
	}


}
