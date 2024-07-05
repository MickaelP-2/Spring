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
	
	@Transactional
	public void ajouterVille(Villes ville) {
		em.persist(em);
	}
	
	@Transactional
	public List<Villes> extraireVille(Villes ville) {
		TypedQuery<Villes> query = em.createQuery("Select v FROM Villes",Villes.class);
		return query.getResultList();
		
	}
	
}
