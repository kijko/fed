package pl.edu.prz.baw.houston.fed.usermgmt;

class UserRegisterRequest {

    private String login;
    private String password;
    private String firstname;
    private String lastname;

    public UserRegisterRequest() {
    }

    UserRegisterRequest(String login, String password, String firstname, String lastname) {

        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
