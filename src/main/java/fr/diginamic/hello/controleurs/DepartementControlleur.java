package fr.diginamic.hello.controleurs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.departements.Departement;
import fr.diginamic.hello.services.DepartementServices;
import fr.diginamic.hello.villes.Villes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
//import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/departement")
public class DepartementControlleur {
//
	public List<Departement> depListe = new ArrayList<>();
	@PersistenceContext
	EntityManager em;
	DepartementServices DS;
	Departement D;
	Villes ville;
	List<Departement> depList;
	//
	/**
	 * Constructeur sans arguments utilisé par JPA/Hibernate
	 */
	public DepartementControlleur() {
		System.out.println("constructeur de departementControlleur "+depListe.size());
		//DS = new DepartementServices();//POur utilsier les methodes de DepartementServices()
	} 
	//
	@Transactional
	@GetMapping("/afficher")
	public List<Departement> extractsDepartement(){
		TypedQuery<Departement> query = em.createQuery("SELECT d FROM Departement d",Departement.class); 
		depListe = query.getResultList();
		return depListe;
	}
	//
	@Transactional
	@GetMapping("/afficherNom/{nom}")
	public Departement extractsDepartementNom(@PathVariable String nom){
		TypedQuery<Departement> query = em.createQuery("Select dep FROM Departements dep WHERE dep.nom = :nom",Departement.class); 
		query.setParameter("nom",nom);
		Departement dep = query.getSingleResult();
		return dep;
	}
	//
	@Transactional
	@GetMapping("/afficherId/{id}")
	public Departement extractsDepartementId(@PathVariable int id){
		TypedQuery<Departement> query = em.createQuery("Select dep FROM Departement dep WHERE dep.id = :id",Departement.class); 
		query.setParameter("id",id);
		Departement dep = query.getSingleResult();
		return dep;
	}
	//
	@Transactional
	@GetMapping("/afficherNum/{numero}")
	public Departement extractsDepartementNumero(@PathVariable String numero){
		TypedQuery<Departement> query = em.createQuery("Select dep FROM Departementy dep WHERE dep.numero = :numero",Departement.class); 
		query.setParameter("numero",numero);
		Departement dep = query.getSingleResult();
		return dep;
	}
	//
	
	@Transactional
	@PostMapping("/ajouter/{numero},{nom}")//??{id}+@Valid @PathVariable int id, 
	public ResponseEntity<?> insererDepartement(@Valid @PathVariable String numero,@Valid @PathVariable String nom){
		 Departement copieDep = new Departement(numero,nom);//-> POSTMAN=OK,Navigateur!=OK
		 boolean res = true;
		 Departement departementLue = null;
		 for(Departement departement : depListe) {
			 departementLue = departement;
			 if(copieDep.getNumero().equals(departementLue.getNumero())==true) {
				 res = false;
			 }
		 }
		 if(res == false) {
			 return ResponseEntity.badRequest().body("Ce département éxiste déja!!");
		 }
		 else {
			  DS = new DepartementServices();
			  depListe.add(copieDep);//Ok pour postman OK web/BDD??
			  D = new Departement();
			  D.setNumero(numero);
			  D.setNom(nom);
			  DS.ajouterDepartement(numero, nom);
			  if(em.isOpen()==true) {
				   em.persist(D);//OK
			  }
			  return ResponseEntity.ok("Departement inseré avec succès!!");
		 }
	}
	//
	@Transactional
	@PutMapping("/modifier/{id},{numero},{nom}")//??{id}+@Valid @PathVariable int id, 
	public ResponseEntity<?> modifierDepartement(@Valid @PathVariable int id, @Valid @PathVariable String numero, @Valid @PathVariable String nom){
		  boolean res = false;
		TypedQuery<Departement> query = em.createQuery("SELECT dep FROM Departement dep WHERE dep.id = :id",Departement.class); 
		query.setParameter("id",id);
		Departement depmodif = query.getSingleResult();
		if(depmodif!=null) {
			Query query2 = em.createQuery("UPDATE Departement dep SET dep.numero= :newnumero  WHERE dep.numero= :oldnumero");
			query2.setParameter("newnumero",numero);
			query2.setParameter("oldnumero",depmodif.getNumero());
			query2.executeUpdate();
			query2 = em.createQuery("UPDATE Departement dep SET dep.nom= :newnom  WHERE dep.nom= :oldnom");
			query2.setParameter("newnom",nom);
			query2.setParameter("oldnom",depmodif.getNom());
			query2.executeUpdate();
			res=true;
		}
		 if(res==true) {
			 return ResponseEntity.ok("Département modifié avec succès!!");
		 }
		 else {
			 return ResponseEntity.badRequest().body("Problème lors de la modification!!");
		 }
	}
	//
	@Transactional
	@DeleteMapping("/supprimer/{id}")//??{id}+@Valid @PathVariable int id, 
	public ResponseEntity<?> supprimerDepartement(@Valid @PathVariable int id){
		  boolean res = false;
		 TypedQuery<Departement> query = em.createQuery("SELECT dep FROM Departement dep WHERE dep.id = :id",Departement.class); 
		 query.setParameter("id",id);
		 Departement depsup = query.getSingleResult();
		 if(depsup!=null) {
			 Query query2 = em.createQuery("DELETE FROM Departement dep WHERE dep.id= :id");
			query2.setParameter("id",id);
			query2.executeUpdate();
			 res=true;
		 }
		  if(res==true) {
			 return ResponseEntity.ok("Département supprimé avec succès!!");
		 }
		 else {
			 return ResponseEntity.badRequest().body("Problème lors de la suppréssion!!");
		 } 
	}
	//
	//Methodes pour gerer les villes de la liste des villes du département
	
	@Transactional
	@PostMapping("/ajouterVille/{id},{nom},{nbHabitants}")
	public ResponseEntity<?> insererVille(@Valid @PathVariable int id,@Valid @PathVariable String nom,@Valid @PathVariable int nbHabitants){
		Villes ville = new Villes(nom,nbHabitants);
		boolean res = false, res2=true;
		TypedQuery<Departement> query = em.createQuery("SELECT dep FROM Departement dep WHERE dep.id = :id",Departement.class); 
		query.setParameter("id",id);
		Departement depVille = query.getSingleResult();
		if(depVille.getVilleList().size()==0) {
			if(em.isOpen()==true) {
				depVille.villeList.add(ville);//conserve l'id des villes
				em.persist(ville);
				res=true;
			}
		}
		else if(depVille.getVilleList().size()>0) {
			 Villes villeLue = null;
			 for(Villes villes : depVille.villeList) {
			 villeLue = villes;
			 	if(ville.getNom().equals(villeLue.getNom())==true) {
			 		res2 = false;
			 	}
			 }//fin for()
			 if(res2==false) {
				 res=false;
			 }
			 else if(res2==true) {
				 depVille.villeList.add(ville);//conserve l'id des villes
				 em.persist(ville);
				 res=true;
			 }
		}
		
		if(res==true) {
			 return ResponseEntity.ok("Ville insérée au département avec succès!!");
		 }
		 else {
			 if(res2==false) {
				 return ResponseEntity.badRequest().body("Ville déja présente!!");
			 }
			 else return ResponseEntity.badRequest().body("Problème lors de l'ajout de la ville!!");
		 } 
	}
	//
	@Transactional
	@GetMapping("/afficherVilleDep/{id}")
	public List<Villes> afficherVille(@Valid @PathVariable int id){
		TypedQuery<Departement> query = em.createQuery("SELECT dep FROM Departement dep WHERE dep.id = :id",Departement.class); 
		query.setParameter("id",id);
		Departement depVille = query.getSingleResult();
		//OK mais utilise l'id des  villes et ajoute la ville dans la table ville
		if(depVille.getVilleList().equals(null)) {
			return null;
		}
		else {
			return depVille.getVilleList();
		}
	}
	//
	@Transactional
	@PutMapping("/modifierVilleDep/{id},{idVille},{nomVille},{nbHabitants}")
	public List<Villes> modifierVilleDepartement(@Valid @PathVariable int id,@Valid @PathVariable int idVille, @Valid @PathVariable String nomVille, @Valid @PathVariable int nbHabitants){
		TypedQuery<Departement> query = em.createQuery("SELECT dep FROM Departement dep WHERE dep.id = :id",Departement.class); 
		query.setParameter("id",id);
		Departement depVille = query.getSingleResult();
		if(depVille.getVilleList().equals(null)) {
			return null;
		}
		else {
			if(depVille.getVilleList().equals(null)==false) {
				 Villes villeLue = null;
				 	for(Villes villes : depVille.villeList) {
				 			villeLue = villes;
				 			if(idVille == villeLue.getId()) {
				 				villeLue.setNom(nomVille);
				 				villeLue.setNbHabitants(nbHabitants);
				 				em.merge(villeLue);
				 			}
				 	}//fin for()
			}
			return depVille.getVilleList();
		}
	}
	//
	
	/*
	@Transactional
	@DeleteMapping("/supprimerVilleDep/{id},{idVille}")//??{id}+@Valid @PathVariable int id, 
	public List<Villes> supprimerVilleDepartement(@Valid @PathVariable int id, @Valid @PathVariable int idVille){
		  TypedQuery<Departement> query = em.createQuery("SELECT dep FROM Departement dep WHERE dep.id = :id",Departement.class); 
		query.setParameter("id",id);
		Departement dep2 = query.getSingleResult();
		Departement depVille = query.getSingleResult();
		
		List<Villes> vilsup = null;
		if(depVille.getVilleList().equals(null)) {
			return null;
		}
		else {
			if(depVille.getVilleList().equals(null)==false) {
				
				 Villes villeLue = null;
				 	for(Villes villes : depVille.villeList) {
				 			villeLue = villes;
				 			System.out.println("villeLue: "+villeLue.getId()+" idVille: "+idVille);
				 			//ACTION REFUSEE CAR CLE SECONDAIRE DANS DEPARTEMENT_VILLE_LIST
				 			if(idVille == villeLue.getId()) {
				 				em.remove(villeLue);//Contrainte d'integritée!!
				 				em.persist(depVille);
				 			}	
				 	}//fin for()
			}//fin if()
		}//fin else 
		return depVille.getVilleList();  
	}
	*/
	//
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
