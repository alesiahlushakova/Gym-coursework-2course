package by.training.gym.model;

import java.util.Date;

/**
 * @author Alesya Hlushakova.
 * entity for program.
 */
public class Program extends Entity{
    private int programId;
    private int clientId;
    private int coachId;
    private Date startDate;
    private Date endDate;
    private String diet;
    /**
     * constructor for program.
     */
    public Program() {
    }

    /**
     * getter for client id.
     * @return program's client id.
     */
    public int getClientId() {
        return this.clientId;
    }
    /**
     * setter for client id.
     * @param id client id
     */
    public void setClientId(int id) {
        this.clientId = id;
    }
    /**
     * setter for coach id.
     * @param id coach id
     */
    public void setCoachId(int id) {
        this.coachId = id;
    }
    /**
     * getter for coach id.
     * @return program's coach id.
     */
    public int getCoachId() {
        return this.coachId;
    }
    /**
     * getter for start date.
     * @return program's start date.
     */
    public Date getStartDate() {
        return this.startDate;
    }
    /**
     * setter for start date.
     * @param date start date
     */
    public void setStartDate(Date date) {
        this.startDate = date;
    }
    /**
     * getter for end date.
     * @return program's end date.
     */
    public Date getEndDate() {
        return this.endDate;
    }
    /**
     * setter for start date.
     * @param date start date
     */
    public void setEndDate(Date date) {
        this.endDate = date;
    }
    /**
     * gets program's diet.
     * @return the diet.
     */
    public String getDiet() {
        return diet;
    }

    /**
     * sets program's diet.
     * @param diet the diet.
     */
    public void setDiet(String diet) {
        this.diet = diet;
    }
    /**
     * method overrides hashcode.
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + clientId;
        result = 31 * result + coachId;
        result = 31 * result + clientId;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (diet != null ? diet.hashCode() : 0);
        return result;
    }

    /**
     * method builds string information about object.
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "TrainingProgram{" +
                ", personalTrainerId=" + coachId +
                ", clientId=" + clientId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", diet='" + diet + '\'' +
                '}';
    }
}
