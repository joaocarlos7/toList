package i.tolist.exception;

public class UserNameNotFound extends RuntimeException {
    public UserNameNotFound(String email) {
        super("Incorrect credentials for username " + email);
    }
}
