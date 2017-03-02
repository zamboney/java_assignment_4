/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modals;

import java.io.Serializable;

/**
 *
 * @author ritzhaki
 */
public class Score implements Serializable {

    private String ID;
    private String COURSE_NUMBER;
    private String COURSE_NAME;
    private int POINTS;
    private int GRADE;
    private int SIMESTER;
    private String STUDENT_ID;

    public Score() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCOURSE_NUMBER() {
        return COURSE_NUMBER;
    }

    public void setCOURSE_NUMBER(String COURSE_NUMBER) {
        this.COURSE_NUMBER = COURSE_NUMBER;
    }

    public String getCOURSE_NAME() {
        return COURSE_NAME;
    }

    public void setCOURSE_NAME(String COURSE_NAME) {
        this.COURSE_NAME = COURSE_NAME;
    }

    public int getPOINTS() {
        return POINTS;
    }

    public void setPOINTS(int POINTS) {
        this.POINTS = POINTS;
    }

    public int getGRADE() {
        return GRADE;
    }

    public void setGRADE(int GRADE) {
        this.GRADE = GRADE;
    }

    public int getSIMESTER() {
        return SIMESTER;
    }

    public void setSIMESTER(int SIMESTER) {
        this.SIMESTER = SIMESTER;
    }

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }
}
