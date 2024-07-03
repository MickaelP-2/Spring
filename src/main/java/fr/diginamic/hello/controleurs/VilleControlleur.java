package fr.diginamic.hello.controleurs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.villes.Villes;

@RestController
@RequestMapping("/ville")
public class VilleControlleur {

	List<Villes> liste = new ArrayList<>();//initialiser obligatoire
	
	public VilleControlleur() {//constructeur pour classe
		liste.add(new Villes("Nimes",150000));
		liste.add(new Villes("Montpellier",250000));
	}
	@GetMapping("/afficher")//->OK
	public List<Villes> afficherVilles() {
		//return helloserv.salutations();
		//return List.of(new Villes("Nimes",150000),new Villes("Montpellier",300000));
		return liste;
		
	}
	@GetMapping("/afficher/{nom}")
	public Villes afficherVilleNom(@PathVariable String nom){//PathVariable
		///String nom_cherche = nom;
		Villes nomVille = null;
		Villes vil = new Villes();
		for(Villes ville : liste) {
			 nomVille = ville;
			 //System.out.println("vil: "+nomVille.getNom()+" nom_cherche: "+nom);//nomVille=OK, nom=OK
			 if(nom.equals(nomVille.getNom().toString())==true) {
				vil.setNom(ville.getNom());
				vil.setNbHabitants(ville.getNbHabitants());
				//System.out.println("vil: "+vil.toString());
			 } 
		}
		return vil;
	}
	//
	
	@PostMapping("/ajouter/{nom},{nbHabitants}")//->OK
	public ResponseEntity<?> insererVille(@PathVariable String nom,@PathVariable int nbHabitants){
		 Villes ville = new Villes(nom,nbHabitants);//-> POSTMAN=OK,Navigateur!=OK
		 //liste.insererVille(new Villes("Orleans",110000));
		 //System.out.println(" ville: "+ville.toString());
		 boolean res = true;
		 Villes villeLue = null;
		 for(Villes villes : liste) {
			 villeLue = villes;
			 //System.out.println("vil: "+villeLue.getNom()+" nom_cherche: "+nom);//nomVille=OK, nom=OK
			 if(nom.equals(villeLue.getNom())==true) {
				 res = false;
			 }
		 }
		 if(res == false) {
			 return ResponseEntity.badRequest().body("Cette ville éxiste déja!!");
		 }
		 else {
			  liste.add(ville);
			  return ResponseEntity.ok("Ville inserée avec succès!!");
		 }
		//return ResponseEntity.ok("Ville inserée avec succès!!");
	}
	/*
	 @PostMapping("/ajouter")//->OK
	public ResponseEntity<?> insererVille(@RequestBody Villes ville){
		 //ville = new Villes("Orleans",110000);
		 //liste.insererVille(new Villes("Orleans",110000));
		 liste.add(ville);
		 
		//
		return ResponseEntity.ok("Ville inserée avec succès!!");
	} 
	 */
	//
	/*
	@PutMapping("/modifier")
	public ResponseEntity<?> modifierVille(@RequestBody Villes ville){
		
	}
	*/
}
