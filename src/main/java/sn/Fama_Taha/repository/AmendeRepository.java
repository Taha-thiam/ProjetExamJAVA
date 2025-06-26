package sn.Fama_Taha.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import sn.Fama_Taha.entity.Amende;

import java.util.List;

public class AmendeRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public void save(Amende amende) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(amende);
        em.getTransaction().commit();
        em.close();
    }

    public Amende findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Amende amende = em.find(Amende.class, id);
        em.close();
        return amende;
    }

    public List<Amende> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Amende> list = em.createQuery("SELECT a FROM Amende a", Amende.class).getResultList();
        em.close();
        return list;
    }

    public void update(Amende amende) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(amende);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Amende amende) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Amende toRemove = em.merge(amende);
        em.remove(toRemove);
        em.getTransaction().commit();
        em.close();
    }
}