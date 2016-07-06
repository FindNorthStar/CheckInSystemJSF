package edu.bupt.checkinsystem.beans;

import edu.bupt.checkinsystem.Globals;
import edu.bupt.checkinsystem.util.IpUtils;
import edu.bupt.checkinsystem.util.SqlUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "checkin")
@SessionScoped // TODO:
public class CheckIn implements Serializable {

    private List<Map<String, Object>> records = null;
    private String studentId = null;

    @PostConstruct
    private void init() {
        try {
            if (!isRegistered(getStudentId())) {
                // Student not registered.
                FacesContext.getCurrentInstance().getExternalContext().redirect("register");
            }
            else {
                // Student already registered.

                if (isCheckedIn()) {
                    // Student already checked in.

                }
                else {
                    // Student not checked in.
                    checkIn();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean isRegistered(String studentId) throws Exception {
        return studentId != null;
    }


    private String getStudentId() throws Exception {
        if (studentId == null) {
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, IpUtils.getMacAddress());

            List<Map<String, Object>> resultList = SqlUtils.executeSqlQuery(
                    "SELECT id FROM student WHERE macAddress = ?", map);

            if (resultList != null) {
                studentId = String.valueOf(resultList.get(0).get("id"));
            }
        }
        return studentId;
    }


    private boolean isCheckedIn() throws Exception {
        Map<Integer, Object> param = new HashMap<Integer, Object>();
        param.put(1, Globals.currentEventId);
        param.put(2, studentId);
        return !SqlUtils.executeSqlQuery("SELECT id FROM record WHERE (eventId = ? AND studentId = ?)", param).isEmpty();
    }

    private void checkIn() throws Exception {

        Map<Integer, Object> param = new HashMap<Integer, Object>();
        param.put(1, studentId);
        param.put(2, Globals.currentEventId);

        SqlUtils.executeSqlUpdate("INSERT INTO record(studentId, eventId) VALUE (?, ?)", param);
    }


    public List<Map<String, Object>> getRecords() throws Exception {
        if (records == null) {
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, studentId);

            records = SqlUtils.executeSqlQuery(
                    "SELECT c.courseName AS courseName, DATE_FORMAT(r.checkDateTime, \"%m-%d %H:%i\") AS checkDateTime, t.name AS typeName\n" +
                            "FROM course AS c, event AS e, record AS r, type AS t\n" +
                            "WHERE e.courseId = c.id AND e.typeId = t.id AND r.eventId = e.id AND r.studentId = ?\n" +
                            "ORDER BY r.checkDateTime DESC\n" +
                            "LIMIT 5\n",
                    map);
        }

        return records;
    }


    public String getName() {
        try {
            Map<Integer, Object> param = new HashMap<Integer, Object>();
            param.put(1, studentId);
            List<Map<String, Object>> maps = SqlUtils.executeSqlQuery("SELECT studentName FROM student WHERE id = ?", param);
            return (String) maps.get(0).get("studentName");
        } catch (Exception e) {
            return "孙艺";
        }
    }
}