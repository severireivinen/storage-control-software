package r13.javafx.Varastonhallinta.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Represents the status of an individual order. Determines if the order is processed or not
 * @author Severi Reivinen
 */
@Entity()
@Table(name = "\"OrderStatusCode\"")
public class OrderStatusCode {

    /** The automatically generated id of the status code. */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private String id;

    /** If the order is processed or not. */
    @Column(name = "processed")
    private Boolean processed;

    /** References the processed order. */
    @Column(name = "description")
    private String description;

    /** References the orders that have a status set. */
    @OneToMany(mappedBy = "orderStatusCode")
    private List<Order> orders;

    /**
     * Instantiates a new order status code.
     *
     * @param processed returns true if the order is processed and false if its not
     * @param description references the orders that have a status set
     */
    public OrderStatusCode(Boolean processed, String description) {
        this.processed = processed;
        this.description = description;
    }

    /**
     * Instantiates a new order status code.
     */
    public OrderStatusCode() {

    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
