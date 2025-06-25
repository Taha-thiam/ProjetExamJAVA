package sn.Fama_Taha.repository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sn.Fama_Taha.config.HibernateUtils;
import sn.Fama_Taha.entity.Emprunt;
import java.util.List;




public class EmpruntRepository {

    public List<Emprunt> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Emprunt", Emprunt.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de recherche", e);
        }
    }

    public Emprunt findById(String idEmprunt) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Emprunt.class, idEmprunt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de recherche", e);
        }
    }

    public Emprunt save(Emprunt emprunt) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(emprunt);
            tx.commit();
            return emprunt;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'enregistrement", e);
        }
    }

    public void delete(Emprunt emprunt) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(emprunt);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }

    public Emprunt update(Emprunt emprunt) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Emprunt updated = (Emprunt) session.merge(emprunt);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour", e);
        }
    }
}
