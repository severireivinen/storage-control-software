package r13.javafx.Varastonhallinta.models;


import javax.persistence.*;
import java.util.List;
import java.util.UUID;


/**
 * Contains data for an individual product
 * @author Severi Reivinen
 */
@Entity()
@Table(name = "\"Product\"")
public class Product {

    /** The id. */
    @Id
    @Column(name = "id", unique = true)
    private String id;

    /**
     * Ensures that all products in the database have an identifier.
     */
    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }


    /** The product name. */
    @Column(name = "name", nullable = false)
    private String name;

    /** The product price. */
    @Column(name = "price", nullable = false)
    private double price;

    /** The product description. */
    @Column(name = "description", nullable = true)
    private String description;

    /** The amount of products of the same type. */
    @Column(name = "stock", nullable = false)
    private int stock;

    /** Product location in the warehouse as a String. */
    @Column(name = "location", nullable = false)
    private String location;

    /** Category the product belongs to. */
    @ManyToOne
    @JoinColumn(name = "\"productCategoryId\"", nullable = true)
    private ProductCategory productCategory;

    /** Reference to OrderItem object, contains a list of all the products as items in an Order. */
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    /**
     * Instantiates a new product.
     *
     * @param id the id
     * @param name the name
     * @param price the price
     * @param description the description
     * @param stock the stock
     * @param location the location
     */
    public Product(String id, String name, double price, String description, int stock, String location) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.location = location;
    }

    /**
     * Instantiates a new product.
     */
    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }
}
