package edu.bupt.checkinsystem.beans;

import edu.bupt.checkinsystem.Config;
import edu.bupt.checkinsystem.entities.TypeEntity;
import edu.bupt.checkinsystem.util.SqlUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.*;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "helloWorld")
@SessionScoped
public class HelloWorld implements Serializable {

    private static final long serialVersionUID = -6913972022251814607L;

    public String getS1() throws Exception {
        try {
            List<Map<String, Object>> maps = SqlUtils.executeSqlQuery("SELECT * FROM type");
            StringBuilder sb = new StringBuilder();

            for (Map<String, Object> row
                    : maps) {
                Integer integer = (Integer) row.get("id");
                sb.append(integer.toString());
                sb.append(" ");
                sb.append(row.get("name"));
                sb.append("\n");
            }

            return sb.toString();
        } catch (Exception e) {
            String a = "fuck";
            throw e;
        }
    }
}