package edu.bupt.checkinsystem.beans;

import edu.bupt.checkinsystem.util.NetUtils;
import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ManagedBean(name = "register")
@RequestScoped // TODO:
public class Register implements Serializable {

    private String studentNo;
    private String studentName;

    @PostConstruct
    public void init() {
        try {
            if (NetUtils.getMacAddress() == null) {
                Faces.redirect("ban");
            }

            if (isRegistered(NetUtils.getMacAddress())) {
                Faces.redirect("checkin");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Language("MySQL")
    private static final String LIST_ID_TO_VERIFY_REGISTERED_SQL = "SELECT id FROM student WHERE macAddress = ?";

    private boolean isRegistered(String macAddress) throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, macAddress);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(LIST_ID_TO_VERIFY_REGISTERED_SQL, map);
        return !result.isEmpty();
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Language("MySQL")
    private static final String UPDATE_STUDENT_TO_REGISTER = "UPDATE student SET macAddress = ? WHERE studentNo = ?";

    public void submit() throws Exception {
        // UPDATE student to map macAddress

        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, NetUtils.getMacAddress());
        map.put(2, getStudentNo());

        SqlUtils.executeSqlUpdate(UPDATE_STUDENT_TO_REGISTER, map);
        Faces.redirect("checkin");
    }
}