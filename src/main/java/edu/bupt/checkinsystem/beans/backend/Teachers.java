package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Map;

/**
 * This is the Teachers class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/9 00:05
 */

@ManagedBean(name = "backend_teachers")
public class Teachers {

    @Language("MySQL")
    private static final String LIST_TEACHER_SQL = "SELECT id, username, teacherName FROM teacher";

    private List<Map<String, Object>> teachers = null;

    @PostConstruct
    private void init() {
        try {
            getTeachers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Map<String, Object>> getTeachers() throws Exception {
        if (teachers == null) {
            teachers = SqlUtils.executeSqlQuery(LIST_TEACHER_SQL);
        }
        return teachers;
    }

}
