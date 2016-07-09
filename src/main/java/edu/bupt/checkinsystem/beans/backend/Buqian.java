package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import org.intellij.lang.annotations.Language;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by wangym5106 on 16-7-9.
 */

@ManagedBean(name = "backend_buqian")
@RequestScoped
public class Buqian implements Serializable {

    private String eventId;
    private String studentId;

    @PostConstruct
    private void init() {
        eventId = Faces.getRequestParameter("eventId");
        studentId = Faces.getRequestParameter("studentId");
    }

    @Language("MySQL")
    private static final String BUQIAN_SQL =
            "INSERT INTO record (studentId, eventId) " +
            "VALUES (?, ?)";

    public void mdzz() throws Exception {
        Map<Integer, Object> param = new HashMap<Integer, Object>();
        param.put(1, Integer.valueOf(studentId));
        param.put(2, Integer.valueOf(eventId));
        SqlUtils.executeSqlUpdate(BUQIAN_SQL, param);
        Faces.redirect(Faces.getRequest().getHeader("Referer"));
    }
}
