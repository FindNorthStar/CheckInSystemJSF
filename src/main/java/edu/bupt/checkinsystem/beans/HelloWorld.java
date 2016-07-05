package edu.bupt.checkinsystem.beans;

import edu.bupt.checkinsystem.entities.TypeEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.*;

@ManagedBean(name="helloWorld")
@SessionScoped
public class HelloWorld implements Serializable{

    private static final long serialVersionUID = -6913972022251814607L;

    private String s1 = "Hello World!!";

    private Connection newConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://10.125.103.123:3306/sunyi", "sunyi", "sunyi");
    }

    public String getS1() throws Exception {
        try {
            Connection conn = this.newConnection();
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

    public void setS1(String s1) {
        this.s1 = s1;
    }

}