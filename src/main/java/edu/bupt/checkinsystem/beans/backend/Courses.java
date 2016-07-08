package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "backend_courses")
@RequestScoped
public class Courses implements Serializable {
    private List<Map<String, Object>> records = null;

    @Language("MySQL")
    private static final String LIST_ALL_COURSES_SQL =
            "SELECT course.id, \n" +
                    "\tcourse.courseName, \n" +
                    "\t\t(SELECT GROUP_CONCAT(class.classNo SEPARATOR ', ') \n" +
                    "\t\t\tFROM class, courseClass \n" +
                    "\t\t\tWHERE courseClass.courseId = course.id \n" +
                    "\t\t\t\tAND class.id = courseClass.classId\n" +
                    "\t\t) AS classNumbers, \n" +
                    "\tcourse.teachers\n" +
                    "FROM course\n" +
                    "ORDER BY course.id";


    public List<Map<String, Object>> getRecords() throws Exception {
        if (records == null) {
            records = SqlUtils.executeSqlQuery(LIST_ALL_COURSES_SQL);
        }
        return records;
    }
}
