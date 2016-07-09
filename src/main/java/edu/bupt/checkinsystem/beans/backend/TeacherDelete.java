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
 * This is the TeacherDelete class
 * Please put some info here.
 *
 * @author Alexander Qi
 * @since 16/7/9 10:19
 */

@ManagedBean(name = "backend_teacher_delete")
@RequestScoped
public class TeacherDelete {

    @Language("MySQL")
    private static final String DELETE_TEACHER_SQL = "DELETE FROM teacher WHERE id = ?";

    private String teacherId;

    @PostConstruct
    private void init() {
        teacherId = Faces.getRequestParameter("teacherId");
    }

    public void deleteTeacher() throws Exception {
        Map<Integer, Object> param = new HashMap<Integer, Object>();
        param.put(1, teacherId);

        SqlUtils.executeSqlUpdate(DELETE_TEACHER_SQL, param);

        Faces.redirect("/backend/teachers");
    }
}
