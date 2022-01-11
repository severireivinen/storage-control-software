package r13.javafx.Varastonhallinta.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Represents the Order made by a Customer. Contains references to the Customer
 * and the list of items on the order
 * @author Severi Reivinen
 */
@Entity()
@Table(name = "\"Order\"")
public class Order {

    /** The id. */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private String id;

    /** The date of the order */
    @Column(name = "\"orderedAt\"")
    private Timestamp orderedAt;


    /** The Customer who made the order. */
    @ManyToOne
    @JoinColumn(name = "\"customerId\"", nullable = false)
    private Customer customer;

    /** Order status code (processed or not processed). */
    @ManyToOne
    @JoinColumn(name = "\"orderStatusCodeId\"")
    private OrderStatusCode orderStatusCode;

    /** The items associated with the order. */
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    /**
     * Instantiates a new order.
     *
     * @param id the id
     * @param orderedAt Order date
     */
    public Order(String id, Timestamp orderedAt) {
        this.id = id;
        this.orderedAt = orderedAt;
    }

    /**
     * Instantiates a new order.
     */
    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Timestamp orderedAt) {
        this.orderedAt = orderedAt;
    }


    public Customer getCustomer() {
        return customer;
    }


    public OrderStatusCode getOrderStatusCode() {
        return orderStatusCode;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderStatusCode(OrderStatusCode orderStatusCode) {
        this.orderStatusCode = orderStatusCode;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
