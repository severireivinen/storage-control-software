package r13.javafx.Varastonhallinta.models.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import r13.javafx.Varastonhallinta.models.User;

import java.util.List;

/**
 * Class contains methods for communication with the database for users
 *
 * @author Juuso Lahtinen
 */
public class UserAccessObject {

    /**
     * Create an EntityManagerFactory when you start the application
     */
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");

    /**
     * Check if the given parameters match a row in the database
     *
     * @param username String username
     * @param password String password
     * @return boolean based on if the login was successfully
     */
    public boolean checkLogin(String username, String password) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String strQuery = "SELECT u FROM User u WHERE u.username=:username AND u.password=:password";

        TypedQuery<User> tq = em.createQuery(strQuery, User.class);
        tq.setParameter("username", username);
        tq.setParameter("password", password);

        if (tq.getResultList().size() == 1) {
            em.close();
            return true;
        } else {
            em.close();
            return false;
        }
    }

    /**
     * Used in tests
     *
     * @param username String username
     * @return single User based on username from the database
     */
    public static User getDBUsername(String username) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String strQuery = "SELECT u FROM User u WHERE u.username=:username";

        TypedQuery<User> tq = em.createQuery(strQuery, User.class);
        tq.setParameter("username", username);

        User user = null;
        try {
            user = tq.getSingleResult();

        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return user;
    }

    /**
     * Add a new User to the database
     *
     * @param user User
     * @return User
     */
    public static User addUser(User user) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();

            em.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Remove a single User based on username from the database
     *
     * @param username String username
     * @return boolean based on if the query was successful
     */
    public static boolean removeUserByUsername(String username) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "DELETE User u WHERE u.username = :username";

        try {
            em.getTransaction().begin();
            em.createQuery(query).setParameter("username", username).executeUpdate();
            em.getTransaction().commit();
            System.out.println("Removed " + username);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't remove " + username);
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Returns full list of Users from the database
     *
     * @return Full list of Users from the database
     */
    public static List getUsers() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "SELECT u FROM User u WHERE u.id IS NOT NULL";

        TypedQuery<User> tq = em.createQuery(query, User.class);
        List<User> users = null;

        try {
            users = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return users;
    }

    /**
     * Returns single User based on username from the database
     *
     * @param username String username
     * @return single User based on username from the database
     */
    public static User getUser(String username) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "SELECT u FROM User u WHERE u.username = :username";

        TypedQuery<User> tq = em.createQuery(query, User.class);
        tq.setParameter("username", username);

        User user = null;

        try {
            user = tq.getSingleResult();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return user;
    }
}
