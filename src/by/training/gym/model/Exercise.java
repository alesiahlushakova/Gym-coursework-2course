package by.training.gym.model;

/**
 * class for exercises.
 * @author AlesyaHlushakova
 */
public class Exercise extends Entity{
    private String name;
    private String restrictions;
    private int caloriesLost;
    private ExerciseLevel level;
    private String description;
    private int weightLoss;
    private int setsCount;
    private int repeatsCount;
    private int dayNumber;
    private int executionNumber;

    /**
     * constructor for a new Exercise.
     */
    public Exercise() {
    }

    /**
     * getter for exercise name.
     * @return the exercise name.
     */
    public String getName() {
        return name;
    }

    /**
     * setter for exercise name.
     * @param name the exercise name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for exercise's difficulty level.
     * @return the exercise's difficulty level.
     */
    public ExerciseLevel getLevel() {
        return level;
    }

    /**
     * setter for exercise's restrictions.
     * @param restrictions the exercise's restrictions.
     */
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * getter for exercise's restrictions.
     * @return the exercise's restrictions.
     */
    public String getRestrictions() {
        return restrictions;
    }

    /**
     * setter for exercise's difficulty level.
     * @param level the exercise's difficulty level.
     */
    public void setLevel(ExerciseLevel level) {
        this.level = level;
    }
    /**
     * getter for calories lost.
     * @return the calories lost.
     */
    public int getCaloriesLost() {
        return caloriesLost;
    }

    /**
     * setter for calories lost.
     * @param calories the calories lost.
     */
    public void setCaloriesLost(int calories) {
        this.caloriesLost = calories;
    }
    /**
     * getter for exercise's description.
     * @return the exercise's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for exercise's description.
     * @param description the exercise's description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter for sets count of exercise.
     * @return the sets count.
     */
    public int getSetsCount() {
        return setsCount;
    }

    /**
     * setter for count of exercise's sets.
     * @param setsCount the exercise's sets count.
     */
    public void setSets(int setsCount) {
        this.setsCount = setsCount;
    }

    /**
     * getter for weight loss.
     * @return the weight loss.
     */
    public int getWeightLoss() {
        return weightLoss;
    }

    /**
     * setter for weight loss.
     * @param weight weight loss.
     */
    public void setWeightLoss(int weight) {
        this.weightLoss = weight;
    }

    /**
     * getter for repeats count of exercise.
     * @return the repeats count.
     */
    public int getRepeatsCount() {
        return repeatsCount;
    }

    /**
     * setter for repeats count of exercise.
     * @param repeatsCount the repeats count of exercise.
     */
    public void setRepeats(int repeatsCount) {
        this.repeatsCount = repeatsCount;
    }

    /**
     * getter for day number.
     * @return the day number.
     */
    public int getDayNumber() {
        return dayNumber;
    }

    /**
     * setter for the day number.
     * @param dayNumber the day number.
     */
    public void setDays(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    /**
     * getter for execution number.
     * @return the execution number.
     */
    public int getExecutionNumber() {
        return executionNumber;
    }

    /**
     * setter for the execution number.
     * @param executionNumber the execution number.
     */
    public void setExecutionNumber(int executionNumber) {
        this.executionNumber = executionNumber;
    }

    /**
     * method overrides equals.
     * @param object the object.
     * @return true if objects are equal and false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }

        Exercise exercise = (Exercise) object;

        if (setsCount != exercise.setsCount) {
            return false;
        }
        if (repeatsCount != exercise.repeatsCount) {
            return false;
        }
        if (dayNumber != exercise.dayNumber) {
            return false;
        }
        if (executionNumber != exercise.executionNumber) {
            return false;
        }
        if (name != null ? !name.equals(exercise.name) : exercise.name != null) {
            return false;
        }
        if (level != exercise.level) {
            return false;
        }
        return description != null ? description.equals(exercise.description) : exercise.description == null;
    }

    /**
     * This method calculate object's hashcode.
     *
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + setsCount;
        result = 31 * result + repeatsCount;
        result = 31 * result + dayNumber;
        result = 31 * result + executionNumber;
        return result;
    }

    /**
     * This method builds string information about object.
     *
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", description='" + description + '\'' +
                ", setsCount=" + setsCount +
                ", repeatsCount=" + repeatsCount +
                ", dayNumber=" + dayNumber +
                ", executionNumber=" + executionNumber +
                '}';
    }
}
