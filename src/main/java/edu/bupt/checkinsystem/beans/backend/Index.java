package edu.bupt.checkinsystem.beans.backend;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.bupt.checkinsystem.util.SqlUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
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

    private static final String LIST_ALL_COURSE_TYPE_SQL = "SELECT id, name FROM type";

    private List<Map<String, Object>> resultList = null;
    private Map<String, String> courseClasses = null;
    private List<SelectItem> typeList = null;
    private String courseClassJsonArray = null;
    private List<SelectItem> courseList = null;

    private String selectedCourseName = null;
    private String selectedTypeName = "1";

    @PostConstruct
    private void init() {
        try {
            resultList = SqlUtils.executeSqlQuery(LIST_ALL_COURSES_SQL);
            getCourseClasses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<SelectItem> getCourseList() throws Exception {
        if (courseList == null) {
            courseList = new ArrayList<SelectItem>();
            for (Map<String, Object> column
                    : resultList) {
                courseList.add(new SelectItem(String.valueOf(column.get("id")), column.get("courseName").toString()));
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


    public List<SelectItem> getTypeList() throws Exception {
        if (typeList == null) {
            typeList = new ArrayList<SelectItem>();

            List<Map<String, Object>> result = SqlUtils.executeSqlQuery(Index.LIST_ALL_COURSE_TYPE_SQL);

            for (Map<String, Object> column
                    : result) {
                typeList.add(new SelectItem(String.valueOf(column.get("id")), String.valueOf(column.get("name"))));
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
