package edu.bupt.checkinsystem.beans.backend;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;
import java.io.Serializable;

/**
 * Created by Alexander Qi on 7/10/16.
 */
@ManagedBean(name = "backend_student_import")
@RequestScoped
public class StudentImport implements Serializable {
    private Part excelFile;

    public Part getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(Part excelFile) {
        this.excelFile = excelFile;
    }

    public void submit() throws Exception {
        throw new Exception(this.excelFile.getSubmittedFileName());
    }
}
