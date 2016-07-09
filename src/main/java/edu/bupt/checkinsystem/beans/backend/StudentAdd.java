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
 * This is the StudentAdd class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/8 22:22
 */

@ManagedBean(name = "backend_student_add")
@RequestScoped
public class StudentAdd {

    @Language("MySQL")
    private static final String INSERT_STUDENT_SQL =
            "INSERT INTO student (classId, studentNo, studentName) VALUES (?, ?, ?)";

    @Language("MySQL")
    private static final String LIST_CLASSNO_SQL = "SELECT classNo FROM class WHERE id = ?";

    private String classId;
    private String classNo;
    private String studentNumber;
    private String studentName;

    @PostConstruct
    private void init() {
        classId = Faces.getRequestParameter("classId");
    }

    public void submit() throws Exception {

        if (TextUtils.isEmpty(studentNumber) || TextUtils.isEmpty(studentName)) {
            Faces.redirect("/backend/student-management?classId=" + classId + "#emptyError");
        } else {
            Map<Integer, Object> param = new HashMap<Integer, Object>();
            param.put(1, classId);
            param.put(2, studentNumber);
            param.put(3, studentName);

            SqlUtils.executeSqlUpdate(INSERT_STUDENT_SQL, param);
            Faces.redirect("/backend/student-management?classId=" + classId + "#added");
        }
    }


    public String getClassNo() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, classId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(LIST_CLASSNO_SQL, map);

        if (!result.isEmpty()) {
            classNo = (String) result.get(0).get("classNo");
        } else {
            classNo = "";
        }

        return classNo;
    }


    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
