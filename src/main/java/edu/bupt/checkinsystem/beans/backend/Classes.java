package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.Globals;
import edu.bupt.checkinsystem.util.NetUtils;
import edu.bupt.checkinsystem.util.SqlUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "classes")
@RequestScoped
public class Classes implements Serializable {

    private List<Map<String, Object>> classes = null;

    public List<Map<String, Object>> getClasses() throws Exception {
        classes = SqlUtils.executeSqlQuery("SELECT id, classNo FROM class");
        return classes;
    }
}
