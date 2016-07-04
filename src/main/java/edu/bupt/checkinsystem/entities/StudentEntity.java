package edu.bupt.checkinsystem.entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * This is the StudentEntity class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 21:45
 */
@Entity
@Table(name = "student", schema = "sunyi", catalog = "")
public class StudentEntity {
    private int id;
    private String studentNo;
    private String studentName;
    private String macAddress;
    private Collection<AppealEntity> appealsById;
    private Collection<RecordEntity> recordsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "studentNo", nullable = false, length = 10)
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    @Basic
    @Column(name = "studentName", nullable = false, length = 30)
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Basic
    @Column(name = "macAddress", nullable = true, length = 12)
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentEntity that = (StudentEntity) o;

        if (id != that.id) return false;
        if (studentNo != null ? !studentNo.equals(that.studentNo) : that.studentNo != null) return false;
        if (studentName != null ? !studentName.equals(that.studentName) : that.studentName != null) return false;
        if (macAddress != null ? !macAddress.equals(that.macAddress) : that.macAddress != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (studentNo != null ? studentNo.hashCode() : 0);
        result = 31 * result + (studentName != null ? studentName.hashCode() : 0);
        result = 31 * result + (macAddress != null ? macAddress.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "studentByStudentId")
    public Collection<AppealEntity> getAppealsById() {
        return appealsById;
    }

    public void setAppealsById(Collection<AppealEntity> appealsById) {
        this.appealsById = appealsById;
    }

    @OneToMany(mappedBy = "studentByStudentId")
    public Collection<RecordEntity> getRecordsById() {
        return recordsById;
    }

    public void setRecordsById(Collection<RecordEntity> recordsById) {
        this.recordsById = recordsById;
    }
}
