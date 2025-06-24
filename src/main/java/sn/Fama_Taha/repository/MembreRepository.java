package sn.Fama_Taha.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import sn.Fama_Taha.config.HibernateUtils;
import sn.Fama_Taha.entity.Membre;
import java.util.List;




public class MembreRepository implements IMembreRepository {

    @Override
    public List<Membre> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Membre", Membre.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de recherche", e);
        }
    }

    @Override
    public Membre findById(String idMembre) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Membre.class, idMembre);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de recherche", e);
        }
    }

    @Override
    public Membre save(Membre membre) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(membre);
            tx.commit();
            return membre;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'enregistrement", e);
        }
    }

    @Override
    public void delete(Membre membre) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(membre);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }

    @Override
    public Membre update(Membre membre) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Membre updated = (Membre) session.merge(membre);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour", e);
        }
    }
}
