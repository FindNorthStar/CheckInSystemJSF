package edu.bupt.checkinsystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * This is the CheckInRecord class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 12:09
 */

@Entity
public class CheckInRecord {

    @Id
    private long id;
    private String dateTime;

    @OneToOne
    private Student student;

    @OneToOne
    private Class aClass;

    @OneToOne
    private CheckInEvent checkInEvent;

    public CheckInRecord(String dateTime, Student student, Class aClass, CheckInEvent checkInEvent) {
        this.dateTime = dateTime;
        this.student = student;
        this.aClass = aClass;
        this.checkInEvent = checkInEvent;
    }

    public CheckInRecord() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public CheckInEvent getCheckInEvent() {
        return checkInEvent;
    }

    public void setCheckInEvent(CheckInEvent checkInEvent) {
        this.checkInEvent = checkInEvent;
    }
}
