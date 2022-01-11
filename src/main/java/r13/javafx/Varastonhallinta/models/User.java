package r13.javafx.Varastonhallinta.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * A class for a User/Employee. Used as an account for the application and to identify workers for Shifts
 * @author Juuso Lahtinen
 */
@Entity
@Table(name = "\"User\"")
public class User {

    /** The id. */
    @Id
    @Column(name = "id", unique = true)
    private String id;

    /**
     * Ensure all users in the database have an id.
     */
    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }

    /** The username. */
    @Column(name = "username", nullable = false)
    private String username;

    /** The password. */
    @Column(name = "password", nullable = false)
    private String password;

    /** The first name. */
    @Column(name = "\"firstName\"")
    private String firstName;

    /** The last name. */
    @Column(name = "\"lastName\"")
    private String lastName;

    /** The shifts the user/employee has. */
    @OneToMany(mappedBy = "user")
    private List<Shift> shifts;
    
    /** True if the user is admin and false if hes not. */
    @Column(name = "\"isAdmin\"")
    private Boolean isAdmin;


    /**
     * Instantiates a new user.
     *
     * @param username the username
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     */
    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = false;
    }

    /**
     * Instantiates a new user.
     *
     * @param username the username
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param isAdmin the is admin
     */
    public User(String username, String password, String firstName, String lastName, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
    }

    /**
     * Checks if user is an admin or not. Used to give admins full access to tyhe application and
     * to limit the access of regular users.
     * @return if the user is an admin or not.
     */
    public Boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * Instantiates a new user.
	 */
	public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    /**
     * Returns a shift the user has with shift date as a parameter
     *
     * @param date the date
     * @param startTime the start time
     * @param endTime the end time
     * @return the corresponding Shift object
     */
    public Shift getSingleShift(LocalDate date, LocalTime startTime, LocalTime endTime) {
        for (Shift s : shifts) {
            if (s.getDate().equals(date) && s.getStart().equals(startTime) && s.getEnd().equals(endTime)) {
                return s;
            }
        }
        return null;
    }
}
