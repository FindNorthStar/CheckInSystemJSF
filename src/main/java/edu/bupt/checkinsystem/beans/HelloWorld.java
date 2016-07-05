package edu.bupt.checkinsystem.beans;

import edu.bupt.checkinsystem.Config;
import edu.bupt.checkinsystem.entities.TypeEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.*;

@ManagedBean(name="helloWorld")
@SessionScoped
public class HelloWorld implements Serializable{

    private static final long serialVersionUID = -6913972022251814607L;

    public String getS1() throws Exception {
        try {
            Connection conn = Config.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM type");
            ResultSet result = ps.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (result.next()) {
                sb.append(String.valueOf(result.getInt("id")));
                sb.append(" ");
                sb.append(result.getString("name"));
                sb.append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            String a = "fuck";
            throw e;
        }
    }
}