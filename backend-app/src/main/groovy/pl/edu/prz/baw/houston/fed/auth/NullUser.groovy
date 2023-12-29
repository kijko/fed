package pl.edu.prz.baw.houston.fed.auth

class NullUser extends FedUser {
    static final NullUser INSTANCE = new NullUser()

    private NullUser() {
        super(null, false)
    }
}
