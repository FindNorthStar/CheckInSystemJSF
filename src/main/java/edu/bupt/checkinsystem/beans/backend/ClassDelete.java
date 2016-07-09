package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by wangym5106 on 16-7-9.
 */

@ManagedBean(name = "backend_class_delete")
@RequestScoped
public class ClassDelete implements Serializable {

    private String classId;

    @PostConstruct
    private void init() {
        classId = Faces.getRequestParameter("classId");
    }

    @Language("MySQL")
    private static final String DELETE_CLASS_SQL = "DELETE FROM class WHERE class.id = ?";
    public void deleteClass() throws Exception {
        Map<Integer, Object> param = new HashMap<Integer, Object>();
        param.put(1, Integer.valueOf(classId));
        SqlUtils.executeSqlUpdate(DELETE_CLASS_SQL, param);
        Faces.redirect("/backend/classes");
    }
}
