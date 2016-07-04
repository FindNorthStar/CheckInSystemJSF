package edu.bupt.checkinsystem.beans;

import edu.bupt.checkinsystem.entities.TypeEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.Serializable;


@ManagedBean(name="helloWorld")
@SessionScoped
public class HelloWorld implements Serializable{

    private static final long serialVersionUID = -6913972022251814607L;

    private String s1 = "Hello World!!";

    public String getS1() {
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setName("name");
        entityManager.persist(typeEntity);
        entityManager.getTransaction().commit();
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

}