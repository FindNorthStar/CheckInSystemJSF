package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangym5106 on 16-7-9.
 */

@ManagedBean(name = "backend_event_detail")
@RequestScoped
public class EventDetail implements Serializable {

    private Integer eventId = 0;
    private String courseName;
    private String startDateTime;
    private String typeName;
    private Integer totalStudentCount;
    private Integer checkInCount;
    private List<Map<String, Object>> studentList = null;
    private List<Map<String, Object>> classList = null;

    @PostConstruct
    private void init() {
        try {
            eventId = Integer.valueOf(Faces.getRequestParameter("eventId"));
        } catch (Exception e) {
            try {
                Faces.redirect("/backend/index");
            } catch (Exception f) {
                //Fuck you!
            }
        }
    }

    @Language("MySQL")
    private static final String GET_COURSE_NAME_SQL = "SELECT course.courseName FROM course, event " +
            "WHERE course.id = event.courseId AND event.id = ?";
    public String getCourseName() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, eventId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(GET_COURSE_NAME_SQL, map);
        courseName = (String) result.get(0).get("courseName");
        return courseName;
    }

    @Language("MySQL")
    private static final String GET_STARTDATETIME_SQL = "SELECT DATE_FORMAT(startDateTime, \"%m-%d %H:%i\") AS startDateTime " +
            "FROM event WHERE id = ?";
    public String getStartDateTime() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, eventId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(GET_STARTDATETIME_SQL
                , map);
        startDateTime = (String) result.get(0).get("startDateTime");
        return startDateTime;
    }

    @Language("MySQL")
    private static final String GET_TYPENAME_SQL = "SELECT type.name AS typeName\n" +
            "FROM event, type\n" +
            "WHERE event.id = ? AND type.id = event.typeId";
    public String getTypeName() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, eventId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(GET_TYPENAME_SQL, map);
        typeName = (String) result.get(0).get("typeName");
        return typeName;
    }

    @Language("MySQL")
    private static final String GET_CLASS_LIST_SQL = "SELECT class.classNo\n" +
            "FROM class, courseClass, event\n" +
            "WHERE courseClass.courseId = event.courseId AND  class.id = courseClass.classId AND event.id = ?";

    public List<Map<String, Object>> getClassList() throws Exception{
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, eventId);
        classList = SqlUtils.executeSqlQuery(GET_CLASS_LIST_SQL, map);
        return classList;
    }

    @Language("MySQL")
    private static final String GET_TOTAL_STUDENT_COUNT_SQL = "SELECT COUNT(student.id) AS totalStudentCount\n" +
            "FROM courseClass, student, event\n" +
            "WHERE courseClass.courseId = event.courseId\n" +
            "\tAND student.classId = courseClass.classId" +
            "\tAND event.id = ?";
    public Integer getTotalStudentCount() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, eventId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(GET_TOTAL_STUDENT_COUNT_SQL, map);
        totalStudentCount = (int) (long) (Long) result.get(0).get("totalStudentCount");
        return totalStudentCount;
    }

    @Language("MySQL")
    private static final String GET_CHECKIN_COUNT_SQL = "SELECT COUNT(id) AS checkInCount\n" +
            "FROM record\n" +
            "WHERE eventId = ?";
    public Integer getCheckInCount() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, eventId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(GET_CHECKIN_COUNT_SQL, map);
        checkInCount = (int) (long) (Long) result.get(0).get("checkInCount");
        return checkInCount;
    }

    @Language("MySQL")
    private static final String LIST_STUDENT_SQL = "SELECT student.id, student.studentNo, \n" +
            "\tstudent.studentName,\n" +
            "\t(\n" +
            "\t\tSELECT DATE_FORMAT(record.checkDateTime, \"%m-%d %H:%i\")\n" +
            "\t\tFROM record\n" +
            "\t\tWHERE record.studentId = student.id\n" +
            "\t\t\tAND record.eventId = event.id\n" +
            "\t) AS checkDateTime\n" +
            "FROM courseClass, student, course, event\n" +
            "WHERE event.id = ?\n" +
            "\tAND course.id = event.courseId\n" +
            "\tAND courseClass.courseId = course.id\n" +
            "\tAND student.classId = courseClass.classId\n" +
            "ORDER BY studentNo";

    public List<Map<String, Object>> getStudentList() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, eventId);
        studentList = SqlUtils.executeSqlQuery(LIST_STUDENT_SQL, map);
        return studentList;
    }



}
