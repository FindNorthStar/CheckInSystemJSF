package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import edu.bupt.checkinsystem.util.TextUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the ClassAdd class
 * Please put some info here.
 *
 * @author Alexander Qi
 * @since 16/7/9 16:39
 */

@ManagedBean(name = "backend_class_modification")
@RequestScoped
public class ClassModification {
    private String classNo;
    private String classId;

    @Language("MySQL")
    private static final String GET_CLASS_INFO_SQL = "SELECT classNo FROM class WHERE id = ?";

    @PostConstruct
    private void init() {
        try {
            classId = Faces.getRequestParameter("classId");
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, Integer.valueOf(classId));
            List<Map<String, Object>> results = SqlUtils.executeSqlQuery(GET_CLASS_INFO_SQL, map);
            Map<String, Object> result = results.get(0);

            classNo = String.valueOf(result.get("classNo"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Language("MySQL")
    private static final String UPDATE_CLASS_SQL = "UPDATE class SET classNo = ? WHERE id = ?";

    public void submit() throws Exception {

        if (TextUtils.isEmpty(classNo)) {
            Faces.redirect("/backend/classes#emptyError");
        } else {
            Map<Integer, Object> param = new HashMap<Integer, Object>();
            param.put(1, classNo);
            param.put(2, classId);

            SqlUtils.executeSqlUpdate(UPDATE_CLASS_SQL, param);
            Faces.redirect("/backend/classes#modified");
        }
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
