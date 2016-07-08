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

/**
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

    public String getStartDate() {
        startDate = Faces.getRequestParameter("start");
        if (startDate == null || startDate == "")
            startDate = "1970-01-01";
        return startDate;
    }

    public String getEndDate() {
        endDate = Faces.getRequestParameter("end");
        if (endDate == null || endDate == "") {
            endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        return endDate;
    }

    public String getCourseName() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, courseId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery("SELECT courseName " +
                        "FROM course WHERE id = ?"
                , map);
        courseName = (String) result.get(0).get("courseName");
        return courseName;
    }

    public List<Map<String, Object>> getClassList() throws Exception{
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, courseId);
        classList = SqlUtils.executeSqlQuery("SELECT class.classNo\n" +
                "FROM class, courseClass\n" +
                "WHERE courseClass.courseId = ? AND  class.id = courseClass.classId", map);
        return classList;
    }

    public Integer getTotalEventCount() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, courseId);
        map.put(2, getStartDate());
        map.put(3, getEndDate());
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery("SELECT COUNT(id) AS totalEventCount\n" +
                        "FROM event\n" +
                        "WHERE courseId = ?\n" +
                        "\tAND DATE(startDateTime) BETWEEN ? AND ?"
                , map);
        totalEventCount = (int) (long) (Long) result.get(0).get("totalEventCount");
        return totalEventCount;
    }
    public Integer getTotalStudentCount() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, courseId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery("SELECT COUNT(student.id) AS totalStudentCount\n" +
                        "FROM courseClass, student\n" +
                        "WHERE courseClass.courseId = ?\n" +
                        "\tAND student.classId = courseClass.classId"
                , map);
        totalStudentCount = (int) (long) (Long) result.get(0).get("totalStudentCount");
        return totalStudentCount;
    }

    public List<Map<String, Object>> getStudentList() throws Exception{
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, getStartDate());
        map.put(2, getEndDate());
        map.put(3, courseId);
        studentList = SqlUtils.executeSqlQuery("SELECT student.id, student.studentNo, \n" +
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
                "ORDER BY studentNo", map);
        return studentList;
    }
}
