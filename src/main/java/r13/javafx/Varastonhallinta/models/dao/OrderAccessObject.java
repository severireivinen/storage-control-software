package r13.javafx.Varastonhallinta.models.dao;

import r13.javafx.Varastonhallinta.models.Order;

import javax.persistence.*;
import java.util.List;

/**
 * Class contains methods for communication with the database for orders
 * 
 * @author Severi Reivinen
 */

public class OrderAccessObject {

    /**
     * Create an EntityManagerFactory when you start the application
     */
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");

    /**
     * Returns list of Orders from the database
     * @return List of Orders from the database
     */
    public static List getOrders() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String strQuery = "SELECT o FROM Order o WHERE o.id IS NOT NULL";

        TypedQuery<Order> tq = em.createQuery(strQuery, Order.class);
        List<Order> orders = null;
        try {
            orders = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return orders;
    }

    /**
     *
     * Returns order based on parameter id from the database
     * @param id orderId
     * @return Order based on parameter id from the database
     */
    public static Order getOrderByOrderId(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "SELECT o FROM Order o WHERE o.id = :id";

        TypedQuery<Order> tq = em.createQuery(query, Order.class);
        tq.setParameter("id", id);

        Order order = null;
        try {
            order = tq.getSingleResult();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            //em.close();
        }
        return order;
    }

    /**
     * Update Order as processed in the database
     * @param id orderStatusCodeId
     */
    public static void setOrderProcessed(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "UPDATE Order o SET o.orderStatusCode='60ff029d-f91f-4b89-a3bd-6e87538d45d6' WHERE o.id = :id";

        try {
            em.getTransaction().begin();
            em.createQuery(query).setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            System.out.println("Update executed");
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Update Order as not processed in the database
     * @param id orderStatusCodeId
     */
    public static void setOrderNotProcessed(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "UPDATE Order o SET o.orderStatusCode='d32f2d6f-109b-480b-9454-34dd1334db27' WHERE o.id = :id";

        try {
            em.getTransaction().begin();
            em.createQuery(query).setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            System.out.println("Update executed");
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
