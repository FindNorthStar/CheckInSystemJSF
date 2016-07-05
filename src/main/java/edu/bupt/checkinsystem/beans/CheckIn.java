package edu.bupt.checkinsystem.beans;

import edu.bupt.checkinsystem.util.IpUtils;
import edu.bupt.checkinsystem.util.SqlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name="checkin")
@SessionScoped // TODO:
public class CheckIn implements Serializable {
    public String getName() {
        try {
            Map<Integer, Object> param = new HashMap<Integer, Object>();
            param.put(1, IpUtils.getMacAddress());
            List<Map<String, Object>> maps = SqlUtils.executeSqlQuery("SELECT * FROM student WHERE macAddress = ?", param);
            return (String) maps.get(0).get("studentName");
        } catch(Exception e){
            return "孙艺";
        }
    }
}