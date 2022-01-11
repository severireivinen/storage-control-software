package r13.javafx.Varastonhallinta.models.dao;

import r13.javafx.Varastonhallinta.models.ProductCategory;

import javax.persistence.*;

public class ProductCategoryAccessObject {

    /**
     * Create an EntityManagerFactory when you start the application
     */
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("test");


    /**
     * Create a new ProductCategory with a name and description to the database
     *
     * @param name        Category name
     * @param description Category description
     */
    public static void createProductCategory(String name, String description) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            ProductCategory category = new ProductCategory();
            category.setName(name);
            category.setDescription(description);

            em.persist(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
