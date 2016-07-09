package edu.bupt.checkinsystem.beans;

import edu.bupt.checkinsystem.util.NetUtils;
import edu.bupt.checkinsystem.util.SqlUtils;
import edu.bupt.checkinsystem.util.TextUtils;
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
            if (NetUtils.getMacAddress() == null) { // 非 DHCP
                Faces.redirect("ban");
            }

            if (isRegistered(NetUtils.getMacAddress())) {
                Faces.redirect("redirect?message=%s&buttonText=%s&uri=%s",
                        "您已经注册过了,现在请按以下按钮签到",
                        "签到",
                        "checkin");
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
    private static final String LIST_MAC_ADDRESS_BY_STUDENT_NUMBER_AND_STUDENT_NAME_SQL =
            "SELECT macAddress FROM student WHERE studentNo = ? AND studentName = ?";

    private List<Map<String, Object>> macAddressQueryResult = null;

    private List<Map<String, Object>> getMacAddressQueryResult() throws Exception {
        if (macAddressQueryResult == null) {
            Map<Integer, Object> param = new HashMap<Integer, Object>();
            param.put(1, studentNo);
            param.put(2, studentName);
            macAddressQueryResult = SqlUtils.executeSqlQuery(LIST_MAC_ADDRESS_BY_STUDENT_NUMBER_AND_STUDENT_NAME_SQL, param);
        }

        return macAddressQueryResult;
    }

    private boolean hasStudentInDb() throws Exception {
        return !macAddressQueryResult.isEmpty();
    }

    private boolean isMacAddressExist() {
        Object object = macAddressQueryResult.get(0).get("macAddress");

        return object != null && !TextUtils.isEmpty(String.valueOf(object));
    }


    @Language("MySQL")
    private static final String UPDATE_STUDENT_TO_REGISTER = "UPDATE student SET macAddress = ? WHERE studentNo = ?";

    public void submit() throws Exception {
        // UPDATE student to map macAddress
        getMacAddressQueryResult();

        if (!hasStudentInDb()) {
            Faces.redirect("redirect?message=%s&buttonText=%s&uri=%s",
                    "学号或姓名不正确",
                    "返回",
                    "register");
        } else if (isMacAddressExist()) {
            Faces.redirect("redirect?message=%s&buttonText=%s&uri=%s",
                    "您已经在其他设备注册过了",
                    "返回",
                    "register");
        } else {
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, NetUtils.getMacAddress());
            map.put(2, getStudentNo());

            SqlUtils.executeSqlUpdate(UPDATE_STUDENT_TO_REGISTER, map);
            Faces.redirect("redirect?message=%s&buttonText=%s&uri=%s",
                    "注册成功, 您现在可以签到了",
                    "签到",
                    "checkin");
        }
    }
}