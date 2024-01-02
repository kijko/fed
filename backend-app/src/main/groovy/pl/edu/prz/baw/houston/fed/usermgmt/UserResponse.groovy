package pl.edu.prz.baw.houston.fed.usermgmt

class UserResponse {
    private String id
    private String firstname
    private String lastname
    private String type
    private boolean blocked

    UserResponse(String uuid, String firstname, String lastname, String type, boolean blocked) {
        this.blocked = blocked
        this.type = type
        this.lastname = lastname
        this.firstname = firstname
        this.id = uuid
    }

    String getUuid() {
        return id
    }

    void setUuid(String uuid) {
        this.id = uuid
    }

    String getFirstname() {
        return firstname
    }

    void setFirstname(String firstname) {
        this.firstname = firstname
    }

    String getLastname() {
        return lastname
    }

    void setLastname(String lastname) {
        this.lastname = lastname
    }

    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }

    boolean getBlocked() {
        return blocked
    }

    void setBlocked(boolean blocked) {
        this.blocked = blocked
    }
}
