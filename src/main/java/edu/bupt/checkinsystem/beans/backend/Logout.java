package edu.bupt.checkinsystem.beans.backend;

import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean(name = "backend_logout")
public class Logout implements Serializable {
    private String emptyString = "";

    public String getEmptyString() {
        return emptyString;
    }

    public void setEmptyString(String emptyString) {
        this.emptyString = emptyString;
    }

    @PostConstruct
    public void init() {
        Faces.removeResponseCookie("teacherName", "/");
        Faces.removeResponseCookie("token", "/");
        try {
            Faces.redirect("/backend/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
