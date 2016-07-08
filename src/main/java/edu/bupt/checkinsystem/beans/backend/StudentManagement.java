package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by wangym5106 on 16-7-7.
 */
@ManagedBean(name = "backend_student_management")
@RequestScoped
public class StudentManagement implements Serializable {

    private Integer classId = 0;
    private String classNo = null;
    private Integer studentCount = 0;
    private List<Map<String, Object>> courseList = null;
    private List<Map<String, Object>> studentList = null;

    @PostConstruct
    private void init() {
        try {
            classId = Integer.valueOf(Faces.getRequestParameter("classId"));
            classNo = getClassNo();
        } catch (Exception e) {
            try {
                Faces.redirect("/backend/index");
            } catch (Exception f) {
                //Fuck you!
            }

        }
    }

    public Integer getClassId() {
        return classId;
    }

    public String getClassNo() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, classId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery("SELECT classNo " +
                        "FROM class WHERE id = ?"
                , map);
        classNo = (String) result.get(0).get("classNo");
        return classNo;
    }

    public Integer getStudentCount() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, classId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery("SELECT COUNT(*) AS studentCount " +
                "FROM student WHERE classId = ?"
                , map);
        studentCount = (int) (long) (Long) result.get(0).get("studentCount");
        return studentCount;
    }

    public List<Map<String, Object>> getCourseList() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, classId);
        courseList = SqlUtils.executeSqlQuery("SELECT course.id AS courseId, course.courseName AS courseName \n" +
                "FROM course, courseClass\n" +
                "WHERE course.id = courseClass.courseId AND courseClass.classId = ?\n" +
                "ORDER BY course.id", map);
        return courseList;
    }

    public List<Map<String, Object>> getStudentList() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, classId);
        studentList = SqlUtils.executeSqlQuery("SELECT id, studentNo, studentName, macAddress\n" +
                "FROM student\n" +
                "WHERE classId = ?\n" +
                "ORDER BY studentNo", map);
        return studentList;
    }



}
