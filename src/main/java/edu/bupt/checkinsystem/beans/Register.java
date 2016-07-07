package edu.bupt.checkinsystem.beans;

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


@ManagedBean(name = "register")
@RequestScoped // TODO:
public class Register implements Serializable {

    private String studentNo;
    private String studentName;

    @PostConstruct
    public void init() {
        try {
            if (NetUtils.getMacAddress() == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("ban");
            }

            if (isRegistered(NetUtils.getMacAddress())) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("checkin");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isRegistered(String macAddress) throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, macAddress);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery("SELECT id FROM student WHERE macAddress = ?", map);
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


    public void submit() throws Exception {
        // UPDATE student to map macAddress

        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, NetUtils.getMacAddress());
        map.put(2, getStudentNo());

        SqlUtils.executeSqlUpdate("UPDATE student SET macAddress = ? WHERE studentNo = ?", map);
        FacesContext.getCurrentInstance().getExternalContext().redirect("checkin");
    }
}