package fr.diginamic.hello.departements;

import java.util.List;

import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class DepartementDAO {
	//
	@PersistenceContext
	private EntityManager em;
	private List<Departement> depList;
	
	@Transactional
	public List<Departement> insertDepartement(Departement departement) {
			
			Departement D  = new Departement();
			  D.setNumero(departement.getNumero());
			  D.setNom(departement.getNom());
			  if(em.isOpen()==true) {
				   em.persist(D);//OK
			  }
			  TypedQuery<Departement> query = em.createQuery("Select dep FROM Departement dep",Departement.class);
			depList = query.getResultList();
			return depList;
	}
	
	@Transactional
	public List<Departement> extractDepartement() {
		TypedQuery<Departement> query = em.createQuery("Select d FROM Departement dep",Departement.class);
		depList = query.getResultList();
		return depList;
		
	}
	
	@Transactional
	public Departement extractDepartementId(Integer nb) {
		TypedQuery<Departement> query = em.createQuery("Select d FROM Departement dep WHERE dep.id = :id",Departement.class);
		query.setParameter("id", nb);
		Departement d = query.getSingleResult();
		return d;
		
	}
	
	@Transactional
	public List<Departement> extractDepartement(String nom) {
		TypedQuery<Departement> query = em.createQuery("Select d FROM Departement WHERE dep.nom = :nom",Departement.class);
		query.setParameter("nom",nom);
		return query.getResultList();
	}
	
	@Transactional
	public List<Departement> modifierDepartement(int id, Departement departementModifie) {
		TypedQuery<Departement> query = em.createQuery("Select d FROM Departement dep WHERE dep.id = :id",Departement.class);
		query.setParameter("id",id);
		Departement dep = new Departement();
		dep = query.getSingleResult();//Pour initialiser le resultat obtenu de la recherche
		query = em.createQuery("UPDATE Departement d SET d.nom = :newnom WHERE d.nom = :oldnom",Departement.class);
		query.setParameter("newnom",departementModifie.getNom());
		query.setParameter("oldnom",dep.getNom());
		query.executeUpdate();
		query = em.createQuery("UPDATE Departementd SET d.numero = :newnb WHERE d.numero = :oldnumero",Departement.class);
		query.setParameter("newnbHabitants",departementModifie.getNumero());
		query.setParameter("oldnbHabitants",dep.getNumero());
		query.executeUpdate();
		query = em.createQuery("Select d FROM Departement dep",Departement.class);
		depList = query.getResultList();
		return depList;
	}
	
	@Transactional
	public List<Departement> suprimerDepartement(Integer id) {
		TypedQuery<Departement> query = em.createQuery("DELETE FROM Departement dep WHERE dep.id= :id",Departement.class);
		query.setParameter("id",id);
		query.executeUpdate();
		query = em.createQuery("Select d FROM Departement dep",Departement.class);
		depList = query.getResultList();
		return depList;
	}

}
