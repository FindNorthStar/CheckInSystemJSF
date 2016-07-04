package edu.bupt.checkinsystem.entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * This is the EventEntity class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 21:45
 */
@Entity
@Table(name = "event", schema = "sunyi", catalog = "")
public class EventEntity {
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date startDateTime;
    private int courseId;
    private int typeId;
    private Collection<AppealEntity> appealsById;
    private CourseEntity courseByCourseId;
    private TypeEntity typeByTypeId;
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
    @Column(name = "startDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public java.util.Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(java.util.Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Basic
    @Column(name = "courseId", nullable = false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "typeId", nullable = false)
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventEntity that = (EventEntity) o;

        if (id != that.id) return false;
        if (courseId != that.courseId) return false;
        if (typeId != that.typeId) return false;
        if (startDateTime != null ? !startDateTime.equals(that.startDateTime) : that.startDateTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (startDateTime != null ? startDateTime.hashCode() : 0);
        result = 31 * result + courseId;
        result = 31 * result + typeId;
        return result;
    }

    @OneToMany(mappedBy = "eventByEventId")
    public Collection<AppealEntity> getAppealsById() {
        return appealsById;
    }

    public void setAppealsById(Collection<AppealEntity> appealsById) {
        this.appealsById = appealsById;
    }

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id", nullable = false)
    public CourseEntity getCourseByCourseId() {
        return courseByCourseId;
    }

    public void setCourseByCourseId(CourseEntity courseByCourseId) {
        this.courseByCourseId = courseByCourseId;
    }

    @ManyToOne
    @JoinColumn(name = "typeId", referencedColumnName = "id", nullable = false)
    public TypeEntity getTypeByTypeId() {
        return typeByTypeId;
    }

    public void setTypeByTypeId(TypeEntity typeByTypeId) {
        this.typeByTypeId = typeByTypeId;
    }

    @OneToMany(mappedBy = "eventByEventId")
    public Collection<RecordEntity> getRecordsById() {
        return recordsById;
    }

    public void setRecordsById(Collection<RecordEntity> recordsById) {
        this.recordsById = recordsById;
    }
}
