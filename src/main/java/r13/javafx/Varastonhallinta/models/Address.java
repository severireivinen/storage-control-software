package r13.javafx.Varastonhallinta.models;

import javax.persistence.*;

/**
 * Represents the Customer delivery address of an Order
 * @author Severi Reivinen
 */
@Entity()
@Table(name = "\"Address\"")
public class Address {
    
    /** The id. */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /** The address. */
    @Column(name = "address")
    private String address;

    /** The city. */
    @Column(name = "city")
    private String city;

    /** The postal. */
    @Column(name = "postal")
    private String postal;

    /** The customer. */
    @OneToOne(mappedBy = "address")
    private Customer customer;

    /**
     * Instantiates a new address.
     *
     * @param id the id
     * @param address the address
     * @param city the city
     * @param postal the postal
     * @param customer the customer
     */
    public Address(String id, String address, String city, String postal, Customer customer) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.postal = postal;
        this.customer = customer;
    }

    /**
     * Instantiates a new address.
     */
    public Address() {
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the postal.
     *
     * @return the postal
     */
    public String getPostal() {
        return postal;
    }

    /**
     * Sets the postal.
     *
     * @param postal the new postal
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     * Gets the customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer.
     *
     * @param customer the new customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
