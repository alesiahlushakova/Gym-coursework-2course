package by.training.gym.model;

/**
 * abstract class for entities.
 * @author AlesyaHlushakova
 */
public abstract class Entity {

    private int id;

    /**
     * getter for entity's id.
     * @return the entity's id.
     */
    public int getId() {
        return id;
    }

    /**
     * setter for entity's id.
     * @param id the entity's id.
     */
    public void setId(int id) {
        this.id = id;
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

        Entity entity = (Entity) object;

        return id == entity.id;
    }

    /**
     * method overrides object's hashcode.
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        return 31 * id;
    }

    /**
     * method overrides to string.
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}
