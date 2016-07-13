package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "backend_classes")
@RequestScoped
public class Classes implements Serializable {

    private List<Map<String, Object>> classes = null;

    @Language("MySQL")
    private static final String LIST_CLASSES_SQL = "SELECT id, classNo FROM class";

    public List<Map<String, Object>> getClasses() throws Exception {
        classes = SqlUtils.executeSqlQuery(LIST_CLASSES_SQL);
        return classes;
    }

    public void redirectToClassAdd() {
        try {
            Faces.redirect("/backend/class-add");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirectToStudentImport() {
        try {
            Faces.redirect("/backend/student-import");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
