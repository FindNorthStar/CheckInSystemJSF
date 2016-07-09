package edu.bupt.checkinsystem;

import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

/**
 * Created by Alexander Qi on 7/9/16.
 */
@ManagedBean(name = "redirect")
@RequestScoped
public class Redirect implements Serializable {
    private String message;
    private String buttonText;
    private String uri;

    @PostConstruct
    public void init() {
        try {
            message = Faces.getRequestParameter("message");
            buttonText = Faces.getRequestParameter("buttonText");
            uri = Faces.getRequestParameter("uri");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submit() throws Exception {
        Faces.redirect(uri);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
