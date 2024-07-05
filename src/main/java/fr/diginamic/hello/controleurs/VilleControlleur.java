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
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/ville")

public class VilleControlleur {

	VillesServices VS;//Pour utiliser les methodes de la classe
	Villes V;//pour utiliser le constructeur de Villes
	public List<Villes> liste = new ArrayList<>();//initialiser obligatoire
	@PersistenceContext
	private EntityManager em;
	
	//public EntityTransaction transaction;
	
	
	public VilleControlleur() {//constructeur pour classe
		
		//FAIRE AVEC BDD VIDE ET LISTE DE VILLES VIDE
		liste.add(new Villes("Nimes",150000));
		liste.add(new Villes("Montpellier",250000));
	}
	@Transactional
	@GetMapping("/afficher")//->OK
	public List<Villes> afficherVilles() {
		//**VS = new VillesServices();
		//VS.OuvrirConnexion();
		//VS.afficherVille();
		return liste;
		
	}
	
	@Transactional
	@GetMapping("/afficher/{nom}")
	public Villes afficherVilleNom(@PathVariable String nom){//PathVariable
		Villes nomVille = null;
		Villes vil = new Villes();
		for(Villes ville : liste) {
			 nomVille = ville;
			 if(nom.equals(nomVille.getNom().toString())==true) {
				vil.setNom(ville.getNom());
				vil.setNbHabitants(ville.getNbHabitants());
			 } 
		}
		return vil;
	}
	
	@Transactional
	@GetMapping("/afficherId/{id}")
	public Villes afficherVilleId(@PathVariable int id){//PathVariable
		Villes nomVille = null;
		Villes vil = new Villes();
		for(Villes ville : liste) {
			 nomVille = ville;
			 if(id == nomVille.getId()) {
				 vil.setId(ville.getId());
				vil.setNom(ville.getNom());
				vil.setNbHabitants(ville.getNbHabitants());
			 } 
		}
		return vil;
	}
	//
	@Transactional
	@PostMapping("/ajouter/{id},{nom},{nbHabitants}")//??{id}
	public ResponseEntity<?> insererVille(@Valid @PathVariable int id, @Valid @PathVariable String nom,@Valid @PathVariable int nbHabitants){
		 Villes copieVille = new Villes(id, nom, nbHabitants);//-> POSTMAN=OK,Navigateur!=OK
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
	
	@Transactional
	@PutMapping("/modifier/{id},{nom},{nbHabitants}")//->OK
	public ResponseEntity<?> modifierVille(@Valid @PathVariable int id, @Valid @PathVariable String nom,@Valid @PathVariable int nbHabitants){
		Villes copieVille = new Villes(nom,nbHabitants);//-> POSTMAN=OK,Navigateur!=OK
		 boolean res = false;
		 Villes villeLue = null;
		 for(Villes villes : liste) {
			 villeLue = villes;
			 if(copieVille.getId() == villeLue.getId()) {
				 //copieVille.setId(id);
				 copieVille.setNom(nom);
				 copieVille.setNbHabitants(nbHabitants);
				 liste.set((id-1),copieVille);
				 res=true;
			 }
		 }
		 if(res==true) {
			 return ResponseEntity.ok("Ville modifiée avec succès!!");
		 }
		 else{
			 return ResponseEntity.badRequest().body("Problème lors de la modification!!");
		 }
	} 
	 
	@Transactional
	@DeleteMapping("/supprimer/{id}")
	public String supprimerVille(@Valid @PathVariable int id){
		 boolean res = false;
		 String resultat = null;
		 Villes villeLue = null;
		 for(Villes villes : liste) {
			 villeLue = villes;
			 if(id == villeLue.getId()) {
				 liste.remove((id-1));//retrait dans List
				 res=true;
				 break;
			 }
		 }
		 if(res==false) {
			 resultat = "Problème lors de la suppréssion!!";
		 }
		 else if(res==true){
			  resultat = "Ville supprimée avec succès!!";
		 }
		 return resultat;
	}
	
}
