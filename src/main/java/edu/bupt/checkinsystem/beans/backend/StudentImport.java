package edu.bupt.checkinsystem.beans.backend;

import edu.bupt.checkinsystem.util.SqlUtils;
import edu.bupt.checkinsystem.util.TextUtils;
import org.apache.poi.ss.usermodel.*;
import org.intellij.lang.annotations.Language;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Statement.SUCCESS_NO_INFO;

/**
 * Created by Alexander Qi on 7/10/16.
 */
@ManagedBean(name = "backend_student_import")
@RequestScoped
public class StudentImport implements Serializable {
    private Part excelFile;
    private List<List<List<String>>> data = new ArrayList<List<List<String>>>();
    private DataFormatter formatter = new DataFormatter();

    @Language("MySQL")
    private static final String INSERT_STUDENT_SQL =
            "INSERT INTO student (classId, studentNo, studentName) VALUES (?, ?, ?)";

    @Language("MySQL")
    private static final String QUERY_CLASSID_SQL =
            "SELECT id FROM class WHERE classNo = ?";

    @Language("MySQL")
    private static final String CREATE_CLASS_SQL =
            "INSERT INTO class (classNo) VALUES (?)";


    public Part getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(Part excelFile) {
        this.excelFile = excelFile;
    }

    public List<List<List<String>>> getData() { return data; }

    public void setData(List<List<List<String>>> data) { this.data = data; }

    public String getClassId(String classNo) throws Exception {
        Map<Integer, Object> param = new HashMap<Integer, Object>();
        param.put(1, classNo);
        List<Map<String, Object>> result = SqlUtils.executeSqlQuery(QUERY_CLASSID_SQL, param);
        String classId;
        if (!result.isEmpty()) {
            classId = String.valueOf(result.get(0).get("id"));
        } else {
            classId = String.valueOf(SqlUtils.executeSqlInsertAndGetIt(CREATE_CLASS_SQL,param).get(0).get("GENERATED_KEY"));
        }
        return classId;
    }


    public String insertRow(List<String> rowList) throws Exception {
        String classId;
        int insertStatus;
        try {
            classId = getClassId(rowList.get(0));
        } catch (Exception e) {
            return "FAILED_CLASS_UNAVAILABLE";
        }

        Map<Integer, Object> param = new HashMap<Integer, Object>();
        param.put(1, classId);
        param.put(2, rowList.get(1));
        param.put(3, rowList.get(2));

        try {
           insertStatus = SqlUtils.executeSqlUpdate(INSERT_STUDENT_SQL, param);
        } catch(Exception e) {
            return "FAILED_SQL_ERROR";
        }
        if (insertStatus > 0)
            return "SUCCESS";
        else
            return "FAILED_DUPLICATED";

    }

    public void submit() throws Exception {
        try {
            Workbook wb = WorkbookFactory.create(excelFile.getInputStream());
            for (Sheet sheet: wb) {
                List<List<String>> sheetList = new ArrayList<List<String>>();

                invalidRow:
                for (Row row: sheet) {
                    // invalidRow:;
                    List<String> rowList = new ArrayList<String>();

                    for (int cellIndex = 0; cellIndex < 3; cellIndex++) {
                        String cellStr = formatter.formatCellValue(row.getCell(cellIndex));
                        if (TextUtils.isEmpty(cellStr))
                            continue invalidRow;
                        rowList.add(cellStr);
                    }
                    String insertStatus = insertRow(rowList);
                    rowList.add(insertStatus);

                    sheetList.add(rowList);
                }

                data.add(sheetList);
            }
        } catch (Exception e){

            e.printStackTrace();
        }
        //throw new Exception(this.excelFile.getSubmittedFileName());
    }
}
