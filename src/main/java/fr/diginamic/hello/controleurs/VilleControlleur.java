package fr.diginamic.hello.controleurs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.villes.Villes;
import fr.diginamic.hello.villes.VillesServices;//import de la classe VillesServices
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/ville")

public class VilleControlleur {

	VillesServices VS;//Pour utiliser les methodes de la classe
	Villes V;//pour utiliser le constructeur de Villes
	public List<Villes> liste = new ArrayList<>();//initialiser obligatoire
	@PersistenceContext
	//private EntityManager em;
	EntityManager em;
	//public EntityTransaction transaction;
	
	
	public VilleControlleur() {//constructeur pour classe
		
		//FAIRE AVEC BDD VIDE ET LISTE DE VILLES VIDE
		System.out.println("constructeur(BDD): "+liste.size());
		//**liste.add(new Villes("Nimes",150000));
		//**liste.add(new Villes("Montpellier",250000));
	}
	/**
	 * methode pour afficher la liste des villes
	 * @return liste (type List<Villes>)
	 */
	@Transactional
	@GetMapping("/afficher")//->OK
	public List<Villes> extractsVilles() {
		
			TypedQuery<Villes> query = em.createQuery("SELECT v FROM Villes v",Villes.class); 
			liste = query.getResultList();
			//System.out.println("rechercher ville liste : "+liste.size());
		return liste;
		
	}
	
	/**
	 * methode d'affichage d'un objet ville apres recherche par nom
	 * @param nom
	 * @return 1 objet ville si il éxiste
	 */
	@Transactional
	@GetMapping("/afficher/{nom}")
	public Villes afficherVilleNom(@PathVariable String nom){//PathVariable
		TypedQuery<Villes> query = em.createQuery("Select vil FROM Villes vil WHERE vil.nom = :nom",Villes.class); 
		query.setParameter("nom",nom);
		Villes vil = query.getSingleResult();
		return vil;
	}
	
	/**
	 * methode pour afficher un objet ville apres recherche par id
	 * @param id
	 * @return 1 objet ville
	 */
	@Transactional
	@GetMapping("/afficherId/{id}")
	public Villes afficherVilleId(@PathVariable int id){//PathVariable
		//Villes vil = new Villes();
		TypedQuery<Villes> query = em.createQuery("Select vil FROM Villes vil WHERE vil.id = :id",Villes.class); 
		query.setParameter("id",id);
		Villes vil = query.getSingleResult();
		return vil;
	}
	//
	/**
	 * methode pour ajouter un objet ville en base de donnée
	 * @param id type integer mais créé par la base de donnée(auto-increment)
	 * @param nom
	 * @param nbHabitants
	 * @return message de type ResponsesEntity (2 statuts: ok, badRequest)
	 */
	@Transactional
	@PostMapping("/ajouter/{nom},{nbHabitants}")//??{id}+@Valid @PathVariable int id, 
	public ResponseEntity<?> insererVille(@Valid @PathVariable String nom,@Valid @PathVariable int nbHabitants){
		 Villes copieVille = new Villes(nom, nbHabitants);//-> POSTMAN=OK,Navigateur!=OK
		 boolean res = true;
		 Villes villeLue = null;
		 for(Villes villes : liste) {
			 villeLue = villes;
			 if(copieVille.getNom().equals(villeLue.getNom())==true) {
				 res = false;
			 }
		 }
		 if(res == false) {
			 return ResponseEntity.badRequest().body("Cette ville éxiste déja!!");
		 }
		 else {
			  VS = new VillesServices();
			  liste.add(copieVille);//Ok pour postman OK web/BDD??
			  V = new Villes();
			  V.setNom(nom);
			  V.setNbHabitants(nbHabitants);
			  VS.ajouterVille(nom, nbHabitants);
			  if(em.isOpen()==true) {
				   em.persist(V);//OK
			  }
			  return ResponseEntity.ok("Ville inserée avec succès!!");
		 }
	}
	
	/**
	 * methode de modification d'objet villes
	 * Postman: OK
	 * Navigateur: OK
	 * @param id
	 * @param nom
	 * @param nbHabitants
	 * @return message de type ResponseEntity (2 statuts: ok, badRequest)
	 */
	@Transactional
	@PutMapping("/modifier/{id},{nom},{nbHabitants}")//->OK
	public ResponseEntity<?> modifierVille(@Valid @PathVariable int id, @Valid @PathVariable String nom,@Valid @PathVariable int nbHabitants){
		//ResponseEntity<?>
		//Villes copieVille = new Villes(nom,nbHabitants);//-> POSTMAN=OK,Navigateur!=OK
		 boolean res = false;
		TypedQuery<Villes> query = em.createQuery("SELECT vil FROM Villes vil WHERE vil.id = :id",Villes.class); 
		query.setParameter("id",id);
		Villes vilmodif = query.getSingleResult();
		if(vilmodif!=null) {
			Query query2 = em.createQuery("UPDATE Villes vil SET vil.nom= :newnom  WHERE vil.nom= :oldnom");
			query2.setParameter("newnom",nom);
			query2.setParameter("oldnom",vilmodif.getNom());
			query2.executeUpdate();
			query2 = em.createQuery("UPDATE Villes vil SET vil.nbHabitants= :newnb  WHERE vil.nbHabitants= :oldnb");
			query2.setParameter("newnb",nbHabitants);
			query2.setParameter("oldnb",vilmodif.getNbHabitants());
			query2.executeUpdate();
			res=true;
		}
		
		 if(res==true) {
			 return ResponseEntity.ok("Ville modifiée avec succès!!");
		 }
		 else {
			 return ResponseEntity.badRequest().body("Problème lors de la modification!!");
		 }
		 
	} 
	 
	/**
	 * methode de suppréssion d'une instance de Villes
	 * Postman = OK,
	 * Navigateur = OK
	 * @param id
	 * @return message de type ResponseEntity(2 statuts:ok, badRequest)
	 */
	@Transactional
	@DeleteMapping("/supprimer/{id}")
	public ResponseEntity<?> supprimerVille(@Valid @PathVariable int id){
		 boolean res = false;
		 TypedQuery<Villes> query = em.createQuery("SELECT vil FROM Villes vil WHERE vil.id = :id",Villes.class); 
		 query.setParameter("id",id);
		 Villes vilsup = query.getSingleResult();
		 if(vilsup!=null) {
			 Query query2 = em.createQuery("DELETE FROM Villes vil WHERE vil.id= :id");
			query2.setParameter("id",id);
			query2.executeUpdate();
			 res=true;
		 }
		 
		  if(res==true) {
			 return ResponseEntity.ok("Ville supprimée avec succès!!");
		 }
		 else {
			 return ResponseEntity.badRequest().body("Problème lors de la suppréssion!!");
		 }
	}
	
}
