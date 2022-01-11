package r13.javafx.Varastonhallinta.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Contains data for an individual work shift of an Employee (User)
 * @author Severi Reivinen
 */
@Entity
@Table(name = "\"Shift\"")
public class Shift {

    /** The id. */
    @Id
    @Column(name = "id", unique = true)
    private String id;

    /**
     * Ensures all Shifts in the database have an ID
     */
    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }

    /** The user associated with the work shift. */
    @ManyToOne
    @JoinColumn(name = "\"userId\"")
    private User user;

    /** The start time of the shift. */
    @Column(name = "\"startTime\"")
    private LocalTime startTime;

    /** The end time of the shift. */
    @Column(name = "\"endTime\"")
    private LocalTime endTime;

    /** The date of the shift. */
    @Column(name = "date")
    private LocalDate date;

    /**
     * Instantiates a new shift.
     *
     * @param user The user associated with the work shift.
     * @param startTime The start time of the shift
     * @param endTime The end time of the shift.
     * @param timestamp the Date of the Shift
     */
    public Shift(User user, LocalTime startTime, LocalTime endTime, LocalDate timestamp) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = timestamp;
    }

    /**
     * Instantiates a new shift.
     *
     * @param start The start time of the shift
     * @param end The end time of the shift.
     * @param date the Date of the Shift
     */
    public Shift(LocalTime start, LocalTime end, LocalDate date) {
        this.startTime = start;
        this.endTime = end;
        this.date = date;
    }

    /**
     * Instantiates a new shift.
     *
     * @param start The start time of the shift
     * @param end The end time of the shift.
     */
    public Shift(LocalTime start, LocalTime end) {
        this.startTime = start;
        this.endTime = end;
    }

    /**
     * Instantiates a new shift.
     */
    public Shift() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalTime getStart() {
        return startTime;
    }

    public void setStart(LocalTime start) {
        this.startTime = start;
    }

    public LocalTime getEnd() {
        return endTime;
    }

    public void setEnd(LocalTime end) {
        this.endTime = end;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
