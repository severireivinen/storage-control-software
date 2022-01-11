package r13.javafx.Varastonhallinta.models.dao;

import r13.javafx.Varastonhallinta.models.Shift;

import javax.persistence.*;
import java.util.List;

/**
 * Class contains methods for communication with the database for shifts
 * @author Severi Reivinen
 */

public class ShiftAccessObject {


    /**
     * Create an EntityManagerFactory when you start the application
     */
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");


    /**
     * Add a new Shift to the database
     *
     * @param shift Shift
     * @return Shift
     */
    public static Shift addShift(Shift shift) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();

            em.persist(shift);
            transaction.commit();
            return shift;
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
     * Delete a single Shift based on ShiftId from the database
     *
     * @param id shiftId
     * @return boolean based on if the query was successful
     */
    public static boolean deleteShiftById(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "DELETE Shift s WHERE s.id = :id";

        try {
            em.getTransaction().begin();
            em.createQuery(query).setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Returns list of Shifts based on UserId from the database
     *
     * @param id userId
     * @return List of Shifts based on UserId from the database
     */
    public static List getShiftsByUserId(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT s FROM Shift s WHERE s.user.id = :id";

        TypedQuery<Shift> tq = em.createQuery(query, Shift.class);
        tq.setParameter("id", id);
        List<Shift> shifts = null;

        try {
            shifts = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return shifts;
    }
}
