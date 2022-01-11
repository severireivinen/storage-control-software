package r13.javafx.Varastonhallinta.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Represents an individual Customer. Contains references to the orders the customer has made and its address.
 * @author Severi Reivinen
 */
@Entity()
@Table(name = "\"Customer\"")
public class Customer {
    
    /** The id. */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private String id;

    /** The email. */
    @Column(name = "email")
    private String email;

    /** The password. */
    @Column(name = "password")
    private String password;

    /** The first name. */
    @Column(name = "\"firstName\"")
    private String firstName;

    /** The last name. */
    @Column(name = "\"lastName\"")
    private String lastName;

    /** The phone number. */
    @Column(name = "phone")
    private String phone;

    /** The registration date. */
    @Column(name = "\"registeredAt\"")
    private Timestamp registeredAt;

    
    @Column(name = "\"accessToken\"")
    private String accessToken;

    /** List of orders the customer has made. */
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    /** Address of the customer. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "\"addressId\"", referencedColumnName = "id")
    private Address address;

    /**
     * Instantiates a new customer.
     *
     * @param id the id
     * @param email the email
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param phone the phone
     * @param registeredAt the registration date
     * @param accessToken C
     * @param address the address
     */
    public Customer(String id, String email, String password, String firstName, String lastName, String phone, Timestamp registeredAt, String accessToken, Address address) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.registeredAt = registeredAt;
        this.accessToken = accessToken;
        this.address = address;
    }

    /**
     * Instantiates a new customer.
     */
    public Customer() {
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
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone.
     *
     * @param phone the new phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the registered at.
     *
     * @return the registered at
     */
    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    /**
     * Sets the registered at.
     *
     * @param registeredAt the new registered at
     */
    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }

    /**
     * Gets the access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the access token.
     *
     * @param accessToken the new access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Gets the orders.
     *
     * @return the orders
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Sets the orders.
     *
     * @param orders the new orders
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address the new address
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
