package edu.bupt.checkinsystem.beans;

import edu.bupt.checkinsystem.Globals;
import edu.bupt.checkinsystem.util.NetUtils;
import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.convert.IntegerConverter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "checkin")
@RequestScoped // TODO:
public class CheckIn implements Serializable {

    private List<Map<String, Object>> records = null;
    private Integer studentId = null;

    @PostConstruct
    private void init() {
        try {
            if (NetUtils.getMacAddress() == null) {
                Faces.redirect("ban");
            }

            if (Globals.currentEventId == null) {
                Faces.redirect("redirect?message=%s&buttonText=%s&uri=%s",
                        "当前不是签到时间,<br>请联系管理员或按以下按钮重试",
                        "重试",
                        "checkin");
                return;
            }

            if (!isRegistered(getStudentId())) {
                // Student not registered.
                Faces.redirect("register");
                return;
            }

            if (!isAllowedToCheckInNow()) {
                // Student registered, but his class is not allowed to attend the course
                Faces.redirect("redirect?message=%s&buttonText=%s&uri=%s",
                        "当前不是签到时间,<br>请联系管理员或按以下按钮重试",
                        "重试",
                        "checkin");
                return;
            }

            // Student already registered.
            if (!isCheckedIn()) {
                // Student not checked in.
                checkIn();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Language("MySQL")
    private static final String GET_CLASS_EXISTS_SQL = "SELECT DISTINCT class.id AS classId\n" +
            "FROM class\n" +
            "WHERE class.id IN (\n" +
            "\tSELECT student.classId \n" +
            "\tFROM student \n" +
            "\tWHERE id = ?)\n" +
            "AND class.id IN (\n" +
            "\tSELECT courseClass.classId \n" +
            "\tFROM courseClass, event\n" +
            "\tWHERE courseClass.courseId = event.courseId\n" +
            "    AND event.id = ?)";
    private boolean isAllowedToCheckInNow() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, getStudentId());
        map.put(2, Globals.currentEventId);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(GET_CLASS_EXISTS_SQL, map);
        return !result.isEmpty();
    }

    private boolean isRegistered(Integer studentId) throws Exception {
        return studentId != null;
    }


    @Language("MySQL")
    private static final String LIST_STUDENT_ID_SQL = "SELECT id FROM student WHERE macAddress = ?";

    private Integer getStudentId() throws Exception {
        if (studentId == null) {
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, NetUtils.getMacAddress());

            List<Map<String, Object>> resultList = SqlUtils.executeSqlQuery(LIST_STUDENT_ID_SQL, map);

            if (!resultList.isEmpty()) {
                studentId = (Integer) resultList.get(0).get("id");
            }
        }
        return studentId;
    }


    @Language("MySQL")
    private static final String LIST_RECORD_ID_VERIFY_CHECK_IN_SAL = "SELECT id FROM record WHERE (eventId = ? AND studentId = ?)";

    private boolean isCheckedIn() throws Exception {
        Map<Integer, Object> param = new HashMap<Integer, Object>();
        param.put(1, Globals.currentEventId);
        param.put(2, studentId);
        return !SqlUtils.executeSqlQuery(LIST_RECORD_ID_VERIFY_CHECK_IN_SAL, param).isEmpty();
    }


    @Language("MySQL")
    private static final String INSERT_CHECK_IN_RECORD_SQL = "INSERT INTO record(studentId, eventId) VALUE (?, ?)";

    private void checkIn() throws Exception {

        Map<Integer, Object> param = new HashMap<Integer, Object>();
        param.put(1, studentId);
        param.put(2, Globals.currentEventId);

        SqlUtils.executeSqlUpdate(INSERT_CHECK_IN_RECORD_SQL, param);
    }


    @Language("MySQL")
    private static final String LIST_RECORD_BY_COURSE_TYPE_EVENT_STUDENT_SQL =
            "SELECT c.courseName AS courseName, DATE_FORMAT(r.checkDateTime, '%m-%d %H:%i') AS checkDateTime, t.name AS typeName\n" +
            "FROM course AS c, event AS e, record AS r, type AS t\n" +
            "WHERE e.courseId = c.id AND e.typeId = t.id AND r.eventId = e.id AND r.studentId = ?\n" +
            "ORDER BY r.checkDateTime DESC\n" +
            "LIMIT 5\n";

    public List<Map<String, Object>> getRecords() throws Exception {
        if (records == null) {
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, studentId);

            records = SqlUtils.executeSqlQuery(LIST_RECORD_BY_COURSE_TYPE_EVENT_STUDENT_SQL, map);
        }

        return records;
    }


    @Language("MySQL")
    private static final String LIST_STUDENT_NAME_SQL = "SELECT studentName FROM student WHERE id = ?";

    public String getName() throws Exception {
//        try {
            Map<Integer, Object> param = new HashMap<Integer, Object>();
            param.put(1, studentId);
            List<Map<String, Object>> maps = SqlUtils.executeSqlQuery(LIST_STUDENT_NAME_SQL, param);
            return (String) maps.get(0).get("studentName");
//        }
//        catch (Exception e) {
//            return "孙艺";
//        }
    }
}