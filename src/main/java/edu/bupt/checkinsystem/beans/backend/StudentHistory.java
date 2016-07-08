package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "backend_student_history")
@RequestScoped
public class StudentHistory implements Serializable {

    private Integer studentId = 0;
    private String studentName;
    private Integer courseId = 0;
    private String startDate, endDate;
    private List<Map<String, Object>> courseList = null;
    private List<Map<String, Object>> eventList = null;


    @PostConstruct
    private void init() {
        try {
            studentId = Integer.valueOf(Faces.getRequestParameter("studentId"));
            studentName = getStudentName();
        } catch (Exception e) {
            try {
                Faces.redirect("/backend/index");
            } catch (Exception f) {
                //Fuck you!
            }
        }
    }

    public String getStartDate() {
        try {
            startDate = Faces.getRequestParameter("start");
        } catch (Exception e) {
            startDate = "1970-01-01";
        }
        return startDate;
    }

    public String getEndDate() {
        try {
            endDate = Faces.getRequestParameter("end");
        } catch (Exception e) {
            endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        return endDate;
    }

    public String getStudentName() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, studentId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery("SELECT studentName " +
                        "FROM student WHERE id = ?"
                , map);
        studentName = (String) result.get(0).get("studentName");
        return studentName;
    }

    public Integer getCourseId() {
        try {
            courseId = Integer.valueOf(Faces.getRequestParameter("courseId"));
        } catch (Exception e) {
            courseId = 0;
        }
        return courseId;
    }

    public List<Map<String, Object>> getCourseList() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, studentId);
        courseList = SqlUtils.executeSqlQuery("SELECT course.id AS courseId, course.courseName AS courseName \n" +
                "FROM course, courseClass, student\n" +
                "WHERE courseClass.classId = student.classId AND course.id = courseClass.courseId AND student.id = ? \n" +
                "ORDER BY course.id", map);
        return courseList;
    }


    public List<Map<String, Object>> getEventList() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, studentId);
        map.put(2, getStartDate());
        map.put(3, getEndDate());
        String QUERY_SQL;
        if (getCourseId() == 0) {
            QUERY_SQL = "SELECT \n" +
                    "\tDATE_FORMAT(event.startDateTime, \"%m-%d %H:%i\") AS eventTime,\n" +
                    "\tcourse.courseName AS courseName,\n" +
                    "\ttype.name AS typeName,\n" +
                    "\tevent.id AS eventId,\n" +
                    "\t(\n" +
                    "\t\tSELECT DATE_FORMAT(record.checkDateTime, \"%m-%d %H:%i\") FROM record\n" +
                    "\t\tWHERE record.studentId = student.id AND record.eventId = event.id\n" +
                    "\t) AS checkInTime\n" +
                    "FROM  student, courseClass, course, event, type\n" +
                    "WHERE \n" +
                    "\tstudent.id = ?\n" +
                    "\tAND courseClass.classId = student.classId \n" +
                    "\tAND course.id = courseClass.courseId \n" +
                    "\tAND event.courseId = courseClass.courseId \n" +
                    "\tAND event.typeId = type.id\n" +
                    "\tAND DATE(event.startDateTime) BETWEEN ? AND ?\n" +
                    "ORDER BY event.startDateTime DESC";
        } else {
            map.put(4, courseId);
            QUERY_SQL = "SELECT \n" +
                    "\tDATE_FORMAT(event.startDateTime, \"%m-%d %H:%i\") AS eventTime,\n" +
                    "\tcourse.courseName AS courseName,\n" +
                    "\ttype.name AS typeName,\n" +
                    "\tevent.id AS eventId,\n" +
                    "\t(\n" +
                    "\t\tSELECT DATE_FORMAT(record.checkDateTime, \"%m-%d %H:%i\") FROM record\n" +
                    "\t\tWHERE record.studentId = student.id AND record.eventId = event.id\n" +
                    "\t) AS checkInTime\n" +
                    "FROM  student, course, event, type\n" +
                    "WHERE \n" +
                    "\tstudent.id = ?\n" +
                    "\tAND event.courseId = course.id\n" +
                    "\tAND event.typeId = type.id\n" +
                    "\tAND DATE(event.startDateTime) BETWEEN ? AND ?\n" +
                    "\tAND course.id = ?\n" +
                    "ORDER BY event.startDateTime DESC";
        }
        eventList = SqlUtils.executeSqlQuery(QUERY_SQL, map);
        return eventList;
    }
}
