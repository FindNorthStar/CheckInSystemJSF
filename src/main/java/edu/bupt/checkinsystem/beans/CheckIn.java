package edu.bupt.checkinsystem.beans;

import edu.bupt.checkinsystem.Globals;
import edu.bupt.checkinsystem.util.NetUtils;
import edu.bupt.checkinsystem.util.SqlUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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
                FacesContext.getCurrentInstance().getExternalContext().redirect("ban");
            }

            if (!isRegistered(getStudentId())) {
                // Student not registered.
                FacesContext.getCurrentInstance().getExternalContext().redirect("register");
            } else {
                // Student already registered.

                if (isCheckedIn()) {
                    // Student already checked in.

                } else {
                    // Student not checked in.
                    checkIn();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean isRegistered(Integer studentId) throws Exception {
        return studentId != null;
    }


    private Integer getStudentId() throws Exception {
        if (studentId == null) {
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, NetUtils.getMacAddress());

            List<Map<String, Object>> resultList = SqlUtils.executeSqlQuery(
                    "SELECT id FROM student WHERE macAddress = ?", map);

            if (!resultList.isEmpty()) {
                studentId = (Integer) resultList.get(0).get("id");
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