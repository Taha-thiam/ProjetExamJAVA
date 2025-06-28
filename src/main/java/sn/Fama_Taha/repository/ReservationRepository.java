package sn.Fama_Taha.repository;

import sn.Fama_Taha.entity.Reservation;
import jakarta.persistence.*;
import java.util.List;

public class ReservationRepository {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public List<Reservation> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Reservation> list = em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
        em.close();
        return list;
    }

    public Reservation findById(String id) {
        EntityManager em = emf.createEntityManager();
        Reservation reservation = em.find(Reservation.class, id);
        em.close();
        return reservation;
    }

    public void save(Reservation reservation) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(reservation);
        em.getTransaction().commit();
        em.close();
    }

    public void update(Reservation reservation) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(reservation);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Reservation reservation) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Reservation toRemove = em.merge(reservation);
        em.remove(toRemove);
        em.getTransaction().commit();
        em.close();
    }
}