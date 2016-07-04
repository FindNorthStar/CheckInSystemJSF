package edu.bupt.checkinsystem.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;


@ManagedBean(name="register")
@SessionScoped // TODO:
public class Register implements Serializable{

    private static final long serialVersionUID = -6913972022251814607L;

    private String s1 = "Hello World!!";

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

}