package edu.bupt.checkinsystem.beans;

import edu.bupt.checkinsystem.util.IpUtils;
import edu.bupt.checkinsystem.util.MacUtils;
import edu.bupt.checkinsystem.util.SqlUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@ManagedBean(name = "register")
@SessionScoped // TODO:
public class Register implements Serializable {

    private String studentNo;
    private String studentName;

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
        map.put(1, IpUtils.getMacAddress());
        map.put(2, getStudentNo());

        SqlUtils.executeSqlUpdate("UPDATE student SET macAddress = ? WHERE studentNo = ?", map);
    }
}