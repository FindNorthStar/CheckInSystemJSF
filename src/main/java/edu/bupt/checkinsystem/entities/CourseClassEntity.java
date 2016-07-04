package edu.bupt.checkinsystem.entities;

import javax.persistence.*;

/**
 * This is the CourseClassEntity class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 21:45
 */
@Entity
@Table(name = "courseClass", schema = "sunyi", catalog = "")
public class CourseClassEntity {
    private int id;
    private int classId;
    private int courseId;
    private ClazzEntity clazzByClassId;
    private CourseEntity courseByCourseId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "classId", nullable = false)
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Basic
    @Column(name = "courseId", nullable = false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseClassEntity that = (CourseClassEntity) o;

        if (id != that.id) return false;
        if (classId != that.classId) return false;
        if (courseId != that.courseId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + classId;
        result = 31 * result + courseId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "classId", referencedColumnName = "id", nullable = false)
    public ClazzEntity getClazzByClassId() {
        return clazzByClassId;
    }

    public void setClazzByClassId(ClazzEntity clazzByClassId) {
        this.clazzByClassId = clazzByClassId;
    }

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id", nullable = false)
    public CourseEntity getCourseByCourseId() {
        return courseByCourseId;
    }

    public void setCourseByCourseId(CourseEntity courseByCourseId) {
        this.courseByCourseId = courseByCourseId;
    }
}
