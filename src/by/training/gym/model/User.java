package by.training.gym.model;

/**
 * class-entity for users.
 * @author AkesyaHlushakova
 */
public class User extends Entity{
    private String login;
    private String password;
    private UserRole userRole;
    private String firstName;
    private String lastName;
    private String telephone;
    /**
     * constructor for a new User.
     */
    public User() {
    }

    /**
     * getter for user's login.
     * @return the user's login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * setter for user's login.
     * @param login the user's login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Getter for user's password.
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for user's password.
     * @param password the user's password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter for user's role.
     * @return the user's role.
     */
    public UserRole getUserRole() {
        return userRole;
    }

    /**
     * setter for user's role.
     * @param userRole the user's role.
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    /**
     * getter for user's first name.
     * @return the user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setter for user's first name.
     * @param firstName the user's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getter for user's last name.
     * @return the user's last name.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * setter for user's phone.
     * @param phone the user's phone.
     */
    public void setTelephone(String phone) {
        this.telephone = phone;
    }

    /**
     * getter for user's last name.
     * @return the user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setter for user's last name.
     * @param lastName the user's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * method overrides equals.
     * @param object the object.
     * @return is equal.
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

        User user = (User) object;

        if (login != null ? !login.
                equals(user.login) : user.login != null) {
            return false;
        }
        if (password != null ? !password.equals(user.
                password) : user.password != null) {
            return false;
        }
        if (userRole != user.userRole) {
            return false;
        }
        if (firstName != null ? !firstName.equals(user.
                firstName) : user.firstName != null) {
            return false;
        }
        return lastName != null ? lastName.equals(user.
                lastName) : user.lastName == null;
    }

    /**
     * method overrides object's hashcode.
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    /**
     * method builds string information about object.
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
