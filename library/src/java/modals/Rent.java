package modals;

import java.io.Serializable;
import java.util.Date;

public class Rent implements Serializable{
    private int id;
    private int bookId;
    private int userId;
    private int dayToReturn;
    private Date startDate;
    private String BookName;
    private int conditionId;
    private double penalty;

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }
    public Rent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDayToReturn() {
        return dayToReturn;
    }

    public void setDayToReturn(int dayToReturn) {
        this.dayToReturn = dayToReturn;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String BookName) {
        this.BookName = BookName;
    }

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }
    
    
}
