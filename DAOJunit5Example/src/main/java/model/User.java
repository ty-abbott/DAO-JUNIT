package model;

import java.util.Objects;

public class User {
    /**
     * String username - the username of the user
     * String password - the password of the user
     * String email - the email of the user
     * String firstName - the first name of the user
     * String lastName - the last name of the user
     * String gender - the gender of the user
     * String personID - the personID of the user
     */
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String gender;
    private String personID;

    public User(String username, String password, String email, String firstname, String lastname,
                String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public User values(){
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null|| getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email)
                && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(gender, user.gender)
                && Objects.equals(personID, user.personID);
    }
}



