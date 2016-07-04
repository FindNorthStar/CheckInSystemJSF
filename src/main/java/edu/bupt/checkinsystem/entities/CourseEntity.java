package edu.bupt.checkinsystem.entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * This is the CourseEntity class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 21:45
 */
@Entity
@Table(name = "course", schema = "sunyi", catalog = "")
public class CourseEntity {
    private int id;
    private String courseName;
    private Collection<CourseClassEntity> courseClassesById;
    private Collection<EventEntity> eventsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "courseName", nullable = false, length = 20)
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseEntity that = (CourseEntity) o;

        if (id != that.id) return false;
        if (courseName != null ? !courseName.equals(that.courseName) : that.courseName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "courseByCourseId")
    public Collection<CourseClassEntity> getCourseClassesById() {
        return courseClassesById;
    }

    public void setCourseClassesById(Collection<CourseClassEntity> courseClassesById) {
        this.courseClassesById = courseClassesById;
    }

    @OneToMany(mappedBy = "courseByCourseId")
    public Collection<EventEntity> getEventsById() {
        return eventsById;
    }

    public void setEventsById(Collection<EventEntity> eventsById) {
        this.eventsById = eventsById;
    }
}
