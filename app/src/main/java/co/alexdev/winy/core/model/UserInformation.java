package co.alexdev.winy.core.model;

public class UserInformation {
    private String firstname;
    private String lastname;

    public UserInformation(){}

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
