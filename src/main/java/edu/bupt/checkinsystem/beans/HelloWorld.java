package edu.bupt.checkinsystem.beans;

import org.omnifaces.util.Faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean(name = "helloWorld")
@RequestScoped
public class HelloWorld implements Serializable {

    private static final long serialVersionUID = -6913972022251814607L;

    public void redirect() throws IOException {
            Faces.redirect("/checkin");
    }
}