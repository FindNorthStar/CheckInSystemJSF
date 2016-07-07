package edu.bupt.checkinsystem.beans.backend;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.bupt.checkinsystem.util.SqlUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the Index class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/5 14:42
 */

@ManagedBean(name = "backend_index")
@RequestScoped
public class Index implements Serializable {

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
                    "    course.courseName;";

    private static final String LIST_ALL_COURSE_TYPE_SQL = "SELECT name FROM type";

    private List<Map<String, Object>> resultList = null;
    private Map<String, String> courseClasses = null;
    private List<String> typeList = null;
    private String courseClassJsonArray = null;
    private List<String> courseList = null;

    private String selectedCourseName = null;
    private String selectedTypeName;

    @PostConstruct
    private void init() {
        try {
            resultList = SqlUtils.executeSqlQuery(LIST_ALL_COURSES_SQL);
            getCourseClasses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<String> getCourseList() throws Exception {
        if (courseList == null) {
            courseList = new ArrayList<String>();
            for (Map<String, Object> column
                    : resultList) {
                courseList.add(column.get("courseName").toString());
            }
        }

        return courseList;
    }

    public Map<String, String> getCourseClasses() {
        if (courseClasses == null) {

            courseClasses = new HashMap<String, String>();

            for (Map<String, Object> column
                    : resultList) {

                courseClasses.put(
                        String.valueOf(column.get("courseName")),
                        String.valueOf(column.get("classNumbers"))
                );

            }
        }

        return courseClasses;
    }


    public List<String> getTypeList() throws Exception {
        if (typeList == null) {
            typeList = new ArrayList<String>();

            List<Map<String, Object>> result = SqlUtils.executeSqlQuery(Index.LIST_ALL_COURSE_TYPE_SQL);

            for (Map<String, Object> column
                    : result) {
                typeList.add(String.valueOf(column.get("name")));
            }
        }

        return typeList;
    }


    public String getCourseClassJsonObject() {
        if (courseClassJsonArray == null) {
            getCourseClasses();
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            courseClassJsonArray = gson.toJson(courseClasses);
        }

        return courseClassJsonArray;
    }

    public String getSelectedCourseName() {
        return selectedCourseName;
    }

    public void setSelectedCourseName(String selectedCourseName) {
        this.selectedCourseName = selectedCourseName;
    }

    public String getSelectedTypeName() {
        return selectedTypeName;
    }

    public void setSelectedTypeName(String selectedTypeName) {
        this.selectedTypeName = selectedTypeName;
    }
}
