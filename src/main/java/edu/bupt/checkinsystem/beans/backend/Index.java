package edu.bupt.checkinsystem.beans.backend;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.bupt.checkinsystem.Globals;
import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;

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
    @Language("MySQL")
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

    @Language("MySQL")
    private static final String LIST_ALL_COURSE_TYPE_SQL = "SELECT id, name FROM type";

    private List<Map<String, Object>> resultList = null;
    private Map<String , Map<String, String>> courseClassesTeachers = null;

    private List<SelectItem> typeList = null;
    private List<SelectItem> courseList = null;

    private String courseJsonObjectContainClassesTeachersObject = null;

    private String selectedCourseId = "-1";
    private String selectedTypeId = "1";

    @PostConstruct
    private void init() {
        try {
            resultList = SqlUtils.executeSqlQuery(LIST_ALL_COURSES_SQL);
            getCourseClassesTeachers();
            if (Globals.currentEventId != null) {
                initCurrentSelectedItems();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Language("MySQL")
    private static final String GET_CURRENT_SELECTED_ITEMS_SQL = "SELECT courseId, typeId FROM event WHERE id = ?";
    public void initCurrentSelectedItems() throws Exception {
        Map<Integer, Object> args = new HashMap<Integer, Object>();
        args.put(1, Globals.currentEventId);
        List<Map<String, Object>> results = SqlUtils.executeSqlQuery(GET_CURRENT_SELECTED_ITEMS_SQL, args);
        Map<String, Object> result = results.get(0);
        selectedCourseId = String.valueOf(result.get("courseId"));
        selectedTypeId = String.valueOf(result.get("typeId"));
    }

    @Language("MySQL")
    private static final String NEW_EVENT_SQL = "INSERT INTO event (courseId, typeId) VALUES (?, ?)";
    public void submit() throws Exception {
        // The submit button action listener
        if (Globals.currentEventId == null) {
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, Integer.valueOf(selectedCourseId));
            map.put(2, Integer.valueOf(selectedTypeId));
            List<Map<String, Object>> result = SqlUtils.executeSqlInsertAndGetIt(NEW_EVENT_SQL, map);
            Globals.currentEventId = Integer.valueOf(String.valueOf(result.get(0).get("GENERATED_KEY")));
        } else {
            Globals.currentEventId = null;
        }
    }

    public boolean getDisabled() {
        return Globals.currentEventId != null;
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


    public String getCourseJsonObjectContainClassesTeachersObject() {
        if (courseJsonObjectContainClassesTeachersObject == null) {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            courseJsonObjectContainClassesTeachersObject = gson.toJson(
                    courseClassesTeachers, new TypeToken<Map<String, Map<String, String>>>(){}.getType());
        }

        return courseJsonObjectContainClassesTeachersObject;
    }

    public String getSelectedCourseId() {
        return selectedCourseId;
    }

    public void setSelectedCourseId(String selectedCourseId) {
        this.selectedCourseId = selectedCourseId;
    }

    public String getSelectedTypeId() {
        return selectedTypeId;
    }

    public void setSelectedTypeId(String selectedTypeId) {
        this.selectedTypeId = selectedTypeId;
    }

    private Map<String, Map<String, String>> getCourseClassesTeachers() {
        if (courseClassesTeachers == null) {
            courseClassesTeachers = new HashMap<String, Map<String, String>>();

            for (Map<String, Object> column
                    : resultList) {

                Map<String, String> classesTeacher = new HashMap<String, String>();
                classesTeacher.put("classes", String.valueOf(column.get("classNumbers")));
                classesTeacher.put("teachers", String.valueOf(column.get("teachers")));

                courseClassesTeachers.put(String.valueOf(column.get("id")), classesTeacher);
            }
        }

        return courseClassesTeachers;
    }
}
