package edu.bupt.checkinsystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This is the Class class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 14:21
 */

@Entity
public class Class {

    @Id
    private long id;
    private String className;

    public Class(String className) {
        this.className = className;
    }

    public Class() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
