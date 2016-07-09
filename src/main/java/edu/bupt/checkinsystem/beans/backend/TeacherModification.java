package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.PasswordUtils;
import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the TeacherModify class
 * Please put some info here.
 *
 * @author Alexander Qi
 * @since 16/7/9 09:19
 */

@ManagedBean(name = "backend_teacher_modification")
public class TeacherModification {

    private String teacherId;
    private String username;
    private String teacherName;
    private String password;

    @Language("MySQL")
    private static final String GET_TEACHER_INFO_SQL = "SELECT username, teacherName FROM teacher WHERE id = ?";
    @PostConstruct
    private void init() {
        try {
            teacherId = Faces.getRequestParameter("teacherId");
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            map.put(1, Integer.valueOf(teacherId));
            List<Map<String, Object>> results = SqlUtils.executeSqlQuery(GET_TEACHER_INFO_SQL, map);
            Map<String, Object> result = results.get(0);

            username = (String) result.get("username");
            teacherName = (String) result.get("teacherName");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Language("MySQL")
    private static final String UPDATE_TEACHER_BASIC_INFO_SQL = "UPDATE teacher SET username = ?, teacherName = ? WHERE id = ?";
    @Language("MySQL")
    private static final String UPDATE_TEACHER_HASH_SQL = "UPDATE teacher SET hash = ? WHERE id = ?";
    public void submit() throws Exception {
        Map<Integer, Object> basicParam = new HashMap<Integer, Object>();
        basicParam.put(1, username);
        basicParam.put(2, teacherName);
        basicParam.put(3, Integer.valueOf(teacherId));
        SqlUtils.executeSqlUpdate(UPDATE_TEACHER_BASIC_INFO_SQL, basicParam);
        if (!password.isEmpty()) {
            Map<Integer, Object> hashParam = new HashMap<Integer, Object>();
            hashParam.put(1, PasswordUtils.generateHash(password));
            hashParam.put(2, Integer.valueOf(teacherId));
            SqlUtils.executeSqlUpdate(UPDATE_TEACHER_HASH_SQL, hashParam);
        }

        Faces.redirect("/backend/teachers#modified");
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
