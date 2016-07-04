package edu.bupt.checkinsystem.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This is the RecordEntity class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 21:45
 */
@Entity
@Table(name = "record", schema = "sunyi", catalog = "")
public class RecordEntity {
    private int id;
    private int studentId;
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date checkDateTime;
    private int eventId;
    private EventEntity eventByEventId;
    private StudentEntity studentByStudentId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "studentId", nullable = false)
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "checkDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public java.util.Date getCheckDateTime() {
        return checkDateTime;
    }

    public void setCheckDateTime(java.util.Date checkDateTime) {
        this.checkDateTime = checkDateTime;
    }

    @Basic
    @Column(name = "eventId", nullable = false)
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecordEntity that = (RecordEntity) o;

        if (id != that.id) return false;
        if (studentId != that.studentId) return false;
        if (eventId != that.eventId) return false;
        if (checkDateTime != null ? !checkDateTime.equals(that.checkDateTime) : that.checkDateTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + studentId;
        result = 31 * result + (checkDateTime != null ? checkDateTime.hashCode() : 0);
        result = 31 * result + eventId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "eventId", referencedColumnName = "id", nullable = false)
    public EventEntity getEventByEventId() {
        return eventByEventId;
    }

    public void setEventByEventId(EventEntity eventByEventId) {
        this.eventByEventId = eventByEventId;
    }

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id", nullable = false)
    public StudentEntity getStudentByStudentId() {
        return studentByStudentId;
    }

    public void setStudentByStudentId(StudentEntity studentByStudentId) {
        this.studentByStudentId = studentByStudentId;
    }
}
