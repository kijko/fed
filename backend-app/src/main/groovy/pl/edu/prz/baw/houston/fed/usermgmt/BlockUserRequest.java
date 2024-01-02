package pl.edu.prz.baw.houston.fed.usermgmt;

class BlockUserRequest {

    private boolean blocked;

    public BlockUserRequest() {
    }

    BlockUserRequest(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
