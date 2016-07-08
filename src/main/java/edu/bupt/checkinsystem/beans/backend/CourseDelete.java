package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the CourseDelete class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/8 21:55
 */

@ManagedBean(name = "backend_course_delete")
public class CourseDelete {

    @Language("MySQL")
    private static final String DELETE_COURSE_SQL = "DELETE FROM course WHERE course.id = ?";

    private String courseId;


    @PostConstruct
    private void init() {
        courseId = Faces.getRequestParameter("courseId");
    }

    public void deleteCourse() throws Exception {
        Map<Integer, Object> param = new HashMap<Integer, Object>();
        param.put(1, Integer.valueOf(courseId));
        SqlUtils.executeSqlUpdate(DELETE_COURSE_SQL, param);

        Faces.redirect("/backend/courses");
    }
}
