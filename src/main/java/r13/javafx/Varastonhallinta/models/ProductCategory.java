package r13.javafx.Varastonhallinta.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Contains the category information of a product
 * @author Severi Reivinen
 */
@Entity()
@Table(name = "\"ProductCategory\"")
public class ProductCategory {

    /** Automatically generated ID of a product category. */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private String id;

    /** The category name. */
    @Column(name = "name")
    private String name;

    /** The category description. */
    @Column(name = "description")
    private String description;

    /** The products that are a part of this category. */
    @OneToMany(mappedBy = "productCategory")
    private List<Product> products;

    /**
     * Instantiates a new product category.
     */
    public ProductCategory() {

    }

    /**
     * Instantiates a new product category.
     *
     * @param id Automatically generated ID of a product category.
     * @param name The category name.
     * @param description The category description.
     */
    public ProductCategory(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
