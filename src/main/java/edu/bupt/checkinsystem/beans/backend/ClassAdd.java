package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import edu.bupt.checkinsystem.util.TextUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the ClassAdd class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/8 20:57
 */

@ManagedBean(name = "backend_class_add")
@RequestScoped
public class ClassAdd {

    @Language("MySQL")
    private static final String INSERT_CLASS_SQL = "INSERT INTO class (classNo) VALUES (?)";

    private String classNumber;


    public void submit() throws Exception {

        if (TextUtils.isEmpty(classNumber)) {
            Faces.redirect("/backend/classes#emptyError");
        } else {
            Map<Integer, Object> param = new HashMap<Integer, Object>();
            param.put(1, classNumber);

            SqlUtils.executeSqlUpdate(INSERT_CLASS_SQL, param);
            Faces.redirect("/backend/classes#added");
        }
    }


    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
}
