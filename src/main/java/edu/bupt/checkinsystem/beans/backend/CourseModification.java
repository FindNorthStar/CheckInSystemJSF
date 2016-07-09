package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import edu.bupt.checkinsystem.util.TextUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "backend_course_modification")
public class CourseModification implements Serializable {
    private String courseId;
    private String courseName;
    private List<String> selectedClasses;

    private List<SelectItem> classes;
    private String teachers;

    @Language("MySQL")
    private static final String LIST_CLASSES_SQL = "SELECT id, classNo FROM class";
    @Language("MySQL")
    private static final String GET_COURSE_INFO_SQL = "SELECT courseName, teachers FROM course WHERE id = ?";

    @PostConstruct
    private void init() {
        try {
            courseId = Faces.getRequestParameter("courseId");
            Map<Integer, Object> param = new HashMap<Integer, Object>();
            param.put(1, Integer.valueOf(courseId));
            List<Map<String, Object>> result = SqlUtils.executeSqlQuery(GET_COURSE_INFO_SQL, param);
            if (result.size() == 0) {
                throw new Exception("No result of course");
            }
            teachers = (String) result.get(0).get("teachers");
            courseName = (String) result.get(0).get("courseName");
        } catch (Exception e) {
            try {
                Faces.redirect("/backend/index");
            } catch (Exception f) {
                //Fuck you!
            }
        }
    }


    public List<SelectItem> getClasses() throws Exception {
        if (classes == null) {
            classes = new ArrayList<SelectItem>();
            List<Map<String, Object>> results = SqlUtils.executeSqlQuery(LIST_CLASSES_SQL);
            for (Map<String, Object> result : results) {
                classes.add(new SelectItem(result.get("id"), (String) result.get("classNo")));
            }
        }
        return classes;

    }


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    @Language("MySQL")
    private static final String GET_COURSE_CLASSES_SQL = "SELECT classId FROM courseClass WHERE courseId = ?";

    public List<String> getSelectedClasses() throws Exception {
        if (selectedClasses == null) {
            selectedClasses = new ArrayList<String>();
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, Integer.valueOf(courseId));
            List<Map<String, Object>> results = SqlUtils.executeSqlQuery(GET_COURSE_CLASSES_SQL, map);
            for (Map<String, Object> result : results) {
                selectedClasses.add(String.valueOf(result.get("classId")));
            }
        }
        return selectedClasses;
    }

    public void setSelectedClasses(List<String> selectedClasses) {
        this.selectedClasses = selectedClasses;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }


    @Language("MySQL")
    private static final String UPDATE_COURSE_SQL = "UPDATE course SET courseName = ?, teachers = ? WHERE id = ?";
    @Language("MySQL")
    private static final String INSERT_REL_SQL = "INSERT IGNORE INTO courseClass (courseId, classId) VALUES (?, ?)";
    @Language("MySQL")
    private static final String DELETE_REL_SQL = "DELETE IGNORE FROM courseClass WHERE courseId = ? AND classId = ?";

    public void submit() throws Exception {

        if (TextUtils.isEmpty(courseName) || TextUtils.isEmpty(teachers)) {
            Faces.redirect("/backend/courses#emptyError");
        } else {

            Map<Integer, Object> courseMap = new HashMap<Integer, Object>();
            courseMap.put(1, courseName);
            courseMap.put(2, teachers);
            courseMap.put(3, Integer.valueOf(courseId));
            SqlUtils.executeSqlUpdate(UPDATE_COURSE_SQL, courseMap);

            List<Map<Integer, Object>> insertMapList = new ArrayList<Map<Integer, Object>>();
            List<Map<Integer, Object>> deleteMapList = new ArrayList<Map<Integer, Object>>();

            for (SelectItem clazz : classes) {
                Map<Integer, Object> map = new HashMap<Integer, Object>();
                map.put(1, Integer.valueOf(courseId));
                map.put(2, clazz.getValue());
                if (selectedClasses.contains(String.valueOf(clazz.getValue()))) {
                    insertMapList.add(map);
                } else {
                    deleteMapList.add(map);
                }
            }

            SqlUtils.executeSqlUpdate(INSERT_REL_SQL, insertMapList);
            SqlUtils.executeSqlUpdate(DELETE_REL_SQL, deleteMapList);
            Faces.redirect("/backend/courses#modified");
        }
    }
}
