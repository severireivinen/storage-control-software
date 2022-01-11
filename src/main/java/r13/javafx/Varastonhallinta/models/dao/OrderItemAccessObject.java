package r13.javafx.Varastonhallinta.models.dao;

import r13.javafx.Varastonhallinta.models.OrderItem;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a product thats contained within an Order. Contains references to the Product
 * and the Order
 *
 * @author Severi Reivinen
 */

public class OrderItemAccessObject {

    /**
     * Create an EntityManagerFactory when you start the application
     */
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");


    /**
     * Returns list of OrderItems based on OrderId from the database
     *
     * @param id orderId
     * @return List of OrderItems based on OrderId from the database
     */
    public static List getOrderItemsByOrderId(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "SELECT o FROM OrderItem o WHERE o.order.id = :id";

        TypedQuery<OrderItem> tq = em.createQuery(query, OrderItem.class);
        tq.setParameter("id", id);

        List<OrderItem> orderItems = null;
        try {
            orderItems = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return orderItems;
    }

    /**
     * Returns list of OrderItems based on ProductId from the database
     *
     * @param id productId
     * @return List of OrderItems based on ProductId from the database
     */
    public static List getOrderItemsByProductId(String id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "SELECT o FROM OrderItem o WHERE o.product.id = :id";

        TypedQuery<OrderItem> tq = em.createQuery(query, OrderItem.class);
        tq.setParameter("id", id);

        List<OrderItem> orderItems = null;
        try {
            orderItems = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return orderItems;
    }
}
