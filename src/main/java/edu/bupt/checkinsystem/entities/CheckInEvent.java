package edu.bupt.checkinsystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * This is the CheckInEvent class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 12:04
 */

@Entity
public class CheckInEvent {

    @Id
    private long id;
    private String startDateTime;

    @OneToOne
    private Course course;

    @OneToOne
    private Type checkInType;

    public CheckInEvent(String startDateTime, Course course, Type checkInType) {
        this.startDateTime = startDateTime;
        this.course = course;
        this.checkInType = checkInType;
    }

    public CheckInEvent() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Type getCheckInType() {
        return checkInType;
    }

    public void setCheckInType(Type checkInType) {
        this.checkInType = checkInType;
    }
}
