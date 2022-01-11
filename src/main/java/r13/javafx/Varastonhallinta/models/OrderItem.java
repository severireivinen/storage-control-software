package r13.javafx.Varastonhallinta.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Represents a product thats contained within an Order. Contains references to the Product
 * and the Order
 * @author Severi Reivinen
 */
@Entity()
@Table(name = "\"OrderItem\"")
public class OrderItem {

    /** The id. */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private String id;

    /** The quantity of products. */
    @Column(name = "quantity")
    private int quantity;

    /** Sum of all the product prices. */
    @Column(name = "price")
    private double price;

    /** The product. */
    @ManyToOne
    @JoinColumn(name = "\"productId\"", nullable = false)
    private Product product;

    /** The order this item is associated with. */
    @ManyToOne
    @JoinColumn(name = "\"orderId\"")
    private Order order;

    public Order getOrder() {
        return order;
    }

    /**
     * Instantiates a new order item.
     */
    public OrderItem() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
