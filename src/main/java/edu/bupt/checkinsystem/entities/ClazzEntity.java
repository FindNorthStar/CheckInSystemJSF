package edu.bupt.checkinsystem.entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * This is the ClazzEntity class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 21:45
 */
@Entity
@Table(name = "class", schema = "sunyi", catalog = "")
public class ClazzEntity {
    private int id;
    private String classNo;
    private Collection<CourseClassEntity> courseClassesById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "classNo", nullable = false, length = 10)
    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClazzEntity that = (ClazzEntity) o;

        if (id != that.id) return false;
        if (classNo != null ? !classNo.equals(that.classNo) : that.classNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (classNo != null ? classNo.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "clazzByClassId")
    public Collection<CourseClassEntity> getCourseClassesById() {
        return courseClassesById;
    }

    public void setCourseClassesById(Collection<CourseClassEntity> courseClassesById) {
        this.courseClassesById = courseClassesById;
    }
}
