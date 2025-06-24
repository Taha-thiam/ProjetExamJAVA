package sn.Fama_Taha.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import sn.Fama_Taha.config.HibernateUtils;
import sn.Fama_Taha.entity.Ouvrage;
import java.util.List;



public class OuvrageRepository {

    public List<Ouvrage> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Ouvrage", Ouvrage.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de recherche", e);
        }
    }

    public Ouvrage findById(String idOuvrage) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Ouvrage.class, idOuvrage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de recherche", e);
        }
    }

    public Ouvrage save(Ouvrage ouvrage) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(ouvrage);
            tx.commit();
            return ouvrage;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'enregistrement", e);
        }
    }

    public void delete(Ouvrage ouvrage) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(ouvrage);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }

    public Ouvrage update(Ouvrage ouvrage) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Ouvrage updated = (Ouvrage) session.merge(ouvrage);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour", e);
        }
    }
}
