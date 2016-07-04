package edu.bupt.checkinsystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * This is the Course class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 12:08
 */

@Entity
public class Course {

    @Id
    private long id;
    private String courseName;

    @OneToMany
    private List<Class> classes;

    public Course(String courseName, List<Class> classes) {
        this.courseName = courseName;
        this.classes = classes;
    }

    public Course() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }
}
