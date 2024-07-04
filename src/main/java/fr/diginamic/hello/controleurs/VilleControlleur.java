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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ville")
public class VilleControlleur {

	public List<Villes> liste = new ArrayList<>();//initialiser obligatoire
	
	
	public VilleControlleur() {//constructeur pour classe
		liste.add(new Villes(1,"Nimes",150000));
		liste.add(new Villes(2,"Montpellier",250000));
	}
	@GetMapping("/afficher")//->OK
	public List<Villes> afficherVilles() {
		return liste;
		
	}
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
	@PostMapping("/ajouter/{id},{nom},{nbHabitants}")
	public ResponseEntity<?> insererVille(@Valid @PathVariable int id,@Valid @PathVariable String nom,@Valid @PathVariable int nbHabitants){
		 Villes copieVille = new Villes(id,nom,nbHabitants);//-> POSTMAN=OK,Navigateur!=OK
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
			  liste.add(copieVille);
			  return ResponseEntity.ok("Ville inserée avec succès!!");
		 }
	}
	
	 @PutMapping("/modifier/{id},{nom},{nbHabitants}")//->OK
	public ResponseEntity<?> modifierVille(@Valid @PathVariable int id,@Valid @PathVariable String nom,@Valid @PathVariable int nbHabitants){
		Villes copieVille = new Villes(id,nom,nbHabitants);//-> POSTMAN=OK,Navigateur!=OK
		 boolean res = false;
		 Villes villeLue = null;
		 for(Villes villes : liste) {
			 villeLue = villes;
			 if(copieVille.getId() == villeLue.getId()) {
				 copieVille.setId(id);
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
