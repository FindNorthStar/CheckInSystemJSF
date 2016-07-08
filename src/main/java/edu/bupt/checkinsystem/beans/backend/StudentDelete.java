package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the StudentDelete class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/8 23:37
 */

@ManagedBean(name = "backend_student_delete")
@RequestScoped
public class StudentDelete {

    @Language("MySQL")
    private static final String DELETE_STUDENT_SQL = "DELETE FROM student WHERE student.id = ?";

    private String studentId;
    private String classId;

    @PostConstruct
    private void init() {
        classId = Faces.getRequestParameter("classId");
        studentId = Faces.getRequestParameter("studentId");
    }

    public void deleteStudent() throws Exception {
        Map<Integer, Object> param = new HashMap<Integer, Object>();
        param.put(1, studentId);

        SqlUtils.executeSqlUpdate(DELETE_STUDENT_SQL, param);

        Faces.redirect("/backend/student-management?classId=" + classId);
    }
}
