package edu.bupt.checkinsystem.entities;

import javax.persistence.*;
import java.util.Arrays;

/**
 * This is the AppealEntity class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 21:45
 */
@Entity
@Table(name = "appeal", schema = "sunyi", catalog = "")
public class AppealEntity {
    private int id;
    private int studentId;
    private int eventId;
    private byte[] file;
    private String fileName;
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
    @Column(name = "eventId", nullable = false)
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Basic
    @Column(name = "file", nullable = true)
    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Basic
    @Column(name = "fileName", nullable = false, length = 100)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppealEntity that = (AppealEntity) o;

        if (id != that.id) return false;
        if (studentId != that.studentId) return false;
        if (eventId != that.eventId) return false;
        if (!Arrays.equals(file, that.file)) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + studentId;
        result = 31 * result + eventId;
        result = 31 * result + Arrays.hashCode(file);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
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
