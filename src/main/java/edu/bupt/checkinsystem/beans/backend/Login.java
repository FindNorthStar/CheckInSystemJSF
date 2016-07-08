package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.PasswordUtils;
import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@ManagedBean(name = "backend_login")
@RequestScoped
public class Login implements Serializable {
    private String username;
    private String password;
    private boolean rememberMe;
    private static final int ONE_YEAR = 365 * 24 * 60 * 60;

    @PostConstruct
    public void init() {
        if (Faces.getRequestCookie("token") != null && Faces.getRequestCookie("teacherName") != null) {
            try {
                Faces.redirect("/backend/index");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPassword() throws IOException {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void submit() throws Exception {
        int maxAge;
        if (isRememberMe()) {
            maxAge = ONE_YEAR;
        } else {
            maxAge = -1;
        }
        if (getTeacherName() != null) {
            Faces.addResponseCookie("teacherName", getTeacherName(), "/", maxAge);
            Faces.addResponseCookie("token", String.valueOf(new Random().nextLong()), "/", maxAge);
            Faces.redirect("/backend/index");
        } else {
            Faces.redirect("/backend/login-failed");
        }
    }


    @Language("MySQL")
    private static final String LIST_TEACHER_HASH_NAME_SQL = "SELECT hash, teacherName FROM teacher WHERE username = ?";

    private String getTeacherName() throws Exception {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, getUsername());
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(LIST_TEACHER_HASH_NAME_SQL, map);
        if (result.isEmpty()) return null;
        if (!PasswordUtils.checkPassword(result.get(0).get("hash"), getPassword())) return null;
        return (String) result.get(0).get("teacherName");
    }
}
