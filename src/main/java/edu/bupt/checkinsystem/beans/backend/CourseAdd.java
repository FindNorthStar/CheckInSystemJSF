package edu.bupt.checkinsystem.beans.backend;


import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the CourseAdd class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/8 16:37
 */

@ManagedBean(name = "backend_course_add")
@RequestScoped
public class CourseAdd {

    @Language("MySQL")
    private static final String LIST_CLASSES_SQL = "SELECT id, classNo FROM class";
    @Language("MySQL")
    private static final String INSERT_COURSE_SQL = "INSERT INTO course (courseName, teachers) VALUES (?, ?)";
    @Language("MySQL")
    private static final String INSERT_REL_SQL = "INSERT IGNORE INTO courseClass (courseId, classId) VALUES (?, ?)";

    private List<Map<String, Object>> queryResultList = null;
    private List<SelectItem> classes;


    private List<String> selectedClasses = null;
    private String courseName = null;
    private String teachers = null;


    @PostConstruct
    public void init() {
        try {
            getQueryResultList();
            getClasses();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void submit() throws Exception {

        // insert into course
        Map<Integer, Object> courseMap = new HashMap<Integer, Object>();
        courseMap.put(1, courseName);
        courseMap.put(2, teachers);
        List<Map<String, Object>> insertedCourseRow = SqlUtils.executeSqlInsertAndGetIt(INSERT_COURSE_SQL, courseMap);

        // get course id
        BigInteger bigInteger = (BigInteger) insertedCourseRow.get(0).get("GENERATED_KEY");
        Integer courseId = bigInteger.intValue();

        List<Map<Integer, Object>> insertMapList = new ArrayList<Map<Integer, Object>>();

        for (SelectItem clazz : classes) {
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, courseId);
            map.put(2, clazz.getValue());
            if (selectedClasses.contains(String.valueOf(clazz.getValue()))) {
                insertMapList.add(map);
            }
        }

        SqlUtils.executeSqlUpdate(INSERT_REL_SQL, insertMapList);
        Faces.redirect("/backend/courses");
    }


    public List<String> getSelectedClasses() {
        if (selectedClasses == null) {
            selectedClasses = new ArrayList<String>();
        }

        return selectedClasses;
    }

    public void setSelectedClasses(List<String> selectedClasses) {
        this.selectedClasses = selectedClasses;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<SelectItem> getClasses() {
        if (classes == null) {
            classes = new ArrayList<SelectItem>();

            for (Map<String, Object> column
                    : queryResultList) {
                classes.add(new SelectItem(String.valueOf(column.get("id")), String.valueOf(column.get("classNo"))));
            }
        }

        return classes;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    private List<Map<String, Object>> getQueryResultList() throws Exception {
        if (queryResultList == null) {
            queryResultList = SqlUtils.executeSqlQuery(LIST_CLASSES_SQL);
        }

        return queryResultList;
    }
}
