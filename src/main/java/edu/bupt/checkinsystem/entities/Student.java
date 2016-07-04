package edu.bupt.checkinsystem.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * This is the Student class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 11:55
 */

@Entity
public class Student implements Serializable{

    private long id;
    private String studentNo;
    private String name;

    @OneToOne
    private Class aClass;

    private String macAddress;

    public Student(String studentNo, String name, Class aClass) {
        this.studentNo = studentNo;
        this.name = name;
        this.aClass = aClass;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
