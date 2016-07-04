package edu.bupt.checkinsystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

/**
 * This is the AppealRecord class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 12:17
 */

@Entity
public class AppealRecord {

    @Id
    private long id;

    @Lob
    private byte[] blob = null;

    @OneToOne
    private Student student;

    @OneToOne
    private CheckInEvent checkInEvent;

    public AppealRecord(byte[] blob, Student student, CheckInEvent checkInEvent) {
        this.blob = blob;
        this.student = student;
        this.checkInEvent = checkInEvent;
    }

    public AppealRecord() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CheckInEvent getCheckInEvent() {
        return checkInEvent;
    }

    public void setCheckInEvent(CheckInEvent checkInEvent) {
        this.checkInEvent = checkInEvent;
    }
}
