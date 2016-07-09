package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.PasswordUtils;
import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.faces.bean.ManagedBean;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the TeacherAdd class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/8 23:55
 */

@ManagedBean(name = "backend_teacher_add")
public class TeacherAdd {

    @Language("MySQL")
    private static final String INSERT_TEACHER_SQL = "INSERT INTO teacher (username, hash, teacherName) VALUES (?, ?, ?)";

    private String username;
    private String teacherName;
    private String password;

    public void submit() throws Exception {
        Map<Integer, Object> param = new HashMap<Integer, Object>();
        param.put(1, username);
        param.put(2, PasswordUtils.generateHash(password));
        param.put(3, teacherName);
        SqlUtils.executeSqlUpdate(INSERT_TEACHER_SQL, param);

        Faces.redirect("/backend/teachers");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
