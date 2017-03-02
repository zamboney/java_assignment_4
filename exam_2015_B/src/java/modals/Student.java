/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modals;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ritzhaki
 */
public class Student implements Serializable{
    
    String id;
    String fName;
    String lName;

    public List<String> getSimesters() {
        return simesters;
    }

    public void setSimesters(List<String> simesters) {
        this.simesters = simesters;
    }
    List<String> simesters;
    

    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
    
}
