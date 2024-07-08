package fr.diginamic.hello.villes;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class VilleDao {
	//
	@PersistenceContext
	private EntityManager em;
	private List<Villes> list;
	
	@Transactional
	public List<Villes> insertVille(Villes ville) {
			Villes V = new Villes();
			  V.setNom(ville.getNom());
			  V.setNbHabitants(ville.getNbHabitants());
			  if(em.isOpen()==true) {
				   em.persist(V);//OK
			  }
			TypedQuery<Villes> query = em.createQuery("Select v FROM Villes vil",Villes.class);
			list = query.getResultList();
			return list;
	}
	
	@Transactional
	public List<Villes> extractVille() {
		TypedQuery<Villes> query = em.createQuery("Select v FROM Villes vil",Villes.class);
		list = query.getResultList();
		return list;
		
	}
	
	@Transactional
	public Villes extractVilleId(Integer nb) {
		TypedQuery<Villes> query = em.createQuery("Select v FROM Villes vil WHERE vil.id = :id",Villes.class);
		query.setParameter("id", nb);
		Villes v = query.getSingleResult();
		return v;
		
	}
	
	@Transactional
	public List<Villes> extractVille(String nom) {
		TypedQuery<Villes> query = em.createQuery("Select v FROM Villes vil WHERE vil.nom = :nom",Villes.class);
		query.setParameter("nom",nom);
		return query.getResultList();
	}
	
	@Transactional
	public List<Villes> modifierVille(int id, Villes villeModifiee) {
		TypedQuery<Villes> query = em.createQuery("Select v FROM Villes vil WHERE vil.id = :id",Villes.class);
		query.setParameter("id",id);
		Villes v = new Villes();
		v = query.getSingleResult();//Pour initialiser le resultat obtenu de la recherche
		query = em.createQuery("UPDATE Villes v SET v.nom = :newnom WHERE v.nom = :oldnom",Villes.class);
		query.setParameter("newnom",villeModifiee.getNom());
		query.setParameter("oldnom",v.getNom());
		query.executeUpdate();
		query = em.createQuery("UPDATE Villes v SET v.nbHabitants = :newnb WHERE v.nbHabitants = :oldnbHabitants",Villes.class);
		query.setParameter("newnbHabitants",villeModifiee.getNbHabitants());
		query.setParameter("oldnbHabitants",v.getNbHabitants());
		query.executeUpdate();
		query = em.createQuery("Select v FROM Villes vil",Villes.class);
		list = query.getResultList();
		return list;
		
	}
	
	@Transactional
	public List<Villes> suprimerVille(Integer id) {
		TypedQuery<Villes> query = em.createQuery("DELETE FROM Results rs WHERE rs.id= :id",Villes.class);
		query.setParameter("id",id);
		query.executeUpdate();
		query = em.createQuery("Select v FROM Villes vil",Villes.class);
		list = query.getResultList();
		return list;
	}

}
