package sn.Fama_Taha.repository;

import org.hibernate.query.Query;
import sn.Fama_Taha.entity.Administrateur;
import sn.Fama_Taha.exception.AdministrateurNotFoundException;
import sn.Fama_Taha.config.HibernateUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthRepository {
    public Administrateur login(String login, String password) throws Exception {
        try (var session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Administrateur> query = session.createQuery(
                "FROM Administrateur WHERE login = :login AND password = :password",
                Administrateur.class
            );
            query.setParameter("login", login);
            query.setParameter("password", password);
            Administrateur administrateur = query.uniqueResult();
            if (administrateur == null) {
                throw new AdministrateurNotFoundException("Login ou mot de passe incorrect");
            }
            return administrateur;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
}
