package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.NetUtils;
import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the StudentModification class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/8 22:54
 */

@ManagedBean(name = "backend_student_modification")
@RequestScoped
public class StudentModification {

    @Language("MySQL")
    private static final String UPDATE_STUDENT_SQL = "UPDATE student " +
            "SET classId = ?, studentNo = ?, studentName = ?, macAddress = ?" +
            "WHERE id = ?";

    @Language("MySQL")
    private static final String LIST_ALL_CLASSES_SQL = "SELECT id, classNo FROM class";

    private String classId;
    private String studentId;

    private List<SelectItem> classesList = null;

    private String studentNumber;
    private String studentName;
    private String selectedClassId;
    private String macAddress;

    @Language("MySQL")
    private static final String GET_STUDENT_INFO_SQL = "SELECT studentName, studentNo, macAddress, classId FROM student WHERE id = ?";
    @PostConstruct
    private void init() {
        try {
            getClassesList();
            classId = Faces.getRequestParameter("classId");
            studentId = Faces.getRequestParameter("studentId");
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, Integer.valueOf(studentId));
            List<Map<String, Object>> results = SqlUtils.executeSqlQuery(GET_STUDENT_INFO_SQL, map);
            Map<String, Object> result = results.get(0);

            selectedClassId = String.valueOf(result.get("classId"));
            macAddress = (String) result.get("macAddress");
            studentName = (String) result.get("studentName");
            studentNumber = (String) result.get("studentNo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void submit() throws Exception {
        Map<Integer, Object> param = new HashMap<Integer, Object>();

        param.put(1, selectedClassId);
        param.put(2, studentNumber);
        param.put(3, studentName);
        param.put(4, macAddress);
        param.put(5, studentId);

        SqlUtils.executeSqlUpdate(UPDATE_STUDENT_SQL, param);

        Faces.redirect("/backend/student-management?classId=" + classId);
    }


    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = NetUtils.stripMacAddress(macAddress);
    }


    public List<SelectItem> getClassesList() throws Exception {
        if (classesList == null) {
            classesList = new ArrayList<SelectItem>();

            List<Map<String, Object>> classesListResult = SqlUtils.executeSqlQuery(LIST_ALL_CLASSES_SQL);

            for (Map<String, Object> column
                    : classesListResult) {
                classesList.add(
                        new SelectItem(column.get("id"), String.valueOf(column.get("classNo"))));
            }
        }

        return classesList;
    }

    public String getSelectedClassId() {
        return selectedClassId;
    }

    public void setSelectedClassId(String selectedClassId) {
        this.selectedClassId = selectedClassId;
    }
}
