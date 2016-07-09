package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * Created by wangym5106 on 16-7-8.
 */

@ManagedBean(name = "backend_course_history")
@RequestScoped
public class CourseHistory implements Serializable {
    private Integer courseId;
    private String courseName;
    private Integer totalEventCount;
    private Integer totalStudentCount;
    private List<Map<String, Object>> classList = null;
    private String startDate, endDate;
    private List<Map<String, Object>> studentList = null;
    private List<Map<String, Object>> eventList = null;

    @PostConstruct
    private void init() {
        try {
            courseId = Integer.valueOf(Faces.getRequestParameter("courseId"));
            courseName = getCourseName();
        } catch (Exception e) {
            try {
                Faces.redirect("/backend/index");
            } catch (Exception f) {
                //Fuck you!
            }
        }
    }

    public Integer getCourseId() {
        return courseId;
    }

    public String getStartDate() {
        startDate = Faces.getRequestParameter("start");
        if (startDate == null || startDate.equals("") || startDate.equals("0")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.YEAR, -1);
            startDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        }
        return startDate;
    }

    public String getEndDate() {
        endDate = Faces.getRequestParameter("end");
        if (endDate == null || endDate.equals("") || endDate.equals("0")) {
            endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        return endDate;
    }


    @Language("MySQL")
    private static final String LIST_COURSE_NAME_SQL = "SELECT courseName " +
                        "FROM course WHERE id = ?";

    public String getCourseName() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, courseId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(LIST_COURSE_NAME_SQL
                , map);
        courseName = (String) result.get(0).get("courseName");
        return courseName;
    }


    @Language("MySQL")
    private static final String LIST_CLASS_FROM_COURSE_CLASSES_TABLE_SQL = "SELECT class.classNo\n" +
                "FROM class, courseClass\n" +
                "WHERE courseClass.courseId = ? AND  class.id = courseClass.classId";

    public List<Map<String, Object>> getClassList() throws Exception{
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, courseId);
        classList = SqlUtils.executeSqlQuery(LIST_CLASS_FROM_COURSE_CLASSES_TABLE_SQL, map);
        return classList;
    }


    @Language("MySQL")
    private static final String LIST_TOTAL_EVENT_COUNT_SQL = "SELECT COUNT(id) AS totalEventCount\n" +
                        "FROM event\n" +
                        "WHERE courseId = ?\n" +
                        "\tAND DATE(startDateTime) BETWEEN ? AND ?";

    public Integer getTotalEventCount() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, courseId);
        map.put(2, getStartDate());
        map.put(3, getEndDate());
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(LIST_TOTAL_EVENT_COUNT_SQL, map);
        totalEventCount = (int) (long) (Long) result.get(0).get("totalEventCount");
        return totalEventCount;
    }


    @Language("MySQL")
    private static final String LIST_TOTAL_STUDENT_COUNT_SQL = "SELECT COUNT(student.id) AS totalStudentCount\n" +
                        "FROM courseClass, student\n" +
                        "WHERE courseClass.courseId = ?\n" +
                        "\tAND student.classId = courseClass.classId";


    public Integer getTotalStudentCount() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, courseId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(LIST_TOTAL_STUDENT_COUNT_SQL, map);
        totalStudentCount = (int) (long) (Long) result.get(0).get("totalStudentCount");
        return totalStudentCount;
    }


    @Language("MySQL")
    private static final String LIST_STUDENT_SQL = "SELECT student.id, student.studentNo, \n" +
                "\tstudent.studentName,\n" +
                "\t(SELECT COUNT(record.id) \n" +
                "\t\tFROM record, event\n" +
                "\t\tWHERE record.studentId = student.id\n" +
                "\t\t\tAND record.eventId = event.id\n" +
                "\t\t\tAND DATE(startDateTime) BETWEEN ? AND ?\n" +
                "\t\t\tAND event.courseId = course.id) AS checkInCount\n" +
                "FROM courseClass, student, course\n" +
                "WHERE course.id = ?\n" +
                "\tAND courseClass.courseId = course.id\n" +
                "\tAND student.classId = courseClass.classId\n" +
                "ORDER BY studentNo";

    public List<Map<String, Object>> getStudentList() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, getStartDate());
        map.put(2, getEndDate());
        map.put(3, courseId);
        studentList = SqlUtils.executeSqlQuery(LIST_STUDENT_SQL, map);
        return studentList;
    }

    @Language("MySQL")
    private static final String LIST_EVENT_SQL = "SELECT event.id, DATE(event.startDateTime) AS startDateTime, type.name AS typeName,\n" +
            "\t(SELECT COUNT(*) FROM record WHERE record.eventId = event.id) AS checkInCount\n" +
            "FROM event, type\n" +
            "WHERE courseId = ?\n" +
            "\tAND event.typeId = type.id\n" +
            "\tAND DATE(event.startDateTime) BETWEEN ? AND ? \n" +
            "ORDER BY event.startDateTime DESC";

    public List<Map<String, Object>> getEventList() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, courseId);
        map.put(2, getStartDate());
        map.put(3, getEndDate());
        eventList = SqlUtils.executeSqlQuery(LIST_EVENT_SQL, map);
        return eventList;
    }
}
