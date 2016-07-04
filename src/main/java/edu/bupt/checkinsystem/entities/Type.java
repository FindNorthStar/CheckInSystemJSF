package edu.bupt.checkinsystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This is the Type class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/4 12:13
 */

@Entity
public class Type {

    @Id
    private int typeNo;
    private String typeName;

    public Type(String typeName) {
        this.typeName = typeName;
    }

    public Type() {
    }

    public int getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(int typeNo) {
        this.typeNo = typeNo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
