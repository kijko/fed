package pl.edu.prz.baw.houston.fed.auth

abstract class FedUser {
    private String uuid
    private boolean blocked

    FedUser(String uuid, boolean blocked) {
        this.uuid = uuid
        this.blocked = blocked
    }

    String getUuid() {
        return uuid
    }

    void setUuid(String uuid) {
        this.uuid = uuid
    }

    boolean getBlocked() {
        return blocked
    }

    void setBlocked(boolean blocked) {
        this.blocked = blocked
    }
}
