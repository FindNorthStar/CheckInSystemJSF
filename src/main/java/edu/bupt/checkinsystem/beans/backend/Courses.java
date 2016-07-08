package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "backend_courses")
@RequestScoped
public class Courses implements Serializable {
    private List<Map<String, Object>> records = null;
    private static final String LIST_ALL_COURSES_SQL =
            "SELECT course.id, " +
                    "course.courseName, " +
                    "GROUP_CONCAT(class.classNo SEPARATOR ', ') AS classNumbers, " +
                    "course.teachers\n" +
            "FROM\n" +
            "    class, course, courseClass\n" +
            "WHERE\n" +
            "    class.id = courseClass.classId AND\n" +
            "    courseClass.courseId = course.id\n" +
            "GROUP BY\n" +
            "    course.courseName\n" +
            "ORDER BY\n" +
                    "course.id";


    public List<Map<String, Object>> getRecords() throws Exception {
        if (records == null) {
            records = SqlUtils.executeSqlQuery(LIST_ALL_COURSES_SQL);
        }
        return records;
    }
}
